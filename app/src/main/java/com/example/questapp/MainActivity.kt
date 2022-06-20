package com.example.questapp


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.questapp.data.Route
import com.example.questapp.data.RoutePoint
import com.example.questapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.yarolegovich.discretescrollview.DiscreteScrollView


//import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var itemsList: ArrayList<Route>

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //IT SEEMS I GOTTA WRITE MULTITHREAD TO TRACK DISTANCE TO MARKER:
        //🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯🌯


//      kinda trying to create recycler view, sorta works
        // Test Data Fill
        itemsList = ArrayList()
        prepareItems()


//        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
//        val customAdapter = CustomAdapter(this, itemsList)
//
//        val viewManager = LinearLayoutManager(this)
//
//        recyclerView.apply {
//            layoutManager = viewManager
//            adapter = customAdapter
//        }
//        customAdapter.notifyDataSetChanged()

        val customAdapter = CustomAdapter(this, itemsList)
        val scrollView = findViewById<DiscreteScrollView>(R.id.recyclerView)
        scrollView.adapter = CustomAdapter(this, itemsList)
        customAdapter.notifyDataSetChanged()
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        setSupportActionBar(binding.toolbar)

        // Set action bar
        var actionBar: View? = findViewById(R.id.appBarLayout2)
        if (actionBar != null) {
            actionBar.isVisible = false
        }
//
        // Fab button

        var fab: View = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    private fun prepareItems() {
        var routedescr = "Маршрут номер 1(Тестовый)"
        var routepoints = ArrayList<RoutePoint>()
        var routepoint = RoutePoint(59.9786, 30.34853, "Точка 1")
        routepoints.add(routepoint)
        routepoints.add(RoutePoint(59.97883058079127, 30.349726356261876, "Точка 2"))
        var route = Route(routepoints, routedescr)
        itemsList.add(route)
        itemsList.add(route.copy())
        itemsList.add(route.copy())
        itemsList.add(route.copy())

        println("LIst: " + itemsList.size)
        println("DEBUG")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}