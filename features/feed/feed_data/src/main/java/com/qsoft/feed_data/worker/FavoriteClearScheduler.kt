package com.qsoft.feed_data.worker
import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface FavoriteClearScheduler {
    fun schedule()// → clear করার timer start করবে
    fun cancel()// কাজ বাতিল করো /→ আগের timer বন্ধ করবে
    //মানে এই interface বলতেছে:
    //“যে class এটা implement করবে, তার timer start আর cancel করার ability থাকতে হবে।”
}

class FavoriteClearSchedulerImpl @Inject constructor(//FavoriteClearSchedulerImpl হচ্ছে আসল class
    //@Inject → Hilt automatically এই class তৈরি করবে
    @ApplicationContext private val context: Context
    // ↑ Hilt কে বলছে: "Application-level Context দাও, Activity-র টা না"
) : FavoriteClearScheduler {// implement করছে

    override fun schedule() {//দিয়ে delayed clear job set করা
        val request = OneTimeWorkRequestBuilder<ClearFavoritesWorker>()
            //.setInitialDelay(1, TimeUnit.HOURS)// 1 ঘণ্টা পর clear হবে
            .setInitialDelay(3, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            ClearFavoritesWorker.WORK_NAME,
            ExistingWorkPolicy.REPLACE,//দিয়ে নতুন action এ timer reset করা
            request
        )
    }

    override fun cancel() {
        WorkManager.getInstance(context).cancelUniqueWork(ClearFavoritesWorker.WORK_NAME)
    }
}
