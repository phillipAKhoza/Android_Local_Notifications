package com.phillip_dev.local_notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationReceiver : BroadcastReceiver() {
    private  val CHANNEL_ID = "1"
    override fun onReceive(context: Context?, intent: Intent?) {
        if(context !=null){
            val builder = NotificationCompat.Builder(this@MainActivity,CHANNEL_ID)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                val chanel = NotificationChannel(CHANNEL_ID,"1", NotificationManager.IMPORTANCE_DEFAULT)
                val manager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannel(chanel)

                builder.setSmallIcon(R.drawable.notification_icon)
                    .setContentTitle("Notification")
                    .setContentText("You have add 5 to the counter")
            }else{
                builder.setSmallIcon(R.drawable.notification_icon)
                    .setContentTitle("Notification")
                    .setContentText("You have add 5 to the counter")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            }
            val notificationManagerCompat = NotificationManagerCompat.from(this@MainActivity)
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS),1)
            }else {
                notificationManagerCompat.notify(1, builder.build())
            }
        }
    }
}