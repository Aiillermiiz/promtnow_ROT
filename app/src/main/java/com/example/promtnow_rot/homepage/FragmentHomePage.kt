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
class FragmentHomePage : Fragment() {
        lateinit var binding:FragmentHomePageBinding
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
        binding.mainSetting.setOnClickListener {
            startActivity(Intent(activity,SettingActivity::class.java))
        }
        binding.mainAdd.setOnClickListener {
            startActivity(Intent(activity,AddActivity::class.java))
        }
        binding.mainApprove.setOnClickListener {
            startActivity(Intent(activity,ApproveActivity::class.java))
        }
        binding.mainPending.setOnClickListener {
            startActivity(Intent(activity,PendingActivity::class.java))
        }
        binding.mainReject.setOnClickListener {
            startActivity(Intent(activity,RejectActivity::class.java))
        }
        binding.mainProfile.setOnClickListener {
            startActivity(Intent(activity,ProfileActivity::class.java))
        }

    }

}
