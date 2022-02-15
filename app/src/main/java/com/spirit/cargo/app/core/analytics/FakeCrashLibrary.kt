package com.spirit.cargo.app.core.analytics

/** Not a real crash reporting library!  */
class FakeCrashLibrary private constructor() {
    companion object {
        fun log(priority: Int, tag: String?, message: String?) {
            println(priority)
            println(tag)
            println(message)
            // TODO add log entry to circular buffer.
        }

        fun logWarning(t: Throwable?) {
            println(t)
            // TODO report non-fatal warning.
        }

        fun logError(t: Throwable?) {
            println(t)
            // TODO report non-fatal error.
        }
    }
}
