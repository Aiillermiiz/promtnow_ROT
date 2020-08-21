package com.example.promtnow_rot.setting

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil

import com.example.promtnow_rot.R
import com.example.promtnow_rot.databinding.FragmentChangePasswordBinding
import com.example.promtnow_rot.homepage.InfoUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.HashMap

/**
 * A simple [Fragment] subclass.
 */
class FragmentChangePassword : Fragment() {
        lateinit var binding : FragmentChangePasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_change_password, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //---------------------------------------------------------------- ONCLICK -----------------
        binding.changepassBtnBack.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.changepassBtnConfirm.setOnClickListener {
            val newPass = binding.changepassInputNewPassword.text.toString()
            val conPass = binding.changepassInputConfirmPassword.text.toString()
            val pass = binding.changepassInputCurrPassword.text.toString()
            when {//empty
                checkEdittextEmpty() -> {
                    Toast.makeText(context, "empty", Toast.LENGTH_LONG).show()
                }//validate
                validatePassword(pass)-> {
                    Toast.makeText(context,"กรุณาตรวจสอบข้อมูล",Toast.LENGTH_LONG).show()
                }//not match
                 checkPassword(newPass,conPass) -> {
                     Toast.makeText(context, "not match", Toast.LENGTH_LONG).show()
                }//...
                else -> {
                    val builder = AlertDialog.Builder(activity)
                    builder.setTitle("ต้องการเปลี่ยนรหัสใช่หรือไม่")
                    builder.setMessage("")
                    builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                        setPasswordToDB(binding.changepassInputConfirmPassword.text.toString())
                    }
                    builder.setNegativeButton(android.R.string.no) { dialog, which ->

                    }
                    builder.show()
                }
            }//when
        }
        //__________________________________________________________________________________________
        //---------------------------------------------------------------- VALIDATE PASSWORD -------
        binding.changepassInputCurrPassword.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                validatePassword(binding.changepassInputCurrPassword.text.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        //__________________________________________________________________________________________
    }//on view create
    //----------------------------------------------------------------- FUN CHECK EDITTEXT EMPTY ---
    fun checkEdittextEmpty():Boolean{
        if(binding.changepassInputCurrPassword.getText().isEmpty()
            || binding.changepassInputNewPassword.getText().isEmpty()
            || binding.changepassInputConfirmPassword.getText().isEmpty()) {
            return true
        }
        return false
    }
    //______________________________________________________________________________________________
    //-------------------------------------------------- FUN PASSWORD NOT MATCH BETWEEN NEW&CON ----
    fun checkPassword(new:String,con:String):Boolean{
        if (new != con){
            binding.changepassInputConfirmPassword.setBackgroundResource(R.drawable.input_fail)
            binding.changepassInputNewPassword.setBackgroundResource(R.drawable.input_fail)
            return true
        }
        binding.changepassInputConfirmPassword.setBackgroundResource(R.drawable.card_input)
        binding.changepassInputNewPassword.setBackgroundResource(R.drawable.card_input)
        return false
    }
    //______________________________________________________________________________________________
    //------------------------------------------------------------- FUN SET PASSWORD TO DATABASE ---
    fun setPasswordToDB(password:String){
        val db = Firebase.firestore
        val data = db.collection("Users account")
        data.get().addOnSuccessListener { result ->
            for(document in result){
                if (InfoUser.staff_code == document.data["staff_code"]){
                    data.document(document.id).update("password",password)
                }//if
            }//for
        }//get
    }
    //______________________________________________________________________________________________
    //--------------------------------------------- FUN PASSWORD NOT MATCH WITH CURRENT PASSWORD ---
    fun validatePassword(password: String):Boolean{
        if (password != InfoUser.password){
            binding.changepassInputCurrPassword.error = "กรอกรหัสผิด"
            return true
        }//if
        return false
    }
    //______________________________________________________________________________________________

}
