package com.example.promtnow_rot.homepage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.example.promtnow_rot.R
import com.example.promtnow_rot.add.AddActivity
import com.example.promtnow_rot.approve.ApproveActivity
import com.example.promtnow_rot.databinding.FragmentHomePageBinding
import com.example.promtnow_rot.pending.PendingActivity
import com.example.promtnow_rot.profile.ProfileActivity
import com.example.promtnow_rot.reject.RejectActivity
import com.example.promtnow_rot.setting.SettingActivity

/**
 * A simple [Fragment] subclass.
 */
class FragmentHomePage : Fragment(), View.OnClickListener {
        lateinit var binding:FragmentHomePageBinding
        //data user
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home_page, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //-------------------------------------------------------------------- ONCLICK -------------
        binding.mainSetting.setOnClickListener(this)
        binding.mainAdd.setOnClickListener(this)
        binding.mainApprove.setOnClickListener(this)
        binding.mainPending.setOnClickListener(this)
        binding.mainReject.setOnClickListener(this)
        binding.mainProfile.setOnClickListener(this)
        //__________________________________________________________________________________________
    }
    //-------------------------------------------------------------------- FUN ONCLICK -------------
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.main_setting -> {
                startActivity(Intent(activity,SettingActivity::class.java))
            }
            R.id.main_add -> {
                startActivity(Intent(activity,AddActivity::class.java))
            }
            R.id.main_approve -> {
                startActivity(Intent(activity,ApproveActivity::class.java))
            }
            R.id.main_pending -> {
                startActivity(Intent(activity,PendingActivity::class.java))
            }
            R.id.main_reject -> {
                startActivity(Intent(activity,RejectActivity::class.java))
            }
            R.id.main_profile -> {
               startActivity(Intent(activity,ProfileActivity::class.java))
            }
        }//when
    }
    //______________________________________________________________________________________________

}
