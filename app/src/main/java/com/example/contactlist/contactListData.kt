package com.example.contactlist

data class contactListData(
    var name:String,
    var img:Int,
    var phoneType:String,
    var phoneNumber :String,
    var isSelected:Boolean=false
)
