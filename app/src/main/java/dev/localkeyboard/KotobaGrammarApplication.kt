package dev.localkeyboard

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build

/** Android only sends screen-on to runtime receivers; it is best-effort while this app process is alive. */
class KotobaGrammarApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val wakeReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == Intent.ACTION_SCREEN_ON) {
                    val prefs = getSharedPreferences("kotoba_grammar", Context.MODE_PRIVATE)
                    prefs.edit().putInt("step", prefs.getInt("step", 0) + 1).apply()
                }
            }
        }
        val filter = IntentFilter(Intent.ACTION_SCREEN_ON)
        if (Build.VERSION.SDK_INT >= 33) registerReceiver(wakeReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
        else @Suppress("DEPRECATION") registerReceiver(wakeReceiver, filter)
    }
}
