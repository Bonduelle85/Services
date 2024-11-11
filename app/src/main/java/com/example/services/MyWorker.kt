package com.example.services

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class MyWorker(
    appContext: Context,
    private val workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        log("doWork")
        val page = workerParams.inputData.getInt(PAGE_KEY, 0)
        repeat(10) {
            Thread.sleep(1000)
            log("Page $page: $it")
        }
        return Result.success()
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", "MyWorker: $message")
    }

    companion object {

        private const val PAGE_KEY = "PAGE_KEY"
        const val WORK_NAME = "WORK_NAME"

        fun oneTimeWorkRequest(page: Int) = OneTimeWorkRequestBuilder<MyWorker>()
            .setInputData(workDataOf(PAGE_KEY to page))
            .setConstraints(constrains())
            .build()

        private fun constrains() = Constraints.Builder()
            .setRequiresCharging(true)
            .build()
    }
}