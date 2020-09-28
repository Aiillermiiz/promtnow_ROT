package com.example.promtnow_rot.homepage

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.promtnow_rot.R
import com.example.promtnow_rot.add.AddActivity
import com.example.promtnow_rot.databinding.FragmentHomePageBinding
import com.example.promtnow_rot.money.MoneyActivity
import com.example.promtnow_rot.request.RequestActivity
import com.example.promtnow_rot.setting.SettingActivity
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
@Suppress("DEPRECATION")
class FragmentHomePage : Fragment(), View.OnClickListener {
    lateinit var binding: FragmentHomePageBinding
    var list = ArrayList<PieEntry>()
    var state = ""
    var alertPen = 0
    //data user
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_page, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (state == "hide"){
            binding.cardRequest.visibility == View.INVISIBLE
        }
        //pie chart
        pieChart(binding.pieId)
        //set date
        val sdf = SimpleDateFormat("MMMM")
        binding.date.text = sdf.format(Date())
        //-------------------------------------------------------------------- ONCLICK -------------
        binding.mainSetting.setOnClickListener(this)
        binding.mainAdd.setOnClickListener(this)
        binding.mainApprove.setOnClickListener(this)
        binding.mainPending.setOnClickListener(this)
        binding.mainReject.setOnClickListener(this)
        binding.maintEditRequest.setOnClickListener(this)
        binding.mainMoney.setOnClickListener(this)
        //__________________________________________________________________________________________
    }

    //-------------------------------------------------------------------- FUN ONCLICK -------------
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.main_setting -> {
                startActivity(Intent(activity, SettingActivity::class.java))
            }
            R.id.main_add -> {
                if (binding.cardRequest.visibility == View.INVISIBLE) {
                    //show card
                    binding.cardRequest.visibility = View.VISIBLE;
                } else {
                    Toast.makeText(context, "คุณได้สร้างเอกสารคำร้องเเล้ว", Toast.LENGTH_LONG).show()
                }
            }
            R.id.main_approve -> {
                startActivity(Intent(activity, RequestActivity::class.java).apply {
                    putExtra("state_request",RequestActivity.RequestState.APPROVED)
                })
            }
            R.id.main_pending -> {
                startActivityForResult(Intent(activity, RequestActivity::class.java).apply {
                    putExtra("state_request",RequestActivity.RequestState.PENDING)
                },2)
                //delay visibility
                binding.alertPen.postDelayed({ binding.alertPen.visibility = View.INVISIBLE }, 1500)
                alertPen = 0
            }
            R.id.main_reject -> {
                startActivity(Intent(activity, RequestActivity::class.java).apply {
                    putExtra("state_request",RequestActivity.RequestState.REJECTED)
                })
            }
            R.id.maint_edit_request -> {
                startActivityForResult(Intent(activity, AddActivity::class.java).apply {
                    putExtra("state_list",AddActivity.ListRequestState.ADD)
                },1)
            }
            R.id.main_money -> {
                startActivity(Intent(activity,MoneyActivity::class.java))
            }
        }//when
    }
    //______________________________________________________________________________________________
    //----------------------------------------------------------------------- ACTIVITY RESULT ------
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 1 = add
        if (requestCode == 1 && resultCode == Activity.RESULT_OK){
            binding.cardRequest.visibility = View.INVISIBLE
            binding.alertPen.visibility = View.VISIBLE
            alertPen++
            binding.alertPen.text = alertPen.toString()
        }
        // 2 = pending
    }
    //______________________________________________________________________________________________
    private fun pieChart(pie: PieChart) {
        //set color
        val color = intArrayOf(
            Color.rgb(0, 111, 227),
            Color.rgb(116, 76, 188),
            Color.rgb(93, 232, 176)
        )
        //
        list.add(PieEntry(2f, "pending"))
        list.add(PieEntry(3f, "rejected"))
        list.add(PieEntry(5f, "approved"))
        //
        var dataset = PieDataSet(list, "request")
        dataset.valueTextSize = 0f;
        dataset.colors = color.toList()
        var data = PieData(dataset)
        pie.data = data
        //hide description
        val des: Description = pie.description
        des.isEnabled = false
        //hide legend
        val leg: Legend = pie.legend
        leg.isEnabled = false
        //
        pie.isDrawHoleEnabled = true;
        pie.setDrawSliceText(false);
        pie.centerText = "คำร้อง";
        pie.setCenterTextSize(19f);
        pie.holeRadius = 70f;
        pie.animateY(2000)
    }

}
