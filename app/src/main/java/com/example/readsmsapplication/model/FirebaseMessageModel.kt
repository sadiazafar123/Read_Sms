package com.example.readsmsapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FirebaseMessageModel(
    val name:String,
    val company:String,
    val position:String,
    val salary:String
):Parcelable

@Parcelize
data class FirebaseEmployeeModel(
    val name:String,
    val company:String,
    val designation:String,
    val pay:String
):Parcelable
