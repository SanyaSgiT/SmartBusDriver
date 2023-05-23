package com.example.smartbusdriver.ui.account

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.smartbusdriver.MapActivity
import com.example.smartbusdriver.R

class CardActivity : AppCompatActivity() {
    private lateinit var btnAdd: Button
    private lateinit var cardName: TextView
    private lateinit var cardCode: TextView
    private lateinit var cardNum: TextView

    companion object {
        lateinit var userCardName: String
        lateinit var userCardCode: String
        lateinit var userCardNum: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)
        btnAdd = findViewById(R.id.button)

        cardNum = findViewById(R.id.card_num)
        cardName = findViewById(R.id.card_name)
        cardCode = findViewById(R.id.card_code)

        btnAdd.setOnClickListener {
            userCardNum = cardNum.text.toString()
            userCardName = cardName.text.toString()
            userCardCode = cardCode.text.toString()
            startActivity(Intent(this, MapActivity::class.java))
        }
    }
}