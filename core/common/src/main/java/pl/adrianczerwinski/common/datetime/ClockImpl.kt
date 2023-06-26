package pl.adrianczerwinski.common.datetime

import java.util.Calendar
import javax.inject.Inject

class ClockImpl @Inject constructor() : Clock {
    override fun isTomorrow(date: Long): Boolean {
        val today = Calendar.getInstance().apply { timeInMillis = date }
        val tomorrow = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val isSameYear = today.get(Calendar.YEAR) == tomorrow.get(Calendar.YEAR)
        val isTomorrow = today.get(Calendar.DAY_OF_YEAR) == tomorrow.get(Calendar.DAY_OF_YEAR)

        return isSameYear && isTomorrow
    }
}
