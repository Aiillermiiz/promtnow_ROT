package com.example.promtnow_rot.add.recycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.promtnow_rot.R
import com.example.promtnow_rot.add.FragmentFormAdd
import kotlinx.android.synthetic.main.list_item_of_add_main.view.*

class Adapter(fragmentActivity: FragmentActivity,private val data: MutableList<InfoDocument>) : RecyclerView.Adapter<Adapter.ViewHolder>(){
    private val thisActivity = fragmentActivity
    //create attribute in view
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date: TextView =  view.date
        val listItem = view
    }
    //create view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_of_add_main,parent,false))
    }
    //size of data
    override fun getItemCount() = data.size
    //create set data to view holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date.text = data[position].date
        //onclick
        holder.listItem.setOnClickListener {
            //Log.d("tag",position.toString())
            thisActivity.supportFragmentManager.beginTransaction().apply {
                replace(R.id.layoutFragmentContainerAdd,FragmentFormAdd.newInstance(FragmentFormAdd.PageState.EDIT_STATE))
                addToBackStack("add")
                commit()
            }
        }
    }

}