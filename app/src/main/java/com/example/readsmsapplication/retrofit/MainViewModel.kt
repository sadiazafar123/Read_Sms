package com.example.readsmsapplication.retrofit

import android.content.Context
import android.provider.Telephony
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.readsmsapplication.model.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainViewModel(private val repository: Repository) : ViewModel() {

    var smsResponseSucces = MutableLiveData<UserInfo>()
    var errorMsg = MutableLiveData<String>()
    var threadIdList = ArrayList<Int>()

    fun upLoadUserSmsInfo(smsInfoList: ArrayList<UserInfo>) {
        val response = repository.upLoadSmsInfo(smsInfoList)
        response.enqueue(object : Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                if (response.isSuccessful) {
                    smsResponseSucces.postValue(response.body())
                    Log.v("response", "success: ..." + response.body())

                } else {
                    Log.v("response", "failure: ...." + response.message())
                }

            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {

                Log.v("response", "onFailure: ...." + t)
                errorMsg.postValue(t.message)
            }

        })

    }

    /*
    fun fetchSms(context: Context, unique: Boolean, thId: Int): ArrayList<UserInfo> {
        val smsInfoList = ArrayList<UserInfo>()
        val smsInfoList2 = ArrayList<UserInfo>()

        val numberCol = Telephony.TextBasedSmsColumns.ADDRESS
        val threadCol = Telephony.TextBasedSmsColumns.THREAD_ID
        val nameCol = Telephony.TextBasedSmsColumns.PERSON
        val smsCol = Telephony.TextBasedSmsColumns.BODY
        val typeCol = Telephony.TextBasedSmsColumns.TYPE // 1 - Inbox, 2 - Sent
        val smsDate = Telephony.TextBasedSmsColumns.DATE
        val projection = arrayOf(numberCol, nameCol, smsCol, typeCol, smsDate, threadCol)

        val cursor = context.contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            projection, null, null, null
        )

        val numberColIdx = cursor!!.getColumnIndex(numberCol)
        val nameColIdx = cursor!!.getColumnIndex(nameCol)
        val textColIdx = cursor.getColumnIndex(smsCol)
        val typeColIdx = cursor.getColumnIndex(typeCol)
        val typeColDate = cursor.getColumnIndex(smsDate)
        val typeColThreadId = cursor.getColumnIndex(threadCol)

        while (cursor.moveToNext()) {
            val number = cursor.getString(numberColIdx)
            val name = cursor.getString(nameColIdx)
            val text = cursor.getString(textColIdx)
            val type = cursor.getString(typeColIdx)
            val date = cursor.getString(typeColDate)
            val threadId = cursor.getInt(typeColThreadId)
            val userInfo = UserInfo(number, text, date, type, threadId)
            if (unique) {
                if (threadIdList.size < 1) {
                    threadIdList.add(threadId)
                } else {
                    for (j in 0 until threadIdList.size) {
                        if (!threadIdList.contains(threadId)) {
                            threadIdList.add(threadId)
                            smsInfoList.add(userInfo)
                            Log.v("threadIdList", "threadId " + threadId)
                            Log.v("threadIdList", "smsInfoList2 " + smsInfoList2.size)
                        }

                    }
                }
            } else {

                if (thId == threadId) {

                    smsInfoList.add(userInfo)
                    Log.v("threadIdList", "threadId " + threadId)
                    Log.v("threadIdList", "smsInfoList2 " + smsInfoList2.size)
                    // break


                }
            }




            Log.d("MY_APP", "$number $text $type $name")
        }
        cursor.close()
        return smsInfoList

    }
    */
    fun fetchSms(context: Context): ArrayList<UserInfo> {
        val smsInfoList = ArrayList<UserInfo>()

        val numberCol = Telephony.TextBasedSmsColumns.ADDRESS
        val threadCol = Telephony.TextBasedSmsColumns.THREAD_ID
        val nameCol = Telephony.TextBasedSmsColumns.PERSON
        val smsCol = Telephony.TextBasedSmsColumns.BODY
        val typeCol = Telephony.TextBasedSmsColumns.TYPE // 1 - Inbox, 2 - Sent
        val smsDate = Telephony.TextBasedSmsColumns.DATE
        val projection = arrayOf(numberCol, nameCol, smsCol, typeCol, smsDate, threadCol)

        val cursor = context.contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            projection, null, null, null
        )

        val numberColIdx = cursor!!.getColumnIndex(numberCol)
        val nameColIdx = cursor.getColumnIndex(nameCol)
        val textColIdx = cursor.getColumnIndex(smsCol)
        val typeColIdx = cursor.getColumnIndex(typeCol)
        val typeColDate = cursor.getColumnIndex(smsDate)
        val typeColThreadId = cursor.getColumnIndex(threadCol)

        while (cursor.moveToNext()) {
            val number = cursor.getString(numberColIdx)
            val name = cursor.getString(nameColIdx)
            val text = cursor.getString(textColIdx)
            val type = cursor.getString(typeColIdx)
            val date = cursor.getString(typeColDate)
            val threadId = cursor.getInt(typeColThreadId)
            val userInfo = UserInfo(number, text, date, type, threadId)
            smsInfoList.add(userInfo)

            Log.d("MY_APP", "$number $text $type $name")
        }

        cursor.close()
        return smsInfoList
    }
}



