package pl.adrianczerwinski.common.datetime

import pl.adrianczerwinski.common.resourceprovider.ResourceProvider
import pl.adrianczerwinski.testdazn.core.common.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class DateTimeConverterImpl @Inject constructor(
    private val resourceProvider: ResourceProvider
) : DateTimeConverter {
    override fun toEpochMilliseconds(input: String): Long {
        val sdf = SimpleDateFormat(SERVER_TIME_PATTERN, Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone(DEFAULT_ZONE)
        val date = sdf.parse(input)
        return date?.time ?: 0
    }

    override fun formatEpochToLocalDateString(epochMillis: Long): String {
        val today = Calendar.getInstance().apply { set(Calendar.HOUR_OF_DAY, 0) }
        val yesterday = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1); set(Calendar.HOUR_OF_DAY, 0) }
        val inputDate = Calendar.getInstance().apply { timeInMillis = epochMillis }

        val timeFormat = SimpleDateFormat(APP_TIME_FORMAT, Locale.getDefault())
        val dateFormat = SimpleDateFormat(APP_DATE_FORMAT, Locale.getDefault())
        val time = timeFormat.format(inputDate.time)

        return when {
            inputDate >= today -> resourceProvider.getString(R.string.today, time)
            inputDate >= yesterday -> resourceProvider.getString(R.string.yesterday, time)
            else -> dateFormat.format(inputDate.time)
        }
    }
}

const val SERVER_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
const val DEFAULT_ZONE = "UTC"
const val APP_TIME_FORMAT = "HH:mm"
const val APP_DATE_FORMAT = "dd.MM.yyyy"
