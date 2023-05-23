package com.example.smartbusdriver.ui.account

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartbusdriver.R
import com.example.smartbusdriver.data.api.TransportApi
import com.example.smartbusdriver.data.api.DriverApi
import com.example.smartbusdriver.data.service.LoginRequest
import com.example.smartbusdriver.data.storage.UserStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity(){
    private lateinit var btnToRegister: TextView
    private lateinit var btnLogin: Button
    private lateinit var login: TextView
    private lateinit var password: TextView

    private val userApi: DriverApi by inject()
    private val transportApi: TransportApi by inject()
    private val userStorage: UserStorage by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnToRegister = findViewById(R.id.regText)
        btnLogin = findViewById(R.id.logInButton)

        login = findViewById(R.id.login)
        password = findViewById(R.id.password)

        btnToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btnLogin.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val user = userApi.authentication(
                    LoginRequest(
                        login.text.toString(),
                        password.text.toString()
                    )
                )
//                try {
//                    val user = userApi.authentication(
//                        LoginRequest(
//                            login.text.toString(),
//                            password.text.toString()
//                        )
//                    )
//
//                    userStorage.token = user.token.token
//                    userStorage.user = user.driver
//
//                    println(user)
//
//                    withContext(Dispatchers.Main) {
//                        startActivity(Intent(this@LoginActivity, RouteActivity::class.java))
//                    }
//                } catch (_: Exception) {
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(this@LoginActivity, "Нет такого пользователя", Toast.LENGTH_LONG).show()
//                    }
//                }
            }
            startActivity(Intent(this@LoginActivity, RouteActivity::class.java))
        }
    }
}