package com.zhq.toolslib.media;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Build;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/2/28 14:26
 * Description
 */
public class AudioFocusManagerUtils {

    public static final int FOCUS_CHANGE_N_1 = -1;//表示，系统通知你失去声音焦点，此时你应该停止播放
    public static final int FOCUS_CHANGE_N_2 = -2;//表示，系统通知你短暂失去声音焦点，此时你应该暂停播放
    public static final int FOCUS_CHANGE_N_3 = -3;//表示，系统通知你需要压低播放的声音，此时你应该压低音量继续播放
    public static final int FOCUS_CHANGE_1 = 1;//表示，系统通知你获取到声音焦点了，你可以继续播放了
    private AudioManager audioManager;

    public static AudioFocusManagerUtils getInstance() {
        return AudioFocusManagerUtilsHolder.instance;
    }

    private static final class AudioFocusManagerUtilsHolder {
        private static AudioFocusManagerUtils instance = new AudioFocusManagerUtils();
    }

    public void requestAudioFocus(Context context, int audioFocusGainType) {
        if (context == null) {
            throw new NullPointerException("context can not null");
        }
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        //AudioManager.AUDIOFOCUS_GAIN = 1;//想要长期占有焦点，失去焦点者stop播放和释放（原有声音焦点者失去声音焦点）
        //AudioManager.AUDIOFOCUS_GAIN_TRANSIENT = 2;//想要短暂占有焦点，失去焦点者pause播放（原有声音焦点者继续持有音焦点），比如语音、比如电话
        //AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK = 3;//想要短暂占有焦点，失去焦点者可以继续播放但是音量需要调低，比如导航
        //AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE = 4;//想要短暂占有焦点，但希望失去焦点者不要有声音播放，比如电话
        int requestAudioFocus = -1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            AudioFocusRequest.Builder builder = new AudioFocusRequest.Builder(audioFocusGainType);
            builder.setAudioAttributes(attributes)
                    .setAcceptsDelayedFocusGain(false)//是否接受延迟获取音频焦点
                    .setOnAudioFocusChangeListener(onAudioFocusChangeListener);
            AudioFocusRequest build = builder.build();
            requestAudioFocus = audioManager.requestAudioFocus(build);
        } else {
            requestAudioFocus = audioManager.requestAudioFocus(onAudioFocusChangeListener
                    , AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        }
        if (onRequestAudioFocusListener != null) {
            onRequestAudioFocusListener.onRequestAudioFocusState(requestAudioFocus);
        }
    }

    //移除音频焦点
    public void abandonAudioFocus() {
        if (audioManager != null) {
            if (onAudioFocusChangeListener != null) {
                audioManager.abandonAudioFocus(onAudioFocusChangeListener);
            }
        }
    }

    public AudioManager setAudioManager() {
        return audioManager;
    }

    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
//            switch (focusChange){
//                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
//                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
//                case AudioManager.AUDIOFOCUS_LOSS:
//            }
            if (onRequestAudioFocusListener != null) {
                onRequestAudioFocusListener.onAudioFocusChange(focusChange);
            }
        }
    };

    public interface OnRequestAudioFocusListener {
        void onRequestAudioFocusState(int requestAudioFocus);

        void onAudioFocusChange(int focusChange);
    }

    private OnRequestAudioFocusListener onRequestAudioFocusListener;

    public void setOnRequestAudioFocusListener(OnRequestAudioFocusListener onRequestAudioFocusListener) {
        this.onRequestAudioFocusListener = onRequestAudioFocusListener;
    }
}
