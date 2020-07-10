package com.example.promtnow_rot

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.promtnow_rot.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var rId: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rId = DataBindingUtil.setContentView(this,R.layout.activity_main)

        //---------------------------------------------------------------------- CLICK BUTTON ------
        rId.btnLogin.setOnClickListener {
            Toast.makeText(this,rId.inputGmail.text,Toast.LENGTH_LONG).show()
        }
        rId.btnSignup.setOnClickListener {
            Toast.makeText(this,"Singup",Toast.LENGTH_LONG).show()
        }
        //__________________________________________________________________________________________
        //---------------------------------------------------------------- CHECK EDITTEXT EMPTY ----
        rId.inputGmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEdittextEmpty()
            }
        })
        rId.inputPassword.addTextChangedListener(object : TextWatcher {
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

    fun checkEdittextEmpty(){
        if(rId.inputGmail.getText().isEmpty() || rId.inputPassword.getText().isEmpty()) {
            rId.btnLogin.isEnabled = false
            rId.btnLogin.setBackgroundResource(R.drawable.login_btn_disable)
        }else{
            rId.btnLogin.isEnabled = true
            rId.btnLogin.setBackgroundResource(R.drawable.login_btn_login)
        }
    }
    //______________________________________________________________________________________________
}
