package com.zhq.devtools;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Build;
import android.os.Bundle;

import com.zhq.devtools.databinding.ActivityNfcshareBinding;
import com.zhq.toolslib.toast.ToastUtils;

public class NFCShareActivity extends AppCompatActivity {

    private com.zhq.devtools.databinding.ActivityNfcshareBinding binding;
    private NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNfcshareBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        PackageManager packageManager = getPackageManager();
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_NFC)){
            ToastUtils.getInstance().showShortToast(this,"不支持NFC功能");
            return;
        }else {
            nfcAdapter = NfcAdapter.getDefaultAdapter(this);
            nfcAdapter.setBeamPushUrisCallback(new NfcAdapter.CreateBeamUrisCallback() {
                @Override
                public Uri[] createBeamUris(NfcEvent event) {
                    return new Uri[0];
                }
            },this);
        }
    }
}