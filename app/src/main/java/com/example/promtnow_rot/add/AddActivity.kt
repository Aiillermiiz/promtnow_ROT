package com.example.promtnow_rot.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.promtnow_rot.R

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.layoutFragmentContainerAdd,FragmentAdd())
            commit()
        }
    }
}
