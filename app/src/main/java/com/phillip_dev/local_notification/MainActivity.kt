package com.phillip_dev.local_notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.phillip_dev.local_notification.databinding.ActivityMainBinding
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {

    lateinit var appBinding: ActivityMainBinding
    var counter =0
    private  val CHANNEL_ID = "1"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = appBinding.root
        setContentView(view)

        appBinding.btnCount.setOnClickListener {
            counter++
                appBinding.btnCount.text = counter.toString()
            if( counter % 5 == 0){

                    sendNotification()
            }

        }

    }

    private fun sendNotification(){

        val intent = Intent(applicationContext,MainActivity::class.java)
        val pendingIntent = if(Build.VERSION.SDK_INT >=23){
            PendingIntent.getActivity(applicationContext,2,intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }else{
            PendingIntent.getActivity(applicationContext,2,intent, PendingIntent.FLAG_UPDATE_CURRENT)

        }

        //ActionButton
        val actionIntent = Intent(applicationContext,ActionReceiver::class.java)
        actionIntent.putExtra("toast","This is a Notification Message")
        val pendingActionIntent = if(Build.VERSION.SDK_INT >=23){
            PendingIntent.getBroadcast(applicationContext,3,actionIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }else{
            PendingIntent.getBroadcast(applicationContext,3,actionIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        }

        val builder = NotificationCompat.Builder(this@MainActivity,CHANNEL_ID)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val chanel = NotificationChannel(CHANNEL_ID,"1",NotificationManager.IMPORTANCE_DEFAULT)
            val manager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(chanel)

            builder.setSmallIcon(R.drawable.notification_icon)
                    .setContentTitle("Notification")
                    .setContentText("You have add 5 to the counter")
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .addAction(R.drawable.notification_icon,"Toast Message",pendingActionIntent)
        }else{
            builder.setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Notification")
                .setContentText("You have add 5 to the counter")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

        }
        val notificationManagerCompat = NotificationManagerCompat.from(this@MainActivity)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS),1)
        }else {
            notificationManagerCompat.notify(1, builder.build())
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 1 && grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            val builder = NotificationCompat.Builder(this@MainActivity,CHANNEL_ID)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                val chanel = NotificationChannel(CHANNEL_ID,"1",NotificationManager.IMPORTANCE_DEFAULT)
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

            }
            notificationManagerCompat.notify(1, builder.build())
        }
    }

}