package com.example.promtnow_rot.recycleview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.promtnow_rot.R

class ListRequestAdapter(var listener:OnClickListener) :
    RecyclerView.Adapter<ListRequestViewHolder>() {
    var InfoForm = mutableListOf<dataList>()
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
    //update list
    fun updateForm(updateObj:dataList){
        InfoForm.forEachIndexed { index, dataList ->
            if (dataList.id == updateObj.id){
                InfoForm[index] = updateObj
            }
        }//for index
        notifyDataSetChanged()
    }
    //sum amount
    fun sumAmount(): Int {
        var sum = 0
        InfoForm.forEachIndexed { index, dataList ->
            sum += InfoForm[index].amount
        }
        return sum
    }
    // interface class onclick item
    interface OnClickListener{
        fun onClick(data:dataList)
        fun onLongClick(data:dataList)
    }
}