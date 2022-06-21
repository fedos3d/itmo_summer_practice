package com.example.questapp.activity

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.navigation.ui.AppBarConfiguration
import com.example.questapp.CustomAdapter
import com.example.questapp.MyCustomApplication
import com.example.questapp.R
import com.example.questapp.data.Route
import com.example.questapp.data.RoutePoint
import com.example.questapp.databinding.ActivityMainBinding
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer


class MainActivity : AppCompatActivity() {
    private lateinit var itemsList: ArrayList<Route>

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var myapp: MyCustomApplication


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myapp = applicationContext as MyCustomApplication


        //IT SEEMS I GOTTA WRITE MULTITHREAD TO TRACK DISTANCE TO MARKER:
        //🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯

        // Need to work through navigation

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
            val inputEditTextField = EditText(this)
            val dialog = AlertDialog.Builder(this)
                .setTitle("Добавить квест")
                .setMessage("Введите ID квеста для добавления")
                .setView(inputEditTextField)
                .setPositiveButton("OK") { _, _ ->
                    val editTextInput = inputEditTextField .text.toString()
                    // Need to use retrofit or okhttps to retrieve json
                }
                .setNegativeButton("Отмена", null)
                .create()
            dialog.show()
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
        var route = Route(1, routepoints, routeTitle, routeDescr, routePic)
        itemsList.add(route)
        itemsList.add(route.copy(2, routeTitle = "Маршрут номер 2(Тестовый)"))
        itemsList.add(route.copy(3, routeTitle = "Маршрут номер 3(Тестовый)"))
        itemsList.add(route.copy(4, routeTitle = "Маршрут номер 4(Тестовый)"))

        Log.d("","Route list size: " + itemsList.size)
    }

    override fun onDestroy() {
        myapp.saveStatus()
        super.onDestroy()
    }
}