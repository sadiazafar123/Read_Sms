package com.example.readsmsapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.readsmsapplication.model.FirebaseMessageModel

class FirebaseNotificationDataActivity : AppCompatActivity() {
    lateinit var fbNotificationData:FirebaseMessageModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_notification_data)
        intent.extras?.let {
            fbNotificationData = it.getParcelable<FirebaseMessageModel>("firebaseMessageModel")!!

        }
        var name:TextView = findViewById(R.id.tvDataName)
        var designation:TextView = findViewById(R.id.tvDesignation)
        name.setText(fbNotificationData.name)
        designation.setText(fbNotificationData.position)





    }
}