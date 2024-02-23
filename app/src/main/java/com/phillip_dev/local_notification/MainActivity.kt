package com.phillip_dev.local_notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.phillip_dev.local_notification.databinding.ActivityMainBinding
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {

    lateinit var appBinding: ActivityMainBinding
    var counter =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = appBinding.root
        setContentView(view)

        appBinding.btnCount.setOnClickListener {
            counter++

            if( counter % 5 == 0){
                sendNotification()
            }

        }

    }

    fun sendNotification(){


    }
}