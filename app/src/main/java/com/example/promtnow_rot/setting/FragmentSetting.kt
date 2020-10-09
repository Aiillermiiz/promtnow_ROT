package com.example.promtnow_rot.setting

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.promtnow_rot.LoginActivity
import com.example.promtnow_rot.Prefs
import com.example.promtnow_rot.R
import com.example.promtnow_rot.databinding.FragmentSettingBinding
import com.example.promtnow_rot.homepage.InfoUser
import com.example.promtnow_rot.homepage.MainPageActivity
import com.example.promtnow_rot.profile.ProfileActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.dialog.view.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentSetting : Fragment() {
    lateinit var binding: FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //set text status bar black
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        binding.settingTextProfile.setOnClickListener {
            startActivity(Intent(activity, ProfileActivity::class.java))
        }
        binding.settingTextChangepass.setOnClickListener {
            fragmentManager!!.beginTransaction().apply {
                replace(R.id.layoutFragmentSetting, FragmentChangePassword())
                addToBackStack("changepassword")
                commit()
            }
        }
        binding.settingBtnBack.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.settingTextLogout.setOnClickListener {
            //Inflate the dialog with custom view
            val DialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog, null)
            val title = TextView(requireContext())
            title.text = "Do you want to logout?"
            title.setPadding(50, 50, 50, 50);
            title.textSize = 20F;
            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(requireContext())
                .setView(DialogView)
                .setCustomTitle(title)
            //show dialog
            val mAlertDialog = mBuilder.show()
            //button ok
            DialogView.btn_yes.setOnClickListener {
                Prefs(requireContext()).rememberCheck = false
                Prefs(requireContext()).image = ""
                clearDeviceId()
                activity!!.finishAffinity()
                startActivity(Intent(context, LoginActivity::class.java))
            }
            //button cancel
            DialogView.btn_no.setOnClickListener {
                //dismiss dialog
                mAlertDialog.dismiss()
            }
        }
    }

    private fun clearDeviceId() {
        val db = Firebase.firestore
        val data = db.collection("Users account")
        data.whereEqualTo("device_id", InfoUser.device_id)
            .get()
            .addOnSuccessListener { docs ->
                for (doc in docs) {
                    data.document(doc.id).update("device_id", "")
                }//for
            }
    }

}
