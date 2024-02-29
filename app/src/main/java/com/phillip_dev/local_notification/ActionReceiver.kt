package com.phillip_dev.local_notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ActionReceiver :BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val toastMessage = intent?.getStringExtra("toast")

        Toast.makeText(context,toastMessage,Toast.LENGTH_LONG).show()
    }
}