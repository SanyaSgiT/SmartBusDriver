package com.example.smartbusdriver.ui.bottombar

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.smartbusdriver.MainActivity
import com.example.smartbusdriver.MapActivity
import com.example.smartbusdriver.ui.account.CardActivity
import com.example.smartbusdriver.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class UserActivity: AppCompatActivity() {

    private lateinit var exit: TextView
    private lateinit var edit: ImageButton
    private lateinit var bottomBar: BottomNavigationView
    private lateinit var name: TextView
    private lateinit var num: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        setupBinding()
        setupUi()
    }

    private fun setupBinding() {
        exit = findViewById(R.id.exitText)
        edit = findViewById(R.id.edit)
        bottomBar = findViewById(R.id.bottomNavigationView)
        name = findViewById(R.id.name)
        num = findViewById(R.id.description2)
    }

    @SuppressLint("SetTextI18n")
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
                else -> false
            }
        }

        exit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        edit.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }

//        name.text = CardActivity.userCardName
//        num.text = "Номер: " + CardActivity.userCardNum
    }
}