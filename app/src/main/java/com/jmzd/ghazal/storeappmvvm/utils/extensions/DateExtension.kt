package academy.nouri.storeapp.utils.extensions

import academy.nouri.storeapp.utils.views.TimeUtils

fun String.convertDateToFarsi(): String {
    val dateSplit = this.split("-")
    val timeUtils = TimeUtils(dateSplit[0].toInt(), dateSplit[1].toInt(), dateSplit[2].toInt())
    val iranianDate = timeUtils.iranianDate
    val iranianDateSplit = iranianDate.split("/")
    val year = iranianDateSplit[0]
    val month = iranianDateSplit[1]
    val day = iranianDateSplit[2]
    return "$year ${monthName(month.toInt())} $day"
}

fun monthName(index: Int): String {
    val name = when (index) {
        1 -> "فروردین"
        2 -> "اردیبهشت"
        3 -> "خرداد"
        4 -> "تیر"
        5 -> "مرداد"
        6 -> "شهریور"
        7 -> "مهر"
        8 -> "آبان"
        9 -> "آذر"
        10 -> "دی"
        11 -> "بهمن"
        12 -> "اسفند"
        else -> ""
    }
    return name
}