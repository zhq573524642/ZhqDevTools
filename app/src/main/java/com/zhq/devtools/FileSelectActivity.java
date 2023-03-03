package com.zhq.devtools;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;
import com.zhq.devtools.databinding.ActivityFileSelectBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSelectActivity extends AppCompatActivity {

    private com.zhq.devtools.databinding.ActivityFileSelectBinding binding;
    private List<String> imageName = new ArrayList<>();
    private Uri uriForFile;
    private Intent requestIntent;
    private static final String TAG = "FileSelectActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFileSelectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnBack.setOnClickListener(v -> {
            finish();
        });
        requestIntent = new Intent("com.zhq.devtools.ACTION_RETURN_FILE");
        PermissionX.init(this)
                .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.MANAGE_EXTERNAL_STORAGE)
                .request(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {
                        if (allGranted){
                            File filesDir = getFilesDir();
                            File file = new File(filesDir, "images");
                            File[] imageFiles = file.listFiles();
                            setResult(RESULT_CANCELED, null);
                            try {
                                Log.d(TAG, "===扫描到的文件: "+imageFiles.length);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (imageFiles != null) {
                                imageName.clear();
                                for (int i = 0; i < imageFiles.length; i++) {
                                    imageName.add(imageFiles[i].getAbsolutePath());
                                }
                            }
                        }
                    }
                });


        binding.listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, imageName));
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File requestFile = new File(imageName.get(position));
                try {
                    uriForFile = FileProvider.getUriForFile(FileSelectActivity.this,
                            "com.zhq.devtools.fileprovider",
                            requestFile);
                    if (uriForFile != null) {
                        requestIntent.addFlags(
                                Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        requestIntent.setDataAndType(
                                uriForFile,
                                getContentResolver().getType(uriForFile));
                        FileSelectActivity.this.setResult(Activity.RESULT_OK,
                                requestIntent);
                    } else {
                        requestIntent.setDataAndType(null, "");
                        FileSelectActivity.this.setResult(RESULT_CANCELED,
                                requestIntent);
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}