package com.zhq.devtools.database;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.zhq.devtools.R;
import com.zhq.devtools.databinding.ActivityLitepalBinding;

public class LitepalActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityLitepalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLitepalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnAdd.setOnClickListener(this);
        binding.btnDelete.setOnClickListener(this);
        binding.btnUpdate.setOnClickListener(this);
        binding.btnQuery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                break;
            case R.id.btn_delete:
                break;
            case R.id.btn_update:
                break;
            case R.id.btn_query:
                break;
        }
    }
}