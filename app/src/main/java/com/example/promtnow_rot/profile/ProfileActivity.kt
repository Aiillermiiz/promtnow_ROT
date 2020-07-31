package com.example.promtnow_rot.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.promtnow_rot.R

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.layoutFragmentProfile,FragmentProfile())
            commit()
        }
    }
}
