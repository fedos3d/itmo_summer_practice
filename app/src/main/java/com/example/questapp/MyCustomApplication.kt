package com.example.questapp

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.questapp.data.RoutePoint
import org.osmdroid.util.GeoPoint

class MyCustomApplication: Application() {
    var state: Boolean = false
    var questId: Int = 0

    override fun onCreate() {
        loadStatus()
        super.onCreate()
    }

    fun saveStatus() {
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putBoolean("state", state)
        editor.putInt("questId", questId)
        editor.commit()
    }

    fun loadStatus() {
        val sharedPreference = getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
        state = sharedPreference.getBoolean("state", false)
        questId = sharedPreference.getInt("questId", 0)
    }
    fun getDistanceMeters(pt1: RoutePoint, pt2: GeoPoint): Double {
        var distance = 0.0
        try {
            val theta: Double = pt1.longtitude - pt2.longitude
            var dist =
                (Math.sin(Math.toRadians(pt1.latitude)) * Math.sin(Math.toRadians(pt2.latitude))
                        + Math.cos(Math.toRadians(pt1.latitude)) * Math.cos(Math.toRadians(pt2.latitude)) * Math.cos(
                    Math.toRadians(theta)
                ))
            dist = Math.acos(dist)
            dist = Math.toDegrees(dist)
            distance = dist * 60 * 1853.1596
        } catch (ex: Exception) {
            println(ex.message)
        }
        return distance
    }

    fun checkDistanceIsClose(pt1: RoutePoint, pt2: GeoPoint, distance: Double): Boolean {
        var isInDistance = false;
        try{
            var calcDistance = getDistanceMeters(pt1, pt2)

            if(distance <= calcDistance){
                isInDistance = true;
            }
        }
        catch (ex: Exception){
            println(ex.message);
        }
        return isInDistance;
    }
}