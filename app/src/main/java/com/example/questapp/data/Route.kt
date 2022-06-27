package com.example.questapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Route(
    var id: Int, var routePoint: ArrayList<RoutePoint>, var routeTitle: String,
    var routeDescription: String,
    var routePic: String
) : Parcelable {
    fun copy(
        id: Int = this.id, list: ArrayList<RoutePoint> = this.routePoint,
        routeDescription: String = this.routeDescription,
        routePic: String = this.routePic,
        routeTitle: String = this.routeTitle
    ) = Route(
        id, list, routeTitle, routeDescription,
        routePic
    )

    override fun toString(): String {
        return "Route(id=$id, routePoint=${routePoint.toString()}, routeTitle='$routeTitle', " +
                "routeDescription='$routeDescription', routePic='$routePic')"
    }
}