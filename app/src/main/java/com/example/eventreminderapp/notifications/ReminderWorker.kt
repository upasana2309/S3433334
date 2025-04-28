package com.example.eventreminderapp.notifications

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class ReminderWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        val title = inputData.getString("title") ?: return Result.failure()
        val description = inputData.getString("description") ?: ""

        NotificationHelper.showNotification(applicationContext, title, description)
        return Result.success()
    }
}
