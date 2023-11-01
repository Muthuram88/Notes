package `in`.kumudan.notes.Util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun fromDate(time:Long):String{
    val date= Date(time)
    val format=SimpleDateFormat("EEE,dd MMM", Locale.getDefault())
    return format.format(date)
}

fun onlyTimeFromDate(time:Long):String{
    val date= Date(time)
    val format=SimpleDateFormat("hh:mm aaa", Locale.getDefault())
    return format.format(date)
}

fun isToday(time: Long): Boolean {
    val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(System.currentTimeMillis())
    val date= Date(time)
    val format=SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    return today == (format.format(date))
}