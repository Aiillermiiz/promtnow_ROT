package com.example.promtnow_rot.register

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.promtnow_rot.LoginActivity
import com.example.promtnow_rot.R
import com.example.promtnow_rot.databinding.FragmentSignupBinding


/**
 * A simple [Fragment] subclass.
 */
class FragmentSignup : Fragment() {

    lateinit var binding:FragmentSignupBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //---------------------------------------------------------------------- ONCLICK -----------
        var pinFragmentSignupPin = FragmentSignupPin.newInstance(FragmentSignupPin.PinState.STATE_CREATE)
        pinFragmentSignupPin.setPinListener(object : FragmentSignupPin.PinListener{
            override fun onSuccess(pin: String) {
                Log.d("pinsucess",pin)
            }
            override fun onFail(fail: Int) {
                Log.d("failcount",fail.toString())
            }
        })
        //button sign up
        binding.regBtnSignup.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("ต้องการดำเนินการต่อหรือไม่")
            builder.setMessage("")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                fragmentManager!!.beginTransaction().apply {
                    replace(R.id.layoutFragmentContainer, pinFragmentSignupPin,"inputPin")
                    addToBackStack("inputPin")
                    commit()
                }
            }
            builder.setNegativeButton(android.R.string.no) { dialog, which ->

            }
            builder.show()
        }
        //button back
        binding.regBtnBackLogin.setOnClickListener {
            activity?.onBackPressed()
        }
        //__________________________________________________________________________________________
        //---------------------------------------------------------------- CHECK EDITTEXT EMPTY ----
        binding.regInputGmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEdittextEmpty()
            }
        })
        binding.regInputPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEdittextEmpty()
            }
        })
        binding.regInputName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEdittextEmpty()
            }
        })
        binding.regInputStaffCode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEdittextEmpty()
            }
        })
        //__________________________________________________________________________________________

    }
    //----------------------------------------------------------------- FUN CHECK EDITTEXT EMPTY ---
    fun checkEdittextEmpty(){
        if(binding.regInputGmail.getText().isEmpty()
            || binding.regInputPassword.getText().isEmpty()
            || binding.regInputName.getText().isEmpty()
            || binding.regInputStaffCode.getText().isEmpty()) {
            binding.regBtnSignup.isEnabled = false
            binding.regBtnSignup.setBackgroundResource(R.drawable.signup_btn_signup_disable)
        }else{
            binding.regBtnSignup.isEnabled = true
            binding.regBtnSignup.setBackgroundResource(R.drawable.signup_btn_signup)
        }
    }
    //______________________________________________________________________________________________

}

