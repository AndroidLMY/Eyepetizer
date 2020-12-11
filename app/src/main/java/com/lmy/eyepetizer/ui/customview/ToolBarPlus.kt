package com.lmy.eyepetizer.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.lmy.eyepetizer.R
import kotlinx.android.synthetic.main.toolbar_plus_layout.view.*

/**
 * @功能: 自定义标题栏View
 * @Creat 2019/07/16 9:59
 * @User Lmy
 * @By Android Studio
 */
class ToolBarPlus @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) :
    RelativeLayout(context, attrs) {
    var inflate: View
    var title: TextView
    var tvright: TextView
    var ivright: ImageView
    var toolbarplus: Toolbar

    init {
        inflate = LayoutInflater.from(context).inflate(R.layout.toolbar_plus_layout, this, true)
        title = tv_title
        tvright = tv_right
        ivright = iv_right
        toolbarplus = toolbar
    }

}