package com.example.promtnow_rot.profile

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.promtnow_rot.R
import com.example.promtnow_rot.databinding.ActivityEditProfileBinding
import com.example.promtnow_rot.homepage.InfoUser
import com.example.promtnow_rot.recycleview.dataList
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.dialog.view.*
import java.util.regex.Pattern.compile

class EditProfileActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var binding:ActivityEditProfileBinding
    var select_dep = ""
    var select_pos = ""
    var checkGmail = ""
    var checkStfcode = ""
    var data = mutableListOf<dataList>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)
        //------------------------------------------------------------------ SPINNER ---------------
        //spinner department
        val spinner_dep = binding.spinnerDep
        //create value from resource
        val adapter_dep = ArrayAdapter.createFromResource(this,R.array.department,android.R.layout.simple_spinner_dropdown_item)
        spinner_dep.setPopupBackgroundResource(R.drawable.card_dropdown)
        //set dropdown
        adapter_dep.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinner_dep.adapter = adapter_dep
        //when select
        spinner_dep.onItemSelectedListener = this

        //spinner position
        val spinner_pos = binding.spinnerPos
        spinner_pos.setPopupBackgroundResource(R.drawable.card_dropdown)
        val adapter_pos = ArrayAdapter.createFromResource(this,R.array.position,android.R.layout.simple_spinner_item)
        adapter_pos.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinner_pos.adapter = adapter_pos
        spinner_pos.onItemSelectedListener = this
        //__________________________________________________________________________________________
        //set data
        binding.name.setText(InfoUser.name)
        binding.lname.setText(InfoUser.lname)
        binding.gmail.setText(InfoUser.gmail)
        binding.stfCode.setText(InfoUser.staff_code)
        //---------------------------------------------------------------- ONCLICK -----------------
        binding.btnUpdate.setOnClickListener {
            if(checkEdittextEmpty()){
                Toast.makeText(this,"กรุณากรอกข้อมูลให้ครบทุกช่อง",Toast.LENGTH_LONG).show()
            }else if(!isEmailValid(binding.gmail.text.toString())){
                binding.gmail.error = "กรุณาระบุให้เป็นประเภทอีเมล"
            }else{
                val DialogView = LayoutInflater.from(this).inflate(R.layout.dialog, null)
                val builder = AlertDialog.Builder(this)
                val title = TextView(this)
                title.text = "ต้องการเเก้ไขข้อมูลส่วนตัวหรือไม่"
                title.setPadding(50, 50, 50, 50);
                title.textSize = 20F;
                builder.setCustomTitle(title)
                builder.setView(DialogView)
                val mAlertDialog = builder.show()
                //button yes
                DialogView.btn_yes.setOnClickListener {
                    updateProfile(binding.name.text.toString(),binding.lname.text.toString()
                        ,binding.gmail.text.toString(),binding.stfCode.text.toString()
                        ,select_dep,select_pos)
                }
                //button no
                DialogView.btn_no.setOnClickListener {
                    mAlertDialog.dismiss()
                }
            }//else
        }
        binding.profileBtnBack.setOnClickListener {
            onBackPressed()
        }
        //__________________________________________________________________________________________
        //---------------------------------------------------------------- TEXT CHANGE -------------
        binding.name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (binding.name.text.contains(" ")){
                        binding.name.error = "มีช่องว่าง"
                    }//if

            }
        })//text change
        binding.lname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (binding.lname.text.contains(" ")){
                        binding.lname.error = "มีช่องว่าง"
                    }//if

            }
        })//text change
        binding.gmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val inputGmail = binding.gmail.text.toString()
                validateGmail(inputGmail)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })//text change
        binding.stfCode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val inputStfcode = binding.stfCode.text.toString()
                validateStaffcode(inputStfcode)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (binding.stfCode.text.contains(" ")){
                        binding.stfCode.error = "มีช่องว่าง"
                    }//if

            }
        })//text change
        binding.dep.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })//text change
        binding.pos.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


            }
        })//text change
        //__________________________________________________________________________________________
    }
    //----------------------------------------------------------------- FUN UPDATE PROFILE ---------
    private fun updateProfile(name:String,lname:String, gmail:String,stf:String,dep:String,pos:String){
        val db = Firebase.firestore
        val data = db.collection("Users account")
        data.get().addOnSuccessListener { result ->
            for(document in result){
                if (InfoUser.staff_code == document.data["staff_code"]){
                    data.document(document.id).apply {
                        update("name", name)
                        update("lname", lname)
                        update("gmail", gmail)
                        update("staff_code", stf)
                        update("department", dep)
                        update("position", pos)
                            .addOnSuccessListener {
                                InfoUser.name = name
                                InfoUser.lname = lname
                                InfoUser.gmail = gmail
                                InfoUser.staff_code = stf
                                InfoUser.department = dep
                                InfoUser.position = pos
                                Log.d("TAG", "DocumentSnapshot successfully updated!")
                                Toast.makeText(this@EditProfileActivity,"อัพเดตสำเร็จ",Toast.LENGTH_LONG).show()
                                val intent = Intent()
                                setResult(Activity.RESULT_OK, intent)
                                finish()
                            }
                            .addOnFailureListener { e -> Log.w("TAG", "Error updating document", e) }
                    }//apply
                }//if
            }//for
        }//get
    }
    //______________________________________________________________________________________________

    //----------------------------------------------------------------- FUN CHECK EDITTEXT EMPTY ---
    private fun checkEdittextEmpty():Boolean{
        if(binding.name.text.isEmpty()
            || binding.lname.text.isEmpty()
            || binding.gmail.text.isEmpty()
            || binding.stfCode.text.isEmpty()) {
            return true
        }
        return false
    }
    //______________________________________________________________________________________________
    //------------------------------------------------------------------- FUN VALIDATE TYPE GMAIL --
    private fun isEmailValid(email: String): Boolean {
        return compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }
    //______________________________________________________________________________________________
//-------------------------------------------------------------------- VALIDATE GMAIL ----------
    fun validateGmail(gmail:String){
        val db = Firebase.firestore
        val data = db.collection("Users account")
        checkGmail = "false"
        data.get().addOnSuccessListener { result ->
            for (document in result) {
                val equalsGmail = document.data["gmail"].toString().equals(gmail, true)
                val valGmail = InfoUser.gmail.equals(gmail, true)
                if (equalsGmail && !valGmail) {
                    binding.gmail.error = "อีเมลนี้ถูกใช้ไปแล้ว"
                    checkGmail = "true"
                }//if
            }//for
        }//get
    }
    //______________________________________________________________________________________________
    //---------------------------------------------------------------- VALIDATE STAFFCODE ----------
    fun validateStaffcode(stfcode:String){
        val db = Firebase.firestore
        val data = db.collection("Users account")
        checkStfcode = "false"
        data.get().addOnSuccessListener { result ->
            for(document in result){
                val equalsStfcode = document.data["staff_code"].toString().equals(stfcode,true)
                val valStfcode = InfoUser.staff_code.equals(stfcode, true)
                if(equalsStfcode && !valStfcode){
                    binding.stfCode.error = "รหัสพนักงานนี้ถูกใช้ไปแล้ว"
                    checkStfcode = "true"
                }//if
            }//for
        }//get
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when(parent!!.id){
            R.id.spinner_dep -> {
                select_dep = parent.getItemAtPosition(position).toString()
                binding.txtDep.text = select_dep
            }
            R.id.spinner_pos -> {
                select_pos = parent.getItemAtPosition(position).toString()
                binding.txtPos.text = select_pos
            }
        }//when
    }
    //______________________________________________________________________________________________
}
