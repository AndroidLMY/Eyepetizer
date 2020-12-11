package com.lmy.eyepetizer.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author lmy
 * @功能: 时间工具类
 * @Creat 11/27/20 10:57 AM
 * @Compony 465008238@qq.com
 */
class TimeUtils {
    //时间转化毫秒
    companion object {
        fun date2ms(dateForamt: String?, time: String?): Long {
            val calendar = Calendar.getInstance()
            try {
                calendar.time = SimpleDateFormat(dateForamt).parse(time)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return calendar.timeInMillis
        }

        //毫秒转化成日期
        fun ms2date(dateForamt: String?, ms: Long): String {
            val date = Date(ms)
            val format = SimpleDateFormat(dateForamt)
            return format.format(date)
        }

        /**
         * 时间戳转日期
         */
        fun unix2Date(dateForamt: String?, ms: Long): String {
            val sdf = SimpleDateFormat(dateForamt)
            return sdf.format(Date(ms * 1000))
        }

        /*
         * 将时间戳转换为时间
         */
        fun unix2Date(dateForamt: String?, time: String): String {
            val res: String
            val simpleDateFormat = SimpleDateFormat(dateForamt)
            val lt: Long = time.toLong()
            val date = Date(lt)
            res = simpleDateFormat.format(date)
            return res
        }

        fun trasnsLong2Date(timeL: Long): String {
            return SimpleDateFormat("yyyy/MM/dd").format(
                Date(timeL)
            )
        }
    }

}