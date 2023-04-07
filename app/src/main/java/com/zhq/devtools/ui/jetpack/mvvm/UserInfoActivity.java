package com.zhq.devtools.ui.jetpack.mvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;

import com.zhq.devtools.R;
import com.zhq.devtools.databinding.ActivityUserInfoBinding;
import com.zhq.devtools.ui.jetpack.mvvm.model.Menu;
import com.zhq.devtools.ui.jetpack.mvvm.model.TangPoem;
import com.zhq.devtools.ui.jetpack.mvvm.model.User;
import com.zhq.devtools.ui.jetpack.mvvm.viewmodel.UserViewModel;

import java.util.List;
import java.util.Objects;

public class UserInfoActivity extends AppCompatActivity {

    private com.zhq.devtools.databinding.ActivityUserInfoBinding binding;
    private UserViewModel userViewModel;
    private static final String TAG = "UserInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = (ActivityUserInfoBinding) DataBindingUtil.setContentView(this, R.layout.activity_user_info);
        initView();
    }

    private void initView() {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        LiveData<User> user = userViewModel.getUser();
        if (user!=null){
            user.observe(this, new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    if (user != null) {
                        binding.setUser(user);
                    }
                }
            });
        }

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                userViewModel.refresh();
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });

        LiveData<List<TangPoem>> tangPoemLiveData = userViewModel.getTangPoem();
        tangPoemLiveData.observe(this, new Observer<List<TangPoem>>() {
            @Override
            public void onChanged(List<TangPoem> tangPoems) {
                Log.d(TAG, "===tangPoemLiveData: "+tangPoems.size());
            }
        });

        userViewModel.getPoemMenu().observe(this, new Observer<Menu>() {
            @Override
            public void onChanged(Menu menu) {
                Log.d(TAG, "===Menu: "+menu);
            }
        });
    }
}