package com.lmy.eyepetizer.ui.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.lmy.eyepetizer.R
import com.lmy.eyepetizer.databinding.DefaltDialogBinding
import com.lmy.eyepetizer.logic.bean.DefaltBean
import kotlinx.android.synthetic.main.defalt_dialog.*


/**
 * @author
 * @功能:默认风格的dialog
 * @Creat 12/11/20 1:42 PM
 * @Compony 465008238@qq.com
 */
/*val messageTest = "sss"
DefaltDialog(this).apply {
    setCancelable(false)
    setDefaltBean {
        defaltBean.apply {
            message = messageTest
        }
    }
    setCancelOnclick {
        dismiss()
    }
    setSureOnclick {
        dismiss()
    }
}.show()*/

class DefaltDialog(context: Context, themeResId: Int) : Dialog(context, themeResId) {
    lateinit var defaltbinding: DefaltDialogBinding
    lateinit var defaltBean: DefaltBean

    constructor(context: Context) : this(context, R.style.DefaltDialog) {
        defaltbinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.defalt_dialog,
            null,
            false
        )
        defaltBean = DefaltBean()
        defaltbinding.defaltBean = defaltBean
        setContentView(defaltbinding.root)

        //设置自适应的方法：

        //设置自适应的方法：
        val dialogParams: WindowManager.LayoutParams = window!!.attributes
        dialogParams.apply {
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            //设置底部显示
            gravity = Gravity.CENTER
            windowAnimations = R.style.DefaltDialog_Animation
        }
        window!!.attributes = dialogParams
    }

    fun setDefaltBean(block: () -> DefaltBean) {
        defaltbinding.defaltBean = block()
    }

    fun setSureOnclick(block: () -> Unit) {
        tv_sure.setOnClickListener {
            block()
        }
    }

    fun setCancelOnclick(block: () -> Unit) {
        tv_cancel.setOnClickListener {
            block()
        }
    }
}

