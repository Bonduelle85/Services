package com.example.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyService : Service() {

    val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        scope.launch {
            repeat(50) {
                delay(1000)
                log("onStartCommand..$it")
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
        scope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? { TODO("Not yet implemented") }

    private fun log(message: String) {
        Log.d("MyService", "$this: $message")
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, MyService::class.java)
    }
}