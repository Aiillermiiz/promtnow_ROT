package com.example.promtnow_rot.add

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.example.promtnow_rot.R
import com.example.promtnow_rot.databinding.FragmentFormAddBinding
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentFormAdd : Fragment() {
    lateinit var binding: FragmentFormAddBinding
    var pageState = PageState.ADD_STATE
    //state of page add and edit
    enum class PageState {
        ADD_STATE, EDIT_STATE
    }
    //------------------------------------------------------ RECEIVE & SET DATA TO BUNDLE ----------
    companion object{
        fun newInstance(state:PageState):FragmentFormAdd{
            var bundle = Bundle()
            bundle.putSerializable("STATE", state)
            var fragment = FragmentFormAdd()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageState = arguments?.getSerializable("STATE") as PageState
    }
    //______________________________________________________________________________________________

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_form_add, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //------------------------------------------------------------------ PICKER DATE -----------
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        //__________________________________________________________________________________________
        setState(pageState)
        //------------------------------------------------------------------- ONCLICK --------------
        binding.formAddBtnSubmit.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }
        //date picker
        binding.formAddInputDate.setOnClickListener {
            val dpd = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { view, y, m, d ->
                    binding.formAddInputDate.setText("${d}-${m}-${y}")
                }, year, month, day)
            dpd.show()
        }
        //__________________________________________________________________________________________
        //---------------------------------------------------------------- CHECK EDITTEXT EMPTY ----
        binding.formAddInputDate.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        binding.formAddInputProjectNo.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.textPro.visibility = View.VISIBLE
            }
        })
        binding.formAddInputReson.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.textRes.visibility = View.VISIBLE
            }
        })
        binding.formAddInputFrom.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.textFrom.visibility = View.VISIBLE
            }
        })
        binding.formAddInputTo.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.textTo.visibility = View.VISIBLE
            }
        })
        binding.formAddInputDescription.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.textDes.visibility = View.VISIBLE
            }
        })
        binding.formInputAddAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.textAmo.visibility = View.VISIBLE
            }
        })
        //__________________________________________________________________________________________
    }

    //----------------------------------------------------------------- FUN CHECK EDITTEXT EMPTY ---
    fun checkEdittextEmpty(): Boolean {
        if (binding.formAddInputDate.getText().isEmpty()
            || binding.formAddInputProjectNo.getText().isEmpty()
            || binding.formAddInputReson.getText().isEmpty()
            || binding.formAddInputFrom.getText().isEmpty()
            || binding.formAddInputTo.getText().isEmpty()
            || binding.formAddInputDescription.getText().isEmpty()
            || binding.formInputAddAmount.getText().isEmpty()
        ) {
            return true
        }
        return false
    }
    //______________________________________________________________________________________________
    //---------------------------------------------------------------- SET PIN STATE ---------------
    fun setState(state:PageState){
        when(state){
            PageState.ADD_STATE -> {
                binding.textTop.text = "List your request"
                binding.formAddBtnSubmit.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.form_add_icon_add, 0)
            }
            PageState.EDIT_STATE -> {
                binding.textTop.text = "Edit list your request"
                binding.formAddBtnSubmit.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.btn_edit_color, 0)
            }
        }
    }
    //______________________________________________________________________________________________


}
