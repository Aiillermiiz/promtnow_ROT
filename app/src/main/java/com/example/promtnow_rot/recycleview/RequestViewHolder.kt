package com.example.promtnow_rot.recycleview

import androidx.recyclerview.widget.RecyclerView
import com.example.promtnow_rot.R
import com.example.promtnow_rot.databinding.ListItemRequestBinding
import com.example.promtnow_rot.request.DataOfForm
import kotlinx.android.synthetic.main.list_item_request.view.*

class RequestViewHolder(itemView:ListItemRequestBinding, var listener: RequestAdapter.OnClickListener?):RecyclerView.ViewHolder(itemView.root) {
    fun bind(dataFormModel:DataOfForm){
        itemView.month.text = dataFormModel.month
        itemView.date.text = "(${dataFormModel.dateRequest})"
        when(dataFormModel.state){
            "pending" -> {
                itemView.color.setBackgroundResource(R.color.pending)
            }
            "approve" -> {
                itemView.color.setBackgroundResource(R.color.approve)
            }
            "reject" -> {
                itemView.color.setBackgroundResource(R.color.reject)
            }
        }//when
        //onclick
        itemView.rootView.setOnClickListener {
            listener?.onClick(dataFormModel)
        }//onclick
        itemView.rootView.setOnLongClickListener {
            listener?.onLongClick(dataFormModel)
            return@setOnLongClickListener true
        }
    }
}