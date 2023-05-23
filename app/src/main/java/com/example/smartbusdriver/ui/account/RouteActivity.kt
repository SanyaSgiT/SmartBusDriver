package com.example.smartbusdriver.ui.account

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.smartbusdriver.MapActivity
import com.example.smartbusdriver.R
import com.example.smartbusdriver.data.api.DriverApi
import com.example.smartbusdriver.data.service.LinkToRouteRequest
import com.example.smartbusdriver.domain.Route
import com.example.smartbusdriver.domain.TransportType
import com.example.smartbusdriver.ui.routeList.RouteAdapter
import com.example.smartbusdriver.ui.routeList.RouteListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
//import com.yandex.mapkit.geometry.Point
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class RouteActivity : AppCompatActivity() {

    private lateinit var recyclerGetter: ImageButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchEditText: EditText
    private lateinit var btn: Button
    lateinit var routeId: Route
    private lateinit var textRoute: TextView
    private val vm: RouteListViewModel by viewModel()
    private lateinit var adapter: RouteAdapter
    var routeNum: String = ""
    var itemRoute: String = ""

    private val driverApi: DriverApi by inject()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route)

        btn = findViewById(R.id.button)

        btn.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                val route = driverApi.linkToRoute(
//                    LinkToRouteRequest(
//                        routeId.id
//                    )
//                )
//                println(routeId.id)
//            }
            startActivity(Intent(this, MapActivity::class.java))
        }
        setupBindings()
        setupUi()
        setupSubscriptions()
    }

    private fun setupBindings() {
        searchEditText = findViewById(R.id.searchEditText)
        recyclerView = findViewById(R.id.recycler)
        recyclerGetter = findViewById(R.id.recyclerGetter)
        textRoute = findViewById(R.id.textRoute)
    }

    private fun setupUi() {
        recyclerView.isVisible = false
        recyclerGetter.setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.arrow_up))

        recyclerGetter.setOnClickListener {
            if (!recyclerView.isVisible) {
                recyclerGetter.setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.arrow_down))
                recyclerView.isVisible = true
            } else if (recyclerView.isVisible) {
                recyclerGetter.setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.arrow_up))
                recyclerView.isVisible = false
            }
        }
        searchEditText.addTextChangedListener {
            it?.let {
                vm.search(Integer.parseInt(it.toString()))
            }
        }
        setupRecycler()
    }

    private fun setupSubscriptions() {
        vm.routes.observe(this) { list ->
            adapter.updateList(list)
        }

        vm.traces.observe(this) { traces ->
            traces?.let {
//                drawTrace(it)
//                mapActivity.drawTrace(it)
            }
        }
    }

    private fun setupRecycler() {
        adapter = RouteAdapter {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Выбран маршрут")
            builder.setMessage("Маршрут номер " + it.id.toString())

            builder.setPositiveButton("OK") { _, _ ->
                Toast.makeText(
                    applicationContext,
                    "OK", Toast.LENGTH_SHORT
                ).show()
            }

            builder.setNegativeButton("Отмена") { _, _ ->
                Toast.makeText(
                    applicationContext,
                    "Отмена", Toast.LENGTH_SHORT
                ).show()
            }
            builder.show()

            val tType: String = newTransportType(it.transportType)
            itemRoute = itemRoute + tType + " " + it.name + "(" + it.firstStop.name + " - " + it.lastStop.name + ")"

            textRoute.text = itemRoute
            recyclerGetter.setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.arrow_up))
            recyclerView.isVisible = false

            routeId = it
            vm.drawRoute(it.id)
            routeNum = it.route
        }
        recyclerView.adapter = adapter
    }

    private fun newTransportType(type: TransportType): String{
        var tType = ""
        if (type == TransportType.BUS){
            tType = "Автобус"
        }else if(type == TransportType.TROLLEY_BUS){
            tType = "Троллейбус"
        }else if(type == TransportType.Minibus){
            tType = "Маршрутка"
        }else if(type == TransportType.TRAM){
            tType = "Трамвай"
        }else if(type == TransportType.MINIBUS){
            tType = "Маршрутка"
        }
        return tType
    }
}