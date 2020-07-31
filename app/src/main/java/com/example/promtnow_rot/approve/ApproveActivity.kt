package com.example.promtnow_rot.approve

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.promtnow_rot.R

class ApproveActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_approve)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.layoutFragmentApprove,FragmentApprove())
            commit()
        }
    }
}
