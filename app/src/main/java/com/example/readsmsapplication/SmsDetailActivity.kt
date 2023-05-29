package com.example.readsmsapplication

import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.readsmsapplication.model.UserInfo

class SmsDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms_detail)

        intent.extras?.let {
            val user = it.getParcelable<UserInfo>("userInfo")
            it.clear()
            Log.d("SmsDetailActivity", "UserInfo: " + user.toString())
        }

    }
}