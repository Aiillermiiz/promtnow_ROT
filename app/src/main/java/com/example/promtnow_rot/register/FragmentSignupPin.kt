package com.example.promtnow_rot.register

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.promtnow_rot.LoginActivity
import com.example.promtnow_rot.R
import com.example.promtnow_rot.databinding.FragmentSignupPinBinding
import com.example.promtnow_rot.homepage.FragmentHomePage
import com.example.promtnow_rot.homepage.InfoUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Element


/**
 * A simple [Fragment] subclass.
 */
class FragmentSignupPin : Fragment(), View.OnClickListener {
    lateinit var binding: FragmentSignupPinBinding
    var pin: String = ""
    var confirmPin : String = ""
    var firstPin : String =""
    var indicators = mutableListOf<View>()
    var pinState = PinState.STATE_CREATE
    var failCount: Int = 0
    var listener: PinListener? = null
    //data user
    var name = ""
    var gmail = ""
    var password = ""
    var staff_code = ""
    var department = ""
    var position = ""
    var pin_from_login = ""

    interface PinListener {
        fun onSuccess(pin:String)
        fun onFail(fail: Int)
    }

    enum class PinState{
        STATE_AUTHEN,
        STATE_CREATE,
        STATE_CONFIRM
    }

    //------------------------------------------------------ RECEIVE & SET DATA TO BUNDLE ----------
    companion object{
        fun newInstance(state: PinState,name:String,gmail:String,password:String,staff_code:String,position:String,department:String,pin:String): FragmentSignupPin{
            val bundle = Bundle()
            bundle.putSerializable("STATE", state)
            bundle.putString("name",name)
            bundle.putString("gmail",gmail)
            bundle.putString("password",password)
            bundle.putString("staff_code",staff_code)
            bundle.putString("department",department)
            bundle.putString("position",position)
            bundle.putString("pin_from_login",pin)
            val fragment = FragmentSignupPin()
            fragment.arguments = bundle
            return fragment
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pinState = arguments?.getSerializable("STATE") as PinState
        name = arguments?.getString("name") as String
        gmail = arguments?.getString("gmail") as String
        password = arguments?.getString("password") as String
        staff_code = arguments?.getString("staff_code") as String
        position = arguments?.getString("position") as String
        department = arguments?.getString("department") as String
        pin_from_login = arguments?.getString("pin_from_login") as String
    }
    //______________________________________________________________________________________________
    //------------------------------------------------------------------ SET PIN LISTENER ----------
    fun setPinListener(listener: PinListener){
        this.listener = listener
    }
    //______________________________________________________________________________________________
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
        setState(pinState)
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
                when(pinState){
                    PinState.STATE_CREATE ->{
                            activity?.onBackPressed()
                        clearFillPin()
                    }
                    PinState.STATE_CONFIRM ->{
                        pinState = PinState.STATE_CREATE
                        setState(pinState)
                    }
                    PinState.STATE_AUTHEN ->{
                        activity?.onBackPressed()
                        clearFillPin()
                    }
                }

            }
        }
    }
    //______________________________________________________________________________________________
    //----------------------------------------------------------------------FUN ADD PIN ------------
    fun addText(pinText: String) {
        val sb = StringBuilder(pin).append(pinText)
        pin = sb.toString()
        notifyIndicatior()
        //check change state
        if (pin.length == 6){
            when(pinState){
                PinState.STATE_CREATE ->{
                    pinState = PinState.STATE_CONFIRM
                    setState(pinState)
                }
                PinState.STATE_CONFIRM ->{
                    confirmPin = pin
                    if(confirmPin == firstPin){
                        Toast.makeText(context,"สร้างบัญชีสำเร็จ",Toast.LENGTH_LONG).show()
                        //set data to database
                        createAccount()
                        //go to login
                        activity!!.startActivity(Intent(activity,LoginActivity::class.java))
                        listener?.onSuccess(confirmPin)
                    }else{
                        pinState = PinState.STATE_CREATE
                        setState(pinState)
                    }
                }
                PinState.STATE_AUTHEN ->{
                    if(pin == pin_from_login){
                        fragmentManager!!.beginTransaction().apply {
                            replace(R.id.layoutFragmentMainPage, FragmentHomePage())
                            commit()
                        }

                    }else{
                        Toast.makeText(context,"รหัสพินไม่ถูกต้อง",Toast.LENGTH_LONG).show()
                        clearFillPin()
                        failCount +=1
                        listener?.onFail(failCount)
                    }
                }
            }
            return
        }
    }

    //______________________________________________________________________________________________
    //------------------------------------------------------------------ FUN REMOVE PIN ------------
    fun removeText() {
        if (pin.length == 0){
            return
        }
        val sb = StringBuilder(pin).deleteCharAt(pin.length - 1)
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
    //---------------------------------------------------------------- SET PIN STATE ---------------
    fun setState(state : PinState){
        when(state){
            PinState.STATE_AUTHEN -> {
                binding.tvTitle.text = "Enter PIN Code"
            }
            PinState.STATE_CREATE -> {
                clearFillPin()
                confirmPin = ""
                binding.tvTitle.text = "Create PIN Code"
            }
            PinState.STATE_CONFIRM -> {
                binding.tvTitle.text = "Confirm PIN Code"
                 firstPin = pin
                clearFillPin()
            }
        }
    }
    //______________________________________________________________________________________________
    //------------------------------------------------------- CLEAR FIILL PIN AND SET EMPTY PIN ----
    fun clearFillPin(){
        pin = ""
        indicators.forEachIndexed { index, view ->
                view.setBackgroundResource(R.drawable.input_pin)
        }
    }
    //______________________________________________________________________________________________
    //------------------------------------------------------------ ADD ACCOUNT TO DATABASE ---------
    fun createAccount(){
        //connect data base
        val db = Firebase.firestore
        //set data
        val account = hashMapOf(
            "department" to department,
            "name" to name,
            "password" to password,
            "pin" to confirmPin,
            "position" to position,
            "staff_code" to staff_code,
            "gmail" to gmail
        )
        // Add data to collection
        db.collection("Users account")
            .add(account)
    }
    //______________________________________________________________________________________________
}

