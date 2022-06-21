package com.example.questapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.ui.AppBarConfiguration
import com.example.questapp.data.Route
import com.example.questapp.databinding.ActivityStartRouteBinding

class StartRouteActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityStartRouteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_route)

        //I need somehow track status of quest


//        val isStarted: Boolean = getIntent().getBooleanExtra("STATE")

//        if (isStarted) {
//            var state: TextView = findViewById(R.id.textView)
//            state.apply {
//                text = resources.getText(R.string.route_in_progress)
//            }
//        }

//        var actionBar: View? = findViewById(R.id.appBarLayout)
//        if (actionBar != null) {
//            actionBar.isVisible = false
//        }




        var descr: TextView = findViewById(R.id.routeDescr)
        val route: Route? = getIntent().getParcelableExtra("ROUTE")
        if (route != null) {
            descr.text = route.routeDescription
        }
        val button: View = findViewById(R.id.start_button)
        button.setOnClickListener{
            val intent = Intent(this, MapActivity::class.java).apply {
                putExtra("ROUTE", route)
//                putExtra("STATE", true)
            }
            startActivity(intent)
        }
    }
}