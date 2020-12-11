package com.lmy.eyepetizer.ui.activity

import com.lmy.eyepetizer.logic.viewmodle.LoginViewModle
import com.lmy.eyepetizer.R
import com.lmy.eyepetizer.utils.initViewModel
import com.lmy.helloweather.base.BaseActivity

class LoginActivity : BaseActivity<LoginViewModle>(false) {

    override fun layoutId() = R.layout.activity_login

    override fun initVM(): LoginViewModle = initViewModel(this)

    override fun initView() {
    }

    override fun initObserve() {
    }
}