package com.example.readsmsapplication.broadcastreceiver

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.readsmsapplication.MainActivity
import com.example.readsmsapplication.R
import com.example.readsmsapplication.model.UserInfo


class MyBroadCastReceiver : BroadcastReceiver() {
    lateinit var notificationManager: NotificationManager
    val CHANNEL_ID = "12345"

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action == "android.provider.Telephony.SMS_RECEIVED") {
            val message = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            val content = message[0].displayMessageBody
            val phoneNumber = message[0].displayOriginatingAddress
            Log.d("BroadcastReceiver", "$content")
            Log.d("BroadcastReceiver", "$phoneNumber")
            var userInfo: UserInfo = UserInfo(phoneNumber, null, content, null, null, null)
            prepareNotification(userInfo, context)
        }
    }

    private fun prepareNotification(userInfo: UserInfo, context: Context?) {
        var bundle = Bundle()
        bundle.putParcelable("userInfo", userInfo)

        notificationManager =
            context?.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(CHANNEL_ID, "location", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val pendingIntent: PendingIntent =
            Intent(context, MainActivity::class.java)
                .apply {
                    putExtra("userInfo", userInfo)
                    this.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                }
                .let { notificationIntent ->
                    PendingIntent.getActivity(
                        context, 0, notificationIntent,
                        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                    )
                }
        val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(userInfo.contact_no)
            .setContentText(userInfo.textMsg)
            .setSmallIcon(R.drawable.sms_notifi)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOngoing(false)

        val notification: Notification = mBuilder.build()
        notificationManager.notify(1, notification)

    }


}