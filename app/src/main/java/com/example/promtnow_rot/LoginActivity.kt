package com.example.promtnow_rot

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
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
    lateinit var dataUser:DataUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rId = DataBindingUtil.setContentView(this,R.layout.activity_main)
        //---------------------------------------------------------------------- CLICK BUTTON ------
        rId.btnLogin.setOnClickListener {
            val inputGmail = rId.logInputGmail.text.toString()
            val inputPassword = rId.logInputPassword.text.toString()
            //check data with edittext
            checkGmailAndPassword(inputGmail,inputPassword)
        }
        rId.btnSignup.setOnClickListener {
           startActivity(Intent(this, RegisterActivity::class.java))
        }
        //hide and show password
        rId.eyes.setOnClickListener {
            if (statusPass == "hide"){
                rId.logInputPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                rId.eyes.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.ic_visibility_off_black_24dp, 0)
                statusPass = "show"
            }else{
                rId.logInputPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                rId.eyes.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.ic_remove_red_eye_black_24dp, 0)
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
                if (rId.logInputGmail.length() > 0){
                    rId.textEmail.setTextColor(getColor(R.color.log2))
                }else{
                    rId.textEmail.setTextColor(getColor(R.color.log1))
                }
            }
        })
        rId.logInputPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEdittextEmpty()
                if (rId.logInputPassword.length() > 0){
                    rId.textPass.setTextColor(getColor(R.color.log2))
                }else{
                    rId.textPass.setTextColor(getColor(R.color.log1))
                }
            }
        })
        //__________________________________________________________________________________________
    }
    //----------------------------------------------------------------- FUN CHECK EDITTEXT EMPTY ---
    fun checkEdittextEmpty(){
        if(rId.logInputGmail.getText().isEmpty() || rId.logInputPassword.getText().isEmpty()) {
            rId.btnLogin.isEnabled = false
            rId.btnLogin.setBackgroundResource(R.drawable.login_btn_disable)
        }else{
            rId.btnLogin.isEnabled = true
            rId.btnLogin.setBackgroundResource(R.drawable.login_btn_login)
        }
    }
    //______________________________________________________________________________________________
    //------------------------------------------------------------ FUN CHECK DATA IN DATABASE ------
    fun checkGmailAndPassword(inputGmail:String,inputPassword:String){
        var gmail = ""
        var name = ""
        var password = ""
        var pin = ""
        var position = ""
        var staff_code = ""
        var department = ""
        //call database
        val db = Firebase.firestore
        val Data = db.collection("Users account")
        //get data and check
        Data.get().addOnSuccessListener { result ->
        loop@for (document in result) {
                //gmail equals gmail in database
                //ignore case = (value,ignorecase true or false) ไม่สนว่าจะเป็นตัวอักษรพิมพ์เล็กหรือใหญ่
                val gmailEquals = document.data["gmail"]?.toString().equals(inputGmail, true)
                //password equals password in database
                val passwordEquals = document.data["password"] == inputPassword
                //check in database and get data
                if (gmailEquals && passwordEquals){
                     gmail = document.data["gmail"].toString()
                     name = document.data["name"].toString()
                     password = document.data["password"].toString()
                     pin = document.data["pin"].toString()
                     position = document.data["position"].toString()
                     staff_code = document.data["staff_code"].toString()
                     department = document.data["department"].toString()
                    break@loop
                }
            }//loop
            //check in input and put data
            when {
                inputGmail.equals(gmail, true) && inputPassword == password -> {
                    //put data
                    InfoUser.getInstance()?.name = name
                    InfoUser.getInstance()?.gmail = gmail
                    InfoUser.getInstance()?.password = password
                    InfoUser.getInstance()?.position = position
                    InfoUser.getInstance()?.staff_code = staff_code
                    InfoUser.getInstance()?.department = department
                    InfoUser.getInstance()?.pin = pin
                    startActivity(Intent(this, MainPageActivity::class.java))
                }else -> {Toast.makeText(this, "ข้อมูลไม่ถูกต้อง", Toast.LENGTH_LONG).show()}
            }//when
        }
    }
    //______________________________________________________________________________________________

}