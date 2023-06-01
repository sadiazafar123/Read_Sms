package com.example.readsmsapplication.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.readsmsapplication.R


object Globals {
    private var dialog: AlertDialog? = null
    fun showProgressBar(context: Activity){
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)

        val inflater: LayoutInflater = context.layoutInflater
        builder.setView(inflater.inflate(R.layout.progress_bar, null))
        builder.setCancelable(true)

        dialog = builder.create()
        dialog?.show()
    }
    fun hideProgressDialog(){
        if (dialog?.isShowing!!){
            dialog?.dismiss()
        }
    }
}