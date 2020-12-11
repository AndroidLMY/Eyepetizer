package com.lmy.eyepetizer.logic.bean

/**
 * @author
 * @功能:
 * @Creat 12/10/20 4:59 PM
 * @Compony 465008238@qq.com
 */


data class MessageBean(
    val actionUrl: String,
    val content: String,
    val date: Long,
    val icon: String,
    val id: Int,
    val ifPush: Boolean,
    val pushStatus: Int,
    val title: String,
    val uid: Any,
    val viewed: Boolean
)