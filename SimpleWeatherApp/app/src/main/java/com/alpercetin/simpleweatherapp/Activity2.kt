package com.alpercetin.simpleweatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.alpercetin.simpleweatherapp.databinding.ActivityMainBinding
import org.w3c.dom.Text

class Activity2 : AppCompatActivity() {
    private lateinit var newtext:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.frameLayout.findViewById<TextView>(R.id.selected_city_text).text = "saddaa"*/
        //newtext = findViewById<TextView>(R.id.selected_city_text)
        //newtext.text = "City Selected: "+intent.getStringExtra("cityId") + " " + intent.getStringExtra("cityCode")
    }
}