package com.example.promtnow_rot.add

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.promtnow_rot.R

class AddAdapter(val data: MutableList<String>) : RecyclerView.Adapter<AddAdapter.MyViewHolder>() {

    class MyViewHolder(val view:View) : RecyclerView.ViewHolder(view){
        val date = view.findViewById<View>(R.id.date) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_of_add_main, parent, false))
    }
    //size of data
    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: AddAdapter.MyViewHolder, position: Int) {
        holder.date.text = data[position]
    }

}