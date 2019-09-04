package zalo.taitd.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class RescheduleAlarmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED -> context.startService(
                Intent(
                    context,
                    RescheduleAlarmsService::class.java
                ).apply {
                    action = Constants.ACTION_RESCHEDULE
                })
        }
    }
}