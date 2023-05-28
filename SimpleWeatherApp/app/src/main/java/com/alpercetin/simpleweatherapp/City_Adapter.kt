package com.alpercetin.simpleweatherapp

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

class City_Adapter(private val context: FragmentActivity?, private val arrayList: ArrayList<City_Info>) :ArrayAdapter<City_Info>
    (context!!, R.layout.list_item, arrayList){

    private lateinit var myListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        myListener = listener
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.list_item, null)
        val city_id : TextView = view.findViewById(R.id.city_id)
        val city_code : TextView = view.findViewById(R.id.city_code)

        city_id.text = arrayList[position].cityId
        city_code.text = arrayList[position].cityCode


        return view//super.getView(position, convertView, parent)
    }

    //class MyViewHolder(itemView: View): RecyclerView.ViewHolder{}
}