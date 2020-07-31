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
import com.example.promtnow_rot.databinding.FragmentEditProfileBinding

/**
 * A simple [Fragment] subclass.
 */
class FragmentEditProfile : Fragment() {
        lateinit var binding : FragmentEditProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_edit_profile, container, false)
        return  binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //----------------------------------------------------------------- ONCLICK ----------------
        binding.editproBtnBack.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.editproBtnEdit.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("ต้องการแก้ไขข้อมูลหรือไม่")
            builder.setMessage("")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->

            }
            builder.setNegativeButton(android.R.string.no) { dialog, which ->

            }
            builder.show()
        }
        //__________________________________________________________________________________________
        //---------------------------------------------------------------- CHECK EDITTEXT EMPTY ----
        binding.editproInputGmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEdittextEmpty()
            }
        })
        binding.editproInputPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEdittextEmpty()
            }
        })
        binding.editproInputName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEdittextEmpty()
            }
        })
        binding.editproInputStaffCode.addTextChangedListener(object : TextWatcher {
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
        if(binding.editproInputGmail.getText().isEmpty()
            || binding.editproInputPassword.getText().isEmpty()
            || binding.editproInputName.getText().isEmpty()
            || binding.editproInputStaffCode.getText().isEmpty()) {
            binding.editproBtnEdit.isEnabled = false
            binding.editproBtnEdit.setBackgroundResource(R.drawable.signup_btn_signup_disable)
        }else{
            binding.editproBtnEdit.isEnabled = true
            binding.editproBtnEdit.setBackgroundResource(R.drawable.edit_pro_btn_edit)
        }
    }
    //______________________________________________________________________________________________

}
