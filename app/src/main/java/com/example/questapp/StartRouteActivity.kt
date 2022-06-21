package com.example.questapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.ui.AppBarConfiguration
import com.example.questapp.data.Route
import com.example.questapp.databinding.ActivityStartRouteBinding
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

class StartRouteActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityStartRouteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_route)

        // Need to fix description TextView sizes to dynamically fill with content

        //I need somehow track status of quest

        // Fill start route activity
        val route: Route? = getIntent().getParcelableExtra("ROUTE")
        var descr: TextView = findViewById(R.id.routeDescr)
        var imVies: ImageView = findViewById(R.id.iv_picasso)
        var title: TextView = findViewById(R.id.routeTitle)
        if (route != null) {
            descr.text = route.routeDescription
            title.text = route.routeTitle
            Picasso.get()
                .load(route.routePic)
                .placeholder(R.drawable.pic_3)//it will show placeholder image when url is not valid.
                .networkPolicy(NetworkPolicy.OFFLINE) //for caching the image url in case phone is offline
                .into(imVies)
        }

        val button: View = findViewById(R.id.start_button)
        button.setOnClickListener{
            val intent = Intent(this, MapActivity::class.java).apply {
                putExtra("ROUTE", route)
            }
            startActivity(intent)
        }
    }
}