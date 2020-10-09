package com.example.promtnow_rot.homepage

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import com.example.promtnow_rot.R
import com.example.promtnow_rot.R.drawable
import com.example.promtnow_rot.add.AddActivity
import com.example.promtnow_rot.databinding.ActivityHomePageBinding
import com.example.promtnow_rot.money.MoneyActivity
import com.example.promtnow_rot.request.RequestActivity
import com.example.promtnow_rot.setting.SettingActivity
import com.github.mikephil.charting.data.PieEntry
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.karumi.dexter.Dexter
import io.grpc.InternalChannelz.id
import kotlinx.android.synthetic.main.dialog.view.*
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*
import javax.sql.DataSource
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class HomePageActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding : ActivityHomePageBinding
    var list = ArrayList<PieEntry>()
    var numPending = 0
    var numApprove = 0
    var numReject = 0
    var alertPen = 0
    var alertApp = 0
    var alertRej = 0
    var checkRequest = false
    //data user
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home_page)
        //set text status bar black
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        //
        checkRequest()
        countRequest()
        getAlert()
        //set date
        binding.name.text = "${InfoUser.name} ${InfoUser.lname}"
        //get month
        val sdf = SimpleDateFormat("MMMM", Locale.US)
        binding.date.text = sdf.format(Date())
//        loading()
        //-------------------------------------------------------------------- ONCLICK -------------
        binding.mainSetting.setOnClickListener(this)
        binding.mainAdd.setOnClickListener(this)
        binding.mainApprove.setOnClickListener(this)
        binding.mainPending.setOnClickListener(this)
        binding.mainReject.setOnClickListener(this)
        binding.mainEditRequest.setOnClickListener(this)
//        binding.mainMoney.setOnClickListener(this)
        binding.txtPending.setOnClickListener(this)
        binding.txtApprove.setOnClickListener(this)
        binding.txtReject.setOnClickListener(this)
        binding.txtAll.setOnClickListener(this)
        binding.deleteRequest.setOnClickListener(this)
        //__________________________________________________________________________________________
    }

    //-------------------------------------------------------------------- FUN ONCLICK -------------
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.main_setting -> {
                startActivityForResult(Intent(this, SettingActivity::class.java),3)
            }
            R.id.main_add -> {
                startActivityForResult(Intent(this, AddActivity::class.java).apply {
                    putExtra("state_list",AddActivity.ListRequestState.ADD)
                },1)
                binding.cardRequest.postDelayed({
                    binding.relRequest.visibility = View.INVISIBLE
                    binding.relLoad.visibility = View.INVISIBLE
                    binding.cardRequest.visibility = View.VISIBLE
                },800)
            }
            R.id.main_approve -> {
                startActivityForResult(Intent(this, RequestActivity::class.java).apply {
                    putExtra("state_request",RequestActivity.RequestState.APPROVED)
                },2)
                Handler().postDelayed({ binding.alertApp.visibility = View.INVISIBLE},800)
            }
            R.id.main_pending -> {
                startActivityForResult(Intent(this, RequestActivity::class.java).apply {
                    putExtra("state_request",RequestActivity.RequestState.PENDING)
                },2)
                Handler().postDelayed({ binding.alertPen.visibility = View.INVISIBLE},800)
            }
            R.id.main_reject -> {
                startActivityForResult(Intent(this, RequestActivity::class.java).apply {
                    putExtra("state_request",RequestActivity.RequestState.REJECTED)
                },2)
                Handler().postDelayed({ binding.alertRej.visibility = View.INVISIBLE},800)
            }
            R.id.main_edit_request -> {
                startActivityForResult(Intent(this, AddActivity::class.java).apply {
                    putExtra("state_list",AddActivity.ListRequestState.ADD)
                },1)
            }
//            R.id.main_money -> {
//                startActivity(Intent(this,MoneyActivity::class.java))
//            }
            R.id.txt_pending -> {
                Toast.makeText(this,"Pending ${numPending}",Toast.LENGTH_LONG).show()
            }
            R.id.txt_approve -> {
                Toast.makeText(this,"Approved ${numApprove}",Toast.LENGTH_LONG).show()
            }
            R.id.txt_reject -> {
                Toast.makeText(this,"Rejected ${numReject}",Toast.LENGTH_LONG).show()
            }
            R.id.txt_all -> {
                Toast.makeText(this,"All request ${numPending+numApprove+numReject}",Toast.LENGTH_LONG).show()
            }
            R.id.delete_request -> {
                //Inflate the dialog with custom view
                val DialogView = LayoutInflater.from(this).inflate(R.layout.dialog, null)
                val title = TextView(this)
                title.text = "Do you want to delete request?"
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
                    deleteRequest()
                    mAlertDialog.dismiss()
                }
                //button cancel
                DialogView.btn_no.setOnClickListener {
                    //dismiss dialog
                    mAlertDialog.dismiss()
                }
            }
        }//when
    }
    //______________________________________________________________________________________________
    //----------------------------------------------------------------------- ACTIVITY RESULT ------
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 1 = add
        if (requestCode == 1 && resultCode == Activity.RESULT_OK){
            setAlert("pending")
            binding.cardRequest.visibility = View.INVISIBLE
            binding.relRequest.visibility = View.VISIBLE
            countRequest()
            binding.alertPen.visibility = View.VISIBLE
        }
        // 2 = pending
        else if(requestCode == 2 && resultCode == Activity.RESULT_CANCELED){

            data.let {
                val id =  it?.getStringExtra("alert")
                Log.d("tag",id)
                countRequest()
                clearAlert(id)
            }//let
        }
        // 3 = setting
        else if(requestCode == 3 && resultCode == Activity.RESULT_CANCELED){
            binding.name.text = "${InfoUser.name} ${InfoUser.lname}"
        }
    }
    //______________________________________________________________________________________________
    //-------------------------------------------------------------- COUNT REQUEST -----------------
    private fun countRequest(){
        numPending = 0
        numApprove = 0
        numReject = 0
        val db = Firebase.firestore
        val data = db.collection("Users account")
        data.whereEqualTo("staff_code",InfoUser.staff_code)
            .get()
            .addOnSuccessListener { docs ->
                for(doc in docs){
                    data.document(doc.id)
                        .collection("form")
                        .get()
                        .addOnSuccessListener { doc_form ->
                            for(doc2 in doc_form){
                                when(doc2.data["state"]){
                                    "pending" -> {
                                        numPending++
                                    }
                                    "reject" -> {
                                        numReject++
                                    }
                                    "approve"-> {
                                        numApprove++
                                    }
                                }//when
                            }//for
                            //set data to text
                            binding.numPending.text = numPending.toString()
                            binding.numApprove.text = numApprove.toString()
                            binding.numReject.text = numReject.toString()
                            binding.numAll.text = "${numPending+numApprove+numReject}"
                            //set value to chart rings
                            binding.rings.apply {
                                //set pending
                                setRingInnerFirstProgress(calculatePercentage(numPending+numApprove+numReject,numPending))
                                //set approve
                                setRingInnerSecondProgress(calculatePercentage(numPending+numApprove+numReject,numApprove))
                                //set reject
                                setRingInnerThirdProgress(calculatePercentage(numPending+numApprove+numReject,numReject))
                            }//apply
                        }
                }//for
            }//success
    }
    //______________________________________________________________________________________________
    //------------------------------------------------------------ GET ALERT -----------------------
    private fun getAlert(){
        alertApp = 0
        alertPen = 0
        alertRej = 0
        val db = Firebase.firestore
        val data = db.collection("Users account")
        data.whereEqualTo("staff_code",InfoUser.staff_code)
            .get()
            .addOnSuccessListener { docs ->
                for(doc in docs){
                    data.document(doc.id)
                        .collection("alert")
                        .get()
                        .addOnSuccessListener { docs_alert ->
                            for(doc2 in docs_alert){
                                when(doc2.id){
                                    "approve" -> {
                                        alertApp = doc2.data["number"].toString().toInt()
                                    }
                                    "pending" -> {
                                        alertPen = doc2.data["number"].toString().toInt()
                                    }
                                    "reject" -> {
                                        alertRej = doc2.data["number"].toString().toInt()
                                    }
                                }//when
                            }//for
                            showAlertApp()
                            showAlertPen()
                            showAlertRej()
                        }
                }//for
            }
    }
    //______________________________________________________________________________________________
    private fun deleteRequest(){
        val db = Firebase.firestore
        val data = db.collection("Users account")
        data.whereEqualTo("staff_code",InfoUser.staff_code)
            .get()
            .addOnSuccessListener { docs ->
                for(doc in docs){
                    data.document(doc.id)
                        .collection("form")
                        .whereEqualTo("state","waiting")
                        .get()
                        .addOnSuccessListener { form_doc ->
                            for(doc2 in form_doc){
                                data.document(doc.id)
                                    .collection("form")
                                    .document(doc2.id)
                                    .collection("list_form")
                                    .get()
                                    .addOnSuccessListener { docs_list ->
                                        for(docs3 in docs_list){
                                            data.document(doc.id)
                                                .collection("form")
                                                .document(doc2.id)
                                                .collection("list_form")
                                                .document(docs3.id)
                                                .delete()
                                            //...
                                            data.document(doc.id)
                                                .collection("form")
                                                .document(doc2.id)
                                                .delete()
                                        }
                                    }
                            }//for
                            binding.cardRequest.visibility = View.INVISIBLE
                            binding.relRequest.visibility = View.VISIBLE
                        }//success
                }
            }
    }
    //------------------------------------------------------------ SET ALERT -----------------------
    private fun setAlert(id:String?){
        val db = Firebase.firestore
        val data = db.collection("Users account")
        data.whereEqualTo("staff_code",InfoUser.staff_code)
            .get()
            .addOnSuccessListener { docs ->
                for(doc in docs){
                    data.document(doc.id)
                        .collection("alert")
                        .get()
                        .addOnSuccessListener { docs_alert ->
                            for(doc2 in docs_alert){
                                if(doc2.id == id){
                                    val getNum = doc2.data["number"].toString().toInt()
                                    var sum = getNum + 1
                                    data.document(doc.id).collection("alert").document(id).update("number",sum)
                                }
                            }//for
                            getAlert()
                        }
                }//for
            }
    }
    //______________________________________________________________________________________________
    //------------------------------------------------------------ CLEAR ALERT -----------------------
    private fun clearAlert(id:String?){
        val db = Firebase.firestore
        val data = db.collection("Users account")
        data.whereEqualTo("staff_code",InfoUser.staff_code)
            .get()
            .addOnSuccessListener { docs ->
                for(doc in docs){
                    data.document(doc.id)
                        .collection("alert")
                        .get()
                        .addOnSuccessListener { docs_alert ->
                            for(doc2 in docs_alert){
                                if(doc2.id == id){
                                    data.document(doc.id).collection("alert").document(id).update("number",0)
                                }
                            }//for
                        }
                }//for
            }
    }
    //______________________________________________________________________________________________
    //back press
    override fun onBackPressed() {

    }
    //check have form state waiting?
    private fun checkRequest(){
        binding.rotateLoading.start()
        val db = Firebase.firestore
        val data = db.collection("Users account")
        data.whereEqualTo("staff_code",InfoUser.staff_code)
            .get()
            .addOnSuccessListener { docs ->
                for(doc in docs){
                    data.document(doc.id)
                        .collection("form")
                        .whereEqualTo("state","waiting")
                        .get()
                        .addOnSuccessListener { docs_form ->
                            for (doc2 in docs_form){
                                checkRequest = true
                                Handler().postDelayed({
                                    binding.rotateLoading.stop()
                                    binding.relLoad.visibility = View.INVISIBLE},150)
                                hideCard(checkRequest)
                            }//for
                            hideCard(checkRequest)
                        }
                }//for
            }//success
    }
    //hide card request
    private fun hideCard(hide:Boolean){
        if(hide){
            Handler().postDelayed({ binding.cardRequest.visibility = View.VISIBLE},150)
        }else{
            binding.relRequest.visibility = View.VISIBLE
        }//else
    }
    //show alert approve
    private fun showAlertApp(){
        if (alertApp > 0){
            binding.alertApp.text = alertApp.toString()
            binding.alertApp.visibility = View.VISIBLE
        }else{
            binding.alertApp.visibility = View.INVISIBLE
        }
    }
    //show alert pending
    private fun showAlertPen(){
        if(alertPen > 0){
            binding.alertPen.text = alertPen.toString()
            binding.alertPen.visibility = View.VISIBLE
        }else{
            binding.alertPen.visibility = View.INVISIBLE
        }
    }
    //show alert reject
    private fun showAlertRej(){
        if(alertRej > 0){
            binding.alertRej.text = alertRej.toString()
            binding.alertRej.visibility = View.VISIBLE
        }else{
            binding.alertRej.visibility = View.INVISIBLE
        }
    }
    //---------------------------------------------------------------- CALCULATE PERCENTAGE --------
    private fun calculatePercentage(all:Int,value:Int):Float{
        if(all == 0 && value == 0){
            return 0f
        }
        return  ((value*100)/all).toFloat()
    }
    //______________________________________________________________________________________________

}