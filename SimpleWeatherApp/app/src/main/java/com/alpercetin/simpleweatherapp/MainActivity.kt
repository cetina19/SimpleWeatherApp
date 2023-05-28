package com.alpercetin.simpleweatherapp

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.alpercetin.simpleweatherapp.databinding.ActivityMainBinding
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    val CITY:String = "Istanbul"//"dhaka,bd"
    val CITY_ID:String = "745042"
    val API:String = "c0abaa3112fb22ed05f6f7408112997b"
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(Weather())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.weather -> replaceFragment(Weather())
                R.id.city -> replaceFragment(City())


                else -> {}


            }
            true
        }
    }

    override fun onStart() {
        super.onStart()
        weatherTask().execute()

    }

    inner class weatherTask() : AsyncTask<String, Void, String>(){
        override fun onPreExecute() {
            super.onPreExecute()
            findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
            findViewById<FrameLayout>(R.id.main_frame).visibility = View.GONE
            //findViewById<FrameLayout>(R.id.frame_layout).visibility = View.GONE
            findViewById<TextView>(R.id.errortext).visibility = View.GONE
        }

        override fun doInBackground(vararg p0: String?): String? {
            var response:String?
            try{
                response = URL("https://api.openweathermap.org/data/2.5/weather?id=$CITY_ID&APPID=$API&units=metric")
                    .readText(Charsets.UTF_8)
            }
            catch (e: Exception){
                response = null//"no return"
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            try{
                findViewById<FrameLayout>(R.id.main_frame).visibility = View.VISIBLE
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val coord = jsonObj.getJSONObject("coord")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                val updatedAt:Long = jsonObj.getLong("dt")
                val updatedAtText = "Updated at: "+SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(updatedAt*1000))
                val temp = "Current: " + main.getString("temp") + " C"
                val feels_like = "Feels Like: " + main.getString("feels_like") + "C"
                val tempMin = "L: "+main.getString("temp_min")+" C"
                val tempMax = "H: "+main.getString("temp_max")+" C"
                val humidity = "%"+main.getString("humidity")
                val windSpeed = "Speed:"+ wind.getString("speed") + "km/h"
                val gust = "Gust:" + wind.getString("deg") + "km/h"
                val seaLevel = main.getString("pressure")
                val lon = coord.getString("lon")
                val lat = coord.getString("lat")
                val weatherDescription = "Today's Weather Description: " + weather.getString("description")
                val address = jsonObj.getString("name")//+", "+sys.getString("country")

                findViewById<TextView>(R.id.address).text = address
                findViewById<TextView>(R.id.weather_status).text = weatherDescription
                findViewById<TextView>(R.id.temp).text = temp
                findViewById<TextView>(R.id.feels_like).text = feels_like
                findViewById<TextView>(R.id.temp_min).text = tempMin
                findViewById<TextView>(R.id.temp_max).text = tempMax
                findViewById<TextView>(R.id.hum_text).text = humidity
                findViewById<TextView>(R.id.wind_speed_text).text = windSpeed
                findViewById<TextView>(R.id.wind_gust_text).text = gust
                findViewById<TextView>(R.id.sea_level_text).text = seaLevel
                findViewById<TextView>(R.id.longitude_text).text = lon
                findViewById<TextView>(R.id.latitude_text).text = lat
            }

            catch (e: Exception){
                println(" ********* "+e.message+"**********")

                findViewById<FrameLayout>(R.id.main_frame).visibility = View.GONE
                findViewById<TextView>(R.id.errortext).visibility = View.VISIBLE
            }
        }
    }

    public fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment,"Current_Fragment")
        fragmentTransaction.commit()
    }

    override fun onResume() {
        super.onResume()
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.weather -> replaceFragment(Weather())
                R.id.city -> replaceFragment(City())
                R.id.city -> readJson()



                else -> {}


            }
            true
        }

    }

    fun readJson(){
        var json:String? = null
        try {
            val inputStream:InputStream = assets.open("city_data.json")
            json = inputStream.bufferedReader().use{it.readText()}
            //json_text.text = json
            //findViewById<TextView>(R.id.json_text).text = " ldsljdajas "
        }
        catch(e: IOException){
            println(" ********** "+e+" ********** ")
        }
    }
}