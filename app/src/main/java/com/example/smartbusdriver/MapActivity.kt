package com.example.smartbusdriver

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.PointF
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.smartbusdriver.data.repository.RoutesRepository
import com.example.smartbusdriver.domain.Trace
import com.example.smartbusdriver.ui.bottombar.InfoActivity
import com.example.smartbusdriver.ui.bottombar.UserActivity
import com.example.smartbusdriver.ui.routeList.RouteAdapter
import com.example.smartbusdriver.ui.routeList.RouteListViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.traffic.TrafficColor
import com.yandex.mapkit.traffic.TrafficLayer
import com.yandex.mapkit.traffic.TrafficLevel
import com.yandex.mapkit.traffic.TrafficListener
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.example.smartbusdriver.ui.account.RouteActivity
import com.yandex.mapkit.geometry.Polyline
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.PolylineMapObject

class MapActivity : AppCompatActivity(), TrafficListener, UserLocationObjectListener {

    private var mapview: MapView? = null
    private var levelText: TextView? = null
    private var levelIcon: ImageButton? = null
    private var trafficLevel: TrafficLevel? = null
    private var trafficFreshness: MapActivity.TrafficFreshness? = null
    private var traffic: TrafficLayer? = null

    private val routeActivity = RouteActivity()

    private lateinit var route: TextView
    private var passengers: TextView? = null
    private lateinit var bottomBar: BottomNavigationView

    private lateinit var routesRepository: RoutesRepository
    private val vm: RouteListViewModel by viewModel()
    private lateinit var adapter: RouteAdapter
    private lateinit var pointsToDraw: List<Point>

    private enum class TrafficFreshness {
        Loading, OK, Expired
    }

    var tvStatusGPS: String? = null
    var tvLocationGPS: String? = null
    private var locationManager: LocationManager? = null
    var sbGPS = StringBuilder()
    private var userLocationLayer: UserLocationLayer? = null
    private lateinit var mapObjects: MapObjectCollection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        mapview = findViewById(R.id.mapview)
        mapview!!.map.move(
            CameraPosition(Point(55.0415, 82.9346), 11.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0.0f), null
        )

        levelText = findViewById<TextView>(R.id.traffic_light_text)
        levelIcon = findViewById<ImageButton>(R.id.traffic_light)
        traffic = MapKitFactory.getInstance().createTrafficLayer(mapview!!.getMapWindow())
        traffic!!.isTrafficVisible = true
        traffic!!.addTrafficListener(this)
        updateLevel()

        requestLocationPermission()
        val mapKit = MapKitFactory.getInstance()
        mapKit.resetLocationManagerToDefault()
        userLocationLayer = mapKit.createUserLocationLayer(mapview!!.getMapWindow())
        userLocationLayer!!.isVisible = true
        userLocationLayer!!.isHeadingEnabled = true
        userLocationLayer!!.setObjectListener(this)

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        mapObjects = mapview!!.map.mapObjects.addCollection()

        setupBindings()
        setupUi()
        setupSubscriptions()
    }

    override fun onStop() {
        mapview?.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapview?.onStart()
    }

    private fun updateLevel() {
        val iconId: Int
        var level: String? = ""
        if (!traffic!!.isTrafficVisible) {
            iconId = R.drawable.rec_grey
        } else if (trafficFreshness == MapActivity.TrafficFreshness.Loading) {
            iconId = R.drawable.rec_violet
        } else if (trafficFreshness == MapActivity.TrafficFreshness.Expired) {
            iconId = R.drawable.rec_blue
        } else if (trafficLevel == null) {  // state is fresh but region has no data
            iconId = R.drawable.rec_grey
        } else {
            when (trafficLevel!!.color) {
                TrafficColor.RED -> iconId = R.drawable.rec_red
                TrafficColor.GREEN -> iconId = R.drawable.rec_green
                TrafficColor.YELLOW -> iconId = R.drawable.rec_yellow
                else -> iconId = R.drawable.rec_grey
            }
            level = Integer.toString(trafficLevel!!.level)
        }
        levelIcon!!.setImageBitmap(BitmapFactory.decodeResource(resources, iconId))
        levelText!!.text = level
    }

    fun onLightClick(view: View?) {
        traffic!!.isTrafficVisible = !traffic!!.isTrafficVisible
        updateLevel()
    }

    fun onClickBack(view: View?) {
        finish()
    }

    override fun onTrafficChanged(trafficLevel: TrafficLevel?) {
        this.trafficLevel = trafficLevel
        trafficFreshness = MapActivity.TrafficFreshness.OK
        updateLevel()
    }

    override fun onTrafficLoading() {
        trafficLevel = null
        trafficFreshness = MapActivity.TrafficFreshness.Loading
        updateLevel()
    }

    override fun onTrafficExpired() {
        trafficLevel = null
        trafficFreshness = MapActivity.TrafficFreshness.Expired
        updateLevel()
    }

    private fun setupBindings() {
        bottomBar = findViewById(R.id.bottomNavigationView)
        passengers = findViewById(R.id.passengers)
        route = findViewById(R.id.route)
    }

    private fun setupUi() {
        bottomBar.selectedItemId = R.id.menu_map
        bottomBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_profile -> {
                    startActivity(Intent(this, UserActivity::class.java))
                    finish()
                    true
                }
                R.id.menu_info -> {
                    startActivity(Intent(this, InfoActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }

//        pointsToDraw = routeActivity.tracesToDraw
//        println(pointsToDraw)
//        println("Отработал pointsToDraw")

        route.text = routeActivity.itemRoute
        println(routeActivity.itemRoute)
        passengers!!.text = "0 пассажиров оплатили онлайн"

//        drawTrace(pointsToDraw)
    }

    private fun setupSubscriptions() {
//        vm.routes.observe(this) { list ->
//            adapter.updateList(list)
//        }
    }

    fun drawTrace(traces: List<Trace>) {

        mapObjects.clear()

        val points = traces.map {
            Point(it.latLng.lat, it.latLng.lng)
        }

        val polyline: PolylineMapObject = mapObjects.addPolyline(Polyline(points))
        polyline.setStrokeColor(Color.BLUE)
        polyline.setZIndex(100.0f)

        for(i in traces.indices){
            if(traces[i].stop != null){
                val stop = mapObjects.addPlacemark(Point(traces[i].latLng.lat, traces[i].latLng.lng))
                stop.opacity = 0.5f
                stop.setIcon(ImageProvider.fromResource(this, R.drawable.stop))
                stop.isDraggable = true
            }
        }
    }

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                "android.permission.ACCESS_FINE_LOCATION"
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf("android.permission.ACCESS_FINE_LOCATION"),
                PERMISSIONS_REQUEST_FINE_LOCATION
            )
        }
    }

    override fun onObjectAdded(userLocationView: UserLocationView) {
        userLocationLayer!!.setAnchor(
            PointF((mapview!!.width * 0.5).toFloat(), (mapview!!.height * 0.5).toFloat()),
            PointF((mapview!!.width * 0.5).toFloat(), (mapview!!.height * 0.83).toFloat())
        )
        userLocationView.arrow.setIcon(
            ImageProvider.fromResource(
                this, R.drawable.user_arrow
            )
        )
        userLocationView.accuracyCircle.fillColor = Color.BLUE and -0x66000001
    }

    override fun onObjectRemoved(view: UserLocationView) {}
    override fun onObjectUpdated(view: UserLocationView, event: ObjectEvent) {}
    companion object {
        const val PERMISSIONS_REQUEST_FINE_LOCATION = 1
    }

    override fun onResume() {
        super.onResume()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        locationManager!!.requestLocationUpdates(
            LocationManager.GPS_PROVIDER, (1000 * 10).toLong(), 10f, locationListener)
    }

    override fun onPause() {
        super.onPause()
        locationManager!!.removeUpdates(locationListener)
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            showLocation(location)
            println(tvLocationGPS)
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            if (provider == LocationManager.GPS_PROVIDER) {
                tvStatusGPS = "Status: $status"
            }
        }
    }

    private fun showLocation(location: Location?) {
        if (location == null) return
        if (location.provider == LocationManager.GPS_PROVIDER) {
            tvLocationGPS = formatLocation(location)
            println(tvLocationGPS)
        }
    }

    private fun formatLocation(location: Location?): String {
        return if (location == null) "" else String.format(
            "Coordinates: lat = %1$.4f, lon = %2$.4f, time = %3\$tF %3\$tT",
            location.latitude, location.longitude, Date(
                location.time
            )
        )
    }
}