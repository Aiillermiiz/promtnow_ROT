package com.example.promtnow_rot.request

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Slide
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.promtnow_rot.R
import com.example.promtnow_rot.add.AddActivity
import com.example.promtnow_rot.databinding.ActivityRequestBinding
import com.example.promtnow_rot.homepage.InfoUser
import com.example.promtnow_rot.recycleview.RequestAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_request.*
import kotlinx.android.synthetic.main.dialog.view.*
import kotlinx.android.synthetic.main.dialog_menu.*

class RequestActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var binding : ActivityRequestBinding
    lateinit var dataOfForm : DataOfForm
    var requestState  = RequestState.APPROVED
    var select_month = ""
    var select_year = ""
    lateinit var requestAdapter :RequestAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    enum class RequestState{
        APPROVED,REJECTED,PENDING
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_request)
        //set state
        requestState = intent.getSerializableExtra("state_request") as RequestState
        setState(requestState)
        //...
        //------------------------------------------------------------------ SPINNER ---------------
        //spinner month
        val spinner_month = binding.spinnerMonth
        //create value from resource
        val adapter_month = ArrayAdapter.createFromResource(this,R.array.month,android.R.layout.simple_spinner_dropdown_item)
        spinner_month.setPopupBackgroundResource(R.drawable.card_dropdown)
        //set dropdown
        adapter_month.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinner_month.adapter = adapter_month
        //when select
        spinner_month.onItemSelectedListener = this

        ////////////////////////////////////////////////////////////////////////////////////////////

        //spinner month
        val spinner_year = binding.spinnerYear
        //create value from resource
        val adapter_year = ArrayAdapter.createFromResource(this,R.array.year,android.R.layout.simple_spinner_dropdown_item)
        spinner_month.setPopupBackgroundResource(R.drawable.card_dropdown)
        //set dropdown
        adapter_year.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinner_year.adapter = adapter_year
        //when select
        spinner_year.onItemSelectedListener = this
        //__________________________________________________________________________________________
        //------------------------------------------------------------------ ADAPTER ---------------

                requestAdapter = RequestAdapter(object : RequestAdapter.OnClickListener{
                    override fun onClick(data: DataOfForm) {
                        when(requestState){
                            RequestState.APPROVED -> {
                                startActivity(Intent(baseContext,AddActivity::class.java).apply {
                                    putExtra("state_list",AddActivity.ListRequestState.APPROVE)
                                })
                            }//approve
                            RequestState.REJECTED -> {
                                startActivity(Intent(baseContext,AddActivity::class.java).apply {
                                    putExtra("state_list",AddActivity.ListRequestState.REJECT)
                                })
                            }//reject
                            RequestState.PENDING -> {
                                startActivity(Intent(baseContext,AddActivity::class.java).apply {
                                    putExtra("state_list",AddActivity.ListRequestState.PENDING)
                                })
                            }//pending
                        }//when
                    }//onclick
                    override fun onLongClick(data: DataOfForm) {
                        when(requestState){
                            RequestState.APPROVED -> {

                            }
                            else -> {
                                // Initialize a new layout inflater instance
                                val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                                // Inflate a custom view using layout inflater
                                val view = inflater.inflate(R.layout.dialog_menu,null)
                                // Initialize a new instance of popup window
                                val popupWindow = PopupWindow(
                                    view, // Custom view to show in popup window
                                    LinearLayout.LayoutParams.MATCH_PARENT, // Width of popup window
                                    LinearLayout.LayoutParams.WRAP_CONTENT // Window height
                                )
                                val space = view.findViewById<RelativeLayout>(R.id.space)
                                val delete = view.findViewById<TextView>(R.id.delete)
                                //----------------------------------------------------- onclick ----
                                space.setOnClickListener {
                                    popupWindow.dismiss()
                                }
                                delete.setOnClickListener {
                                    popupWindow.dismiss()
                                    val DialogView = LayoutInflater.from(this@RequestActivity).inflate(R.layout.dialog, null)
                                    val builder = AlertDialog.Builder(this@RequestActivity)
                                    val title = TextView(this@RequestActivity)
                                    title.text = "ต้องการลบเอกสารคำร้องใช่หรือไม่"
                                    title.setPadding(50, 50, 50, 50);
                                    title.textSize = 20F;
                                    builder.setCustomTitle(title)
                                    builder.setView(DialogView)
                                    val mAlertDialog = builder.show()
                                    DialogView.btn_yes.setOnClickListener {
                                        requestAdapter.delete(data)
                                        deleteRequest(data.dateRequest)
                                        mAlertDialog.dismiss()
                                    }
                                    DialogView.btn_no.setOnClickListener {
                                        mAlertDialog.dismiss()
                                    }
                                }
                                //__________________________________________________________________
                                // Finally, show the popup window on app
                                TransitionManager.beginDelayedTransition(root)
                                popupWindow.showAtLocation(root,Gravity.CENTER,0,0)
                            }//else
                        }//when
                    }//onLongClick
                })

        viewManager = GridLayoutManager(this,3)
        approve_recycle.apply {
            layoutManager = viewManager
            when(requestState){
                RequestState.APPROVED -> {getRequest("approve")}
                RequestState.REJECTED -> {getRequest("reject")}
                RequestState.PENDING -> {getRequest("pending")}
            }
            adapter = requestAdapter
        }
        //__________________________________________________________________________________________
        //-------------------------------------------------------------------- ONCLICK -------------
        binding.approvedBtnBack.setOnClickListener {
            onBackPressed()
        }

        //__________________________________________________________________________________________
    }
    //----------------------------------------------------------------- FUN SET STATE --------------
    private fun setState(state:RequestState){
        when(state){
            RequestState.APPROVED -> {
                binding.textTop.text = "คำร้องที่อนุมัติเเล้ว"
                binding.relProSet.text = "คุณสามารดูรายละเอียดคำร้องได้เท่านั้น"
                binding.icon.setBackgroundResource(R.drawable.approve_icon_check)
            }
            RequestState.REJECTED -> {
                binding.textTop.text = "คำร้องที่ถูกปฏิเสธ"
                binding.relProSet.text = "คุณสามารถลบหรือส่งคำร้องได้อีกครั้ง"
                binding.icon.setBackgroundResource(R.drawable.reject_icon_reject)
            }
            RequestState.PENDING -> {
                binding.textTop.text = "คำร้องที่รอดำเนินการ"
                binding.relProSet.text = "คุณสามารถลบหรือเเก้ไขคำร้องได้"
                binding.icon.setBackgroundResource(R.drawable.pending_icon_pending)
            }
        }
    }
    //______________________________________________________________________________________________
    //---------------------------------------------------------- SELECT SPINNER --------------------
    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when(parent!!.id){
            R.id.spinner_month -> {
                select_month = parent.getItemAtPosition(position).toString()
                binding.txtMonth.text = select_month
            }
            R.id.spinner_year -> {
                select_year = parent.getItemAtPosition(position).toString()
                binding.txtYear.text = select_year
            }
        }
    }
    //______________________________________________________________________________________________
    //----------------------------------------------------------------------- GET REQUEST FROM DB --
    private fun getRequest(state:String){
        val db = Firebase.firestore
        val data = db.collection("Users account")
        data.whereEqualTo("gmail",InfoUser.gmail)
            .get()
            .addOnSuccessListener { docs ->
                for (doc in docs){
                    data.document(doc.id).collection("form")
                        .whereEqualTo("state",state)
                        .get()
                        .addOnSuccessListener { sub_form_docs ->
                            for (doc2 in sub_form_docs){
                                requestAdapter.addFormData(DataOfForm(doc2.data["date_request"].toString()
                                    ,doc2.data["month"].toString()
                                    ,doc2.data["year"].toString()
                                    ,doc2.data["state"].toString()))
                            }//for
                            Log.d("TAG", "DocumentSnapshot successfully updated!")
                        }//success
                        .addOnFailureListener { e ->
                            Log.w("TAG", "Error updating document", e)
                        }//fail
                }//for
            }//success
    }
    //______________________________________________________________________________________________
    //---------------------------------------------------------------------- DELETE REQUEST IN DB --
    private fun deleteRequest(date:String){
        val db = Firebase.firestore
        val col = db.collection("Users account")
        col.whereEqualTo("gmail",InfoUser.gmail)
            .get()
            .addOnSuccessListener { docs ->
                for(doc in docs){
                    col.document(doc.id).collection("form")
                        .whereEqualTo("date_request",date)
                        .get()
                        .addOnSuccessListener { form_doc ->
                            for(doc2 in form_doc){
                                col.document(doc.id).collection("form")
                                    .document(doc2.id).update("state","delete")
                            }//for
                        }//success
                }
            }//success
    }
    //______________________________________________________________________________________________
}
