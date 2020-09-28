package com.example.promtnow_rot.register

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.promtnow_rot.R
import com.example.promtnow_rot.databinding.FragmentSignupBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.dialog.view.*
import java.util.regex.Pattern.compile


/**
 * A simple [Fragment] subclass.
 */
@Suppress("DEPRECATION")
class FragmentSignup : Fragment(), AdapterView.OnItemSelectedListener {
    lateinit var binding:FragmentSignupBinding
    var select_dep = "เลือกแผนก"
    var select_pos = "เลือกตำแหน่ง"
    var checkGmail = ""
    var checkStfcode = ""
    var statusPass = "hide"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //------------------------------------------------------------------ SPINNER ---------------
        //spinner department
        val spinner_dep = binding.spinnerDep
        //create value from resource
        val adapter_dep = ArrayAdapter.createFromResource(context!!,R.array.department,android.R.layout.simple_spinner_dropdown_item)
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
        //---------------------------------------------------------------------- ONCLICK -----------
        //button sign up
        binding.regBtnSignup.setOnClickListener {
            if (checkEdittextEmpty()) {
                Toast.makeText(context, "กรุณากรอกข้อมูลให้ครบทุกช่อง", Toast.LENGTH_LONG).show()
            }else if(!binding.checkbox.isChecked){
                Toast.makeText(context, "กรุณายอมรับข้อกำหนดและเงื่อนไข", Toast.LENGTH_LONG).show()
            }else if(checkGmail == "true" || checkStfcode == "true"){
                Toast.makeText(context, "กรุณากตรวจสอบข้อมูล", Toast.LENGTH_LONG).show()
            }else if(!isEmailValid(binding.regInputGmail.text.toString())){
                binding.regInputGmail.error = "กรุณาระบุให้เป็นประเภทอีเมล"
            }else{
                //Inflate the dialog with custom view
                val DialogView = LayoutInflater.from(activity).inflate(R.layout.dialog, null)
                val builder = AlertDialog.Builder(activity)
                val title = TextView(context)
                title.text = "ต้องการดำเนินการต่อหรือไม่"
                title.setPadding(50, 50, 50, 50);
                title.textSize = 20F;
                builder.setCustomTitle(title)
                builder.setView(DialogView)
                //show dialog
                val mAlertDialog = builder.show()
                //button ok
                DialogView.btn_yes.setOnClickListener {
                    //dismiss dialog
                    mAlertDialog.dismiss()
                    //set value
                    val name = binding.regInputName.text.toString()
                    val lname = binding.regInputLname.text.toString()
                    val gmail = binding.regInputGmail.text.toString()
                    val password = binding.regInputPassword.text.toString()
                    val staff_code = binding.regInputStaffCode.text.toString()
                    //set fragment
                    val pinFragmentSignupPin = FragmentSignupPin.newInstance(
                        FragmentSignupPin.PinState.STATE_CREATE,name,lname,gmail,password,staff_code,select_pos,select_dep,""
                    )
                    pinFragmentSignupPin.setPinListener(object : FragmentSignupPin.PinListener {
                        override fun onSuccess(pin: String) {
                            Log.d("pinsucess", pin)
                        }

                        override fun onFail(fail: Int) {
                            Log.d("failcount", fail.toString())
                        }
                    })
                    //replace fragment
                    fragmentManager!!.beginTransaction().apply {
                        replace(R.id.layoutFragmentContainer, pinFragmentSignupPin, "inputPin")
                        addToBackStack("inputPin")
                        commit()
                    }
                }
                //button cancel
                DialogView.btn_no.setOnClickListener {
                    //dismiss dialog
                    mAlertDialog.dismiss()
                }
            }//else
        }//on click
        //button back
        binding.regBtnBackLogin.setOnClickListener {
            activity?.onBackPressed()
        }//on click
        //hide and show password
        binding.eyes.setOnClickListener {
            if (statusPass == "hide"){
                binding.regInputPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.eyes.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.ic_visibility_off_black_24dp, 0)
                //set position cursor
                binding.regInputPassword.setSelection(binding.regInputPassword.text.length)
                statusPass = "show"
            }else{
                binding.regInputPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.eyes.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.ic_remove_red_eye_black_24dp, 0)
                //set position cursor
                binding.regInputPassword.setSelection(binding.regInputPassword.text.length)
                statusPass = "hide"
            }
        }//on click
        //__________________________________________________________________________________________
        //--------------------------------------------------------------------- ADD TEXT CHANGE ----
        binding.regInputGmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val inputGmail = binding.regInputGmail.text.toString()
                validateGmail(inputGmail)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        if (binding.regInputGmail.text.contains(" ")){
                        binding.regInputGmail.error = "มีช่องว่าง"
                    }
            }
        })
        binding.regInputPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (binding.regInputPassword.text.contains(" ")){
                        binding.regInputPassword.error = "มีช่องว่าง"
                    }

            }
        })
        binding.regInputName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (binding.regInputName.text.contains(" ")){
                        binding.regInputName.error = "มีช่องว่าง"
                    }
            }
        })
        binding.regInputLname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (binding.regInputLname.text.contains(" ")){
                        binding.regInputLname.error = "มีช่องว่าง"
                    }
            }
        })
        binding.regInputStaffCode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val inputStfcode = binding.regInputStaffCode.text.toString()
                validateStaffcode(inputStfcode)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (binding.regInputStaffCode.text.contains(" ")){
                        binding.regInputStaffCode.error = "มีช่องว่าง"
                    }//if
            }
        })
        //__________________________________________________________________________________________
    }
    //----------------------------------------------------------------- FUN CHECK EDITTEXT EMPTY ---
    fun checkEdittextEmpty():Boolean{
        if(binding.regInputGmail.text.isEmpty()
            || binding.regInputPassword.text.isEmpty()
            || binding.regInputName.text.isEmpty()
            || binding.regInputStaffCode.text.isEmpty()) {
            return true
        }
        return false
    }
    //______________________________________________________________________________________________
    //--------------------------------------------------------------------- FUN OF SPINNER ---------
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
    //-------------------------------------------------------------------- VALIDATE GMAIL ----------
    fun validateGmail(gmail:String){
        val db = Firebase.firestore
        val data = db.collection("Users account")
        checkGmail = "false"
        data.get().addOnSuccessListener { result ->
                for (document in result) {
                    val equalsGmail = document.data["gmail"].toString().equals(gmail, true)
                    if (equalsGmail) {
                        binding.regInputGmail.error = "อีเมลนี้ถูกใช้ไปแล้ว"
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
                if(equalsStfcode){
                    binding.regInputStaffCode.error = "รหัสพนักงานนี้ถูกใช้ไปแล้ว"
                    checkStfcode = "true"
                }//if
            }//for
        }//get
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


}

