package dev.tenkey.kotoba.widget

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build

/** Runtime-only wake handling is best-effort; Android may stop this process at any time. */
class KotobaWidgetApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val wakeReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == Intent.ACTION_SCREEN_ON) {
                    val prefs = getSharedPreferences(DailyWidgetProvider.PREFS, Context.MODE_PRIVATE)
                    prefs.edit().putInt("step", prefs.getInt("step", 0) + 1).apply()
                    DailyWidgetProvider.refreshAll(context)
                }
            }
        }
        val filter = IntentFilter(Intent.ACTION_SCREEN_ON)
        if (Build.VERSION.SDK_INT >= 33) registerReceiver(wakeReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
        else @Suppress("DEPRECATION") registerReceiver(wakeReceiver, filter)
    }
}
