package com.example.questapp


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.questapp.data.Route

internal class CustomAdapter(private var mycontext: Context, private var names: List<Route>) :
    RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    class MyViewHolder(context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var route: Route;
        val largeTextView: TextView = itemView.findViewById(R.id.textViewLarge)
        val smallTextView: TextView = itemView.findViewById(R.id.textViewSmall)
        init {
            itemView.setOnClickListener {
                println("ROUTE BEFORE PARCELIZATION: " + route.toString())
                val intent = Intent(context, StartRouteActivity::class.java).apply {
                    putExtra("ROUTE", route)
                }
                startActivity(context, intent, null)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item, parent, false)
        return MyViewHolder(mycontext, itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.route = names[position]
        holder.largeTextView.text = names[position].routeDescription
        holder.smallTextView.text = "кот"
    }

    override fun getItemCount(): Int {
        return names.size
    }
}