package com.example.questapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.ui.AppBarConfiguration
import com.example.questapp.data.Route
import com.example.questapp.data.RoutePoint
import com.example.questapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer

class MainActivity : AppCompatActivity() {
    private lateinit var itemsList: ArrayList<Route>

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //IT SEEMS I GOTTA WRITE MULTITHREAD TO TRACK DISTANCE TO MARKER:
        //🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯

        // Test Data Fill
        itemsList = ArrayList()
        prepareItems()

        val customAdapter = CustomAdapter(this, itemsList)
        val scrollView = findViewById<DiscreteScrollView>(R.id.recyclerView)

        // Adds smooth resizing
        scrollView.setItemTransformer(
            ScaleTransformer.Builder()
                .setMaxScale(1.05f)
                .setMinScale(0.8f)
                .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                .setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
                .build()
        )

        // Allows to scroll though more than one item
        scrollView.setSlideOnFling(true);

        // add
        scrollView.adapter = CustomAdapter(this, itemsList)
        customAdapter.notifyDataSetChanged()

        // Set action bar
        var actionBar: View? = findViewById(R.id.appBarLayout2)
        if (actionBar != null) {
            actionBar.isVisible = false
        }

        // Fab button
        var fab: View = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun prepareItems() {
        var routeTitle = "Маршрут номер 1(Тестовый)"
        var routeDescr = "В данном квесте бла бла бла бла бла"
        var routepoints = ArrayList<RoutePoint>()
        var routePic = "https://sun9-87.userapi.com/impf/mqi-t8BU4HQ5CPhm1Gd-hyBJeUJIejlH-AI5fA/wl1Owod-VL4.jpg?size=1280x1281&quality=95&sign=d89be3e82b5dfe28a6421c61250010c0&type=album"
        var routepoint = RoutePoint(59.9786, 30.34853, "Точка 1")
        routepoints.add(routepoint)
        routepoints.add(RoutePoint(59.97883058079127, 30.349726356261876, "Точка 2"))
        var route = Route(routepoints, routeTitle, routeDescr, routePic)
        itemsList.add(route)
        itemsList.add(route.copy())
        itemsList.add(route.copy())
        itemsList.add(route.copy())

        Log.d("","Route list size: " + itemsList.size)
    }
}