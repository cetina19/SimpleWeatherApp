package com.alpercetin.simpleweatherapp

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


class Weather : Fragment() {
    var CITY_ID:String = "745042"
    val API:String = "c0abaa3112fb22ed05f6f7408112997b"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val args = this.arguments
        val newCity = args?.get("data")
        //CITY_ID = newCity.toString()
        println("4%%%%%%%%%%%%%%%%%%" + CITY_ID+ " ----" + newCity+"%%%%%%%%%%%%%%%")
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherTask().execute()
    }

    inner class weatherTask() : AsyncTask<String, Void, String>(){
        override fun onPreExecute() {
            super.onPreExecute()
            view?.findViewById<ProgressBar>(R.id.loader)?.visibility = View.VISIBLE
            view?.findViewById<FrameLayout>(R.id.main_frame)?.visibility = View.GONE
            //findViewById<FrameLayout>(R.id.frame_layout).visibility = View.GONE
            view?.findViewById<TextView>(R.id.errortext)?.visibility = View.GONE
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
                view?.findViewById<FrameLayout>(R.id.main_frame)?.visibility = View.VISIBLE
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val coord = jsonObj.getJSONObject("coord")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                val updatedAt:Long = jsonObj.getLong("dt")
                val updatedAtText = "Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
                    Date(updatedAt*1000)
                )
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

                view?.findViewById<TextView>(R.id.address)?.text = address
                view?.findViewById<TextView>(R.id.weather_status)?.text = weatherDescription
                view?.findViewById<TextView>(R.id.temp)?.text = temp
                view?.findViewById<TextView>(R.id.feels_like)?.text = feels_like
                view?.findViewById<TextView>(R.id.temp_min)?.text = tempMin
                view?.findViewById<TextView>(R.id.temp_max)?.text = tempMax
                view?.findViewById<TextView>(R.id.hum_text)?.text = humidity
                view?.findViewById<TextView>(R.id.wind_speed_text)?.text = windSpeed
                view?.findViewById<TextView>(R.id.wind_gust_text)?.text = gust
                view?.findViewById<TextView>(R.id.sea_level_text)?.text = seaLevel
                view?.findViewById<TextView>(R.id.longitude_text)?.text = lon
                view?.findViewById<TextView>(R.id.latitude_text)?.text = lat
            }

            catch (e: Exception){
                println(" ********* "+e.message+"**********")

                view?.findViewById<FrameLayout>(R.id.main_frame)?.visibility = View.GONE
                view?.findViewById<TextView>(R.id.errortext)?.visibility = View.VISIBLE
            }
        }
    }
}