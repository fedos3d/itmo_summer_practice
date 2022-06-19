package com.example.questapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Route(var list: ArrayList<RoutePoint>, var routeDescription: String): Parcelable{
    fun copy(list: ArrayList<RoutePoint> = this.list, routeDescription: String = this.routeDescription) = Route(list, routeDescription)
    override fun toString(): String {
        return routeDescription
    }
}