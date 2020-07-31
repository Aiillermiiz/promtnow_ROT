package com.example.promtnow_rot.reject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.promtnow_rot.R

class RejectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reject)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.layoutFragmentReject,FragmentReject())
            commit()
        }
    }
}
