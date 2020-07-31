package com.example.promtnow_rot.add

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil

import com.example.promtnow_rot.R
import com.example.promtnow_rot.databinding.FragmentFormAddBinding
import java.text.DateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentFormAdd : Fragment() {
        lateinit var binding: FragmentFormAddBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_form_add, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //------------------------------------------------------------------ PICKER DATE -----------
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.time)

        binding.formAddInputDate.setOnClickListener {

            val dpd = DatePickerDialog(activity!!, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                binding.formAddInputDate.setText(currentDateString)
            }, year, month, day)
            dpd.show()
        }
        //__________________________________________________________________________________________
        binding.formAddBtnSubmit.setOnClickListener {
            Toast.makeText(context,"Success",Toast.LENGTH_LONG).show()
        }

        //---------------------------------------------------------------- CHECK EDITTEXT EMPTY ----
        binding.formAddInputDate.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEdittextEmpty()
            }
        })
        binding.formAddInputProjectNo.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (binding.formAddInputProjectNo.text.isEmpty()){

                    binding.textPro.visibility = View.INVISIBLE
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEdittextEmpty()
                    binding.textPro.visibility = View.VISIBLE
            }
        })
        binding.formAddInputReson.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (binding.formAddInputReson.text.isEmpty()){

                    binding.textRes.visibility = View.INVISIBLE
                }

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEdittextEmpty()
                binding.textRes.visibility = View.VISIBLE
            }
        })
        binding.formAddInputFrom.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (binding.formAddInputFrom.text.isEmpty()){

                    binding.textFrom.visibility = View.INVISIBLE
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEdittextEmpty()
                binding.textFrom.visibility = View.VISIBLE
            }
        })
        binding.formAddInputTo.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (binding.formAddInputTo.text.isEmpty()){

                    binding.textTo.visibility = View.INVISIBLE
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEdittextEmpty()
                    binding.textTo.visibility = View.VISIBLE
            }
        })
        binding.formAddInputBy.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (binding.formAddInputBy.text.isEmpty()){

                    binding.textBy.visibility = View.INVISIBLE
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEdittextEmpty()
                binding.textBy.visibility = View.VISIBLE
            }
        })
        binding.formAddInputDescription.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (binding.formAddInputDescription.text.isEmpty()){

                    binding.textDes.visibility = View.INVISIBLE
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEdittextEmpty()
                binding.textDes.visibility = View.VISIBLE
            }
        })
        binding.formInputAddAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (binding.formInputAddAmount.text.isEmpty()){

                    binding.textAmo.visibility = View.INVISIBLE
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEdittextEmpty()
                binding.textAmo.visibility = View.VISIBLE
            }
        })
        //__________________________________________________________________________________________
    }
    //----------------------------------------------------------------- FUN CHECK EDITTEXT EMPTY ---
    fun checkEdittextEmpty(){
        if(binding.formAddInputDate.getText().isEmpty()
            || binding.formAddInputProjectNo.getText().isEmpty()
            || binding.formAddInputReson.getText().isEmpty()
            || binding.formAddInputFrom.getText().isEmpty()
            || binding.formAddInputTo.getText().isEmpty()
            || binding.formAddInputBy.getText().isEmpty()
            || binding.formAddInputDescription.getText().isEmpty()
            || binding.formInputAddAmount.getText().isEmpty()){
            binding.formAddBtnSubmit.isEnabled = false
            binding.formAddBtnSubmit.setBackgroundResource(R.drawable.signup_btn_back)
        }else{
            binding.formAddBtnSubmit.isEnabled = true
            binding.formAddBtnSubmit.setBackgroundResource(R.drawable.form_add_btn_add_list)
        }
    }
    //______________________________________________________________________________________________


}
