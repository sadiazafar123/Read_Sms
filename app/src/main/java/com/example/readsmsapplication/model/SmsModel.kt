package com.example.readsmsapplication.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.collections.ArrayList


data class SmsModel(
    val userInfo: ArrayList<UserInfo>
)


@Parcelize
data class UserInfo(
    val contact_no: String? = "",
    val name:String? = "",
    val textMsg: String? = "",
    val date: String? = "",
    val textType: String? = "",
    val threadId:Int? = 0
) : Parcelable
