package com.zhq.toolslib;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

public class NotificationUtils {

    private NotificationManager notificationManager;
    private NotificationChannel notificationChannel;

    public static NotificationUtils getInstance() {
        return NotificationUtilsHolder.sInstance;
    }

    private static final class NotificationUtilsHolder {
        private static final NotificationUtils sInstance = new NotificationUtils();
    }

    //创建NotificationManager
    public void initNotificationManager(Context context) {
        if (context == null) return;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * 1.创建普通通知
     *
     * @param context
     * @param intent          用于构建 PendingIntent，跳转目标
     * @param title
     * @param content
     * @param normalChannelId
     * @param smallIcon
     * @param bigIcon
     * @param priority        NotificationCompat.PRIORITY_DEFAULT
     * @param isAutoCancel
     */
    public void createNotificationForNormal(Context context,
                                            int notificationId,
                                            Intent intent,
                                            String title,
                                            String content,
                                            String normalChannelId,
                                            int smallIcon,
                                            Bitmap bigIcon,
                                            int priority,
                                            boolean isAutoCancel) {
        if (notificationManager == null) {
            throw new NullPointerException("NotificationManager cannot null");
        }
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, normalChannelId)
                .setContentTitle(title) // 标题
                .setContentText(content) // 文本
                .setSmallIcon(smallIcon) // 小图标
                .setLargeIcon(bigIcon) // 大图标
                .setPriority(priority) // 7.0 设置优先级
                .setContentIntent(pi) // 跳转配置
                .setAutoCancel(isAutoCancel);
        notificationManager.notify(notificationId, builder.build());

    }

    /**
     * 8.0以上创建渠道
     *
     * @param channelId
     * @param channelName
     * @param importance  NotificationManager.IMPORTANCE_LOW
     * @param description
     * @param isShowBadge 是否桌面显示角标
     */
    public void createNotificationChannel(String channelId, String channelName,
                                          int importance, String description, boolean isShowBadge) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(channelId, channelName, importance);
            notificationChannel.setDescription(description);
            notificationChannel.setShowBadge(isShowBadge);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

    }

    public void createNotificationForHigh(Context context,
                                          int notificationId,
                                          String highChannelId,
                                          Intent intent,
                                          String title,
                                          String content,
                                          int smallIcon,
                                          Bitmap bigIcon,
                                          int priority,
                                          boolean isAutoCancel,
                                          int number,
                                          int actionIcon,
                                          String actionTitle
    ) {
        if (context == null) return;
        if (notificationManager == null) {
            throw new NullPointerException("NotificationManager cannot null");
        }
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, highChannelId)
                .setContentTitle(title) // 标题
                .setContentText(content) // 文本
                .setSmallIcon(smallIcon) // 小图标
                .setLargeIcon(bigIcon) // 大图标
                .setContentIntent(pi) // 跳转配置
                .setAutoCancel(isAutoCancel)
                .setNumber(number)//桌面通知数量
                .addAction(actionIcon, actionTitle, pi)// 通知上的操作
                .setCategory(NotificationCompat.CATEGORY_MESSAGE) // 通知类别，"勿扰模式"时系统会决定要不要显示你的通知
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE); // 屏幕可见性，锁屏时，显示icon和标题，内容隐藏
        notificationManager.notify(notificationId, builder.build());
    }

    public void createNotificationForProgress(Context context,
                                              int notificationId,
                                              String progressChannelId,
                                              String title,
                                              String content,
                                              int smallIcon,
                                              Bitmap bigIcon,
                                              int progressMax,
                                              int progressCurrent) {
        if (context == null) return;
        if (notificationManager == null) {
            throw new NullPointerException("NotificationManager cannot null");
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, progressChannelId)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(smallIcon)
                .setLargeIcon(bigIcon)
                // 第3个参数indeterminate，false表示确定的进度，比如100，true表示不确定的进度，会一直显示进度动画，直到更新状态下载完成，或删除通知
                .setProgress(progressMax, progressCurrent, false);
        notificationManager.notify(notificationId, builder.build());
    }
}
