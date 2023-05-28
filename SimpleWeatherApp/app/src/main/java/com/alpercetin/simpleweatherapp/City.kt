package com.alpercetin.simpleweatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.json.JSONArray

class City : Fragment() {

    private lateinit var cityInfoArrayList : ArrayList<City_Info>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //val view = inflater.inflate(R.layout.fragment_city, container, false)
        //adapter = City_Adapter(requireActivity(),cityInfoArrayList)

        return inflater.inflate(R.layout.fragment_city, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    //private lateinit var binding : ActivityMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var allJson:String?
        //binding = ActivityMainBinding.inflate(layoutInflater)
        //activity?.setContentView(binding.root)

        try {
            val result:TextView = view.findViewById(R.id.selected_city_text)
            var new_city_data:String
            val fragment = Weather()
            val searchView :SearchView
            val bundle = Bundle()
            allJson = activity?.assets?.open("city_list_tr.json")?.bufferedReader().use {
                it?.readText()
            }
            var jsonArrayData = JSONArray(allJson)
            val listView = view.findViewById<ListView>(R.id.city_list_texts)
            searchView = view.findViewById(R.id.search_bar)
            //var city_data = arrayOf(String)
            cityInfoArrayList = ArrayList<City_Info>()
            //val adapter = City_Adapter(this.activity,cityInfoArrayList)//ArrayAdapter(this,android.R.layout.simple_list_item_1,cityInfoArrayList[0].cityCode)
            for (i in 0..jsonArrayData.length() - 1) {
                var jObject = jsonArrayData.getJSONObject(i)
                var cityID = jObject.getString("id")
                var cityName = jObject.getString("name")
                var countryName = jObject.getString("country")
                var cityCode = cityName + "," + countryName

                val cityData = City_Info(cityCode, cityID)
                cityInfoArrayList.add(cityData)
            }
            view.findViewById<ListView>(R.id.city_list_texts).isClickable = true
            view.findViewById<ListView>(R.id.city_list_texts).adapter = City_Adapter(this.activity,cityInfoArrayList)
            //var result:String = "Empty"
            try {
                listView.setOnItemClickListener { parent, _, position, _ ->
                    val selectedItem = parent.getItemAtPosition(position) as City_Info
                    val info = selectedItem.cityCode + " " + selectedItem.cityId
                    result.text = "City Selected: $info"
                    new_city_data = selectedItem.cityCode.toString()
                    bundle.putString("data",new_city_data)

                }
                val buttonClick = view?.findViewById<Button>(R.id.city_selection_button)
                buttonClick?.setOnClickListener{
                    println("I presssed a buttton")
                    fragment.arguments = bundle
                    fragment.CITY_ID = bundle.getString("data").toString()
                    //val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager?.beginTransaction()
                    fragmentTransaction?.replace(R.id.city_frame,fragment,"Current_Fragment")
                    fragmentTransaction?.commit()
                    //println("********** ")
                    println(fragment.CITY_ID + "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+bundle.getString("data"))
                    //childFragmentManager.beginTransaction()?.replace(R.id.main_frame,fragment)?.commit()
                }

                searchView.setOnQueryTextListener(SearchView.OnQueryTextListener{})

                //var result2 = view.findViewById<ListView>(R.id.city_list_texts)

                /*view.findViewById<ListView>(R.id.city_list_texts).setOnItemClickListener { adapterView, view, i, l ->
                    val cityId = cityInfoArrayList[i].cityId
                    val cityCode = cityInfoArrayList[i].cityCode
                    result = "City Selected: " + cityCode.toString() + " " + cityId.toString()
                    //println("&&&&&&&&&&&&&&&&&&&&&"+result)
                    val selection = Intent(this.activity,Activity2::class.java)
                    selection.putExtra("cityId",cityId)
                    selection.putExtra("cityCode",cityCode)

                    //startActivity(selection)
                    //val selectedCity = new Intent(FragmentActivity,)
                    //println(" ************* "+results)
                    // = "City Selected: " + cityId + cityCode
                }*/


            }
            catch (e:Exception){
                println("********* "+e.message)
            }
            //view.findViewById<TextView>(R.id.selected_city_text).text = result
        }
        catch (e:Exception){
            println(" ******** OOOOOOOOOOpppppppSSSSSSSS" + e.message)
        }
        /*view.findViewById<TextView>(R.id.json_text).text = "new string"
        var json:String? = null
        try {
            val stream = activity?.assets?.open("city_list_tr.json")?.bufferedReader().use{
                it?.readText()
            }
            view.findViewById<TextView>(R.id.json_text).text = stream//json
        }
        catch(e: IOException){
            println(" ********** "+e+" ********** ")
        }*/
    }

    override fun onStart() {
        super.onStart()
        //view?.findViewById<TextView>(R.id.selected_city_text)?.text = result
    }

    override fun onResume() {
        super.onResume()
        /*view?.findViewById<ListView>(R.id.city_list_texts)?.setOnItemClickListener { adapterView, view, i, l ->
            val cityId = cityInfoArrayList[i].cityId
            val cityCode = cityInfoArrayList[i].cityCode
            result = "City Selected: " + cityCode.toString() + " " + cityId.toString()
            //println("&&&&&&&&&&&&&&&&&&&&&"+result)
            val selection = Intent(this.activity,Activity2::class.java)
            selection.putExtra("cityId",cityId)
            selection.putExtra("cityCode",cityCode)

            //startActivity(selection)
            //val selectedCity = new Intent(FragmentActivity,)
            //println(" ************* "+results)
            // = "City Selected: " + cityId + cityCode
        }
        view?.findViewById<TextView>(R.id.selected_city_text)?.text = result*/
    }
}

/*
        * fun readJson(){
        var json:String? = null
        try {
            val inputStream:InputStream = assets.open("city_data.json")
            json = inputStream.bufferedReader().use{it.readText()}
            //json_text.text = json
            findViewById<TextView>(R.id.json_text).text = json
        }
        catch(e: IOException){
            println(" ********** "+e+" ********** ")
        }
    }*/