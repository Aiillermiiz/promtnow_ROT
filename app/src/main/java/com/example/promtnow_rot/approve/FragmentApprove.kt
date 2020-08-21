package com.example.promtnow_rot.approve

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil

import com.example.promtnow_rot.R
import com.example.promtnow_rot.databinding.FragmentApproveBinding
import com.example.promtnow_rot.setting.FragmentEditProfile

/**
 * A simple [Fragment] subclass.
 */
class FragmentApprove : Fragment() {
                lateinit var binding:FragmentApproveBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_approve, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.approvedBtnBack.setOnClickListener {
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.layoutFragmentApprove,FragmentListApprove())
                addToBackStack("approve")
                commit()
            }
        }
    }//on view create
}
