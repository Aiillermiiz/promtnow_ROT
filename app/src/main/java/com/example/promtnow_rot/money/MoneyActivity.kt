package com.example.promtnow_rot.money

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.promtnow_rot.R
import com.example.promtnow_rot.databinding.ActivityMoneyBinding
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet


class MoneyActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var binding: ActivityMoneyBinding
    var select_year = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_money)
        //----------------------------------------------------------------- SPINNER ----------------
        //spinner month
        val spinner_year = binding.spinnerYear
        //create value from resource
        val adapter_year = ArrayAdapter.createFromResource(this,R.array.year,android.R.layout.simple_spinner_dropdown_item)
        spinner_year.setPopupBackgroundResource(R.drawable.card_dropdown)
        //set dropdown
        adapter_year.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinner_year.adapter = adapter_year
        //when select
        spinner_year.onItemSelectedListener = this
        //__________________________________________________________________________________________
        //-------------------------------------------------------------------- ONCLICK -------------
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        //__________________________________________________________________________________________
        //----------------------------------------------------------------- BAR CHART --------------
        val chart = binding.chart

        val set1 = BarDataSet(getDataSet(), "The year 2017")

        set1.setColors(
            Color.parseColor("#C61E1E"),
            Color.parseColor("#B456DC"),
            Color.parseColor("#92E4F3"),
            Color.parseColor("#DDDDDD"),
            Color.parseColor("#279C45"),
            Color.parseColor("#D7AAEA"),
            Color.parseColor("#FB3C3C"),
            Color.parseColor("#78EA47"),
            Color.parseColor("#203398"),
            Color.parseColor("#FF52D4"),
            Color.parseColor("#FFDE3B"),
            Color.parseColor("#4791FF")
        )

        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(set1)

        val data = BarData(dataSets)
        data.setValueTextSize(15f)
        chart.data = data
        chart.apply {
            // hide Y-axis
            axisLeft.setDrawLabels(false)
            //hide X
            xAxis.isEnabled = false
            // hide description
            description.isEnabled = false
            // hide legend
            legend.isEnabled = false
            chart.animateY(1000)
            chart.invalidate()
        }//apply
        //__________________________________________________________________________________________
    }
    private fun getDataSet(): ArrayList<BarEntry>? {
        val valueSet1: ArrayList<BarEntry> = ArrayList()
        val v1e2 = BarEntry(1f, 12f)
        valueSet1.add(v1e2)
        val v1e3 = BarEntry(2f, 4000f)
        valueSet1.add(v1e3)
        val v1e4 = BarEntry(3f, 3000f)
        valueSet1.add(v1e4)
        val v1e5 = BarEntry(4f, 500f)
        valueSet1.add(v1e5)
        val v1e6 = BarEntry(5f, 200f)
        valueSet1.add(v1e6)
        val v1e7 = BarEntry(6f, 4500f)
        valueSet1.add(v1e7)
        val v1e8 = BarEntry(7f, 1000f)
        valueSet1.add(v1e8)
        val v1e9 = BarEntry(8f, 250f)
        valueSet1.add(v1e9)
        val v1e10 = BarEntry(9f, 800f)
        valueSet1.add(v1e10)
        val v1e11 = BarEntry(10f, 900f)
        valueSet1.add(v1e11)
        val v1e12 = BarEntry(11f, 70f)
        valueSet1.add(v1e12)
        val v1e13 = BarEntry(12f, 1200f)
        valueSet1.add(v1e13)
        return valueSet1
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        select_year = parent!!.getItemAtPosition(position).toString()
        binding.txtYear.text = select_year
    }
}
