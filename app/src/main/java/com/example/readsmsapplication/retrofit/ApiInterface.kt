package com.example.readsmsapplication.retrofit

import com.example.readsmsapplication.model.UserInfo
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call


interface ApiInterface {
  @POST("abcd")
  fun upLoadSmsInfo(@Body list:ArrayList<UserInfo>):Call<UserInfo>

}