package com.example.smartbusdriver.ui.bottombar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.smartbusdriver.MapActivity
import com.example.smartbusdriver.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class EditActivity: AppCompatActivity() {

    private lateinit var edit: Button
    private lateinit var bottomBar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_editing)
        setupBinding()
        setupUi()
    }

    private fun setupBinding() {
        edit = findViewById(R.id.button)
        bottomBar = findViewById(R.id.bottomNavigationView)
    }

    private fun setupUi() {
        bottomBar.selectedItemId = R.id.menu_profile
        bottomBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_info -> {
                    startActivity(Intent(this, InfoActivity::class.java))
                    finish()
                    true
                }
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

        edit.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }
    }
}