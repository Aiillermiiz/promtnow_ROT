package com.example.promtnow_rot.setting

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.example.promtnow_rot.R
import com.example.promtnow_rot.databinding.FragmentChangePasswordBinding

/**
 * A simple [Fragment] subclass.
 */
class FragmentChangePassword : Fragment() {
        lateinit var binding : FragmentChangePasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_change_password, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //---------------------------------------------------------------- ONCLICK -----------------
        binding.changepassBtnBack.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.changepassBtnConfirm.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("ต้องการเปลี่ยนรหัสใช่หรือไม่")
            builder.setMessage("")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->

            }
            builder.setNegativeButton(android.R.string.no) { dialog, which ->

            }
            builder.show()
        }
        //__________________________________________________________________________________________
        //---------------------------------------------------------------- CHECK EMPTY -------------
        binding.changepassInputCurrPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEdittextEmpty()
            }
        })
        binding.changepassInputNewPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEdittextEmpty()
            }
        })
        binding.changepassInputConfirmPassword.addTextChangedListener(object : TextWatcher {
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
        if(binding.changepassInputCurrPassword.getText().isEmpty()
            || binding.changepassInputNewPassword.getText().isEmpty()
            || binding.changepassInputConfirmPassword.getText().isEmpty()) {
            binding.changepassBtnConfirm.isEnabled = false
            binding.changepassBtnConfirm.setBackgroundResource(R.drawable.signup_btn_signup_disable)
        }else{
            binding.changepassBtnConfirm.isEnabled = true
            binding.changepassBtnConfirm.setBackgroundResource(R.drawable.add_btn_submit)
        }
    }
    //______________________________________________________________________________________________

}
