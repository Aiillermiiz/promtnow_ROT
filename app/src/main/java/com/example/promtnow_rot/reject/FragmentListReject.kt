package com.example.promtnow_rot.reject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.example.promtnow_rot.R
import com.example.promtnow_rot.databinding.FragmentListRejectBinding

/**
 * A simple [Fragment] subclass.
 */
class FragmentListReject : Fragment() {
        lateinit var binding: FragmentListRejectBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_list_reject, container, false)
        return binding.root
    }

}
