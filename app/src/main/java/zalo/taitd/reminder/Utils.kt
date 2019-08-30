package zalo.taitd.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build


object Utils {
    fun createAlarm(context: Context, remind: Remind) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, SchedulingService::class.java)
        for (i in 0..9) {
            intent.putExtra(Constants.REMIND, remind)

            val pendingIntent =
                PendingIntent.getService(
                    context,
                    remind.id, intent, PendingIntent.FLAG_UPDATE_CURRENT
                )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                Constants.remindTimes.forEach {
//                    alarmManager
//                        .setExact(AlarmManager.RTC_WAKEUP, remind.time.time - it, pendingIntent)
//                }
                alarmManager
                    .setExact(AlarmManager.RTC_WAKEUP, remind.time.time, pendingIntent)
            } else {
                alarmManager
                    .set(AlarmManager.RTC_WAKEUP, remind.time.time, pendingIntent)
//                Constants.remindTimes.forEach {
//                    alarmManager
//                        .set(AlarmManager.RTC_WAKEUP, remind.time.time - it, pendingIntent)
//                }
            }
        }
    }

    fun generateNewId(reminds: List<Remind>): Int {
        return (if (reminds.isNotEmpty()) reminds.last().id else 0) + 1
    }
}

val Any.TAG: String
    get() = this::class.java.simpleName