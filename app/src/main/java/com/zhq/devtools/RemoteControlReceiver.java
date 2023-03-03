package com.zhq.devtools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/2/28 13:54
 * Description
 */
public class RemoteControlReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())) {
            KeyEvent keyEvent = (KeyEvent) intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
            if (keyEvent.getKeyCode()==KeyEvent.KEYCODE_MEDIA_PLAY){

            }else if (keyEvent.getKeyCode()==KeyEvent.KEYCODE_MEDIA_PAUSE){

            }
        }
    }
}
