package com.example.promtnow_rot.setting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.example.promtnow_rot.R
import com.example.promtnow_rot.databinding.FragmentSettingBinding
import com.example.promtnow_rot.profile.ProfileActivity

/**
 * A simple [Fragment] subclass.
 */
class FragmentSetting : Fragment() {
    lateinit var binding : FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_setting, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.settingTextProfile.setOnClickListener {
            startActivity(Intent(activity,ProfileActivity::class.java))
        }
        binding.settingTextChangepass.setOnClickListener {
            fragmentManager!!.beginTransaction().apply {
                replace(R.id.layoutFragmentSetting,FragmentChangePassword())
                addToBackStack("changepassword")
                commit()
            }
        }
        binding.settingBtnBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

}
