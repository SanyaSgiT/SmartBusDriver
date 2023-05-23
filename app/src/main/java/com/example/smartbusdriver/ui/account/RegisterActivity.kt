package com.example.smartbusdriver.ui.account

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartbusdriver.R
import com.example.smartbusdriver.data.api.DriverApi
import com.example.smartbusdriver.data.service.CreateUserRequest
import com.example.smartbusdriver.data.storage.UserStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class RegisterActivity : AppCompatActivity() {
    private lateinit var btnLogin: TextView
    private lateinit var btnReg: Button
    private lateinit var login: TextView
    private lateinit var name: TextView
    private lateinit var password: TextView

    private val userApi: DriverApi by inject()
    private val userStorage: UserStorage by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        btnLogin = findViewById(R.id.logInText)
        btnReg = findViewById(R.id.registerButton)

        login = findViewById(R.id.login)
        name = findViewById(R.id.name)
        password = findViewById(R.id.password)

        btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        btnReg.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val user = userApi.createUser(
                    CreateUserRequest(
                        login.text.toString(),
                        name.text.toString(),
                        password.text.toString()
                    )
                )
//                try {
//                    val user = userApi.createUser(
//                        CreateUserRequest(
//                            login.text.toString(),
//                            name.text.toString(),
//                            password.text.toString()
//                        )
//                    )
//
//                    userStorage.token = user.token.token
//                    userStorage.user = user.driver
//
//                    withContext(Dispatchers.Main) {
//                        startActivity(Intent(this@RegisterActivity, RouteActivity::class.java))
//                    }
//                } catch (_: Exception) {
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(this@RegisterActivity, "Ошибка", Toast.LENGTH_LONG).show()
//                    }
//                }
            }
            startActivity(Intent(this@RegisterActivity, RouteActivity::class.java))
        }
    }
}