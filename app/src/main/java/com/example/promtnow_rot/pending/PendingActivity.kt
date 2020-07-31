package com.example.promtnow_rot.pending

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.promtnow_rot.R

class PendingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pending)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.layoutFragmentPending,FragmentPending())
            commit()
        }
    }
}
