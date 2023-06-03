package com.example.smartbusdriver

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.smartbusdriver.data.storage.UserStorage
import com.example.smartbusdriver.ui.account.LoginActivity
import com.example.smartbusdriver.ui.account.RegisterActivity
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var login: Button
    private lateinit var register: Button
    private val userStorage: UserStorage by inject()
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

//        if (userStorage.hasUser) {
//            startActivity(Intent(this, MapActivity::class.java))
//
//            finish()
//        }

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