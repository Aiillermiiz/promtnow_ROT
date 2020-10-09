@file:Suppress("DEPRECATION")

package com.example.promtnow_rot.setting

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil

import com.example.promtnow_rot.R
import com.example.promtnow_rot.databinding.FragmentChangePasswordBinding
import com.example.promtnow_rot.homepage.InfoUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.dialog.view.*
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
        //set text status bar black
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
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
                    Toast.makeText(context, "Please fill out all fields.", Toast.LENGTH_LONG).show()
                }//validate
                validatePassword(pass)-> {
                    Toast.makeText(context,"Please check the information.",Toast.LENGTH_LONG).show()
                }//not match
                 checkPassword(newPass,conPass) -> {
                     Toast.makeText(context, "Data does not match", Toast.LENGTH_LONG).show()
                }//...
                else -> {
                    val DialogView = LayoutInflater.from(activity).inflate(R.layout.dialog, null)
                    val builder = AlertDialog.Builder(activity)
                    val title = TextView(context)
                    title.text = "Do you want to change the code?"
                    title.setPadding(50, 50, 50, 50);
                    title.textSize = 20F;
                    builder.setCustomTitle(title)
                    builder.setView(DialogView)
                    val mAlertDialog = builder.show()
                    DialogView.btn_yes.setOnClickListener {
                        setPasswordToDB(binding.changepassInputConfirmPassword.text.toString())
                        mAlertDialog.dismiss()
                    }
                    DialogView.btn_no.setOnClickListener {
                        mAlertDialog.dismiss()
                    }
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
        binding.changepassInputNewPassword.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        binding.changepassInputConfirmPassword.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        //__________________________________________________________________________________________
    }//on view create
    //----------------------------------------------------------------- FUN CHECK EDITTEXT EMPTY ---
    private fun checkEdittextEmpty():Boolean{
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
            binding.changepassInputConfirmPassword.setTextColor(resources.getColor(R.color.red))
            binding.changepassInputNewPassword.setTextColor(resources.getColor(R.color.red))
            return true
        }
        binding.changepassInputConfirmPassword.setTextColor(resources.getColor(R.color.txt))
        binding.changepassInputNewPassword.setTextColor(resources.getColor(R.color.txt))
        return false
    }
    //______________________________________________________________________________________________
    //------------------------------------------------------------- FUN SET PASSWORD TO DATABASE ---
    private fun setPasswordToDB(password:String){
        val db = Firebase.firestore
        val data = db.collection("Users account")
                    data.whereEqualTo("gmail",InfoUser.gmail)
                    .get()
                    .addOnSuccessListener { docs ->
                        for(doc in docs){
                            data.document(doc.id).update("password",password)
                            Log.d("TAG", "DocumentSnapshot successfully updated!")
                        }//for
                    }//success
                    .addOnFailureListener { e ->
                        Log.w("TAG", "Error updating document", e)
                    }//fail
    }
    //______________________________________________________________________________________________
    //--------------------------------------------- FUN PASSWORD NOT MATCH WITH CURRENT PASSWORD ---
    fun validatePassword(password: String):Boolean{
        if (password != InfoUser.password){
            binding.changepassInputCurrPassword.error = "Wrong password"
            return true
        }//if
        return false
    }
    //______________________________________________________________________________________________

}
