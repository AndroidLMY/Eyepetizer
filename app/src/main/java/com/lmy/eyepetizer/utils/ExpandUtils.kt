package com.lmy.eyepetizer.utils

import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.lmy.eyepetizer.EyepetizerApplication
import java.util.regex.Pattern

/**
 * @author
 * @功能: 扩展函数的工具类
 * @Creat 2020/11/6 5:55 PM
 * @Compony 465008238@qq.com
 */
/**
 * 吐司扩展函数
 */
fun Any.toast(gravity: Int = Gravity.BOTTOM) {
    Toast.makeText(EyepetizerApplication.context, this.toString(), Toast.LENGTH_LONG).apply {
        setGravity(gravity, 0, 0)
    }.show()
}


/**
 * 通过泛型进行封装
 * java规定所有类型的数字都是可比较的  需要实现Comparabla接口
 */
fun <T : Comparable<T>> max(vararg nums: T): T {
    if (nums.isEmpty()) throw RuntimeException("parms can not be empty")
    var maxNum = nums[0]
    for (num in nums) {
        if (num > maxNum) maxNum = num
    }
    return maxNum
}
/****************************************Activity跳转的扩展函数****************************************************/

/**
 * Activity跳转
 * startActivitys<T>()
 * 不携带参数的封装
 */
inline fun <reified T> ktstartActivity() {
    val intent = Intent(EyepetizerApplication.context, T::class.java)
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    EyepetizerApplication.context.startActivity(intent)
}

/**
 * Activity跳转
 * 使用方法
 *   startActivitys<T> {
 *      putExtra("lng", place.location.lng)
 *   }
 *   reified 这个是为了满足inline特性而设计的语法糖，因为给函数使用内联之后，
 *   编译器会用其函数体来替换掉函数调用，而如果该函数里面有泛型就可能会出现编译器不懂该泛型的问题，
 *   所以引入reified，使该泛型被智能替换成对应的类型
 * 携带参数的封装
 */
//
inline fun <reified T> ktstartActivity(block: Intent.() -> Unit) {
    val intent = Intent(EyepetizerApplication.context, T::class.java)
    intent.block()
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    EyepetizerApplication.context.startActivity(intent)
}

/**
 *初始化ViewModel的封装
 */
inline fun <reified T : ViewModel> initViewModel(
    context: ViewModelStoreOwner,
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): T {
    val factory: ViewModelProvider.Factory? = factoryProducer?.invoke()
    return if (factory == null) {
        ViewModelProvider(context)[T::class.java]
    } else {
        ViewModelProvider(context, factory)[T::class.java]
    }
}


/****************************************Log打印得扩展函数****************************************************/
fun Any.logd() {
    if (LogUtils.isPrintLog) {
        Log.d(LogUtils.TGA, "$this")
    }
}

fun Any.loge() {
    if (LogUtils.isPrintLog) {
        Log.e(LogUtils.TGA, "$this")
    }
}

fun Any.logi() {
    if (LogUtils.isPrintLog) {
        Log.i(LogUtils.TGA, "$this")
    }
}

fun Any.logv() {
    if (LogUtils.isPrintLog) {
        Log.v(LogUtils.TGA, "$this")
    }
}

fun Any.logw() {
    if (LogUtils.isPrintLog) {
        Log.w(LogUtils.TGA, "$this")
    }
}

/**
 * 判断手机号是否合法
 */
fun String.isPhoneNumber(): Boolean {
    return if (this.isEmpty()) {
        false
    } else Pattern.matches(REGEX_MOBILE, this)
}

/**
 * 判断手机号是否合法
 */
fun Int.isPhoneNumber(): Boolean {
    return if (this.toString().isEmpty()) {
        false
    } else Pattern.matches(REGEX_MOBILE, this.toString())
}

//校验手机是否合规 2020年最全的国内手机号格式
private const val REGEX_MOBILE =
    "((\\+86|0086)?\\s*)((134[0-8]\\d{7})|(((13([0-3]|[5-9]))|(14[5-9])|15([0-3]|[5-9])|(16(2|[5-7]))|17([0-3]|[5-8])|18[0-9]|19(1|[8-9]))\\d{8})|(14(0|1|4)0\\d{7})|(1740([0-5]|[6-9]|[10-12])\\d{7}))"

