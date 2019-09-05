package zalo.taitd.reminder

import android.app.IntentService
import android.content.Intent

class RescheduleAlarmsService : IntentService(RescheduleAlarmsService::class.java.simpleName) {
    override fun onHandleIntent(intent: Intent) {
        when (intent.action) {
            Constants.ACTION_RESCHEDULE -> {
                ReminderApplication.database.reminderDao()
                    .getAllReminds()
                    .filter { it.isEnabled }
                    .forEach { remind ->
                        Utils.createAlarms(this, remind)
                    }
            }
        }
    }
}