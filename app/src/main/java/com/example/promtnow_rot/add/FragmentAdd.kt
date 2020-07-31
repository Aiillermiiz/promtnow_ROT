package com.example.promtnow_rot.add

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.promtnow_rot.R
import com.example.promtnow_rot.databinding.FragmentAddBinding
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class FragmentAdd : Fragment() {
        lateinit var binding: FragmentAddBinding
        //recycle view
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //----------------------------------------------------------------- ADAPTER ----------------

        //__________________________________________________________________________________________
        //---------------------------------------------------------------- PICKER ------------------
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.time)
        binding.addDate.setOnClickListener {
            val dpd = DatePickerDialog(activity!!, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                binding.addDate.setText(currentDateString)
            }, year, month, day)
            dpd.show()
        }
        //__________________________________________________________________________________________
        //---------------------------------------------------------------- ONCLICK -----------------
        //click add
        binding.addBtnAdd.setOnClickListener {
            fragmentManager!!.beginTransaction().apply {
                replace(R.id.layoutFragmentContainerAdd,FragmentFormAdd())
                addToBackStack("formadd")
                commit()
            }
        }
        binding.addBtnSubmit.setOnClickListener {

        }
        //__________________________________________________________________________________________
    }
}
