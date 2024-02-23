package com.phillip_dev.local_notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
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

    fun sendNotification(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val chanenl = NotificationChannel(CHANNEL_ID,"1",NotificationManager.IMPORTANCE_DEFAULT)


        }

    }
}