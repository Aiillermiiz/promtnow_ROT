package com.example.promtnow_rot

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.promtnow_rot.databinding.ActivityMainBinding
import com.example.promtnow_rot.homepage.InfoUser
import com.example.promtnow_rot.homepage.MainPageActivity
import com.example.promtnow_rot.register.DataUser
import com.example.promtnow_rot.register.RegisterActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {
    lateinit var rId: ActivityMainBinding
    var statusPass = "hide"
    var doubleBackToExitPressedOnce = false
    lateinit var dataUser: DataUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rId = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //set data
        rId.logInputGmail.setText("user@gmail.com")
        rId.logInputPassword.setText("user123")
        //
        if (Prefs(this).rememberCheck){
            startActivity(Intent(this,MainPageActivity::class.java))
        }
        //---------------------------------------------------------------------- CLICK BUTTON ------
        rId.btnLogin.setOnClickListener {
            val inputGmail = rId.logInputGmail.text.toString()
            val inputPassword = rId.logInputPassword.text.toString()
            //check data with edittext
            checkGmailAndPassword(inputGmail, inputPassword)
        }
        rId.btnSignup.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        //hide and show password
        rId.eyes.setOnClickListener {
            if (statusPass == "hide") {
                rId.logInputPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                rId.eyes.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_visibility_off_black_24dp,0)
                //set position cursor
                rId.logInputPassword.setSelection(rId.logInputPassword.text.length)
                statusPass = "show"
            } else {
                rId.logInputPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                rId.eyes.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_remove_red_eye_black_24dp,0)
                //set position cursor
                rId.logInputPassword.setSelection(rId.logInputPassword.text.length)
                statusPass = "hide"
            }
        }//on click
        //__________________________________________________________________________________________
        //---------------------------------------------------------------- CHECK EDITTEXT EMPTY ----
        rId.logInputGmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                checkEdittextEmpty()
            }
        })
        rId.logInputPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEdittextEmpty()
            }
        })
        //__________________________________________________________________________________________
    }

    //----------------------------------------------------------------- FUN CHECK EDITTEXT EMPTY ---
    fun checkEdittextEmpty() {
        if (rId.logInputGmail.getText().isEmpty() || rId.logInputPassword.getText().isEmpty()) {
            rId.btnLogin.isEnabled = false
            rId.btnLogin.setBackgroundResource(R.drawable.login_btn_disable)
        } else {
            rId.btnLogin.isEnabled = true
            rId.btnLogin.setBackgroundResource(R.drawable.login_btn_login)
        }
    }

    //______________________________________________________________________________________________
    //------------------------------------------------------------ FUN CHECK DATA IN DATABASE ------
    private fun checkGmailAndPassword(inputGmail: String, inputPassword: String) {
        var gmail = ""
        var password = ""
        //call database
        val db = Firebase.firestore
        val data = db.collection("Users account")
        //get data and check
        data.get()
            .addOnSuccessListener { docs ->
                for(doc in docs){
                    //ignore case = (value,ignorecase true or false) ไม่สนว่าจะเป็นตัวอักษรพิมพ์เล็กหรือใหญ่
                   if (doc.data["gmail"]?.toString().equals(inputGmail,true) && doc.data["password"]?.toString() == inputPassword){
                       gmail =  doc.data["gmail"].toString()
                       password = doc.data["password"].toString()
                       InfoUser.getInstance()?.pin = doc.data["pin"].toString()
                       InfoUser.getInstance()?.staff_code = doc.data["staff_code"].toString()
                       InfoUser.getInstance()?.name = doc.data["name"].toString()
                       InfoUser.getInstance()?.lname = doc.data["lname"].toString()
                       InfoUser.getInstance()?.gmail = doc.data["gmail"].toString()
                       InfoUser.getInstance()?.department = doc.data["department"].toString()
                       InfoUser.getInstance()?.position = doc.data["position"].toString()
                       InfoUser.getInstance()?.password = doc.data["password"].toString()
                       startActivity(Intent(this,MainPageActivity::class.java))
                   }
                }//for
                Log.d("tag","${gmail} :${password}")
                if (inputGmail != gmail || inputPassword != password){
                    Toast.makeText(this, "Incorrect information.", Toast.LENGTH_LONG).show()
                }//if
            }//on success
    }
    //______________________________________________________________________________________________
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this,"Press again to logout.",Toast.LENGTH_SHORT).show()
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }
}