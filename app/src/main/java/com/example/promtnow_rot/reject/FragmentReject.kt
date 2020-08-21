package com.example.promtnow_rot.reject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.example.promtnow_rot.R
import com.example.promtnow_rot.databinding.FragmentRejectBinding
import com.example.promtnow_rot.pending.FragmentListPending

/**
 * A simple [Fragment] subclass.
 */
class FragmentReject : Fragment() {
            lateinit var binding:FragmentRejectBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_reject, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rejectedBtnBack.setOnClickListener {
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.layoutFragmentReject, FragmentListReject())
                addToBackStack("reject")
                commit()
            }
        }
    }

}
