package com.example.services

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.services.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var notification: Notification
    private lateinit var notificationChannel: NotificationChannel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.service.setOnClickListener {
            startService(MyService.newIntent(this))
        }

        binding.foregroundService.setOnClickListener {
            askPermission()
            ContextCompat.startForegroundService(
                this,
                MyForegroundService.newIntent(this)
            )
        }
    }

    // c 13 версии нужно спрашивать пользователя разрешение отправлять уведомления.
    private fun askPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
            }
        }
    }
}