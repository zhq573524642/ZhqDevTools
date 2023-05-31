package com.zhq.devtools.ui.jetpack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;
import com.zhq.devtools.R;
import com.zhq.devtools.databinding.ActivityJatpackBinding;
import com.zhq.devtools.ui.jetpack.databinding.RecyclerBindingAdapter;
import com.zhq.devtools.ui.jetpack.databinding.TestDatabindingActivity;
import com.zhq.devtools.ui.jetpack.databinding.UserInfoBean;
import com.zhq.devtools.ui.jetpack.databinding.TwoWayBindingViewModel2;
import com.zhq.devtools.ui.jetpack.navigation.NavigationActivity;

import java.util.ArrayList;
import java.util.List;

public class JetpackActivity extends AppCompatActivity {

    private com.zhq.devtools.databinding.ActivityJatpackBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = (ActivityJatpackBinding) DataBindingUtil.setContentView(this, R.layout.activity_jatpack);
        initView();
    }

    private void initView() {
//        binding.btnNavigation.setOnClickListener(v -> {
////            startActivity(new Intent(JetpackActivity.this, NavigationActivity.class));
//        });
        binding.btnMvvm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionX.init(JetpackActivity.this)
                        .permissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .request(new RequestCallback() {
                            @Override
                            public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {
                                if (allGranted) {
                                    startActivity(new Intent(JetpackActivity.this, UserInfoActivity.class));
                                }
                            }
                        });

            }
        });
        binding.btnDataBinding.setOnClickListener(v -> {
            startActivity(new Intent(this, TestDatabindingActivity.class));
        });
        binding.setHandleClickEvent(new HandleClickEventListener());
        binding.setContext(JetpackActivity.this);
        binding.setImageViewPadding(20);
        binding.setImageUrl("https://img1.baidu.com/it/u=413643897,2296924942&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500");
//       binding.setBindingViewModel(new TwoWayBindingViewModel());
        binding.setBindingViewModel2(new TwoWayBindingViewModel2());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<UserInfoBean> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserInfoBean bean = new UserInfoBean();
            bean.username = "张三" + i;
            bean.userAge = (i + 1) * 10;
            bean.userAvatar = "https://img1.baidu.com/it/u=413643897,2296924942&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500";
            userList.add(bean);
        }
        RecyclerBindingAdapter recyclerBindingAdapter = new RecyclerBindingAdapter(userList);
        binding.recyclerView.setAdapter(recyclerBindingAdapter);
    }

    public class HandleClickEventListener {

        public void setClickEvent(View view) {
            startActivity(new Intent(JetpackActivity.this, NavigationActivity.class));
        }
    }
}