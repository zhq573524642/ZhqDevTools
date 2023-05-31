package com.zhq.devtools.ui.jetpack.navigation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.zhq.devtools.R;
import com.zhq.devtools.databinding.ActivityNavigationBinding;

public class NavigationActivity extends AppCompatActivity {

    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNavigationBinding binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private static final String TAG = "NavigationActivity";
    private void initView() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
    }
}