package com.zhq.toolslib.media;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/2/28 15:14
 * Description
 */
public class NoisyAudioStreamReceiver extends BroadcastReceiver {

    private OnNoisyAudioStreamReceiverListener onNoisyAudioStreamReceiverListener;

    public NoisyAudioStreamReceiver(OnNoisyAudioStreamReceiverListener onNoisyAudioStreamReceiverListener) {
        this.onNoisyAudioStreamReceiverListener = onNoisyAudioStreamReceiverListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (onNoisyAudioStreamReceiverListener != null) {
            onNoisyAudioStreamReceiverListener.onReceive(context, intent);
        }
    }

    public interface OnNoisyAudioStreamReceiverListener {
        void onReceive(Context context, Intent intent);
    }

}
