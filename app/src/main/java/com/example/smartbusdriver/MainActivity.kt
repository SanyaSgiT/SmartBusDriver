package com.example.smartbusdriver

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.smartbusdriver.ui.account.LoginActivity
import com.example.smartbusdriver.ui.account.RegisterActivity

class MainActivity : AppCompatActivity() {

    private lateinit var login: Button
    private lateinit var register: Button

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        login = findViewById(R.id.loginButton)
        register = findViewById(R.id.registerButton)

        login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}