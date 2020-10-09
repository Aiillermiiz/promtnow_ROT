package com.example.promtnow_rot.recycleview

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class dataList(var date:String,
                    var projectNo:String,
                    var reson:String,
                    var from:String,
                    var to:String,
                    var vehicle:String,
                    var description:String,
                    var type:String,
                    var amount:Int,
                    var id:String): Parcelable