package com.example.promtnow_rot.add

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    //define variable for recycle view
    var TotalAmount = 0
    private lateinit var viewManager: RecyclerView.LayoutManager
    var data = mutableListOf<dataList>()
    lateinit var listRequestAdapter: ListRequestAdapter
    enum class ListRequestState{
        ADD,APPROVE,PENDING,REJECT
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add)
        //ste state
        listState = intent.getSerializableExtra("state_list") as ListRequestState
        setState(listState)
        //...
        //----------------------------------------------------------------- ADAPTER ----------------
        listRequestAdapter = ListRequestAdapter(object :ListRequestAdapter.OnClickListener{
            override fun onClick(data: dataList) {
                val intent = Intent(baseContext, ActivityForm::class.java).apply {
                putExtra("list_model", data)
                if(listState == ListRequestState.APPROVE){
                    putExtra("state", ActivityForm.PageState.SEE)
                }else{
                    putExtra("state", ActivityForm.PageState.EDIT_STATE)
                }//else
            }
            startActivity(intent)
            }//onclick

            override fun onLongClick(data: dataList) {
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
                        val delete = view.findViewById<TextView>(R.id.delete)
                        //----------------------------------------------------- onclick ----
                        space.setOnClickListener {
                            popupWindow.dismiss()
                        }
                        delete.setOnClickListener {
                            popupWindow.dismiss()
                            val DialogView = LayoutInflater.from(this@AddActivity).inflate(R.layout.dialog, null)
                            val builder = AlertDialog.Builder(this@AddActivity)
                            val title = TextView(this@AddActivity)
                            title.text = "ต้องการลบรายการคำร้องใช่หรือไม่"
                            title.setPadding(50, 50, 50, 50);
                            title.textSize = 20F;
                            builder.setCustomTitle(title)
                            builder.setView(DialogView)
                            val mAlertDialog = builder.show()
                            DialogView.btn_yes.setOnClickListener {
                                listRequestAdapter.deleteListOfMutable(data)
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
            listRequestAdapter.addFormData(dataList("2-5-2563","","","","","","","",0))
            adapter = listRequestAdapter
        }
        //__________________________________________________________________________________________
        //---------------------------------------------------------------- SET DATA ----------------

        //__________________________________________________________________________________________
        //---------------------------------------------------------------- ONCLICK -----------------
        //click add
        binding.addBtnAdd.setOnClickListener {
            startActivityForResult(Intent(this,ActivityForm::class.java).apply {
                putExtra("state",ActivityForm.PageState.ADD_STATE)}, 0)
        }
        binding.addBtnSubmit.setOnClickListener {
            if(listRequestAdapter.itemCount == 0){
                Toast.makeText(this,"ไม่มีรายการคำร้อง", Toast.LENGTH_LONG).show()
            }else{
                //Inflate the dialog with custom view
                val DialogView = LayoutInflater.from(this).inflate(R.layout.dialog, null)
                val title = TextView(this)
                title.text = "ต้องการส่งเอกสารหรือไม่"
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
                    setRequestToDB(listRequestAdapter.InfoForm)
                    //return result ok
                    setResult(Activity.RESULT_OK,Intent())
                    finish()
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
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            data?.let {
                //get value
                var listData = it.getParcelableExtra<dataList>("LIST_DATA")
                //check null and send to adapter
                listRequestAdapter?.let {
                    it.addFormData(listData)
                }
                binding.addTotalAmount.text = "฿ ${listRequestAdapter.sumAmount()}"
            }//let
        }//if
    }

    //______________________________________________________________________________________________
    //------------------------------------------------------------------ SET REQUEST TO DATABASE ---
    private fun setRequestToDB(dataForm: MutableList<dataList>) {
        Log.d("tag",dataForm.toString())
        val db = Firebase.firestore
        db.collection("Users account").get().addOnSuccessListener { res ->
            for (doc in res) {
                val gmailEquals = doc.data["gmail"]?.toString().equals(InfoUser.gmail, true)
                if (gmailEquals) {
                    //set data
                    val makedata = hashMapOf("date_request" to requestDate
                                            ,"state" to "pending"
                                            ,"month" to month
                                            ,"year" to year)
                    db.collection("Users account").document(doc.id)
                        .collection("form").document("form-${month}-${year}").set(makedata)
                    //set list
                    dataForm.forEachIndexed { index, dataFormModel ->
                        db.collection("Users account").document(doc.id).collection("form")
                            .document("form-${month}-${year}").collection("list_form")
                            .add(dataFormModel)
                    }
                }//if
            }//for
            listRequestAdapter.deleteDataAll()
            Toast.makeText(this,"success", Toast.LENGTH_LONG).show()
        }//success
    }
    //______________________________________________________________________________________________
    //------------------------------------------------------------------------ FUN SET STATE -------
    fun setState(state:ListRequestState){
        when(state){
            ListRequestState.ADD -> {
                binding.textTop.text = "ใบเบิกค่าพาหนะ"
            }
            ListRequestState.APPROVE -> {
                binding.textTop.text = "คำร้องที่อนุมัติแล้ว"
                binding.addBtnSubmit.visibility = View.INVISIBLE
                binding.addBtnAdd.visibility = View.INVISIBLE
            }
            ListRequestState.PENDING -> {
                binding.textTop.text = "คำร้องที่รอดำเนินการ"
            }
            ListRequestState.REJECT -> {
                binding.textTop.text = "คำร้องที่ถูกปฏิเสธ"
                binding.addBtnSubmit.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.ic_send_black_24dp, 0)
            }
        }//when
    }
    //______________________________________________________________________________________________

}
