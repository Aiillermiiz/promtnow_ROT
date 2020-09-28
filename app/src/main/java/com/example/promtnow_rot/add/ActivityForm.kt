package com.example.promtnow_rot.add

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.promtnow_rot.R
import com.example.promtnow_rot.recycleview.dataList
import com.example.promtnow_rot.databinding.ActivityFormBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.shagi.materialdatepicker.date.DatePickerFragmentDialog
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.fragment_signup.view.*
import java.text.SimpleDateFormat
import java.util.*

class ActivityForm : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var binding: ActivityFormBinding
    var pageState = PageState.ADD_STATE
    var listType = mutableListOf<String>()
    var listVehicle = mutableListOf<String>()
    var type = ""
    var vehicle = ""

    //state of page add and edit
    enum class PageState {
        ADD_STATE, EDIT_STATE,SEE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_form)
        //---------------------------------------------------------------------- SET STATE ---------
        pageState = intent.getSerializableExtra("state") as PageState
        setState(pageState)
        //__________________________________________________________________________________________
        //----------------------------------------------------------------- GET DATA TO MUTABLE ----
        getListType()
        getListVehicle()
        //__________________________________________________________________________________________
        //------------------------------------------------------------------- ONCLICK --------------
        binding.formAddBtnSubmit.setOnClickListener {
            if (checkEdittextEmpty()) {
                Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบทุกช่อง", Toast.LENGTH_LONG).show()
            } else {
                var listData = initData(
                    binding.formAddInputDate.text.toString(),
                    binding.formAddInputProjectNo.text.toString(),
                    binding.formAddInputReson.text.toString(),
                    binding.formAddInputFrom.text.toString(),
                    binding.formAddInputTo.text.toString(),
                    vehicle,
                    binding.formAddInputDescription.text.toString(),
                    type,
                    binding.formInputAddAmount.text.toString()
                )
                var intent = Intent()
                intent.putExtra("LIST_DATA", listData)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }//else
        }
        binding.btnBack.setOnClickListener {
            when(pageState){
                PageState.ADD_STATE -> {
                    onBackPressed()
                }
                PageState.EDIT_STATE -> {
                    if (checkEdittextEmpty()) {
                        Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบทุกช่อง", Toast.LENGTH_LONG).show()
                    } else {
                        onBackPressed()
                    }//else
                }
                PageState.SEE -> {
                    onBackPressed()
                }
            }//when
        }
        //date picker
        binding.formAddInputDate.setOnClickListener {
            val dialog = DatePickerFragmentDialog.newInstance({ view, year, monthOfYear, dayOfMonth ->
                val calendar = Calendar.getInstance()
                calendar.set(year, monthOfYear, dayOfMonth)
                val simpleDateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
                binding.formAddInputDate.setText("${simpleDateFormat.format(calendar.time)}")
            }, 2017, 11, 4)
            dialog.show(supportFragmentManager, "tag")
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
        if (binding.formAddInputDate.text.isEmpty()
            || binding.formAddInputProjectNo.text.isEmpty()
            || binding.formAddInputReson.text.isEmpty()
            || binding.formAddInputFrom.text.isEmpty()
            || binding.formAddInputTo.text.isEmpty()
            || binding.formAddInputDescription.text.isEmpty()
            || binding.formInputAddAmount.text.isEmpty()
        ) {
            return true
        }
        return false
    }

    //______________________________________________________________________________________________
    //---------------------------------------------------------------- SET PIN STATE ---------------
    fun setState(state: PageState) {
        when (state) {
            PageState.ADD_STATE -> {
                binding.textTop.text = "รายการคำร้อง"
            }
            PageState.EDIT_STATE -> {
                binding.textTop.text = "เเก้ไขรายการคำร้อง"
                intent?.getParcelableExtra<dataList>("list_model").let {
                    binding.formAddInputDate.text = it?.date
                    binding.formAddInputProjectNo.setText(it?.projectNo)
                    binding.formAddInputReson.setText(it?.reson)
                    binding.formAddInputFrom.setText(it?.from)
                    binding.formAddInputTo.setText(it?.to)
                    binding.formAddInputDescription.setText(it?.description)
                    binding.formInputAddAmount.setText(it?.amount.toString())
                }
            }
            PageState.SEE -> {
                binding.textTop.text = "รายการคำร้อง"
                binding.formAddBtnSubmit.visibility = View.INVISIBLE
                binding.formAddInputDate.isEnabled = false
                binding.formAddInputProjectNo.isEnabled = false
                binding.formAddInputReson.isEnabled = false
                binding.formAddInputFrom.isEnabled = false
                binding.formAddInputTo.isEnabled = false
                spinner_vehicle.isEnabled = false
                spinner_type.isEnabled = false
                binding.attachImg.isEnabled = false
                binding.formAddInputDescription.isEnabled = false
                binding.formInputAddAmount.isEnabled = false
            }
        }//when
    }

    //______________________________________________________________________________________________
    //------------------------------------------------------------- ADD DATA TO MUTABLELIST --------
    fun initData(
        date: String,
        projectNo: String,
        reson: String,
        from: String,
        to: String,
        vehicle: String,
        description: String,
        type: String,
        amount: String
    ): dataList {
        return dataList(date, projectNo, reson, from, to, vehicle, description, type, amount.toInt())
    }

    //______________________________________________________________________________________________
    //---------------------------------------------------- FUN GET DATA TYPE FROM DATABASE ---------
    private fun getListType() {
        val db = Firebase.firestore
        val data = db.collection("Type")
        data.get().addOnSuccessListener { result ->
            for (doc in result) {
                listType.add(doc["detail"].toString())
            }//for
            setSpinner(listType, "type")
        }
    }

    //______________________________________________________________________________________________
    //------------------------------------------------- FUN GET DATA VEHICLE TYPE FROM DATABASE ----
    private fun getListVehicle() {
        val db = Firebase.firestore
        val data = db.collection("Vehicle type")
        data.get().addOnSuccessListener { result ->
            for (doc in result) {
                listVehicle.add(doc.id)
            }//for
            setSpinner(listVehicle, "vehicle")
        }
    }

    //______________________________________________________________________________________________
    //------------------------------------------------------------------ FUN SET SPINNER -----------
    fun setSpinner(list: MutableList<String>, name: String) {
        when (name) {
            "vehicle" -> {
                val adapterVeh = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)
                adapterVeh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerVehicle.apply {
                    adapter = adapterVeh
                    onItemSelectedListener = this@ActivityForm
                }//apply
            }
            "type" -> {
                val adapterType = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)
                adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerType.apply {
                    adapter = adapterType
                    onItemSelectedListener = this@ActivityForm
                }//apply
            }
        }//when
    }

    //______________________________________________________________________________________________
    //----------------------------------------------------------------------- FUN ON ITEM SELECT ---
    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent!!.id) {
            R.id.spinner_vehicle -> {
                vehicle = parent.getItemAtPosition(position).toString()
                binding.txtSelectBy.text = vehicle
            }
            R.id.spinner_type -> {
                type = parent.getItemAtPosition(position).toString()
                binding.txtSelectType.text = type
            }
        }//when
    }
    //______________________________________________________________________________________________
}

