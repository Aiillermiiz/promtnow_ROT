package com.example.promtnow_rot.profile

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.promtnow_rot.Prefs
import com.example.promtnow_rot.R
import com.example.promtnow_rot.databinding.ActivityProfileBinding
import com.example.promtnow_rot.homepage.InfoUser

class ProfileActivity : AppCompatActivity() {
    lateinit var binding:ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile)
        //set text status bar black
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        //---------------------------------------------------------------------- SET VALUE ---------
        binding.name.text = "${InfoUser.name} ${InfoUser.lname}"
        binding.stfCode.text = InfoUser.staff_code
        binding.gmail.text = InfoUser.gmail
        binding.dep.text = InfoUser.department
        binding.pos.text = InfoUser.position
        //set image from prefs
                Glide.with(this)
                    .load(Prefs(this).image)
                    .into(binding.img)
        //__________________________________________________________________________________________
        //------------------------------------------------------------------- ONCLICK --------------
        binding.profileBtnBack.setOnClickListener {
            onBackPressed()
        }
        binding.profileBtnEdit.setOnClickListener {
            startActivityForResult(Intent(this,EditProfileActivity::class.java),0)
        }
        binding.gmail.setOnClickListener {
            Toast.makeText(this,InfoUser.gmail,Toast.LENGTH_LONG).show()
        }
        binding.dep.setOnClickListener {
            Toast.makeText(this,InfoUser.department,Toast.LENGTH_LONG).show()
        }
        binding.pos.setOnClickListener {
            Toast.makeText(this,InfoUser.position,Toast.LENGTH_LONG).show()
        }
        //__________________________________________________________________________________________

    }
    //-------------------------------------------------------------------- ACTIVITY RESULT ---------
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK){
            binding.name.text = "${InfoUser.name} ${InfoUser.lname}"
            binding.stfCode.text = InfoUser.staff_code
            binding.gmail.text = InfoUser.gmail
            binding.dep.text = InfoUser.department
            binding.pos.text = InfoUser.position
            //set image from prefs
            Glide.with(this)
                .load(Prefs(this).image)
                .placeholder(R.drawable.icon_no_image)
                .dontAnimate()
                .into(binding.img)
        }//if
    }//
    //______________________________________________________________________________________________

}
