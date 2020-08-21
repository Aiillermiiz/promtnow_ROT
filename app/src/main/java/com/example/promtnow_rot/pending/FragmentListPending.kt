package com.example.promtnow_rot.pending

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.example.promtnow_rot.R
import com.example.promtnow_rot.add.FragmentFormAdd
import com.example.promtnow_rot.databinding.FragmentListPendingBinding

/**
 * A simple [Fragment] subclass.
 */
class FragmentListPending : Fragment() {
        lateinit var binding:FragmentListPendingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_list_pending,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //------------------------------------------------------------- ON CLICK -------------------
        //edit
        binding.pendingBtnEdit.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("ต้องการเเก้ไขเอกสารหรือไม่")
            builder.setMessage("")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->

            }
            builder.setNegativeButton(android.R.string.no) { dialog, which ->

            }
            builder.show()
        }
        //delete
        binding.pendingBtnDelete.setOnClickListener {
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
        binding.pendingBtnAdd.setOnClickListener {
            fragmentManager!!.beginTransaction().apply {
                replace(R.id.layoutFragmentPending,FragmentFormAdd.newInstance(FragmentFormAdd.PageState.ADD_STATE))
                addToBackStack("list_pending")
                commit()
            }
        }
        //back
        binding.btnBack.setOnClickListener {
            activity!!.onBackPressed()
        }
        //__________________________________________________________________________________________
    }

}
