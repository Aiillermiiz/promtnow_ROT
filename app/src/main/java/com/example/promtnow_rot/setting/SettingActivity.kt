package com.example.promtnow_rot.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.promtnow_rot.R

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.layoutFragmentSetting,FragmentSetting())
            commit()
        }
    }
}
