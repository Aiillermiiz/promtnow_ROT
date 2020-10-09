package com.example.promtnow_rot.add

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.promtnow_rot.Prefs
import com.example.promtnow_rot.R
import com.example.promtnow_rot.databinding.ActivityAddBinding
import com.example.promtnow_rot.homepage.InfoUser
import com.example.promtnow_rot.recycleview.ListRequestAdapter
import com.example.promtnow_rot.recycleview.dataList
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.dialog.view.*
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding
    var listState = ListRequestState.ADD
    var requestDate:String = SimpleDateFormat("dd-MMM-yyyy", Locale.US).format(Date())
    var month: String = SimpleDateFormat("MMMM", Locale.US).format(Date())
    var year:String = SimpleDateFormat("yyyy", Locale.US).format(Date())
    var count = 0
    var idRequest = 0
    //define variable for recycle view
    private lateinit var viewManager: RecyclerView.LayoutManager
    var data = mutableListOf<dataList>()
    lateinit var listRequestAdapter: ListRequestAdapter
    enum class ListRequestState{
        ADD,APPROVE,PENDING,REJECT
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add)
        //get extra
        listState = intent.getSerializableExtra("state_list") as ListRequestState
        idRequest = intent.getIntExtra("id",0)
        setState(listState)
        //----------------------------------------------------------------- ADAPTER ----------------
        listRequestAdapter = ListRequestAdapter(object :ListRequestAdapter.OnClickListener{
            override fun onClick(data: dataList) {
                val intent = Intent(baseContext, ActivityForm::class.java).apply {
                if(listState == ListRequestState.APPROVE){
                    putExtra("list_model",data)
                    putExtra("state", ActivityForm.PageState.SEE)
                }else{
                    Log.d("tag",data.toString())
                    putExtra("list_model",data)
                    putExtra("state", ActivityForm.PageState.EDIT_STATE)
                }//else
            }
            startActivityForResult(intent,1)
            }//onclick

            override fun onLongClick(data: dataList) {
                Log.d("tag",data.toString())
                when(listState){
                    ListRequestState.APPROVE -> {

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
                        view.findViewById<TextView>(R.id.txt_del).text = "Delete list"
                        //----------------------------------------------------- onclick ----
                        space.setOnClickListener {
                            popupWindow.dismiss()
                        }
                        delete.setOnClickListener {
                            popupWindow.dismiss()
                            val DialogView = LayoutInflater.from(this@AddActivity).inflate(R.layout.dialog, null)
                            val builder = AlertDialog.Builder(this@AddActivity)
                            val title = TextView(this@AddActivity)
                            title.text = "Want to delete a list of requests?"
                            title.setPadding(50, 50, 50, 50);
                            title.textSize = 20F;
                            builder.setCustomTitle(title)
                            builder.setView(DialogView)
                            val mAlertDialog = builder.show()
                            DialogView.btn_yes.setOnClickListener {
                                deleteListRequestInDB(Prefs(this@AddActivity).idForm,data.id)
                                listRequestAdapter.deleteListOfMutable(data)
                                sumAmount()
                                mAlertDialog.dismiss()
                            }
                            DialogView.btn_no.setOnClickListener {
                                mAlertDialog.dismiss()
                            }
                        }
                        //__________________________________________________________________
                        // Finally, show the popup window on app
                        TransitionManager.beginDelayedTransition(root_add)
                        popupWindow.showAtLocation(root_add,Gravity.CENTER,0,0)
                    }//else
                }
            }//on long click
        })
        viewManager = LinearLayoutManager(this)
        add_recycle_main.apply {
            layoutManager = viewManager
            adapter = listRequestAdapter
        }
        //__________________________________________________________________________________________
        //---------------------------------------------------------------- ONCLICK -----------------
        //click add
        binding.addBtnAdd.setOnClickListener {
            startActivityForResult(Intent(this,ActivityForm::class.java).apply {
                putExtra("state",ActivityForm.PageState.ADD_STATE)}, 0)
        }
        binding.addBtnSubmit.setOnClickListener {
            if(listRequestAdapter.itemCount == 0){
                Toast.makeText(this,"Without list of request", Toast.LENGTH_LONG).show()
            }else{
                //Inflate the dialog with custom view
                val DialogView = LayoutInflater.from(this).inflate(R.layout.dialog, null)
                val title = TextView(this)
                title.text = "Do you want to send request?"
                title.setPadding(50, 50, 50, 50);
                title.textSize = 20F;
                //AlertDialogBuilder
                val mBuilder = AlertDialog.Builder(this)
                    .setView(DialogView)
                    .setCustomTitle(title)
                //show dialog
                val mAlertDialog = mBuilder.show()
                //button ok
                DialogView.btn_yes.setOnClickListener {
                    mAlertDialog.dismiss()
                    // Initialize a new layout inflater instance
                    val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                    // Inflate a custom view using layout inflater
                    val view = inflater.inflate(R.layout.dialog_send_success,null)
                    // Initialize a new instance of popup window
                    val popupWindow = PopupWindow(
                        view, // Custom view to show in popup window
                        LinearLayout.LayoutParams.MATCH_PARENT, // Width of popup window
                        LinearLayout.LayoutParams.WRAP_CONTENT // Window height
                    )
                    // Finally, show the popup window on app
                    TransitionManager.beginDelayedTransition(root_add)
                    popupWindow.showAtLocation(root_add,Gravity.CENTER,0,0)
                    Handler().postDelayed({
                        when(listState){
                            ListRequestState.REJECT -> {
                                setRequestToDB(idRequest)
                                setResult(RESULT_OK,Intent().putExtra("id",idRequest))
                                Log.d("tag",idRequest.toString())
                                finish()
                            }
                            else -> {
                                setRequestToDB(Prefs(this).idForm)
                            }
                        }//when
                        //return result ok
                        setResult(Activity.RESULT_OK,Intent())
                        Prefs(this).haveRequest = false
                        finish()
                        popupWindow.dismiss()
                    },3000)

                }
                //button cancel
                DialogView.btn_no.setOnClickListener {
                    //dismiss dialog
                    mAlertDialog.dismiss()
                }
            }//else
        }
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        //__________________________________________________________________________________________
    }

    //------------------------------------------------------------------- FUN DATA FROM ACTIVITY ---
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 0) {
            Log.d("tag","add")
            data?.let {
                //get value
                val listData = it.getParcelableExtra<dataList>("LIST_DATA")
                //check null and send to adapter
                listRequestAdapter?.let {
                    it.addFormData(listData)
                }
                sumAmount()
                setListRequestTODB(listData)
                count = listRequestAdapter.itemCount
                hideText()
            }//let
        }//if
        else if(resultCode == 1){
            Log.d("tag","edit")
            data.let {
                //get value
                val listData = it!!.getParcelableExtra<dataList>("LIST_DATA")
                listRequestAdapter.updateForm(listData)
                when(listState){
                    ListRequestState.ADD -> {
                        updateList(Prefs(this@AddActivity).idForm,listData)
                    }
                    else -> {
                        updateList(idRequest,listData)
                    }
                }//when
                sumAmount()
            }
        }//else if
    }

    //______________________________________________________________________________________________
    //-------------------------------------------------------------- CREATE REQUEST ----------------
    private fun createRequest(){
        val db = Firebase.firestore
        val collUser = db.collection("Users account")
        collUser.whereEqualTo("staff_code",InfoUser.staff_code)
            .get()
            .addOnSuccessListener{ docs ->
                for(doc in docs){
                    //set data to variable
                    val makeData = hashMapOf("date_request" to requestDate
                        ,"state" to "waiting"
                        ,"month" to month
                        ,"year" to year
                        ,"id" to Prefs(this).idForm)
                    //set data to db
                    collUser.document(doc.id)
                        .collection("form")
                        .document("form-${month}-${year}-${Prefs(this).idForm}")
                        .set(makeData)
                }//for
            }
    }
    //______________________________________________________________________________________________
    //------------------------------------------------------------- SET LIST OF REQUEST TO DB ------
    private fun setListRequestTODB(listOfRequest:dataList){
        val db = Firebase.firestore
        val collUser = db.collection("Users account")
        collUser.whereEqualTo("staff_code",InfoUser.staff_code)
                .get()
                .addOnSuccessListener { docs->
                    for(doc in docs){
                        //set list
                        collUser.document(doc.id)
                            .collection("form")
                            .document("form-${month}-${year}-${Prefs(this).idForm}")
                            .collection("list_form")
                            .add(listOfRequest)
                    }//for
                    Prefs(this).idList++
            }
    }
    //______________________________________________________________________________________________
    //------------------------------------------------------------ DELETE LIST IN DB ---------------
    private fun deleteListRequestInDB(idForm:Int,id:String){
        val db = Firebase.firestore
        val coll = db.collection("Users account")
        coll.whereEqualTo("staff_code",InfoUser.staff_code)
            .get()
            .addOnSuccessListener { docs ->
                for (doc in docs){
                    coll.document(doc.id).collection("form")
                        .whereEqualTo("month",month)
                        .whereEqualTo("year",year)
                        .whereEqualTo("id",idForm)
                        .get()
                        .addOnSuccessListener { docs_form ->
                            for(doc2 in docs_form){
                                coll.document(doc.id)
                                    .collection("form")
                                    .document(doc2.id)
                                    .collection("list_form")
                                    .whereEqualTo("id",id)
                                    .get()
                                    .addOnSuccessListener { docs_list ->
                                        for(doc3 in docs_list){
                                            coll.document(doc.id)
                                                .collection("form")
                                                .document(doc2.id)
                                                .collection("list_form")
                                                .document(doc3.id).delete()
                                        }//for
                                        count = listRequestAdapter.itemCount
                                        hideText()
                                    }
                            }//for
                        }
                }//for
            }
    }
    //______________________________________________________________________________________________
    //------------------------------------------------------------------ SET REQUEST TO DATABASE ---
    private fun setRequestToDB(id:Int) {
        val db = Firebase.firestore
        db.collection("Users account").get().addOnSuccessListener { res ->
            for (doc in res) {
                val gmailEquals = doc.data["gmail"]?.toString().equals(InfoUser.gmail, true)
                if (gmailEquals) {
                    //set data
                    val makedata = hashMapOf("date_request" to requestDate
                                            ,"state" to "pending"
                                            ,"month" to month
                                            ,"year" to year
                                            ,"id" to id)
                    db.collection("Users account")
                        .document(doc.id)
                        .collection("form")
                        .document("form-${month}-${year}-${id}")
                        .set(makedata)
                }//if
            }//for
            Prefs(this).idForm++
            listRequestAdapter.deleteDataAll()
            Toast.makeText(this,"success", Toast.LENGTH_LONG).show()
        }//success
    }
    //______________________________________________________________________________________________
    //----------------------------------------------------------------------- GET REQUEST FROM DB --
    private fun getRequest(state:String,id:Int){
        binding.rotateLoading.start()
        val db = Firebase.firestore
        val data = db.collection("Users account")
        data.whereEqualTo("gmail",InfoUser.gmail)
            .get()
            .addOnSuccessListener { docs ->
                for (doc in docs){
                    data.document(doc.id)
                        .collection("form")
                        .whereEqualTo("state",state)
                        .whereEqualTo("id",id)
                        .get()
                        .addOnSuccessListener { sub_form_docs ->
                            for (doc2 in sub_form_docs){
                                data.document(doc.id)
                                    .collection("form")
                                    .document(doc2.id)
                                    .collection("list_form")
                                    .get()
                                    .addOnSuccessListener { list_docs ->
                                        for(doc3 in list_docs){
                                            listRequestAdapter.addFormData(dataList(
                                                 doc3.data["date"].toString()
                                                ,doc3.data["projectNo"].toString()
                                                ,doc3.data["reson"].toString()
                                                ,doc3.data["from"].toString()
                                                ,doc3.data["to"].toString()
                                                ,doc3.data["vehicle"].toString()
                                                ,doc3.data["description"].toString()
                                                ,doc3.data["type"].toString()
                                                ,doc3.data["amount"].toString().toInt()
                                                ,doc3.data["id"].toString()))
                                        }//for
                                        binding.rotateLoading.stop()
                                        binding.rotateLoading.visibility = View.INVISIBLE
                                        binding.addRecycleMain.visibility = View.VISIBLE
                                        sumAmount()
                                        //set boolean if have request
                                        count = listRequestAdapter.itemCount
                                        hideText()
                                    }
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
    //-------------------------------------------------------------------- UPDATE LIST -------------
    private fun updateList(idForm: Int,updateObj:dataList){
        val db = Firebase.firestore
        val data = db.collection("Users account")
        data.whereEqualTo("staff_code",InfoUser.staff_code)
            .get()
            .addOnSuccessListener { docs ->
                for (doc in docs){
                    data.document(doc.id).collection("form")
                        .whereEqualTo("month",month)
                        .whereEqualTo("year",year)
                        .whereEqualTo("id",idForm)
                        .get()
                        .addOnSuccessListener { docs_form ->
                            for(doc2 in docs_form){
                                data.document(doc.id)
                                    .collection("form")
                                    .document(doc2.id)
                                    .collection("list_form")
                                    .whereEqualTo("id",updateObj.id)
                                    .get()
                                    .addOnSuccessListener { docs_list ->
                                        for(doc3 in docs_list){
                                            val list = hashMapOf(
                                                "amount" to updateObj.amount
                                                ,"date" to updateObj.date
                                                ,"description" to updateObj.description
                                                ,"from" to updateObj.from
                                                ,"projectNo" to updateObj.projectNo
                                                ,"reson" to updateObj.reson
                                                ,"to" to updateObj.to
                                                ,"type" to updateObj.type
                                                ,"vehicle" to updateObj.vehicle
                                                ,"id" to updateObj.id)
                                            data.document(doc.id)
                                                .collection("form")
                                                .document(doc2.id)
                                                .collection("list_form")
                                                .document(doc3.id).set(list)
                                            Log.d("tag","success")
                                        }//for
                                    }
                            }//for
                        }
                }//for
            }
    }
    //______________________________________________________________________________________________
    //------------------------------------------------------------------------ FUN SET STATE -------
    private fun setState(state:ListRequestState){
        when(state){
            ListRequestState.ADD -> {
                binding.textTop.text = "Your request"
                //create request
                createRequest()
                //get request
                getRequest("waiting",Prefs(this).idForm)
            }
            ListRequestState.APPROVE -> {
                binding.textTop.text = "Approved"
                binding.addBtnSubmit.visibility = View.INVISIBLE
                binding.addBtnAdd.visibility = View.INVISIBLE
                binding.relTop.setBackgroundResource(R.color.approve)
                //get request
                getRequest("approve",idRequest)
            }
            ListRequestState.PENDING -> {
                binding.textTop.text = "Pending"
                binding.addBtnSubmit.visibility = View.INVISIBLE
                binding.relTop.setBackgroundResource(R.color.pending)
                //get request
                getRequest("pending",idRequest)
            }
            ListRequestState.REJECT -> {
                binding.textTop.text = "Rejected"
                binding.relTop.setBackgroundResource(R.color.reject)
                //get request
                getRequest("reject",idRequest)
                binding.addBtnSubmit.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.ic_send_black_24dp, 0)
            }
        }//when
    }
    //______________________________________________________________________________________________
    //hide not have list
    private fun hideText(){
        //check have list request
        if(count > 0){
            binding.relNotRequest.visibility = View.INVISIBLE
        }else{
            binding.relNotRequest.visibility = View.VISIBLE
        }//else
    }
    //get sum amount and change txt amount
    private fun sumAmount(){
        binding.addTotalAmount.text = "฿ ${listRequestAdapter.sumAmount()}"
    }
}
