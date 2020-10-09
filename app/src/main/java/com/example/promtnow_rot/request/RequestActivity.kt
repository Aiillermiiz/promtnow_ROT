package com.example.promtnow_rot.request

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.promtnow_rot.R
import com.example.promtnow_rot.add.AddActivity
import com.example.promtnow_rot.databinding.ActivityRequestBinding
import com.example.promtnow_rot.homepage.InfoUser
import com.example.promtnow_rot.homepage.InfoUser.position
import com.example.promtnow_rot.recycleview.RequestAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_request.*
import kotlinx.android.synthetic.main.dialog.view.*

class RequestActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var binding : ActivityRequestBinding
    var month = ""
    var year = ""
    var requestState  = RequestState.APPROVED
    var stateRequest = ""
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
        //spinner department
        val spinner_month = binding.spinnerMonth
        //create value from resource
        val adapter_dep = ArrayAdapter.createFromResource(this,R.array.month,android.R.layout.simple_spinner_dropdown_item)
        spinner_month.setPopupBackgroundResource(R.drawable.card_dropdown)
        //set dropdown
        adapter_dep.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinner_month.adapter = adapter_dep
        //when select
        spinner_month.onItemSelectedListener = this

        //spinner position
        val spinner_year = binding.spinnerYear
        spinner_year.setPopupBackgroundResource(R.drawable.card_dropdown)
        val adapter_pos = ArrayAdapter.createFromResource(this,R.array.year,android.R.layout.simple_spinner_item)
        adapter_pos.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinner_year.adapter = adapter_pos
        spinner_year.onItemSelectedListener = this
        //__________________________________________________________________________________________
        //------------------------------------------------------------------ ADAPTER ---------------

                requestAdapter = RequestAdapter(object : RequestAdapter.OnClickListener{
                    override fun onClick(data: DataOfForm) {
                        when(requestState){
                            RequestState.APPROVED -> {
                                startActivity(Intent(baseContext,AddActivity::class.java).apply {
                                    putExtra("state_list",AddActivity.ListRequestState.APPROVE)
                                    putExtra("id",data.id)
                                })
                            }//approve
                            RequestState.REJECTED -> {
                                startActivityForResult(Intent(baseContext,AddActivity::class.java).apply {
                                    putExtra("state_list",AddActivity.ListRequestState.REJECT)
                                    putExtra("id",data.id)
                                },0)
                            }//reject
                            RequestState.PENDING -> {
                                startActivity(Intent(baseContext,AddActivity::class.java).apply {
                                    putExtra("state_list",AddActivity.ListRequestState.PENDING)
                                    putExtra("id",data.id)
                                })
                            }//pending
                        }//when
                    }//onclick
                    @RequiresApi(Build.VERSION_CODES.P)
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
                                val delete = view.findViewById<RelativeLayout>(R.id.delete)
                                //----------------------------------------------------- onclick ----
                                space.setOnClickListener {
                                    setTheme(R.style.AppTheme);
                                    popupWindow.dismiss()
                                }
                                delete.setOnClickListener {
                                    popupWindow.dismiss()
                                    val DialogView = LayoutInflater.from(this@RequestActivity).inflate(R.layout.dialog, null)
                                    val builder = AlertDialog.Builder(this@RequestActivity)
                                    val title = TextView(this@RequestActivity)
                                    title.text = "Do you want to delete the request?"
                                    title.setPadding(50, 50, 50, 50);
                                    title.textSize = 20F;
                                    builder.setCustomTitle(title)
                                    builder.setView(DialogView)
                                    val mAlertDialog = builder.show()
                                    DialogView.btn_yes.setOnClickListener {
                                        requestAdapter.delete(data)
                                        deleteRequest(data.id,data.dateRequest)
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

        viewManager = LinearLayoutManager(this)
        approve_recycle.apply {
            layoutManager = viewManager
            adapter = requestAdapter
        }
        //__________________________________________________________________________________________
        //-------------------------------------------------------------------- ONCLICK -------------
        binding.approvedBtnBack.setOnClickListener {
            onBackPressed()
        }
        //__________________________________________________________________________________________
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == RESULT_OK){
            val id = data!!.getIntExtra("id",0)
            Log.d("tag",id.toString())
            requestAdapter.deleteFromId(id)
        }
    }
    //----------------------------------------------------------------- FUN SET STATE --------------
    private fun setState(state:RequestState){
        when(state){
            RequestState.APPROVED -> {
                binding.textTop.text = "Approved"
                binding.relTop.setBackgroundResource(R.color.approve)
                setResult(RESULT_CANCELED,Intent().putExtra("alert", "approve"))
                stateRequest = "approve"
            }
            RequestState.REJECTED -> {
                binding.textTop.text = "Rejected"
                binding.relTop.setBackgroundResource(R.color.reject)
                setResult(RESULT_CANCELED,Intent().putExtra("alert", "reject"))
                stateRequest = "reject"
            }
            RequestState.PENDING -> {
                binding.textTop.text = "Pending"
                binding.relTop.setBackgroundResource(R.color.pending)
                setResult(RESULT_CANCELED,Intent().putExtra("alert", "pending"))
                stateRequest = "pending"
            }
        }
    }
    //______________________________________________________________________________________________
    //---------------------------------------------------------------------- DELETE REQUEST IN DB --
    private fun deleteRequest(id:Int,date:String){
        val db = Firebase.firestore
        val col = db.collection("Users account")
        col.whereEqualTo("gmail",InfoUser.gmail)
            .get()
            .addOnSuccessListener { docs ->
                for(doc in docs){
                    col.document(doc.id)
                        .collection("form")
                        .whereEqualTo("date_request",date)
                        .whereEqualTo("id",id)
                        .get()
                        .addOnSuccessListener { form_doc ->
                            for(doc2 in form_doc){
                                col.document(doc.id)
                                    .collection("form")
                                    .document(doc2.id)
                                    .collection("list_form")
                                    .get()
                                    .addOnSuccessListener { docs_list ->
                                        for(doc3 in docs_list){
                                            col.document(doc.id)
                                                .collection("form")
                                                .document(doc2.id)
                                                .delete()
                                            //...
                                            col.document(doc.id)
                                                .collection("form")
                                                .document(doc2.id)
                                                .collection("list_form")
                                                .document(doc3.id)
                                                .delete()
                                        }

                                    }
                            }//for
                        }//success
                }
            }//success
    }
    //______________________________________________________________________________________________
    //on select
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        when(parent!!.id){
            R.id.spinner_month -> {
                Log.d("tag",month)
                month = parent.getItemAtPosition(pos).toString()
                binding.txtMonth.text = month
                searchRequest(month,year,stateRequest)
            }
            R.id.spinner_year -> {
                Log.d("tag",year)
                year = parent.getItemAtPosition(pos).toString()
                binding.txtYear.text = year
                searchRequest(month,year,stateRequest)
            }
        }//when
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
    //------------------------------------------------------------------ SEARCH --------------------
    private fun searchRequest(m:String,y:String,state:String){
        requestAdapter.deleteAll()
        binding.rotateLoading.start()
        val db = Firebase.firestore
        val data = db.collection("Users account")
        data.whereEqualTo("staff_code",InfoUser.staff_code)
            .get()
            .addOnSuccessListener { docs ->
                for(doc in docs){
                    data.document(doc.id)
                        .collection("form")
                        .whereEqualTo("state",state)
                        .get()
                        .addOnSuccessListener { docs_form ->
                            for(doc2 in docs_form){
                                when{
                                    m == "All month" && y != "All year"-> {
                                        if(doc2.data["year"].toString().equals(y,true)){
                                            requestAdapter.addFormData(DataOfForm(
                                                doc2.data["date_request"].toString()
                                                ,doc2.data["month"].toString()
                                                ,doc2.data["year"].toString()
                                                ,doc2.data["state"].toString()
                                                ,doc2.data["id"].toString().toInt()))
                                        }
                                    }//...
                                    y == "All year" && m != "All month" -> {
                                        if(doc2.data["month"].toString().equals(m,true)){
                                            requestAdapter.addFormData(DataOfForm(
                                                doc2.data["date_request"].toString()
                                                ,doc2.data["month"].toString()
                                                ,doc2.data["year"].toString()
                                                ,doc2.data["state"].toString()
                                                ,doc2.data["id"].toString().toInt()))
                                        }
                                    }//...
                                    y == "All year" && m == "All month" -> {
                                        Log.d("tag","all y m db")
                                        Log.d("tag",y)
                                            requestAdapter.addFormData(DataOfForm(
                                                doc2.data["date_request"].toString()
                                                ,doc2.data["month"].toString()
                                                ,doc2.data["year"].toString()
                                                ,doc2.data["state"].toString()
                                                ,doc2.data["id"].toString().toInt()))
                                    }
                                    else -> {
                                        if(doc2.data["month"].toString().equals(m,true) && doc2.data["year"].toString().equals(y,true)){
                                            requestAdapter.addFormData(DataOfForm(
                                                doc2.data["date_request"].toString()
                                                ,doc2.data["month"].toString()
                                                ,doc2.data["year"].toString()
                                                ,doc2.data["state"].toString()
                                                ,doc2.data["id"].toString().toInt()))
                                        }
                                    }//...
                                }//when

                            }//for
                            binding.rotateLoading.stop()
                            binding.rotateLoading.visibility = View.INVISIBLE
                            binding.approveRecycle.visibility = View.VISIBLE
                        }
                }//for
            }
    }
    //______________________________________________________________________________________________
}
