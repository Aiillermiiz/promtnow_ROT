package com.example.promtnow_rot.homepage

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.promtnow_rot.LoginActivity
import com.example.promtnow_rot.R
import com.example.promtnow_rot.databinding.ActivityMainPageBinding
import com.example.promtnow_rot.register.FragmentSignupPin

class MainPageActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)
        //------------------------------------------------------------------------- ONCLICK --------
        var pinFragmentAuthenPin = FragmentSignupPin.newInstance(FragmentSignupPin.PinState.STATE_AUTHEN)
        pinFragmentAuthenPin.setPinListener(object : FragmentSignupPin.PinListener{
            override fun onSuccess(pin: String) {
                Log.d("pinsucess",pin)
            }
            override fun onFail(fail: Int) {
                if (fail == 3){
                    startActivity(Intent(this@MainPageActivity, LoginActivity::class.java))
                }
            }
        })
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.layoutFragmentMainPage,pinFragmentAuthenPin)
            commit()
        }
        //__________________________________________________________________________________________
    }
}
