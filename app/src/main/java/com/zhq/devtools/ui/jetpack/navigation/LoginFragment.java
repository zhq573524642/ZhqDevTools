package com.zhq.devtools.ui.jetpack.navigation;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.text.Editable;
import android.text.TextWatcher;

import com.zhq.devtools.R;
import com.zhq.devtools.databinding.FragmentLoginBinding;
import com.zhq.devtools.ui.jetpack.viewmodel.LoginViewModel;
import com.zhq.devtoolslib.base.BaseFragment;


public class LoginFragment extends BaseFragment<FragmentLoginBinding, LoginViewModel> {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initData() {
        VMProviders(LoginViewModel.class);
        initView();
    }


    private void initView() {
        MutableLiveData<String> usernameMutableLiveData = mViewModel.getUsername();
        usernameMutableLiveData.observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mBinding.setUsername(s);
            }
        });
        MutableLiveData<String> passwordMutableLiveData = mViewModel.getPassword();
        passwordMutableLiveData.observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mBinding.setPassword(s);
            }
        });
        mBinding.etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    usernameMutableLiveData.setValue(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mBinding.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    passwordMutableLiveData.setValue(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


}