package com.zhq.devtools.ui.jetpack.navigation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhq.devtools.R;
import com.zhq.devtools.databinding.FragmentLoginBinding;
import com.zhq.devtools.ui.jetpack.view_model.LoginViewModel;


public class LoginFragment extends Fragment {


    private com.zhq.devtools.databinding.FragmentLoginBinding binding;
    private LoginViewModel loginViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login,container,false);
        initView();
        return binding.getRoot();
    }


    private void initView() {
        loginViewModel = new ViewModelProvider(getActivity()).get(LoginViewModel.class);
        MutableLiveData<String> usernameMutableLiveData = loginViewModel.getUsername();
        usernameMutableLiveData.observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                 binding.setUsername(s);
//                binding.tvUsername.setText(s);
            }
        });
        MutableLiveData<String> passwordMutableLiveData = loginViewModel.getPassword();
        passwordMutableLiveData.observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.setPassword(s);
//                binding.tvPassword.setText(s);
            }
        });
        binding.etUsername.addTextChangedListener(new TextWatcher() {
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

        binding.etPassword.addTextChangedListener(new TextWatcher() {
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