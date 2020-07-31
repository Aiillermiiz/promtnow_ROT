package com.example.promtnow_rot.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.promtnow_rot.LoginActivity
import com.example.promtnow_rot.R

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.layoutFragmentContainer, FragmentSignup())
            commit()
        }
    }
}
