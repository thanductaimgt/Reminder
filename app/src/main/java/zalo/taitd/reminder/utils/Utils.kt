package zalo.taitd.reminder.utils

import zalo.taitd.reminder.models.Remind

object Utils{
    fun generateNewId(reminds:List<Remind>):Int{
        return reminds[reminds.size-1].id+1
    }
}

val Any.TAG: String
    get() = this::class.java.simpleName