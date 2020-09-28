package com.example.promtnow_rot.recycleview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.promtnow_rot.R

class ListRequestAdapter(var listener:OnClickListener) :
    RecyclerView.Adapter<ListRequestViewHolder>() {
    var InfoForm = mutableListOf<dataList>()
    var sum : Int = 0
    //create view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListRequestViewHolder {
        return ListRequestViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_list_detail,
                parent,false),listener)
    }

    //size of data
    override fun getItemCount() = InfoForm.size

    //create set data to view holder
    override fun onBindViewHolder(holder: ListRequestViewHolder, position: Int) {
        holder.bind(InfoForm[position])
    }//on bind view holder
    //fun add list
    fun addFormData(addObj: dataList) {
        InfoForm.add(addObj)
        notifyDataSetChanged()
    }
    //delete list data
    fun deleteListOfMutable(delObj:dataList){
        InfoForm.remove(delObj)
        notifyDataSetChanged()
    }
    //fun delete all
    fun deleteDataAll(){
        InfoForm.removeAll(InfoForm)
        notifyDataSetChanged()
    }
    //sum total amount
    fun sumAmount():Int{
        InfoForm.forEachIndexed { index, dataList ->
            sum += InfoForm[index].amount
        }
        return  sum
    }
    // interface class onclick item
    interface OnClickListener{
        fun onClick(data:dataList)
        fun onLongClick(data:dataList)
    }
}