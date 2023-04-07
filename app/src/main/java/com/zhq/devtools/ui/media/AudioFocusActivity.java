package com.zhq.devtools.ui.media;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;

import com.zhq.devtools.R;
import com.zhq.devtools.databinding.ActivityAudioFocusBinding;
import com.zhq.toolslib.ToastUtils;
import com.zhq.toolslib.media.AudioFocusManagerUtils;
import com.zhq.toolslib.media.MediaPlayerUtil;
import com.zhq.toolslib.media.NoisyAudioStreamReceiver;

public class AudioFocusActivity extends AppCompatActivity implements NoisyAudioStreamReceiver.OnNoisyAudioStreamReceiverListener {
    private static final String TAG = "AudioFocusActivity";
    private com.zhq.devtools.databinding.ActivityAudioFocusBinding binding;
    private int requestFocusType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAudioFocusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        AudioFocusManagerUtils.getInstance().setOnRequestAudioFocusListener(new AudioFocusManagerUtils.OnRequestAudioFocusListener() {
            @Override
            public void onRequestAudioFocusState(int requestAudioFocus) {
                Log.d(TAG, "===onRequestAudioFocusState: " + requestAudioFocus);
                if (requestAudioFocus == AudioFocusManagerUtils.FOCUS_CHANGE_1) {
                    MediaPlayerUtil.getInstance().playVoice(AudioFocusActivity.this, R.raw.v_test_audio, false);
                }
            }

            @Override
            public void onAudioFocusChange(int focusChange) {
                Log.d(TAG, "===onAudioFocusChange: " + focusChange);
            }
        });
        binding.rgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_type1:
                        requestFocusType = AudioManager.AUDIOFOCUS_GAIN;
                        break;
                    case R.id.rb_type2:
                        requestFocusType = AudioManager.AUDIOFOCUS_GAIN_TRANSIENT;
                        break;
                    case R.id.rb_type3:
                        requestFocusType = AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK;
                        break;
                    case R.id.rb_type4:
                        requestFocusType = AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE;
                        break;
                }
            }
        });
        binding.btnRequestAndPlay.setOnClickListener(v -> {
            if (requestFocusType == 0) {
                ToastUtils.getInstance().showShortToast(this, "请选择请求焦点类型");
                return;
            }
            AudioFocusManagerUtils.getInstance().requestAudioFocus(this, requestFocusType);

        });
        binding.btnStopAudioPlay.setOnClickListener(v -> {
            MediaPlayerUtil.getInstance().releaseVoicePlayer();
            AudioFocusManagerUtils.getInstance().abandonAudioFocus();
        });
        binding.btnGetOutDevice.setOnClickListener(v -> {
            //判断当前音频输出设备
            AudioManager audioManager = AudioFocusManagerUtils.getInstance().getAudioManager();
            if (audioManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    StringBuilder sb = new StringBuilder("输出设备：");
                    for (AudioDeviceInfo device : audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS)) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            sb.append(device.getProductName()).append("-").append(device.getAddress());
                        } else {
                            sb.append(device.getProductName());
                        }
                    }
                    binding.tvOutDevice.setText(sb.toString());

                } else {
                    if (audioManager.isBluetoothA2dpOn()) {
                        //蓝牙耳机
                        binding.tvOutDevice.setText("输出设备：蓝牙耳机");
                    } else if (audioManager.isSpeakerphoneOn()) {
                        //扬声器
                        binding.tvOutDevice.setText("输出设备：扬声器");
                    } else if (audioManager.isWiredHeadsetOn()) {
                        //这并不是一个有效的指示，音频播放实际上是通过有线耳机，因为音频路由取决于其他条件。
                        //连线耳机
                        binding.tvOutDevice.setText("输出设备：连线耳机");
                    } else {
                        binding.tvOutDevice.setText("输出设备：其他");
                    }
                }

            }
        });
        binding.btnRegisterNoisyAudioReceiver.setOnClickListener(v -> {
            //当输出设备断开连接，比如耳机断开连接，需要停止当前播放的音频，以免扬声器播放太大声
            noisyAudioStreamReceiver = new NoisyAudioStreamReceiver(this);
            intentFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
            registerNoisyAudioReceiver();
        });
        binding.btnUnregisterNoisyAudioReceiver.setOnClickListener(v -> {
            unregisterNoisyAudioReceiver();
        });

    }

    private NoisyAudioStreamReceiver noisyAudioStreamReceiver;
    private IntentFilter intentFilter;

    private void registerNoisyAudioReceiver() {
        registerReceiver(noisyAudioStreamReceiver, intentFilter);
    }

    private void unregisterNoisyAudioReceiver() {
        if (noisyAudioStreamReceiver != null) {
            unregisterReceiver(noisyAudioStreamReceiver);
            noisyAudioStreamReceiver = null;
        }else {
            ToastUtils.getInstance().showShortToast(this,"请先注册广播");
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())) {
            // Pause the playback
            //暂停当前音频播放
            MediaPlayerUtil.getInstance().pausePlayVoice();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNoisyAudioReceiver();
        MediaPlayerUtil.getInstance().releaseVoicePlayer();
    }
}