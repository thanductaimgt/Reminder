package zalo.taitd.reminder

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat


class NotificationService : IntentService(NotificationService::class.java.simpleName) {
    override fun onHandleIntent(intent: Intent) {
        val remindId = intent.getIntExtra(Constants.REMIND, -1)

        val remind = ReminderApplication.database.reminderDao().getRemind(remindId)

        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val requestID = System.currentTimeMillis().toInt()
        val contentIntent = PendingIntent
            .getActivity(this, requestID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !isNotificationChannelCreated) {
            createNotificationChannel()
            isNotificationChannelCreated = true
        }

        val notificationTextFormat = getNotificationTextFormat(remind)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.app_icon_transparent)
            .setTicker(getString(R.string.app_name))
            .setContentTitle(getString(R.string.incoming_event))
            .setContentText(notificationTextFormat)
            .setStyle(NotificationCompat.BigTextStyle().bigText(notificationTextFormat))
            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
            .setDefaults(Notification.DEFAULT_SOUND)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(
                longArrayOf(
                    TIME_VIBRATE.toLong(),
                    TIME_VIBRATE.toLong(),
                    TIME_VIBRATE.toLong(),
                    TIME_VIBRATE.toLong(),
                    TIME_VIBRATE.toLong()
                )
            )
            .setContentIntent(contentIntent)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(remind.id, builder.build())
    }

    private fun getNotificationTextFormat(remind: Remind): String {
        val dateFormat = Utils.getDateFormat(remind.time)
        val timeFormat = Utils.getTimeFormat(remind.time)

        val timeLabel = getString(R.string.time)

        return "$timeLabel: $timeFormat - $dateFormat ${Utils.getDateTimeDiffFormat(
            this,
            remind.time
        )}\n" +
                "${getString(R.string.description)}: ${remind.description}"
    }

    //helper function to create notification channel, use when start service in foreground
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val chan = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
        )
        chan.lightColor = getColor(R.color.lightPrimary)
        chan.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
    }

    companion object {
        private const val TIME_VIBRATE = 1000
        const val CHANNEL_ID = "1447"
        const val CHANNEL_NAME = "Notification Service"
        var isNotificationChannelCreated = false
    }
}