package com.example.promtnow_rot.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.promtnow_rot.LoginActivity
import com.example.promtnow_rot.R
import com.example.promtnow_rot.databinding.FragmentSignupConfirmPinBinding


/**
 * A simple [Fragment] subclass.
 */
class FragmentSignupConfirmPin : Fragment(), View.OnClickListener {
        lateinit var binding:FragmentSignupConfirmPinBinding
         var pinConfirm:String =""
         var pinFirst:String=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_signup_confirm_pin, container, false)

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.num0Con.setOnClickListener(this)
        binding.num1Con.setOnClickListener(this)
        binding.num2Con.setOnClickListener(this)
        binding.num3Con.setOnClickListener(this)
        binding.num4Con.setOnClickListener(this)
        binding.num5Con.setOnClickListener(this)
        binding.num6Con.setOnClickListener(this)
        binding.num7Con.setOnClickListener(this)
        binding.num8Con.setOnClickListener(this)
        binding.num9Con.setOnClickListener(this)
        binding.numDeleteCon.setOnClickListener(this)
        binding.numBackCon.setOnClickListener(this)
    }
    //-------------------------------------------------------------------- FUN NEWINSTANCE ---------
    fun newInstance(pinFirst:String): FragmentSignupConfirmPin {
        val fragment = FragmentSignupConfirmPin()
        val bundle = Bundle()
        bundle.putString("pinFirst", pinFirst)
        fragment.setArguments(bundle)
        return fragment
    }
    //______________________________________________________________________________________________
    //--------------------------------------------------------------------------- SET BUNDLE -------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
        var bundle = arguments
        if (bundle != null) {
            pinFirst = bundle.getString("pinFirst").toString()
        }
    }
    //______________________________________________________________________________________________
    //--------------------------------------------------------------------- FUN ONCLICK ------------
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.num_0_con -> {
                addTextCon("0")
            }
            R.id.num_1_con -> {
                addTextCon("1")
            }
            R.id.num_2_con -> {
                addTextCon("2")
            }
            R.id.num_3_con -> {
                addTextCon("3")
            }
            R.id.num_4_con -> {
                addTextCon("4")
            }
            R.id.num_5_con -> {
                addTextCon("5")
            }
            R.id.num_6_con -> {
                addTextCon("6")
            }
            R.id.num_7_con -> {
                addTextCon("7")
            }
            R.id.num_8_con -> {
                addTextCon("8")
            }
            R.id.num_9_con -> {
                addTextCon("9")
            }
            R.id.num_delete_con -> {
                removeText()
            }
            R.id.num_back_con -> {
                fragmentManager!!.beginTransaction().apply {
                    replace(R.id.layoutFragmentContainer, FragmentSignupPin(),"inputPin")
                    commit()
                }
            }
        }
    }
    //______________________________________________________________________________________________
    //----------------------------------------------------------------------FUN ADD PIN ------------
    fun addTextCon(pinText:String){
        var sb = StringBuilder(pinConfirm).append(pinText)
        pinConfirm = sb.toString()
        Log.d("pinCon",pinConfirm)
        if (pinConfirm.length == 1){
            binding.regInputPin1Con.setBackgroundResource(R.drawable.input_pin2)
        }else if(pinConfirm.length == 2){
            binding.regInputPin1Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin2Con.setBackgroundResource(R.drawable.input_pin2)
        }else if(pinConfirm.length == 3){
            binding.regInputPin1Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin2Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin3Con.setBackgroundResource(R.drawable.input_pin2)
        }else if(pinConfirm.length == 4){
            binding.regInputPin1Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin2Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin3Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin4Con.setBackgroundResource(R.drawable.input_pin2)
        }else if(pinConfirm.length == 5){
            binding.regInputPin1Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin2Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin3Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin4Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin5Con.setBackgroundResource(R.drawable.input_pin2)
        }else if(pinConfirm.length == 6){
            binding.regInputPin1Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin2Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin3Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin4Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin5Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin6Con.setBackgroundResource(R.drawable.input_pin2)
            Log.d("pinConfirm",(pinConfirm))
            if(pinConfirm == pinFirst){
                Toast.makeText(context,"Success",Toast.LENGTH_LONG).show()
                activity!!.startActivity(Intent().setClass(activity!!, LoginActivity::class.java))
            }else{
                Toast.makeText(context,"Fail",Toast.LENGTH_LONG).show()
                return
            }
        }
    }
    //______________________________________________________________________________________________
    //------------------------------------------------------------------ FUN REMOVE PIN ------------
    fun removeText(){
        var sb = StringBuilder(pinConfirm).deleteCharAt(pinConfirm.length -1)
        pinConfirm = sb.toString()
        if (pinConfirm.length == 1){
            binding.regInputPin1Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin2Con.setBackgroundResource(R.drawable.input_pin)
            binding.regInputPin3Con.setBackgroundResource(R.drawable.input_pin)
            binding.regInputPin4Con.setBackgroundResource(R.drawable.input_pin)
            binding.regInputPin5Con.setBackgroundResource(R.drawable.input_pin)
            binding.regInputPin6Con.setBackgroundResource(R.drawable.input_pin)
        }else if(pinConfirm.length == 2){
            binding.regInputPin1Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin2Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin3Con.setBackgroundResource(R.drawable.input_pin)
            binding.regInputPin4Con.setBackgroundResource(R.drawable.input_pin)
            binding.regInputPin5Con.setBackgroundResource(R.drawable.input_pin)
            binding.regInputPin6Con.setBackgroundResource(R.drawable.input_pin)
        }else if(pinConfirm.length == 3){
            binding.regInputPin1Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin2Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin3Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin4Con.setBackgroundResource(R.drawable.input_pin)
            binding.regInputPin5Con.setBackgroundResource(R.drawable.input_pin)
            binding.regInputPin6Con.setBackgroundResource(R.drawable.input_pin)
        }else if(pinConfirm.length == 4){
            binding.regInputPin1Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin2Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin3Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin4Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin5Con.setBackgroundResource(R.drawable.input_pin)
            binding.regInputPin6Con.setBackgroundResource(R.drawable.input_pin)
        }else if(pinConfirm.length == 5){
            binding.regInputPin1Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin2Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin3Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin4Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin5Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin6Con.setBackgroundResource(R.drawable.input_pin)
        }else if(pinConfirm.length == 6){
            binding.regInputPin1Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin2Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin3Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin4Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin5Con.setBackgroundResource(R.drawable.input_pin2)
            binding.regInputPin6Con.setBackgroundResource(R.drawable.input_pin2)
        }else{
            binding.regInputPin1Con.setBackgroundResource(R.drawable.input_pin)
            binding.regInputPin2Con.setBackgroundResource(R.drawable.input_pin)
            binding.regInputPin3Con.setBackgroundResource(R.drawable.input_pin)
            binding.regInputPin4Con.setBackgroundResource(R.drawable.input_pin)
            binding.regInputPin5Con.setBackgroundResource(R.drawable.input_pin)
            binding.regInputPin6Con.setBackgroundResource(R.drawable.input_pin)
            return
        }
    }
    //______________________________________________________________________________________________

}
