package com.example.readsmsapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.readsmsapplication.adapters.ThreadAdapter
import com.example.readsmsapplication.model.UserInfo
import com.example.readsmsapplication.utils.Globals
import com.google.firebase.database.*
import com.google.gson.Gson
import kotlinx.coroutines.*

class FirebaseActivity : AppCompatActivity(), ThreadAdapter.onItemSpecificUser {
    lateinit var rootDatabase: FirebaseDatabase
    lateinit var smsRef: DatabaseReference
    private var list = ArrayList<UserInfo>()
    lateinit var firebaseRecyclerview: RecyclerView
    val threadsList: ArrayList<ArrayList<UserInfo>> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase)
        firebaseRecyclerview = findViewById(R.id.recyclerViewFirebase)

        rootDatabase = FirebaseDatabase.getInstance()
        smsRef = rootDatabase.getReference("SMS")

        getAllData()
        Log.d("FirebaseActivity", "addListenerForSingleValueEvent onCancelled")

    }

    private fun getAllData() {
//        lifecycleScope.launch(Dispatchers.IO){
            getFirebaseMessages()
//            withContext(Dispatchers.Main) {
//                Globals.hideProgressDialog()
//                threadAdapter(threadsList)
//            }
//        }
    }

    private fun getFirebaseMessages() {
        Globals.showProgressBar(this)
        smsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (value in snapshot.children) {
                    var smsList: ArrayList<UserInfo> = ArrayList()
                    for (thread in value.children) {
                        val userInfo = thread.getValue(UserInfo::class.java)
                        userInfo?.let {
                            smsList.add(it)
                        }
                    }
                    threadsList.add(smsList)
                    Globals.hideProgressDialog()
                    threadsList.sortByDescending { it[0].date }
                    threadAdapter(threadsList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d(
                    "FirebaseActivity",
                    "addListenerForSingleValueEvent onCancelled" + error.toString()
                )
            }
        })
    }


    private fun threadAdapter(threadsList: ArrayList<ArrayList<UserInfo>>) {
        val mySmsAdapter: ThreadAdapter = ThreadAdapter(threadsList, this)
        firebaseRecyclerview.layoutManager = LinearLayoutManager(this)
        firebaseRecyclerview.adapter = mySmsAdapter

    }

    override fun onItemClick(thread: ArrayList<UserInfo>) {
        var intent = Intent(this, SmsActivity::class.java)
        intent.putExtra("thread", thread)
        startActivity(intent)
    }


}

