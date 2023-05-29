package com.example.readsmsapplication.adapters

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.readsmsapplication.R
import com.example.readsmsapplication.databinding.AdapterSmsBinding
import com.example.readsmsapplication.gone
import com.example.readsmsapplication.model.UserInfo
import com.example.readsmsapplication.utils.DateParcer
import com.example.readsmsapplication.visible

class SmsAdapter( val threadListObj: ArrayList<UserInfo>):RecyclerView.Adapter<SmsAdapter.SmsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmsViewHolder {
        val binding: AdapterSmsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.adapter_sms,
            parent,
            false
        )
        return SmsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return threadListObj.size
    }

    override fun onBindViewHolder(holder: SmsViewHolder, position: Int) {
        //type 1 - Inbox, 2 - Sent
        if (threadListObj[position].textType=="1") {
            holder.binding.tvMessageReceived.text = threadListObj[position].textMsg
            holder.binding.tvDateReceved.text = DateParcer.convertLongToTime(threadListObj[position].date!!.toLong())
            holder.binding.tvMessageReceived.visible()
            holder.binding.tvDateReceved.visible()
            holder.binding.tvMessageSent.gone()
            holder.binding.tvDateSent.gone()
        }else{
            holder.binding.tvMessageSent.text = threadListObj[position].textMsg
            holder.binding.tvDateSent.text = DateParcer.convertLongToTime(threadListObj[position].date!!.toLong())
            holder.binding.tvMessageSent.visible()
            holder.binding.tvMessageReceived.gone()
            holder.binding.tvDateReceved.gone()
            holder.binding.tvDateSent.visible()
        }
    }
    //view binding in adapter
    class SmsViewHolder(val binding: AdapterSmsBinding) :RecyclerView.ViewHolder(binding.root)

}