package com.example.smartbusdriver.ui.routeList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.smartbusdriver.R
import com.example.smartbusdriver.data.repository.RoutesRepository
import com.example.smartbusdriver.domain.Route
import com.example.smartbusdriver.domain.TransportType

class RouteAdapter(
    private val onRouteClicked: (Route) -> Unit
) : RecyclerView.Adapter<RouteAdapter.RouteViewHolder>() {

    private var items: List<Route> = emptyList()

    fun updateList(newItems: List<Route>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteViewHolder {
        return RouteViewHolder(
            LayoutInflater
                .from(parent.context).inflate(R.layout.item_route, parent, false),
            onRouteClicked
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RouteViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class RouteViewHolder(itemView: View, private val onRouteClicked: (Route) -> Unit) : RecyclerView.ViewHolder(itemView) {
        fun bind(route: Route) {
            itemView.setOnClickListener {
                onRouteClicked(route)
            }

            var itemRoute: String = ""

            val tType: String = newTransportType(route.transportType)

            itemRoute = itemRoute + tType + " " + route.name + "(" + route.firstStop.name + " - " + route.lastStop.name + ")"

            itemView.findViewById<TextView>(R.id.routeName).text = itemRoute

        }

        fun newTransportType(type: TransportType): String{
            var tType: String = ""
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

}