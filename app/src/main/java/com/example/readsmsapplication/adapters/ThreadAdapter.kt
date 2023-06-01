package com.example.readsmsapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.readsmsapplication.R
import com.example.readsmsapplication.model.UserInfo
import com.example.readsmsapplication.utils.DateParcer

class ThreadAdapter(private val threadsList: ArrayList<ArrayList<UserInfo>>,var mListener: onItemSpecificUser):
    RecyclerView.Adapter<ThreadAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.thread_itemview, parent, false)
        return MyViewHolder(view)

    }

    override fun getItemCount(): Int {
        return threadsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = threadsList[position].get(0)
        holder.tvContactNo.text = data.contact_no
        holder.tvTextMsg.text = data.textMsg
        holder.tvDate.text = DateParcer.convertLongToTime(data.date!!.toLong())
        if (data.textType.equals("1")) {
            holder.ivType.setImageResource(R.drawable.ic_sms_received)
        } else {
            holder.ivType.setImageResource(R.drawable.ic_sms_sent)

        }
        holder.itemClick.setOnClickListener() {
            mListener.onItemClick(threadsList[position])
        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvContactNo: TextView = itemView.findViewById(R.id.tvContactNo)
        val tvTextMsg: TextView = itemView.findViewById(R.id.tvTextMsg)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val ivType: ImageView = itemView.findViewById(R.id.ivType)
        val itemClick: LinearLayout = itemView.findViewById(R.id.linearLayout)

    }

    interface onItemSpecificUser {
        fun onItemClick(thread: ArrayList<UserInfo>)
    }

}