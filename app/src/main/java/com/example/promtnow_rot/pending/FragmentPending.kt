package com.example.promtnow_rot.pending

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.example.promtnow_rot.R
import com.example.promtnow_rot.databinding.FragmentPendingBinding

/**
 * A simple [Fragment] subclass.
 */
class FragmentPending : Fragment() {
        lateinit var binding:FragmentPendingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_pending, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.approvedBtnAdd.setOnClickListener {
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.layoutFragmentPending, FragmentEditPending())
                addToBackStack("approve")
                commit()
            }
        }
    }

}
