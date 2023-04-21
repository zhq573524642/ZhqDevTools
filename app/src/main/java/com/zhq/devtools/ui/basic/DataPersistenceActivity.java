package com.zhq.devtools.ui.basic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.zhq.devtools.R;
import com.zhq.devtools.databinding.ActivityDataPersistenceBinding;
import com.zhq.toolslib.toast.ToastUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class DataPersistenceActivity extends AppCompatActivity {

    private com.zhq.devtools.databinding.ActivityDataPersistenceBinding binding;
    private final static String FILE_NAME = "myFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = (ActivityDataPersistenceBinding) DataBindingUtil.setContentView(this, R.layout.activity_data_persistence);
        initView();
    }

    private void initView() {
        binding.btnSaveFile.setOnClickListener(v -> {
            if (TextUtils.isEmpty(binding.etInput.getText().toString())) {
                ToastUtils.getInstance().showShortToast(this, "请输入内容");
                return;
            }
            saveToFile(binding.etInput.getText().toString());
        });

        binding.btnReadFile.setOnClickListener(v -> {
            String s = readFromFile();
            if (!TextUtils.isEmpty(s)) {
                binding.setReadContent(s);
            } else {
                ToastUtils.getInstance().showShortToast(this, "未读取到内容");
            }
        });
    }

    //1.保存到文件
    private void saveToFile(String text) {
        BufferedWriter bufferedWriter = null;
        try {
            FileOutputStream fileOutputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String readFromFile() {
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder content = new StringBuilder();
        try {
            fileInputStream = openFileInput(FILE_NAME);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String str = "";
            while ((str = bufferedReader.readLine()) != null) {
                content.append(str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

}