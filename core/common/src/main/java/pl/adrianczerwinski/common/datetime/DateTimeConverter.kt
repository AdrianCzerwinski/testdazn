package pl.adrianczerwinski.common.datetime

interface DateTimeConverter {
    fun toEpochMilliseconds(input: String): Long
    fun formatEpochToLocalDateString(epochMillis: Long): String
}
