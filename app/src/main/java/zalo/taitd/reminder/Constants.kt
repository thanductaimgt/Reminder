package zalo.taitd.reminder

object Constants {
    const val REMIND = "REMIND"
    private const val FIRST_REMIND_GAP_IN_MILLI = 24*3_600_000
    private const val SECOND_REMIND_GAP_IN_MILLI = 12*3_600_000
    private const val THIRD_REMIND_GAP_IN_MILLI = 6*3_600_000
    private const val FOUR_REMIND_GAP_IN_MILLI = (0.5*3_600_000).toInt()
    private const val FIFTH_REMIND_GAP_IN_MILLI = (0.25*3_600_000).toInt()

    val remindTimes = arrayListOf(
        FIRST_REMIND_GAP_IN_MILLI,
        SECOND_REMIND_GAP_IN_MILLI,
        THIRD_REMIND_GAP_IN_MILLI,
        FOUR_REMIND_GAP_IN_MILLI,
        FIFTH_REMIND_GAP_IN_MILLI
    )
}