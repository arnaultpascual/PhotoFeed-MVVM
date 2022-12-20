package dev.test.photofeed_mvvm.util

import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.*

/**
 * Format a [String] given date to RFC 1123 standard
 * @param apiDate : [String] => the date we want to parse
 * @return [String]?
 */
fun formatApiDateToRfc1123(apiDate : String) : String?{
    val date = SimpleDateFormat(Constants.API_DATE_FORMAT, Locale.FRANCE)
        .parse(apiDate)
    val timeZone = TimeZone.getTimeZone("Europe/Paris")
    formatRfc1123DateTime(date, timeZone)
    return formatRfc1123DateTime(date, timeZone)
}


/**
 * Format a [Date] to RFC 1123 standard in [String]?
 * @param date : [Date]? => the date we want to format
 * @return [String]?
 */
fun formatRfc1123DateTime(date: Date?, timeZone: TimeZone?): String? {
    val dateFormat: SimpleDateFormat = SimpleDateFormat(Constants.RFC_1123_DATE_TIME, Locale.FRANCE)
    if (timeZone != null) {
        dateFormat.timeZone = timeZone
    }
    return if (date != null)
        dateFormat.format(date)
    else
        "Erreur conversion de date"
}
