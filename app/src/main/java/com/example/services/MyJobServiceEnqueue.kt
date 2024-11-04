package com.example.services

import android.app.job.JobParameters
import android.app.job.JobService
import android.os.PersistableBundle
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyJobServiceEnqueue : JobService() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        log("onStartJob")
        val page = params?.extras?.getInt(PAGE_KEY) ?: 0
        coroutineScope.launch {
            repeat(5) {
                delay(1000)
                log("Page: $page, Timer: $it")
            }
            jobFinished(params, true)
        }
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        log("onStopJob")
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
        coroutineScope.cancel()
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", "MyJobServiceEnqueue: $message")
    }

    companion object {

        const val JOB_ID = 1
        private const val PAGE_KEY = "PAGE_KEY"

        fun newBundle(page: Int): PersistableBundle {
            return PersistableBundle().apply {
                putInt(PAGE_KEY, page)
            }
        }
    }
}