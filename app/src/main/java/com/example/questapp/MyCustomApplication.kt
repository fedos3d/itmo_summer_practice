package com.example.questapp

import android.app.Application
import android.content.Context

class MyCustomApplication : Application() {
    var state: Boolean = false
    var questId: Int = 0

    override fun onCreate() {
        loadStatus()
        super.onCreate()
    }

    fun saveStatus() {
        val sharedPreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putBoolean("state", state)
        editor.putInt("questId", questId)
        editor.commit()
    }

    fun loadStatus() {
        val sharedPreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        state = sharedPreference.getBoolean("state", false)
        questId = sharedPreference.getInt("questId", 0)
    }
}