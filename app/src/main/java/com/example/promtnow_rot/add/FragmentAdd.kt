package com.example.promtnow_rot.add

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.promtnow_rot.R
import com.example.promtnow_rot.add.recycleview.Adapter
import com.example.promtnow_rot.add.recycleview.InfoDocument
import com.example.promtnow_rot.databinding.FragmentAddBinding
import kotlinx.android.synthetic.main.fragment_add.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class FragmentAdd : Fragment() {
        lateinit var binding: FragmentAddBinding
        //define variable for recycle view
        var data = mutableListOf<InfoDocument>()

        private lateinit var viewManager :RecyclerView.LayoutManager
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
        data.add(InfoDocument("2-10-2562"))
        viewManager = LinearLayoutManager(context)
        add_recycle_main.apply {
            layoutManager = viewManager
            adapter = Adapter(activity!!,data)
        }
        //__________________________________________________________________________________________
        //---------------------------------------------------------------- PICKER ------------------
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        //__________________________________________________________________________________________
        //---------------------------------------------------------------- ONCLICK -----------------
        //click add
        binding.addBtnAdd.setOnClickListener {
            fragmentManager!!.beginTransaction().apply {
                replace(R.id.layoutFragmentContainerAdd,FragmentFormAdd.newInstance(FragmentFormAdd.PageState.ADD_STATE))
                addToBackStack("formadd")
                commit()
            }
        }
        binding.addBtnSubmit.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("ต้องการส่งเอกสารหรือไม่")
            builder.setMessage("")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->

            }
            builder.setNegativeButton(android.R.string.no) { dialog, which ->

            }
            builder.show()
        }
        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }
        //date picker
        binding.addDate.setOnClickListener {
            val dpd = DatePickerDialog(context!!,DatePickerDialog.OnDateSetListener { view, y, m, d ->
                binding.addDate.setText("${d}-${m}-${y}")
            },year,month,day)
            dpd.show()
        }
        //__________________________________________________________________________________________
    }

}
