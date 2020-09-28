package com.example.promtnow_rot.homepage


object InfoUser{
    private var instance : InfoUser? = null
    @Synchronized
    fun getInstance():InfoUser?{
        if (instance == null){
            instance = InfoUser
        }
        return instance
    }
    var name = ""
    var lname = ""
    var gmail = ""
    var password = ""
    var staff_code = ""
    var position = ""
    var department = ""
    var pin = ""
}