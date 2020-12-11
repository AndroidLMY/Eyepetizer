package com.lmy.helloweather.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lmy.eyepetizer.ui.dialog.LoadingDialog

/**
 * @author lmy
 * @功能:
 * @Creat 2020/11/6 1:21 PM
 * @Compony 465008238@qq.com
 */
abstract class BaseFragment<VM : BaseViewModel>(isDataBinding: Boolean = true) : Fragment() {
    private var mContext: Context? = null
    protected lateinit var mViewModel: VM
    private var isDataBinding = isDataBinding
    protected lateinit var mBinding: ViewDataBinding
    private var isFirstLoad = true // 是否第一次加载
    private var loadingDialog: LoadingDialog? = null//加载等待的dialog
    protected var isFirstRequest: Boolean = true//设置进入界面是否初始化数据

    /**
     * 初始化ViewBinding
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var parentView = inflater.inflate(getLayoutResId(), container, false)
        if (isDataBinding) {
            mBinding = DataBindingUtil.bind(parentView)!!
            mBinding.lifecycleOwner = this
        }
        return parentView
    }

    /**
     *初始化
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewModel = initVM()
        lifecycle.addObserver(mViewModel)
        initViews()
        initObserve()
        if (isFirstRequest) {
            initData()
        }
        super.onViewCreated(view, savedInstanceState)
        finishCreateView(savedInstanceState)
    }


    /**
     * 获取布局id
     */
    abstract fun getLayoutResId(): Int

    /**
     * 初始化ViewModel
     */
    abstract fun initVM(): VM

    /**
     * 初始化View
     */
    abstract fun initViews()
    abstract fun initObserve()
    abstract fun initData()

    /**
     * 布局加载完成执行
     * CreateView初始化完成
     */
    abstract fun finishCreateView(state: Bundle?)

    override fun onDestroy() {
        mViewModel?.let {
            lifecycle.removeObserver(it)
        }
        super.onDestroy()
        mContext = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    open fun showProgress() {
        mContext?.let {
            if (loadingDialog == null) {
                loadingDialog = LoadingDialog(mContext!!).apply {
                    setCancelable(false)
                }
            }
            loadingDialog?.apply {
                show()
                showLoading()
            }
        }
    }

    open fun hideProgress() {
        mContext?.let {
            loadingDialog?.apply {
                cancel()
                hisLoading()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isFirstLoad) {
            isFirstLoad = false;
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isFirstLoad = false
    }

}