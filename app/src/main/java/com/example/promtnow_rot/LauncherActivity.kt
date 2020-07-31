package com.example.promtnow_rot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        startActivity(Intent(this,LoginActivity()::class.java))
    }
}
