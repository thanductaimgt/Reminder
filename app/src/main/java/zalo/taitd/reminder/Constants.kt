package zalo.taitd.reminder

object Constants {
    const val ACTION_RESCHEDULE = "ACTION_RESCHEDULE"
    const val REMIND = "REMIND"
    const val DEFAULT_HOUR = 9

    private const val FIRST_REMIND_GAP_IN_MILLI = 48*3_600_000
    private const val SECOND_REMIND_GAP_IN_MILLI = 24*3_600_000
    private const val THIRD_REMIND_GAP_IN_MILLI = 3_600_000
    private const val FOUR_REMIND_GAP_IN_MILLI = (0.25*3_600_000).toInt()
    private const val FIFTH_REMIND_GAP_IN_MILLI = 0

    val remindTimes = arrayListOf(
        FIRST_REMIND_GAP_IN_MILLI,
        SECOND_REMIND_GAP_IN_MILLI,
        THIRD_REMIND_GAP_IN_MILLI,
        FOUR_REMIND_GAP_IN_MILLI,
        FIFTH_REMIND_GAP_IN_MILLI
    )

    const val ONE_DAY_IN_MILLISECOND = 24 * 3600 * 1000
    const val ONE_HOUR_IN_MILLISECOND = 3600 * 1000
    const val ONE_MIN_IN_MILLISECOND = 60 * 1000
}