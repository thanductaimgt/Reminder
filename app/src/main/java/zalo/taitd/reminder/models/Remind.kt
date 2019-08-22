package zalo.taitd.reminder.models

import java.util.*

data class Remind(
    var id: Int,
    var time: Date = Date(),
    var isEnabled: Boolean
)