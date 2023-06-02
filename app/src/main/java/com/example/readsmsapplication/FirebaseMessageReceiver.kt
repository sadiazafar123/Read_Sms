package com.example.readsmsapplication

import android.app.*
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.readsmsapplication.model.FirebaseEmployeeModel
import com.example.readsmsapplication.model.FirebaseMessageModel
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson

class FirebaseMessageReceiver : FirebaseMessagingService() {
    lateinit var firebaseNotificationManager: NotificationManager
    val CHANNEL_ID = "notification_channel"

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("fcmtoken", " Fcm $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d(
            "FirebaseMessageReceiver",
            "onMessageReceived: ${message.notification}, ${message.data}"
        )
        if (message.data != null) {

            handleMessage(message)

//            showNotification(message.data.get("title"), message.data.get("body"))
        }
    }

    private fun handleMessage(remoteMessage: RemoteMessage) {
        val data: MutableMap<String, String> = remoteMessage.data
        val title = data["title"]
        val body = data["body"]
        val notification_type = data["notification_type"]
        if (notification_type == "1") {
            val sadiaData = data["data"]
            sadiaData?.let {
                val parsing = Gson().fromJson(it, FirebaseMessageModel::class.java)
                dataNotification(parsing)
                //converting string into JSON Object
                //  val jsonObject = JSONObject(it)
                //val jsonObject = JSONObject(it)
                /*  val name = jsonObject.get("name").toString()
                  val company = jsonObject.get("company").toString()*/
                // showNotification(name, company)

            }
        }
        else if (notification_type == "2") {
            val employeeData = data["data_employee"]
            employeeData?.let {
                val parsing = Gson().fromJson(it, FirebaseEmployeeModel::class.java)
                employeeNotification(parsing)

            }
        }

    }


    private fun dataNotification(parsing: FirebaseMessageModel) {
        firebaseNotificationManager =
            this.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(CHANNEL_ID, "notification", NotificationManager.IMPORTANCE_HIGH)
            firebaseNotificationManager.createNotificationChannel(notificationChannel)
        }
        val pendingIntent: PendingIntent =
            Intent(this, FirebaseNotificationDataActivity::class.java).apply {
                putExtra("firebaseMessageModel", parsing)
                this.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            }
                .let { notificationIntent ->
                    PendingIntent.getActivity(
                        this, 0, notificationIntent,
                        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                    )
                }
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val r = RingtoneManager.getRingtone(this, defaultSoundUri)
        val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(parsing.name)
            .setContentText(parsing.company)
            .setSmallIcon(R.drawable.sms_notifi)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOngoing(false)

        val notification: Notification = mBuilder.build()
        firebaseNotificationManager.notify(1, notification)


    }

    private fun employeeNotification(parsing: FirebaseEmployeeModel) {
        firebaseNotificationManager =
            this.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(CHANNEL_ID, "notification", NotificationManager.IMPORTANCE_HIGH)
            firebaseNotificationManager.createNotificationChannel(notificationChannel)
        }
        val pendingIntent: PendingIntent =
            Intent(this, FirebaseEmployeeActivity::class.java).apply {
                putExtra("firebaseMessageModel", parsing)
                this.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            }
                .let { notificationIntent ->
                    PendingIntent.getActivity(
                        this, 0, notificationIntent,
                        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                    )
                }
        val pattern = longArrayOf(0,100,200)

        val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(parsing.name)
            .setContentText(parsing.designation)
            .setSmallIcon(R.drawable.sms_notifi)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOngoing(false)

        val notification: Notification = mBuilder.build()
        firebaseNotificationManager.notify(1, notification)


    }


}



