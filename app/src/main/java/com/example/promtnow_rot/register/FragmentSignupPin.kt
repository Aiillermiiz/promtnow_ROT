package com.example.promtnow_rot.register

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.promtnow_rot.R
import com.example.promtnow_rot.databinding.FragmentSignupPinBinding
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Element


/**
 * A simple [Fragment] subclass.
 */
class FragmentSignupPin : Fragment(), View.OnClickListener {
    lateinit var binding: FragmentSignupPinBinding
    var pin: String = ""
    var indicators = mutableListOf<View>()
    val listSetbackground = mutableListOf<Unit>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup_pin, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //------------------------------------------------------------------ SET ONCLICK NUMBER ----
        binding.num0.setOnClickListener(this)
        binding.num1.setOnClickListener(this)
        binding.num2.setOnClickListener(this)
        binding.num3.setOnClickListener(this)
        binding.num4.setOnClickListener(this)
        binding.num5.setOnClickListener(this)
        binding.num6.setOnClickListener(this)
        binding.num7.setOnClickListener(this)
        binding.num8.setOnClickListener(this)
        binding.num9.setOnClickListener(this)
        binding.numDelete.setOnClickListener(this)
        binding.numBack.setOnClickListener(this)
        //__________________________________________________________________________________________
        //------------------------------------------------------------ ADD VIEW FOR INPUT PIN ------
        indicators.add(binding.regInputPin1)
        indicators.add(binding.regInputPin2)
        indicators.add(binding.regInputPin3)
        indicators.add(binding.regInputPin4)
        indicators.add(binding.regInputPin5)
        indicators.add(binding.regInputPin6)
        //__________________________________________________________________________________________

    }

    //--------------------------------------------------------------- FUN  WHEN ONCLICK NUMBER -----
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.num_0 -> {
                addText("0")
            }
            R.id.num_1 -> {
                addText("1")
            }
            R.id.num_2 -> {
                addText("2")
            }
            R.id.num_3 -> {
                addText("3")
            }
            R.id.num_4 -> {
                addText("4")
            }
            R.id.num_5 -> {
                addText("5")
            }
            R.id.num_6 -> {
                addText("6")
            }
            R.id.num_7 -> {
                addText("7")
            }
            R.id.num_8 -> {
                addText("8")
            }
            R.id.num_9 -> {
                addText("9")
            }
            R.id.num_delete -> {
                removeText()
            }
            R.id.num_back -> {
                fragmentManager!!.beginTransaction().apply {
                    replace(R.id.layoutFragmentContainer, FragmentSignup(), "signup")
                    commit()
                }
            }
        }
    }

    //______________________________________________________________________________________________
    //----------------------------------------------------------------------FUN ADD PIN ------------
    fun addText(pinText: String) {
        if (pin.length == 6){
            return
        }
        var sb = StringBuilder(pin).append(pinText)
        pin = sb.toString()
        notifyIndicatior()
    }

    //______________________________________________________________________________________________
    //------------------------------------------------------------------ FUN REMOVE PIN ------------
    fun removeText() {
        if (pin.length == 0){
            return
        }
        var sb = StringBuilder(pin).deleteCharAt(pin.length - 1)
        pin = sb.toString()
        notifyIndicatior()
    }

    //______________________________________________________________________________________________
    //---------------------------------------------------------------- SET BACKGROUND WHEN INPUT ---
    fun notifyIndicatior() {
        indicators.forEachIndexed { index, view ->

            view.setBackgroundResource(
                when {
                    index < pin.length -> R.drawable.input_pin2
                    else -> R.drawable.input_pin
                }
            )
            /*if (index <= pin.length){
                view.setBackgroundResource(R.drawable.input_pin2)
            }else{
                view.setBackgroundResource(R.drawable.input_pin)
            }*/
        }
    }
    //______________________________________________________________________________________________
}

