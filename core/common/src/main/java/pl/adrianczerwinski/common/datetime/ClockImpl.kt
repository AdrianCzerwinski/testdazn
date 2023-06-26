package pl.adrianczerwinski.common.datetime

import java.util.Calendar
import javax.inject.Inject

class ClockImpl @Inject constructor() : Clock {
    override fun isTomorrow(date: Long): Boolean {

        val inputDate = Calendar.getInstance().apply { timeInMillis = date }
        val tomorrow = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, 1) }

        return inputDate.get(Calendar.DAY_OF_YEAR) == tomorrow.get(Calendar.DAY_OF_YEAR)
    }
}
