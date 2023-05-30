package com.goda.elmenus.util.extension

import android.content.Context
import android.os.Build
import android.text.format.DateFormat
import android.text.format.DateUtils
import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.annotation.RequiresApi
import java.sql.Timestamp
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.function.Predicate
import java.util.regex.Pattern
import java.util.stream.Collectors
import java.util.stream.Stream
import kotlin.math.roundToInt

private const val SECOND_MILLIS = 1000
private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
private const val DAY_MILLIS = 24 * HOUR_MILLIS
fun String.isNumeric(): Boolean {
    return this.matches("-?\\d+(\\.\\d+)?".toRegex()) //match a number with optional '-' and decimal.
}

fun String.isValidEmailAddress(): Boolean {
    val ePattern =
        "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"
    val p = Pattern.compile(ePattern)
    val m = p.matcher(this)
    return m.matches()
}

fun String.setTodate(): String {
    val dateFormat = SimpleDateFormat("d MMM,h:mm a", Locale.ENGLISH)
    //dateFormat.timeZone = TimeZone.getTimeZone("EET-1")
    // dateFormat.timeZone = TimeZone.getTimeZone("Egypt")
    dateFormat.timeZone = TimeZone.getDefault()
    val dayAfter17 = (Calendar.getInstance().time).time + 17L * 24 * 60 * 60 * 1000
    return dateFormat.format(dayAfter17)
}

fun String.getUserId(): Int {
    if (this == "/users/0" || this.isEmpty() || this.lastIndexOf("/") == -1) return 0
    return this.substring(this.lastIndexOf("/") + 1).toInt()
}

fun String.getuser_phoneId(): Int {
    if (this == "/user_phones/0" || this.isEmpty() || this.lastIndexOf("/") == -1) return 0
    return this.substring(this.lastIndexOf("/") + 1).toInt()
}

fun String.getCountryId(): Int {
    if (this == "/countries/64" || this.isEmpty() || this.lastIndexOf("/") == -1) return 64
    return this.substring(this.lastIndexOf("/") + 1).toInt()
}

fun String.getImageName(): String {
    if (this.isEmpty() || this.lastIndexOf("/") == -1) return ""
    return this.substring(this.lastIndexOf("/") + 1)
}

fun String.getCityId(): Int {
    if (this == "/cities/0" || this.isEmpty() || this.lastIndexOf("/") == -1) return 0
    return this.substring(this.lastIndexOf("/") + 1).toInt()
}
fun String.getCodeId(): Int {
    if (this == "/codes/0" || this.isEmpty() || this.lastIndexOf("/") == -1) return 0
    return this.substring(this.lastIndexOf("/") + 1).toInt()
}
fun String.getColorId(): Int {
    if (this == "/colors/0" || this.isEmpty() || this.lastIndexOf("/") == -1) return 0
    return this.substring(this.lastIndexOf("/") + 1).toInt()
}

fun String.setFromdate(): String {
    val dateFormat = SimpleDateFormat("d MMM,h:mm a", Locale.ENGLISH)
    // dateFormat.timeZone = TimeZone.getTimeZone("UTC+2")
    dateFormat.timeZone = TimeZone.getDefault()
    val dayAfter10 = (Calendar.getInstance().time).time + 10L * 24 * 60 * 60 * 1000
    return dateFormat.format(dayAfter10)

}

fun String.formatDateToanth(): String? {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("d MMM yyyy", Locale.ENGLISH)
    var date: Date?=null
    try {

         date = inputFormat.parse(this) as Date
    } catch (e:Exception) {
       e.printStackTrace()
    }

    return date?.let { outputFormat.format(it) }
}

fun String.formatDateLongToanth(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("d MMM,yyyy", Locale.ENGLISH)
    val date: Date = inputFormat.parse(this)
    return outputFormat.format(date)
}

fun convertToDateViaSqlTimestamp(dateToConvert: LocalDateTime?): Date? {
    return Timestamp.valueOf(dateToConvert.toString())
}

fun String.formatDateEx(): String {
    if (this.isEmpty()) return ""
    val inputFormat: SimpleDateFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        //only api 23 above
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    } else {
        //only api 23 down
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.ENGLISH)
    }
    val inputFormat2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("d MMM,yyyy", Locale.ENGLISH)
    var date: Date = Date()
    try {
        date = inputFormat.parse(this)

    } catch (e: ParseException) {
        date = inputFormat2.parse(this)
    }
    return outputFormat.format(date)
}

fun String.formatDateReview(): String {
    if (this.isEmpty()) return ""
    val inputFormat: SimpleDateFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        //only api 23 above
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.ENGLISH)
    } else {
        //only api 23 down
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.ENGLISH)
    }
    val inputFormat2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("d MMM,yyyy", Locale.ENGLISH)
    var date: Date = Date()
    try {
        date = inputFormat.parse(this)

    } catch (e: ParseException) {
        date = inputFormat2.parse(this)
    }
    return outputFormat.format(date)
}

fun String.formatDateBorrowing(): String {

    val inputFormat: SimpleDateFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        //only api 23 above
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.ENGLISH)
    } else {
        //only api 23 down
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.ENGLISH)
    }
    //inputFormat.timeZone = TimeZone.getTimeZone(TimeZone.getDefault().id)
    val outputFormat = SimpleDateFormat("EEE d/MM/yy, hh:mm a", Locale.ENGLISH)
    val date: Date = inputFormat.parse(this)
    val cal = Calendar.getInstance()
/*    cal.time = date
    cal.add(Calendar.HOUR, -2)
    val oneHourBack = cal.time*/
    outputFormat.timeZone = TimeZone.getTimeZone("GMT")
    return outputFormat.format(date)
}

fun String.formatDateProfile(): String {

    val inputFormat: SimpleDateFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        //only api 23 above
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.ENGLISH)
    } else {
        //only api 23 down
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.ENGLISH)
    }
    val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val date: Date = inputFormat.parse(this)
    return outputFormat.format(date)
}

fun String.formatDateBorrowingDetail(): String {

    val inputFormat: SimpleDateFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        //only api 23 above
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.ENGLISH)
    } else {
        //only api 23 down
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.ENGLISH)
    }
    val outputFormat = SimpleDateFormat("MMM dd, yyyy, hh:mm a", Locale.ENGLISH)
    val date: Date = inputFormat.parse(this)
    val cal = Calendar.getInstance()
    cal.time = date
    cal.add(Calendar.HOUR, -2)
    val oneHourBack = cal.time
    return outputFormat.format(oneHourBack)
}
fun String.formatDateBorrowingex(): String {

    val inputFormat: SimpleDateFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        //only api 23 above
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    } else {
        //only api 23 down
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.ENGLISH)
    }
    val outputFormat = SimpleDateFormat("hh:mm a dd MMM yyyy", Locale.ENGLISH)
    val date: Date = inputFormat.parse(this)
    val cal = Calendar.getInstance()
    cal.time = date
    cal.add(Calendar.HOUR, -2)
    val oneHourBack = cal.time
    return outputFormat.format(oneHourBack)
}
fun String.formatTimeHour(): String {

    val inputFormat: SimpleDateFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        //only api 23 above
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.ENGLISH)
    } else {
        //only api 23 down
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.ENGLISH)
    }
    val outputFormat = SimpleDateFormat("h:mm a", Locale.ENGLISH)
    val date: Date = inputFormat.parse(this)
    return outputFormat.format(date)
}


fun String.toSimpleString(): String {

    val inputFormat: SimpleDateFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        //only api 23 above
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.ENGLISH)
    } else {
        //only api 23 down
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.ENGLISH)
    }
    val outFormat = SimpleDateFormat("EEEE", Locale.ENGLISH)
    val inputFormat2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)

    // val date: Date = inputFormat.parse(this)
    var date: Date = Date()
    try {
        date = inputFormat.parse(this)

    } catch (e: ParseException) {
        date = inputFormat2.parse(this)
    }
    var mReferenceTime: Long = date.time
    when {
        DateUtils.isToday(mReferenceTime) -> return "Today"
        DateUtils.isToday(mReferenceTime + DateUtils.DAY_IN_MILLIS) -> return "Yesterday"
        else -> {
            return outFormat.format(date)
        }

    }

}

fun String.formatConfirmRequest(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("EEE d/MM/yy, HH:mm", Locale.ENGLISH)
    val date: Date = inputFormat.parse(this)
    return outputFormat.format(date)
}
fun String.formatNewDateRequest(): String {
    val inputFormat = SimpleDateFormat("d MMMM yyyy HH:mm", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("EEE d/MM/yy, HH:mm", Locale.ENGLISH)
    val date: Date = inputFormat.parse(this)
    return outputFormat.format(date)
}
fun String.formatNewDateRequest2(): String {
    val inputFormat = SimpleDateFormat("d MMMM yyyy", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val date: Date = inputFormat.parse(this)
    return outputFormat.format(date)
}
fun Date.apiDateFormat(): String {
    return DateFormat.format("yyyy-MM-dd HH:mm", this).toString()


}

fun Int.getDateFormatFilter(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
    dateFormat.timeZone = TimeZone.getDefault()
    val daystring = (Calendar.getInstance().time).time + (this.toLong() * 24 * 60 * 60 * 1000)
    return dateFormat.format(daystring)
}

fun Int.getDateFormatFilterWithoutTime(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    dateFormat.timeZone = TimeZone.getDefault()
    val daystring = (Calendar.getInstance().time).time + (this.toLong() * 24 * 60 * 60 * 1000)
    return dateFormat.format(daystring)
}

fun String.getMinMaxYear(): Int {
    var cal: Calendar = Calendar.getInstance()
    cal.get(Calendar.YEAR)
    if (this == "Min") {
        return cal.get(Calendar.YEAR) - 10

    } else
        return cal.get(Calendar.YEAR) + 1
}

fun Date.toSimpleString(): String {

    val dateFormat = SimpleDateFormat("EEE,MMMd", Locale.ENGLISH)
    return dateFormat.format(this)
}


fun Date.toSimpleTodayString(): String {
    return " Today " /*+ DateFormat.format("hh:mm", this).toString()*/

}

fun String.dayAfterYear(): Date {
    var cal: Calendar = Calendar.getInstance()
    cal.add(Calendar.YEAR, 1)
    var nextYear: Date = cal.time
    return nextYear
}

fun IntRange.rangeIntToArrayStrings(): ArrayList<String>? {
    var returenedList = arrayListOf<String>()
    for (int in this) {
        if (int == 0)
            returenedList.add("00")
        else returenedList.add(int.toString())
    }
    return returenedList
}

fun String.beforeOrNot(anotherDate: String): Boolean {
    val date = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH).parse(this)
    val dateTo = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH).parse(anotherDate)
    return date.before(dateTo)


}

fun String.beforeOrN(anotherDate: String): Boolean {
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(this)
    val dateTo = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(anotherDate)
    return date.before(dateTo)


}

fun String.getPreviousDate(numDatePrevious: Int): String {
    var inputDate = this
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
    try {
        val date = format.parse(inputDate)
        val c = Calendar.getInstance()
        c.time = date
        c.add(Calendar.DATE, -numDatePrevious)
        inputDate = format.format(c.time)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
        inputDate = ""
    }
    return inputDate
}

fun String.stringToDate(): Date {
    return SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH).parse(this)

}

fun String.getDatesBetweenUsing(
    startDate: Date, endDate: Date
): List<Date> {
    val datesInRange = arrayListOf<Date>()
    val calendar: Calendar = /*GregorianCalendar()*/Calendar.getInstance()
    calendar.time = startDate
    val endCalendar: Calendar = Calendar.getInstance()
    endCalendar.time = endDate
    while (calendar.before(endCalendar)) {
        val result = calendar.time
        datesInRange.add(result)
        calendar.add(Calendar.DATE, 1)
    }
    return datesInRange
}



@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.getdaysLocaleDatesBetweenUsing(
    predicate: Predicate<LocalDate>
): List<LocalDate> {
    var cal: Calendar = Calendar.getInstance()
    cal.add(Calendar.YEAR, 1)
    var nextYear: Date = cal.time
    var endDate = nextYear.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()


    val dates: List<LocalDate> = Stream.iterate(this) { date ->
        date.plusDays(
            1
        )
    }
        .limit(ChronoUnit.DAYS.between(this, endDate.plusDays(1)))
        .filter(predicate)
        .collect(Collectors.toList())

    return dates
}


@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.getdaysLocaleDatesBetweenUsingPassedFilter(
    predicate: Predicate<LocalDate>, expireDateLicence: LocalDate, busyDates: MutableSet<LocalDate>
): List<LocalDate> {
    var cal: Calendar = Calendar.getInstance()
    cal.add(Calendar.YEAR, 1)
    var nextYear: Date = cal.time
    var endDate = nextYear.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
    val predicateNotBiggerThanExpireLicenseDate =Predicate { date: LocalDate ->
        !date.isAfter(
            expireDateLicence )
    }
    val predicateBusyDateDate =Predicate { date: LocalDate ->
        !busyDates.contains(
            date )
    }
        val dates: List<LocalDate> = Stream.iterate(this) { date ->
        date.plusDays(
            1
        )
    }
        .limit(ChronoUnit.DAYS.between(this, endDate.plusDays(1)))
        .filter(predicate)
        .filter(predicateBusyDateDate)
        .filter(predicateNotBiggerThanExpireLicenseDate)
        .collect(Collectors.toList())

    return dates
}
@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.getdaysLocaleDatesBetweenUsingPassedFilter2(
    predicate: Predicate<LocalDate>,  lastAvaDate: LocalDate, busyDates: MutableSet<LocalDate>
): List<LocalDate> {
    var cal: Calendar = Calendar.getInstance()
    cal.add(Calendar.YEAR, 1)
    var nextYear: Date = cal.time
    var endDate = nextYear.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
    val predicateNotBiggerThanExpireLicenseDateOrLastAvailbleDate =Predicate { date: LocalDate ->
     !date.isAfter(
            lastAvaDate )
    }
    val predicateBusyDateDate =Predicate { date: LocalDate ->
        !busyDates.contains(
            date )
    }
    val dates: List<LocalDate> = Stream.iterate(this) { date ->
        date.plusDays(
            1
        )
    }
        .limit(ChronoUnit.DAYS.between(this, endDate.plusDays(1)))
        .filter(predicate)
        .filter(predicateBusyDateDate)
        .filter(predicateNotBiggerThanExpireLicenseDateOrLastAvailbleDate)
        .collect(Collectors.toList())

    return dates
}
fun List<Date>.getStartList(): MutableList<String>? {
    var listDatesStrings = arrayListOf<String>()
    for (date in this.indices) {
        if (date == 0) {
            listDatesStrings.add(this[date].toSimpleTodayString())

        } else
            listDatesStrings.add(this[date].toSimpleString())
    }
    return listDatesStrings
}


fun String.passwordValidation(): Boolean {
    var flag = true

    val characterPatten = Pattern.compile("[a-zA-Z ]")
    val digitCasePatten = Pattern.compile("[0-9 ]")
    if (this.length < 8) {
        flag = false
    }


    if (!characterPatten.matcher(this).find()) {
        flag = false
    }
    if (!digitCasePatten.matcher(this).find()) {
        flag = false
    }
    return flag
}

fun String.calculateAge(): Int {
    val birth = Calendar.getInstance()

    val inputFormat: SimpleDateFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        //only api 23 above
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.ENGLISH)
    } else {
        //only api 23 down
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.ENGLISH)
    }
    val date: Date = inputFormat.parse(this)
    birth.time = date
    val today = Calendar.getInstance()
    var yearDifference = (today[Calendar.YEAR]
            - birth[Calendar.YEAR])
    if (today[Calendar.MONTH] < birth[Calendar.MONTH]) {
        yearDifference--
    } else {
        if (today[Calendar.MONTH] === birth[Calendar.MONTH]
            && today[Calendar.DAY_OF_MONTH] < birth[Calendar.DAY_OF_MONTH]
        ) {
            yearDifference--
        }
    }
    return yearDifference
}



fun String.getAge(year: Int, month: Int, day: Int): String {
    val dob = Calendar.getInstance()
    val today = Calendar.getInstance()

    dob.set(year, month, day)

    var age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)

    if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
        age--
    }

    val ageInt = age + 1

    return ageInt.toString()
}

fun getDateDiff(
    format: SimpleDateFormat,
    oldDate: String?,
    newDate: String?
): Long {
    return try {
        TimeUnit.DAYS.convert(
            format.parse(newDate).time - format.parse(oldDate).time, TimeUnit.MILLISECONDS
        )
    } catch (e: Exception) {
        e.printStackTrace()
        0
    }
}

fun dipToPixel(i: Int, context: Context) =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        (i).toFloat(),
        context.resources.displayMetrics
    )
        .roundToInt()
fun convertDpToPixel(dp: Float, context: Context): Float {
    return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun convertPixelsToDp(px: Float, context: Context): Float {
    return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

