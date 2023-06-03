package com.example.smartbusdriver.ui.routeList

import android.graphics.Paint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartbusdriver.data.service.TracesResponse
import com.example.smartbusdriver.data.repository.RoutesRepository
import com.example.smartbusdriver.domain.Route
import com.example.smartbusdriver.domain.Trace
import kotlinx.coroutines.launch
import com.example.smartbusdriver.ui.routeList.RouteRendering

class RouteListViewModel(
    private val routesRepository: RoutesRepository
) : ViewModel() {
    private val _routes = MutableLiveData<List<Route>>()
    val routes: LiveData<List<Route>> = _routes

    val _traces = MutableLiveData<List<Trace>>()
    val traces: LiveData<List<Trace>> = _traces
    lateinit var tracePoints: List<Trace>
    lateinit var traceP: List<Trace>

    init {
        viewModelScope.launch {
            _routes.postValue(routesRepository.getAllRoutes())
            //routePoints.postValue(routesRepository.getAllRoutes())
        }
    }

    fun search(id: Int) {
        viewModelScope.launch {
            val result = routesRepository.findRouteByName(id)
            _routes.postValue(result)
        }
    }

    fun drawRoute(id: Int){
        viewModelScope.launch {
            val traces = routesRepository.getTrace(id)
            println(traces)
            _traces.value = traces
//            tracePoints = traces
        }
    }
}