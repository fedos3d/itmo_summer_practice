package com.example.questapp.activity

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import com.example.questapp.BuildConfig
import com.example.questapp.MyCustomApplication
import com.example.questapp.R
import com.example.questapp.data.Route
import com.example.questapp.data.RoutePoint
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


class MapActivity : AppCompatActivity() {
    private lateinit var map: MapView
    private lateinit var mapController: IMapController
    private lateinit var mLocationOverlay: MyLocationNewOverlay
    private lateinit var mCompassOverlay: CompassOverlay
    private lateinit var mRotationGestureOverlay: RotationGestureOverlay
    private lateinit var route: Route
    lateinit var myapp: MyCustomApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        myapp = applicationContext as MyCustomApplication


        var ctx: Context = this
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        map = findViewById(R.id.map);
        //Issue with slow loading, it is related to setTitleSource, needs further research
        map.setTileSource(TileSourceFactory.MAPNIK);


        //add my location to map
        this.mLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), map)
        this.mLocationOverlay.enableMyLocation()
        map.overlays.add(this.mLocationOverlay)

        //add compass to map
        this.mCompassOverlay =
            CompassOverlay(this, InternalCompassOrientationProvider(this), map)
        this.mCompassOverlay.enableCompass()
        map.overlays.add(this.mCompassOverlay)

        //add rotation gestures
        mRotationGestureOverlay = RotationGestureOverlay(this, map)
        mRotationGestureOverlay.setEnabled(true)

        //Need to work with exception when permission is not granted

        //Need to add my current location, DONE
        //Need to add permission checks


        //testing points
//        var startPoint_1 = GeoPoint(59.9786, 30.34853);
//        var startMarker =  Marker(map);
//        startMarker.position = startPoint_1;
//        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
//        map.overlays.add(startMarker)

        //here we fill map with our points

        route = getIntent().getParcelableExtra("ROUTE")!!;

        println("ROUTE after deparcelization: " + route.toString())
        if (route != null) {
            for (item: RoutePoint in route.routePoint) {
                var startPoint_1 = GeoPoint(item.latitude, item.longtitude);
                var startMarker =  Marker(map);
                startMarker.position = startPoint_1;
                startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                map.overlays.add(startMarker)
            }
        } else {
            println("NO route info was sent")
        }


        map.setMultiTouchControls(true);
        val mapController = map.controller
        mapController.setZoom(9.5)
        val startPoint = GeoPoint(59.9390507529518, 30.31564101110063)
        mapController.setCenter(startPoint)
    }
    override fun onResume() {
        super.onResume()
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map.onResume() //needed for compass, my location overlays, v6.0.0 and up
    }

    override fun onPause() {
        super.onPause()
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map.onPause() //needed for compass, my location overlays, v6.0.0 and up
    }



    //probably methods to calc distance between current location and markers:


//    fun getDistanceMeters(pt1: LatLng, pt2: LatLng): Double {
//        var distance = 0.0
//        try {
//            val theta: Double = pt1.longitude - pt2.longitude
//            var dist =
//                (Math.sin(Math.toRadians(pt1.latitude)) * Math.sin(Math.toRadians(pt2.latitude))
//                        + Math.cos(Math.toRadians(pt1.latitude)) * Math.cos(Math.toRadians(pt2.latitude)) * Math.cos(
//                    Math.toRadians(theta)
//                ))
//            dist = Math.acos(dist)
//            dist = Math.toDegrees(dist)
//            distance = dist * 60 * 1853.1596
//        } catch (ex: Exception) {
//            println(ex.message)
//        }
//        return distance
//    }

//    fun checkDistanceIsClose(pt1: LatLng, pt2: LatLng, distance: Double): Boolean {
//        var isInDistance = false;
//        try{
//            var calcDistance = getDistanceMeters(pt1, pt2)
//
//            if(distance <= calcDistance){
//                isInDistance = true;
//            }
//        }
//        catch (ex: Exception){
//            println(ex.message);
//        }
//        return isInDistance;
//    }
    override fun onDestroy() {
        myapp.saveStatus()
        super.onDestroy()
    }
}