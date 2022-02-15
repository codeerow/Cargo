package com.spirit.cargo.app.core.logging

import android.util.Log
import com.spirit.cargo.app.core.analytics.FakeCrashLibrary
import timber.log.Timber

internal class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE or Log.DEBUG) {
            return
        }
        FakeCrashLibrary.log(priority, tag, message)
        if (t != null) {
            if (priority == Log.ERROR) {
                FakeCrashLibrary.logError(t)
            } else if (priority == Log.WARN) {
                FakeCrashLibrary.logWarning(t)
            }
        }
    }
}
