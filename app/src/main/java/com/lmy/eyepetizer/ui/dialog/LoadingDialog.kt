package com.lmy.eyepetizer.ui.dialog

import android.app.Dialog
import android.content.Context
import com.lmy.eyepetizer.R
import com.wang.avi.AVLoadingIndicatorView

/**
 * Creat 20190221 14:48
 * User Lmy
 * By AndroidStudio
 */
class LoadingDialog @JvmOverloads constructor(
    private val mContext: Context,
    themeResId: Int = R.style.LoadingDialog
) : Dialog(mContext, themeResId) {
    private val loadingView: AVLoadingIndicatorView

    //在构造方法里预加载我们的样式，这样就不用每次创建都指定样式了
    init {
        setContentView(R.layout.loading_dialog)
        loadingView = findViewById(R.id.logdingView)
        loadingView.show()
    }

    fun showLoading() {
        loadingView.show();
    }

    fun hisLoading() {
        loadingView.hide();
    }
}