package com.example.promtnow_rot.add

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.example.promtnow_rot.R
import com.example.promtnow_rot.databinding.FragmentListAddBinding

/**
 * A simple [Fragment] subclass.
 */
class FragmentListAdd : Fragment() {
            lateinit var binding:FragmentListAddBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_list_add, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //---------------------------------------------------------------- CHECK EDITTEXT EMPTY ----
        binding.listInputDate.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEdittextEmpty()
            }
        })
        binding.listInputProjectNo.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (binding.listInputProjectNo.text.isEmpty()){
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
        binding.listInputReson.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (binding.listInputReson.text.isEmpty()){

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
        binding.listInputFrom.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (binding.listInputFrom.text.isEmpty()){

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
        binding.listInputTo.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (binding.listInputTo.text.isEmpty()){

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
        binding.listInputBy.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (binding.listInputBy.text.isEmpty()){

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
        binding.listInputDescription.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (binding.listInputDescription.text.isEmpty()){

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
        binding.listInputAddAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (binding.listInputAddAmount.text.isEmpty()){

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
        if(binding.listInputDate.getText().isEmpty()
            || binding.listInputProjectNo.getText().isEmpty()
            || binding.listInputReson.getText().isEmpty()
            || binding.listInputFrom.getText().isEmpty()
            || binding.listInputTo.getText().isEmpty()
            || binding.listInputBy.getText().isEmpty()
            || binding.listInputDescription.getText().isEmpty()
            || binding.listInputAddAmount.getText().isEmpty()){
            binding.listBtnEdit.isEnabled = false
            binding.listBtnEdit.setBackgroundResource(R.drawable.signup_btn_back)
        }else{
            binding.listBtnEdit.isEnabled = true
            binding.listBtnEdit.setBackgroundResource(R.drawable.edit_pro_btn_edit)
        }
    }
    //______________________________________________________________________________________________


}
