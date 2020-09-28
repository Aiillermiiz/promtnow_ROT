package com.example.promtnow_rot.recycleview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.promtnow_rot.R
import com.example.promtnow_rot.request.DataOfForm

class RequestAdapter(var listener: OnClickListener):
    RecyclerView.Adapter<RequestViewHolder>() {
     var data = mutableListOf<DataOfForm>()
    //enum state
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        return RequestViewHolder(DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_request,parent,false
        ), listener)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
       holder.bind(data[position])
    }//on bind view holder

    //fun add
    fun addFormData(addObj: DataOfForm) {
        data.add(addObj)
        notifyDataSetChanged()
    }
    //fun delete
    fun delete(delObj:DataOfForm){
        data.remove(delObj)
        notifyDataSetChanged()
    }
    interface OnClickListener{
        fun onClick(data:DataOfForm)
        fun onLongClick(data:DataOfForm)
    }
}