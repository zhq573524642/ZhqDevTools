package com.zhq.devtools.ui.jetpack.databinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.zhq.devtools.R
import com.zhq.devtools.databinding.ActivityTestDatabindingBinding

class TestDatabindingActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityTestDatabindingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView<ActivityTestDatabindingBinding>(
            this,
            R.layout.activity_test_databinding
        )
        initView()
    }

    private fun initView() {
        val userInfo = UserInfo("张大师", 28)
        mBinding.userInfo = userInfo
        mBinding.isShow = true
        val userHandle = UserHandleEvent()
        mBinding.userHandle = userHandle
    }
}