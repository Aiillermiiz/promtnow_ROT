package com.example.promtnow_rot.register

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DataUser(var name:String
                    ,var gmail:String
                    ,var password:String
                    ,var staff_code:String
                    ,var position:String
                    ,var department:String
                    ,var pin:String): Parcelable


