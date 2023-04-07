package com.zhq.toolslib.media;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;


/**
 * Created by Huiqiang Zhang
 * on 2020/9/21
 */
public class MediaPlayerUtil {

    private MediaPlayer mediaPlayer;


    public MediaPlayerUtil() {
    }

    public static MediaPlayerUtil getInstance() {
        return VoicePlayerUtilHolder.sInstance;
    }

    private static class VoicePlayerUtilHolder {
        private static final MediaPlayerUtil sInstance = new MediaPlayerUtil();
    }

    public void playVoice(Context context, int rawVoice, boolean isLooping) {
        if (context == null) {
            return;
        }
        releaseVoicePlayer();
        try {
            mediaPlayer = CustomMediaPlayer.create(context, rawVoice);
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(1.0f, 1.0f);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setLooping(isLooping);
                mediaPlayer.start();
            }

        } catch (IllegalStateException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void pausePlayVoice() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
        }
    }


    public void releaseVoicePlayer() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}
