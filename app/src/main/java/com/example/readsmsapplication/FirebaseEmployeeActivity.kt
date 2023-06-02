package com.example.readsmsapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.readsmsapplication.model.FirebaseEmployeeModel
import com.example.readsmsapplication.model.FirebaseMessageModel

class FirebaseEmployeeActivity : AppCompatActivity() {
    lateinit var fbNotificationData : FirebaseEmployeeModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_employee)
        intent.extras?.let {
            fbNotificationData = it.getParcelable<FirebaseEmployeeModel>("firebaseMessageModel")!!

        }
        var employeeName:TextView=findViewById(R.id.tvEmployeeName)
        var employeeDesignation:TextView=findViewById(R.id.tvEmployeeDesignation)
        employeeName.setText(fbNotificationData.name)
        employeeDesignation.setText(fbNotificationData.company)



    }
}