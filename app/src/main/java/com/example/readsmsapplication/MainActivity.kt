package com.example.readsmsapplication

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.readsmsapplication.adapters.ThreadAdapter
import com.example.readsmsapplication.model.UserInfo
import com.example.readsmsapplication.retrofit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() ,ThreadAdapter.onItemSpecificUser{
    var permissionCode = 101
    lateinit var recyclerView: RecyclerView
    private var smsInfoList = ArrayList<UserInfo>()
    lateinit var viewModel: MainViewModel
    lateinit var btnUpload: AppCompatButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        btnUpload=findViewById(R.id.btnUpload)
        btnUpload.setOnClickListener(){
          //  CoroutineScope(IO).launch {
               upLoadData()
           // }


        }
        viewModel = ViewModelProvider(this, MyViewModelFactory(Repository(RetrofitClient)))[MainViewModel::class.java]

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(this, arrayOf(android.Manifest.permission.READ_SMS), permissionCode)
        } else {

            readSms1()
        }
    }

    private fun upLoadData() {

    }


    private fun observerUpLoadInfo() {
        viewModel.smsResponseSucces.observe(this){
            Log.v("observer","$it")


        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionCode) {

            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readSms1()
            } else {
                requestPermissions(
                    this, arrayOf(android.Manifest.permission.READ_SMS), permissionCode
                )


            }
        }
    }

    private fun readSms1() {

        CoroutineScope(Dispatchers.IO).launch {
            val list = viewModel.fetchSms(this@MainActivity)
            smsInfoList.clear()
            smsInfoList.addAll(list)
            Log.d("smsInfoList","${smsInfoList.size}")
            Log.v("smsInfoList","$smsInfoList")

           val haspMap =  smsInfoList.groupBy { it.threadId }

//            val keysList : ArrayList<Int> = haspMap.keys.map { it } as ArrayList<Int>
            val entriesList : ArrayList<ArrayList<UserInfo>> = haspMap.entries.map { it.value } as ArrayList<ArrayList<UserInfo>>

        Log.v("smsInfoMap","$entriesList")
//        Log.v("smsInfoMap","$list1")

            withContext(Dispatchers.Main){
                threadAdapter(entriesList)
            }
        }

    }

    private fun threadAdapter(threadsList: ArrayList<ArrayList<UserInfo>>) {

        val mySmsAdapter: ThreadAdapter = ThreadAdapter(threadsList,this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mySmsAdapter
    }

    override fun onItemClick(thread: ArrayList<UserInfo>) {
        var intent = Intent(this, SmsActivity::class.java)
         intent.putExtra("thread",thread)
        Log.v("thread","$thread")

        //var bundl=Bundle()
       // bundl.putParcelable("thread",thread)
        startActivity(intent)
    }
}