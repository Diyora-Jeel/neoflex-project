package com.example.neoflex

import android.app.Application
import com.onesignal.OneSignal
import com.onesignal.debug.LogLevel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ApplicationClass : Application()
{
    private val ONESIGNAL_APP_ID = "db13aae5-4638-4723-9480-00649e92ad76"

    override fun onCreate() {
        super.onCreate()

        OneSignal.Debug.logLevel = LogLevel.VERBOSE

        OneSignal.initWithContext(this, ONESIGNAL_APP_ID)

        CoroutineScope(Dispatchers.IO).launch {
            OneSignal.Notifications.requestPermission(true)
        }
    }
}