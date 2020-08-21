package com.example.promtnow_rot.setting

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil

import com.example.promtnow_rot.R
import com.example.promtnow_rot.databinding.FragmentEditProfileBinding
import com.example.promtnow_rot.homepage.InfoUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass.
 */
class FragmentEditProfile : Fragment(), AdapterView.OnItemSelectedListener {
        lateinit var binding : FragmentEditProfileBinding
        var checkGmail = ""
        var checkStfcode = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_edit_profile, container, false)
        return  binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //------------------------------------------------------------- SET VALUE TO EDITTEXT ------
        binding.editproInputName.setText(InfoUser.name)
        binding.editproInputGmail.setText(InfoUser.gmail)
        binding.editproInputStaffCode.setText(InfoUser.staff_code)
        //__________________________________________________________________________________________
        //----------------------------------------------------------------------- SPINNER ----------
        //spinner department
        val spinner_dep = binding.spinnerDep
        //create value from resource
        val adapter_dep = ArrayAdapter.createFromResource(context!!,R.array.department,android.R.layout.simple_spinner_item)
        spinner_dep.setPopupBackgroundResource(R.drawable.card_dropdown)
        //set dropdown
        adapter_dep.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinner_dep.adapter = adapter_dep
        //when select
        spinner_dep.onItemSelectedListener = this

        //spinner position
        val spinner_pos = binding.spinnerPos
        spinner_pos.setPopupBackgroundResource(R.drawable.card_dropdown)
        val adapter_pos = ArrayAdapter.createFromResource(context!!,R.array.position,android.R.layout.simple_spinner_item)
        adapter_pos.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinner_pos.adapter = adapter_pos
        spinner_pos.onItemSelectedListener = this
        //__________________________________________________________________________________________
        //----------------------------------------------------------------- ONCLICK ----------------
        binding.editproBtnBack.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.editproBtnEdit.setOnClickListener {
            if (checkEdittextEmpty()) {
                Toast.makeText(context, "empty", Toast.LENGTH_LONG).show()
            }else if(checkGmail == "true" || checkStfcode == "true"){
                Toast.makeText(context, "กรุณากตรวจสอบข้อมูล", Toast.LENGTH_LONG).show()
            }else {
                val builder = AlertDialog.Builder(activity)
                builder.setTitle("ต้องการแก้ไขข้อมูลหรือไม่")
                builder.setMessage("")
                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    setDataToDB(binding.editproInputName.text.toString()
                                ,binding.editproInputGmail.text.toString()
                                ,binding.editproInputStaffCode.text.toString())
                }
                builder.setNegativeButton(android.R.string.no) { dialog, which ->

                }
                builder.show()
            }
        }
        binding.btnImage.setOnClickListener {
            Toast.makeText(context,"ใจเย็น",Toast.LENGTH_LONG).show()
        }
        //__________________________________________________________________________________________
        //---------------------------------------------------------------- CHECK EDITTEXT EMPTY ----
        binding.editproInputGmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val inputGmail = binding.editproInputGmail.text.toString()
                validateGmail(inputGmail)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        binding.editproInputStaffCode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val inputStfcode = binding.editproInputStaffCode.text.toString()
                validateStaffcode(inputStfcode)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        //__________________________________________________________________________________________
    }
    //----------------------------------------------------------------- FUN CHECK EDITTEXT EMPTY ---
    fun checkEdittextEmpty():Boolean{
        if(binding.editproInputGmail.getText().isEmpty()
            || binding.editproInputName.getText().isEmpty()
            || binding.editproInputStaffCode.getText().isEmpty()) {
            return true
        }
        return false
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
                if (equalsGmail) {
                    if(InfoUser.gmail != gmail){
                        binding.editproInputGmail.error = "อีเมลนี้ถูกใช้ไปแล้ว"
                        checkGmail = "true"
                    }
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
                if(equalsStfcode){
                    if(InfoUser.staff_code != stfcode){
                        binding.editproInputStaffCode.error = "รหัสพนักงานนี้ถูกใช้ไปแล้ว"
                        checkStfcode = "true"
                    }
                }//if
            }//for
        }//get
    }
    //______________________________________________________________________________________________
    //--------------------------------------------------------------------------- FUN SPINNER ------
    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }
    //______________________________________________________________________________________________
    //----------------------------------------------------------------- FUN SET DATA TO DATABASE ---
    fun setDataToDB(name:String,gmail: String,staff_code:String){
        val db = Firebase.firestore
        val data = db.collection("Users account")
        data.get().addOnSuccessListener { result ->
            for(document in result){
                if(InfoUser.staff_code == document.data["staff_code"]){
                    data.document(document.id).apply {
                        update("name",name)
                        update("gmail",gmail)
                        update("staff_code",staff_code)
                    }//apply
                }//if
            }//for
        }
    }
    //______________________________________________________________________________________________


}
