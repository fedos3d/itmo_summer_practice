package com.example.questapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.questapp.MyCustomApplication
import com.example.questapp.R
import com.example.questapp.data.Route
import com.example.questapp.databinding.ActivityStartRouteBinding
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

class StartRouteActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityStartRouteBinding
    lateinit var myapp: MyCustomApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_route)
        myapp = applicationContext as MyCustomApplication
        val button: Button = findViewById(R.id.start_button)
        val status: TextView = findViewById(R.id.routeStatus)
        val route: Route? = getIntent().getParcelableExtra("ROUTE")


        // Need to fix description TextView sizes to dynamically fill with content

        //I need somehow track status of quest
        Log.d("", "Quest state: " + myapp.state)
        if (route != null) {
            if (myapp.state && myapp.questId == route.id) {
                button.text = "Остановить маршрут" // Change later to R.string.something
                status.text = "Машрут начат" // Change later to R.string.something
            }
        }

        // Fill start route activity

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


        button.setOnClickListener{
            if (!myapp.state) {
                val intent = Intent(this, MapActivity::class.java).apply {
                    putExtra("ROUTE", route)
                }
                myapp.state = true
                myapp.questId = route!!.id
                startActivity(intent)
                if (myapp.state && myapp.questId == route.id) {
                    button.text = "Остановить маршрут" // Change later to R.string.something
                    status.text = "Машрут начат" // Change later to R.string.something
                }
            } else if (myapp.questId == route!!.id){
                myapp.state = false
                button.text = "Начать маршрут"
                status.text = "Маршрут не начат"
            } else {
                Toast.makeText(this, "Другой квест уже запущен, сначала завершите тот квест", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        myapp.saveStatus()
        super.onDestroy()
    }
}