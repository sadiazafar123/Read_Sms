package com.example.readsmsapplication.model

import android.os.Parcel
import android.os.Parcelable
import kotlin.collections.ArrayList


data class SmsModel(
    val userInfo: ArrayList<UserInfo>
)


data class UserInfo(
    val contact_no: String?,
    val textMsg: String,
    val date: String,
    val textType: String,
    val threadId:Int
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(contact_no)
        parcel.writeString(textMsg)
        parcel.writeString(date)
        parcel.writeString(textType)
        parcel.writeInt(threadId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserInfo> {
        override fun createFromParcel(parcel: Parcel): UserInfo {
            return UserInfo(parcel)
        }

        override fun newArray(size: Int): Array<UserInfo?> {
            return arrayOfNulls(size)
        }
    }
}