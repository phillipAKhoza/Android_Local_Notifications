package com.phillip_dev.local_notification

import android.Manifest
import android.app.AlarmManager
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
import androidx.core.content.getSystemService
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.phillip_dev.local_notification.databinding.ActivityMainBinding
import java.util.Calendar
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
        val calender = Calendar.getInstance()
        val currentHour = calender.get(Calendar.HOUR_OF_DAY)
        val currentMinutes = calender.get(Calendar.MINUTE)

        appBinding.btnCount.setOnClickListener {

            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(currentHour)
                .setMinute(currentMinutes)
                .setTitleText("Set Notification Time")
                .build()

            timePicker.show(supportFragmentManager,"1")

            timePicker.addOnPositiveButtonClickListener {
                calender.set(Calendar.HOUR_OF_DAY,timePicker.hour)
                calender.set(Calendar.MINUTE,timePicker.minute)
                calender.set(Calendar.SECOND,0)
                calender.set(Calendar.MILLISECOND,0)

                val intent = Intent(applicationContext, NotificationReceiver::class.java)

                val pendingIntent = if(Build.VERSION.SDK_INT >= 23){
                PendingIntent.getBroadcast(applicationContext,1,intent,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
                }else{
                    PendingIntent.getBroadcast(applicationContext,1,intent,PendingIntent.FLAG_UPDATE_CURRENT )
                }

                val alarmManager : AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calender.timeInMillis,AlarmManager.INTERVAL_DAY,)
            }

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