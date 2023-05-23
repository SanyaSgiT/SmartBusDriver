package com.example.smartbusdriver.ui.bottombar

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.smartbusdriver.MapActivity
import com.example.smartbusdriver.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class InfoActivity: AppCompatActivity() {

    private lateinit var bottomBar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        setupBinding()
        setupUi()
    }

    private fun setupBinding() {
        bottomBar = findViewById(R.id.bottomNavigationView)
    }

    private fun setupUi() {
        bottomBar.selectedItemId = R.id.menu_info
        bottomBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_map -> {
                    startActivity(Intent(this, MapActivity::class.java))
                    finish()
                    true
                }
                R.id.menu_profile -> {
                    startActivity(Intent(this, UserActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}