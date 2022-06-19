package com.example.questapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.questapp.data.Route
import com.example.questapp.databinding.ActivityStartRouteBinding

class StartRouteActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityStartRouteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_start_route)
        val route: Route? = getIntent().getParcelableExtra("ROUTE")
        val button: View = findViewById(R.id.start_button)
        button.setOnClickListener{
            val intent = Intent(this, MapActivity::class.java).apply {
                putExtra("ROUTE", route)
            }
            startActivity(intent)
        }
    }
}