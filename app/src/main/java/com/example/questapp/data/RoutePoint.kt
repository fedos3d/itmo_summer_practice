package com.example.questapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class RoutePoint(var latitude: Double, var longtitude: Double, var text: String) : Parcelable {
    override fun toString(): String {
        return "RoutePoint(latitude=$latitude, longtitude=$longtitude, text='$text')"
    }
}