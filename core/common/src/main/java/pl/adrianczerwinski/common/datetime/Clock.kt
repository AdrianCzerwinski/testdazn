package pl.adrianczerwinski.common.datetime

interface Clock {
    fun isTomorrow(date: Long): Boolean
}
