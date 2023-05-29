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
import com.example.readsmsapplication.SmsDetailActivity
import com.example.readsmsapplication.model.UserInfo


class MyBroadCastReceiver: BroadcastReceiver() {
    lateinit var notificationManager:NotificationManager
    val CHANNEL_ID= "12345"


    override fun onReceive(context: Context?, intent: Intent?) {
        var SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED"
/*
        val message = Telephony.Sms.Intents.getMessagesFromIntent(intent)
*/
      //  val content =  message[0].displayMessageBody
      //  val sender = message[0].displayOriginatingAddress

     //   Log.d("BroadcastReceiver", "$content")
     //   Log.d("BroadcastReceiver", "$sender")




/*
        if (intent?.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            Log.d("BroadcastReceiver", "SMS received")
            // Will do stuff with message here
        }
*/
        if (intent?.action == "android.provider.Telephony.SMS_RECEIVED"){
            val message = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            val content =  message[0].displayMessageBody
            val phoneNumber = message[0].displayOriginatingAddress

          //  prepareNotification(content,sender,context)

            Log.d("BroadcastReceiver", "$content")
            Log.d("BroadcastReceiver", "$phoneNumber")
            var userInfo:UserInfo = UserInfo(phoneNumber,content,null,null,null)
            prepareNotification(userInfo,context)
        }



/*
        if (intent?.action == android.provider.Telephony.){
            //val bundle = intent.extras
          //  Log.v("bundle","$bundle")
            val bundle = intent?.extras
            val pdus = bundle?.get("pdus") as Array<*>

            // Process each SMS message
            for (pdu in pdus) {
                val smsMessage = SmsMessage.createFromPdu(pdu as ByteArray)
                val sender = smsMessage.originatingAddress
                val message = smsMessage.messageBody
                Log.v("abc","$message")
              //  Toast.makeText(this, "sms", Toast.LENGTH_SHORT).show()

                // Do something with the SMS message
                // For example, you can display a notification, log it, or pass it to another component
            }


        }
*/

    }

    private fun prepareNotification(userInfo: UserInfo, context: Context?) {
        var bundle=Bundle()
        bundle.putParcelable("userInfo",userInfo)

        notificationManager =context?.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
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
                PendingIntent.getActivity(context, 0, notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
            }
        val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(userInfo.contact_no)
            .setContentText(userInfo.textMsg)
            .setSmallIcon(R.drawable.sms_notifi)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOngoing(true)

        //mBuilder.setContentIntent(pendingIntent)
        val notification: Notification = mBuilder.build()
        notificationManager.notify(1, notification)




    }


}