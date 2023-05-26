package com.example.readsmsapplication.retrofit

import com.example.readsmsapplication.model.UserInfo

class Repository(private val retrofitService: RetrofitClient) {
    fun upLoadSmsInfo(smsInfoList: ArrayList<UserInfo>) =
        retrofitService.getInstance().upLoadSmsInfo(smsInfoList)
}