package com.example.promtnow_rot.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataOfForm(var dateRequest:String,var month:String,var year:String,var state:String):Parcelable