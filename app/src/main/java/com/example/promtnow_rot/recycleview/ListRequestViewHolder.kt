package com.example.promtnow_rot.recycleview

import androidx.recyclerview.widget.RecyclerView
import com.example.promtnow_rot.databinding.ListItemListDetailBinding
import kotlinx.android.synthetic.main.list_item_list_detail.view.*

class ListRequestViewHolder(itemView: ListItemListDetailBinding, var listener: ListRequestAdapter.OnClickListener):RecyclerView.ViewHolder(itemView.root) {
    fun bind(docModel:dataList){
        itemView.date.text = docModel.date
        itemView.reson.text = docModel.reson
        itemView.project_no.text = docModel.projectNo
        itemView.from.text = docModel.from
        itemView.to.text = docModel.to
        itemView.amoumt.text = "฿${docModel.amount}"
        //onclick
        itemView.rootView.setOnClickListener {
            listener?.onClick(docModel)
        }//onclick
        itemView.rootView.setOnLongClickListener {
            listener?.onLongClick(docModel)
            return@setOnLongClickListener true
        }
    }//bind
}