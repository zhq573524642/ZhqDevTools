package com.zhq.devtools.database;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zhq.devtools.App;
import com.zhq.devtools.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class SQLiteOpenHelperActivity extends AppCompatActivity implements View.OnClickListener {

    private SQLiteDatabase db;
    private List<PersonInfoBean> personInfoBeanList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SQLiteListAdapter sqLiteListAdapter;
    private static final String TAG = "SQLiteOpenHelperActivit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_open_helper);
        db = App.getSqLiteOpenHelperWritableDatabase();
        Button btnAdd = findViewById(R.id.btn_add);
        Button btnDelete = findViewById(R.id.btn_delete);
        Button btnUpdate = findViewById(R.id.btn_update);
        Button btnQuery = findViewById(R.id.btn_query);
        recyclerView = findViewById(R.id.recyclerView);
        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnQuery.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sqLiteListAdapter = new SQLiteListAdapter(personInfoBeanList);
        recyclerView.setAdapter(sqLiteListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                addData();
                break;
            case R.id.btn_delete:
                deleteDate();
                break;
            case R.id.btn_update:
                updateData();
                break;
            case R.id.btn_query:
                queryData();
                break;
        }
    }

    private int initAge = 10;

    private void addData() {
        if (db == null) return;
        ContentValues values = new ContentValues();
        values.put("name", "张三");
        values.put("age", initAge);
        values.put("avatar", "https://img0.baidu.com/it/u=1705694933,4002952892&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=281");
        initAge += 3;
        values.put("description", "这是描述描述描述" + UUID.randomUUID());
        db.insert("person", null, values);
        //book 中插入数据
        ContentValues values1 = new ContentValues();
        values1.put("name", "书名称啊");
        values1.put("type", 1);
        values1.put("date", System.currentTimeMillis());
        values1.put("description", "书的描述");
        db.insert("book", null, values1);
    }

    private void deleteDate() {
        if (db == null) return;
        //删除全部
        db.delete("person", null, null);
        //删除age>20
//        db.delete("person", "age>?", new String[]{"20"});
    }

    private void updateData() {
        if (db == null) return;
        ContentValues values = new ContentValues();
        values.put("name", "张三");
        values.put("age", 666);
        values.put("description", "这是描述描述描述" + UUID.randomUUID());
        db.update("person", values, "age>?", new String[]{"15"});
    }

    @SuppressLint("Range")
    private void queryData() {
        if (db == null) return;
        personInfoBeanList.clear();
        sqLiteListAdapter.notifyDataSetChanged();
        Cursor cursor = db.query("person", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String avatar = cursor.getString(cursor.getColumnIndex("avatar"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int age = cursor.getInt(cursor.getColumnIndex("age"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                PersonInfoBean person = new PersonInfoBean();
                person.id = id;
                person.avatar = avatar;
                person.name = name;
                person.age = age;
                person.desc = description;
                personInfoBeanList.add(person);
            } while (cursor.moveToNext());
            sqLiteListAdapter.notifyDataSetChanged();
        }
    }


}