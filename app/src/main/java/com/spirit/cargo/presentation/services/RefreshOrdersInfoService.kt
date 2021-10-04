package com.spirit.cargo.presentation.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.spirit.cargo.R
import com.spirit.cargo.domain.model.request.model.CargoRequest
import com.spirit.cargo.presentation.MainActivity
import io.reactivex.rxjava3.disposables.Disposable
import org.koin.android.ext.android.inject

class RefreshOrdersInfoService : Service() {

    private val viewModel by inject<BaseRefreshOrdersInfoViewModel>()
    private var disposables = Disposable.empty()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val requestId = intent?.extras?.getInt(REQUEST_ID_EXTRA)

        if (intent?.action != null && intent.action.equals(STOP_SERVICE_ACTION)) {
            requestId?.let(viewModel::unregisterRequestId)
            if (!viewModel.hasRegisteredIds()) {
                disposables.dispose()
                stopForeground(true)
            }
        } else {
            if (!viewModel.hasRegisteredIds()) {
                createNotificationChannel()
                val notification = buildNotification()
                startForeground(ONGOING_NOTIFICATION_ID, notification)

                disposables = viewModel.entities
                    .doOnNext(::updateNotification)
                    .subscribe()
            }
            requestId?.let(viewModel::registerRequestId)
        }
        return START_NOT_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "RefreshOrdersInfo Service Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(serviceChannel)
        }
    }

    private fun buildNotification(requests: List<CargoRequest> = listOf()): Notification {
        val contentText = getString(R.string.total_orders_count, requests.sumOf { it.orders })

        val pendingIntent: PendingIntent =
            Intent(this, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, 0)
            }

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentText(contentText)
            .setContentIntent(pendingIntent)
            .setSilent(true)
            .build()
    }

    private fun updateNotification(requests: List<CargoRequest> = listOf()) {
        val notification: Notification = buildNotification(requests)

        with(getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager) {
            notify(ONGOING_NOTIFICATION_ID, notification)
        }
    }

    companion object {
        private const val CHANNEL_ID = "com.spirit.cargo"
        private const val ONGOING_NOTIFICATION_ID = 1

        const val STOP_SERVICE_ACTION = "STOP_SERVICE_ACTION"

        const val REQUEST_ID_EXTRA = "REQUEST_ID_EXTRA"
        const val REQUEST_URL_EXTRA = "REQUEST_URL_EXTRA"
        const val REQUEST_TITLE_EXTRA = "REQUEST_TITLE_EXTRA"
    }
}