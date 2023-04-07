package com.zhq.devtools;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zhq.devtools.database.SQLiteOpenHelperActivity;

public class TestDatabaseActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_database);
        Button btnSQLiteOpenHelper = findViewById(R.id.btn_SQLiteOpenHelper);
        Button btnLitePal = findViewById(R.id.btn_litePal);
        btnSQLiteOpenHelper.setOnClickListener(this);
        btnLitePal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_SQLiteOpenHelper:
                startActivity(new Intent(this, SQLiteOpenHelperActivity.class));
                break;
            case R.id.btn_litePal:
                break;
            case R.id.btn_poem:
                break;
        }
    }
}