package com.example.readsmsapplication

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.readsmsapplication.adapters.SmsAdapter
import com.example.readsmsapplication.model.UserInfo

class SmsActivity : AppCompatActivity() {
    lateinit var recyclerView:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms)
        supportActionBar?.hide()
        recyclerView=findViewById(R.id.recyclerSms)
        val threadListObj:ArrayList<UserInfo>?


        if (Build.VERSION.SDK_INT>=33){
            threadListObj = intent.getParcelableArrayListExtra("thread", UserInfo::class.java)
        }else{

            threadListObj = intent.getParcelableArrayListExtra("thread")
        }
        Log.v("thread","$threadListObj")
//        Toast.makeText(this, "$threadListObj", Toast.LENGTH_SHORT).show()
        smsAdapter(threadListObj)
    }

    private fun smsAdapter(threadListObj: ArrayList<UserInfo>?) {
        val smsAdapter:SmsAdapter = SmsAdapter(threadListObj!!)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = smsAdapter
    }
}