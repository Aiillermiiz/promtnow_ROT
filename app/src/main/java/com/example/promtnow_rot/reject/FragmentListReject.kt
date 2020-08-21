package com.example.promtnow_rot.reject

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.example.promtnow_rot.R
import com.example.promtnow_rot.add.FragmentFormAdd
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //---------------------------------------------------------------- ONCLICK -----------------
        //send again
        binding.rejectBtnSendAgain.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("ต้องการส่งเอกสารนี้อีกครั้งหรือไม่")
            builder.setMessage("")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->

            }
            builder.setNegativeButton(android.R.string.no) { dialog, which ->

            }
            builder.show()
        }
        //delete
        binding.rejectBtnDelete.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("ต้องการลบเอกสารหรือไม่")
            builder.setMessage("")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->

            }
            builder.setNegativeButton(android.R.string.no) { dialog, which ->

            }
            builder.show()
        }
        //add
        binding.rejectBtnAdd.setOnClickListener {
            fragmentManager!!.beginTransaction().apply {
                replace(R.id.layoutFragmentReject,FragmentFormAdd.newInstance(FragmentFormAdd.PageState.ADD_STATE))
                addToBackStack("list_reject")
                commit()
            }
        }
        //back
        binding.rejectBtnBack.setOnClickListener {
            activity!!.onBackPressed()
        }
        //__________________________________________________________________________________________
    }

}
