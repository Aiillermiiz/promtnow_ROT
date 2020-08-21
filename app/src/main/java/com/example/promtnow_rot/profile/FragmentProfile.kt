package com.example.promtnow_rot.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.example.promtnow_rot.R
import com.example.promtnow_rot.databinding.FragmentProfileBinding
import com.example.promtnow_rot.homepage.InfoUser
import com.example.promtnow_rot.setting.FragmentEditProfile

/**
 * A simple [Fragment] subclass.
 */
class FragmentProfile : Fragment() {
    lateinit var binding:FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //---------------------------------------------------------------------- SET VALUE ---------
        binding.name.text = InfoUser.name
        binding.code.text = InfoUser.staff_code
        binding.gmail.text = InfoUser.gmail
        binding.department.text = InfoUser.department
        binding.position.text = InfoUser.position
        //__________________________________________________________________________________________
        //------------------------------------------------------------------- ONCLICK --------------
        binding.profileBtnEdit.setOnClickListener {
            fragmentManager!!.beginTransaction().apply {
                replace(R.id.layoutFragmentProfile,FragmentEditProfile())
                addToBackStack("profile")
                commit()
            }
        }
        binding.profileBtnBack.setOnClickListener {
            activity?.onBackPressed()
        }
        //__________________________________________________________________________________________
    }
}
