package com.example.smartbusdriver.ui.routeList

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.smartbusdriver.data.service.TracesResponse
import com.example.smartbusdriver.domain.Route
import com.example.smartbusdriver.domain.Trace
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.Error
import com.yandex.runtime.network.NetworkError
import com.yandex.runtime.network.RemoteError

//import com.yandex.mapkit.directions.DirectionsFactory
//import com.yandex.mapkit.directions.driving.DrivingOptions
//import com.yandex.mapkit.directions.driving.DrivingRoute
//import com.yandex.mapkit.directions.driving.DrivingRouter
//import com.yandex.mapkit.directions.driving.DrivingSession
//import com.yandex.mapkit.directions.driving.VehicleOptions


internal class RouteRendering(context: Context?) : View(context) {
    var p: Paint = Paint()
    var routePoints: List<Trace> = emptyList()
    var tracePoints: List<Double> = emptyList()
    var trace: FloatArray = floatArrayOf()
    var points: FloatArray = floatArrayOf(300f, 200f, 600f, 200f, 300f, 300f, 600f, 300f, 400f, 100f)

    fun completeList(routePoints: List<TracesResponse>): List<Double> {
        for (i in 0..this.routePoints.size){
//            tracePoints = tracePoints + (routePoints[i].latLng.lat)
//            tracePoints = tracePoints + (routePoints[i].latLng.lng)
        }
        return tracePoints
    }

    fun listToFloat(tracePoints: List<Double>): FloatArray{
        for (i in 0..tracePoints.size){
            trace += tracePoints[i].toFloat()
        }
        return trace
    }

    override fun onDraw(canvas: Canvas) {
        p.color = Color.BLUE
        p.strokeWidth = 10f
        canvas.drawLines(trace, p)
    }


    private var ROUTE_START_LOCATION = Point(0.0, 0.0)
    private var ROUTE_END_LOCATION = Point(0.0, 0.0)

    fun setStart(route: Route){
        ROUTE_START_LOCATION = Point(route.firstStop.latLng.lat, route.firstStop.latLng.lng)
    }

    fun setEnd(route: Route){
        ROUTE_END_LOCATION = Point(route.lastStop.latLng.lat, route.firstStop.latLng.lng)
    }

}

//class DrivingActivity : Activity(), DrivingSession.DrivingRouteListener {
//    private val ROUTE_START_LOCATION = Point(59.959194, 30.407094)
//    private val ROUTE_END_LOCATION = Point(55.733330, 37.587649)
//    private val SCREEN_CENTER = Point(
//        (ROUTE_START_LOCATION.latitude + ROUTE_END_LOCATION.latitude) / 2,
//        (ROUTE_START_LOCATION.longitude + ROUTE_END_LOCATION.longitude) / 2
//    )
//    private var mapView: MapView? = null
//    private var mapObjects: MapObjectCollection? = null
//    private var drivingRouter: DrivingRouter? = null
//    private var drivingSession: DrivingSession? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        DirectionsFactory.initialize(this)
//        setContentView(R.layout.driving)
//        super.onCreate(savedInstanceState)
//        mapView = findViewById<MapView>(R.id.mapview)
//        mapView.getMap().move(
//            CameraPosition(
//                SCREEN_CENTER, 5f, 0f, 0f
//            )
//        )
//        drivingRouter = DirectionsFactory.getInstance().createDrivingRouter()
//        mapObjects = mapView.getMap().mapObjects.addCollection()
//        submitRequest()
//    }
//
//    override fun onStop() {
//        mapView!!.onStop()
//        MapKitFactory.getInstance().onStop()
//        super.onStop()
//    }
//
//    override fun onStart() {
//        super.onStart()
//        MapKitFactory.getInstance().onStart()
//        mapView!!.onStart()
//    }
//
//    fun onDrivingRoutes(routes: List<DrivingRoute?>) {
//        for (route in routes) {
//            mapObjects!!.addPolyline(route.getGeometry())
//        }
//    }
//
//    private fun submitRequest() {
//        val drivingOptions = DrivingOptions()
//        val vehicleOptions = VehicleOptions()
//        val requestPoints = ArrayList<RequestPoint>()
//        requestPoints.add(
//            RequestPoint(
//                ROUTE_START_LOCATION,
//                RequestPointType.WAYPOINT,
//                null
//            )
//        )
//        requestPoints.add(
//            RequestPoint(
//                ROUTE_END_LOCATION,
//                RequestPointType.WAYPOINT,
//                null
//            )
//        )
//        drivingSession =
//            drivingRouter.requestRoutes(requestPoints, drivingOptions, vehicleOptions, this)
//    }
//}