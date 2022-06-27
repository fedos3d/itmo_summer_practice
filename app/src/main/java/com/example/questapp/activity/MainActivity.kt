package com.example.questapp.activity

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.ui.AppBarConfiguration
import com.example.questapp.CustomAdapter
import com.example.questapp.MyCustomApplication
import com.example.questapp.R
import com.example.questapp.data.Route
import com.example.questapp.data.RoutePoint
import com.example.questapp.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import okhttp3.*
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private lateinit var itemsList: ArrayList<Route>

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var myapp: MyCustomApplication
    lateinit var client: OkHttpClient
    private lateinit var customAdapter: CustomAdapter

    private lateinit var text: String
    private lateinit var scrollView: DiscreteScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myapp = applicationContext as MyCustomApplication
        client = OkHttpClient()
        itemsList = ArrayList()
        customAdapter = CustomAdapter(this, itemsList)

        // Test Data Fill
//        prepareItems()

        scrollView = findViewById<DiscreteScrollView>(R.id.recyclerView)

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
                    val editTextInput = inputEditTextField.text.toString()
                    getQuest(editTextInput)
                }
                .setNegativeButton("Отмена", null)
                .create()
            dialog.show()
        }
    }

    fun getQuest(editTextInput: String) {
        val request = Request.Builder()
            .url("https://itmo-summer-practice-backend.herokuapp.com/Quest/" + editTextInput)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    // need to fix recyclerview update
                    addNewRoute(response.body!!.string())
                }
            }
        })
    }

    private fun prepareItems() {
        var routeTitle = "Маршрут номер 1(Тестовый)"
        var routeDescr = "Описание квеста"
        var routepoints = ArrayList<RoutePoint>()
        var routePic =
            "https://sun9-87.userapi.com/impf/mqi-t8BU4HQ5CPhm1Gd-hyBJeUJIejlH-AI5fA/wl1Owod-VL4.jpg?size=1280x1281&quality=95&sign=d89be3e82b5dfe28a6421c61250010c0&type=album"
        var routepoint = RoutePoint(59.9786, 30.34853, "Точка 1")
        routepoints.add(routepoint)
        routepoints.add(RoutePoint(59.97883058079127, 30.349726356261876, "Точка 2"))
        var route = Route(1, routepoints, routeTitle, routeDescr, routePic)
        itemsList.add(route)
        itemsList.add(route.copy(2, routeTitle = "Маршрут номер 2(Тестовый)"))
        itemsList.add(route.copy(3, routeTitle = "Маршрут номер 3(Тестовый)"))
        itemsList.add(route.copy(4, routeTitle = "Маршрут номер 4(Тестовый)"))
        customAdapter.notifyDataSetChanged()
        Log.d("", "Route list size: " + itemsList.size)
    }

    private fun addNewRoute(json: String) {
        val gson = Gson()
        var route: Route = gson.fromJson(json, Route::class.java)
        itemsList.add(route)
        Log.d("", "ROUTE TO STRING: " + route.toString())
        Log.d("", "ITEMLISTSIZE: " + itemsList.size)
        customAdapter.notifyItemInserted(scrollView.currentItem + 1)

    }

    override fun onDestroy() {
        myapp.saveStatus()
        super.onDestroy()
    }
}