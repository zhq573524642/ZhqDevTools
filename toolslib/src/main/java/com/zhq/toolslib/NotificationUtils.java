package com.zhq.toolslib;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.RemoteViews;

public class NotificationUtils {

    private NotificationManager notificationManager;
    private NotificationChannel notificationChannel;

    public static NotificationUtils getInstance() {
        return NotificationUtilsHolder.sInstance;
    }

    private static final class NotificationUtilsHolder {
        private static final NotificationUtils sInstance = new NotificationUtils();
    }

    //创建渠道
    private Context context;
    private String channelId;//渠道id
    private String channelName;//渠道名称
    private String channelDescription;//渠道描述
    private int channelImportance = NotificationManager.IMPORTANCE_LOW;
    private boolean isShowBadge;//是否桌面显示角标
    //通知字段
    private String contentTitle;//通知title
    private String contentText;//通知内容
    private String subText;//子标题
    private int smallIcon;//小图标
    private Bitmap largeIcon;//大图标
    private int notificationId;
    private int priority = NotificationCompat.PRIORITY_DEFAULT;
    private boolean isShowWhen;//是否显示通知时间
    private long when;
    private Uri sound;
    private int lightArgb;
    private int onMs = 1000;
    private int offMs = 1000;

    /**
     * int flag = 0;
     * if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
     * flag = PendingIntent.FLAG_IMMUTABLE;
     * } else {
     * flag = PendingIntent.FLAG_UPDATE_CURRENT;
     * }
     * <p>
     * PendingIntent pi = PendingIntent.getActivity(context, 0, intent, flag);
     */
    private PendingIntent pendingIntent;
    private PendingIntent deletePendingIntent;
    private PendingIntent fullScreenPendingIntent;
    private boolean isFullScreenIntentHighPriority = false;
    private boolean isAutoCancel;
    private NotificationCompat.Action action;//通知上的操作
    private int deskNumber;
    private String category;//NotificationCompat.CATEGORY_MESSAGE // 通知类别，"勿扰模式"时系统会决定要不要显示你的通知
    private int visibility = NotificationCompat.VISIBILITY_PRIVATE;// 屏幕可见性，锁屏时，显示icon和标题，内容隐藏
    private int progressMax = 100;
    private int progressCurrent;
    private long[] vibrate;

    public NotificationUtils setNotificationId(int notificationId) {
        this.notificationId = notificationId;
        return this;
    }

    public NotificationUtils setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
        return this;
    }

    public NotificationUtils setContentText(String contentText) {
        this.contentText = contentText;
        return this;
    }

    public NotificationUtils setSubText(String subText) {
        this.subText = subText;
        return this;
    }

    public NotificationUtils setSmallIcon(int smallIcon) {
        this.smallIcon = smallIcon;
        return this;
    }

    public NotificationUtils setLargeIcon(Bitmap largeIcon) {
        this.largeIcon = largeIcon;
        return this;
    }

    public NotificationUtils setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public NotificationUtils isShowWhen(boolean isShowWhen) {
        this.isShowWhen = isShowWhen;
        return this;
    }

    public NotificationUtils setWhen(long when) {
        this.when = when;
        return this;
    }

    public NotificationUtils setSound(Uri sound) {
        this.sound = sound;
        return this;
    }

    public NotificationUtils setLightArgb(int lightArgb) {
        this.lightArgb = lightArgb;
        return this;
    }

    public NotificationUtils setLightOnMs(int onMs) {
        this.onMs = onMs;
        return this;
    }

    public NotificationUtils setLightOffMs(int offMs) {
        this.offMs = offMs;
        return this;
    }

    public NotificationUtils setPendingIntent(PendingIntent pendingIntent) {
        this.pendingIntent = pendingIntent;
        return this;
    }

    public NotificationUtils setDeletePendingIntent(PendingIntent deletePendingIntent) {
        this.deletePendingIntent = deletePendingIntent;
        return this;
    }

    public NotificationUtils isFullScreenIntentHighPriority(boolean isFullScreenIntentHighPriority) {
        this.isFullScreenIntentHighPriority = isFullScreenIntentHighPriority;
        return this;
    }

    public NotificationUtils setFullScreenPendingIntent(PendingIntent screenPendingIntent) {
        this.fullScreenPendingIntent = fullScreenPendingIntent;
        return this;
    }

    public NotificationUtils isAutoCancel(boolean isAutoCancel) {
        this.isAutoCancel = isAutoCancel;
        return this;
    }

    public NotificationUtils setNotificationAction(NotificationCompat.Action action) {
        this.action = action;
        return this;
    }

    public NotificationUtils setNotificationDeskNumber(int deskNumber) {
        this.deskNumber = deskNumber;
        return this;
    }

    public NotificationUtils setCategory(String category) {
        this.category = category;
        return this;
    }

    public NotificationUtils setVisibility(int visibility) {
        this.visibility = visibility;
        return this;
    }

    public NotificationUtils setProgressMax(int progressMax) {
        this.progressMax = progressMax;
        return this;
    }

    public NotificationUtils setProgressCurrent(int progressCurrent) {
        this.progressCurrent = progressCurrent;
        return this;
    }

    public NotificationUtils setVibrate(long[] vibrate) {
        this.vibrate = vibrate;
        return this;
    }

    //创建NotificationManager
    public NotificationUtils initNotificationManager(Context context) {
        this.context = context;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        return this;
    }

    /**
     * 8.0以上创建渠道
     */
    public NotificationUtils createNotificationChannel(String channelId, String channelName, String channelDescription, int channelImportance, boolean isShowBadge) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.channelDescription = channelDescription;
        this.channelImportance = channelImportance;
        this.isShowBadge = isShowBadge;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(channelId, channelName, channelImportance);
            notificationChannel.setDescription(channelDescription);
            notificationChannel.setShowBadge(isShowBadge);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        return this;
    }

    public NotificationUtils createNormalNotification() {
        if (notificationManager == null) {
            throw new NullPointerException("NotificationManager cannot null");
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);
        if (!TextUtils.isEmpty(contentTitle)) {
            builder.setContentTitle(contentTitle);
        }
        if (!TextUtils.isEmpty(contentText)) {
            builder.setContentText(contentText);
        }
        if (!TextUtils.isEmpty(subText)) {
            builder.setSubText(subText);
        }
        builder.setShowWhen(isShowWhen);
        if (isShowWhen) {
            builder.setWhen(when);
        }
        if (sound != null) {
            builder.setSound(sound);
        }
        if (vibrate != null) {
            builder.setVibrate(vibrate);
        }
        if (lightArgb != 0) {
            builder.setLights(lightArgb, onMs, offMs);
        }
        if (deletePendingIntent != null) {
            builder.setDeleteIntent(deletePendingIntent);
        }
        if (fullScreenPendingIntent != null) {
            builder.setFullScreenIntent(fullScreenPendingIntent, isFullScreenIntentHighPriority);
        }
        if (smallIcon != 0) {
            builder.setSmallIcon(smallIcon);
        }
        if (largeIcon != null) {
            builder.setLargeIcon(largeIcon);
        }


        builder.setPriority(priority);
        if (pendingIntent != null) {
            builder.setContentIntent(pendingIntent);
        }
        builder.setAutoCancel(isAutoCancel);
        if (deskNumber != 0) {
            builder.setNumber(deskNumber);//桌面通知数量
        }
        if (action != null) {
            builder.addAction(action);// 通知上的操作
        }
        if (!TextUtils.isEmpty(category)) {
            builder.setCategory(category);
        }
        builder.setVisibility(visibility);

        notificationManager.notify(notificationId, builder.build());
        return this;
    }

    private NotificationCompat.Builder downloadNotificationBuilder;

    public NotificationUtils createProgressNotification() {
        if (notificationManager == null) {
            throw new NullPointerException("NotificationManager cannot null");
        }
        downloadNotificationBuilder = new NotificationCompat.Builder(context, channelId);
        if (!TextUtils.isEmpty(contentTitle)) {
            downloadNotificationBuilder.setContentTitle(contentTitle);
        }
        if (!TextUtils.isEmpty(contentText)) {
            downloadNotificationBuilder.setContentText(contentText);
        }
        if (!TextUtils.isEmpty(subText)) {
            downloadNotificationBuilder.setSubText(subText);
        }
        downloadNotificationBuilder.setShowWhen(isShowWhen);
        if (isShowWhen) {
            downloadNotificationBuilder.setWhen(when);
        }
        if (sound != null) {
            downloadNotificationBuilder.setSound(sound);
        }
        if (vibrate != null) {
            downloadNotificationBuilder.setVibrate(vibrate);
        }
        if (lightArgb != 0) {
            downloadNotificationBuilder.setLights(lightArgb, onMs, offMs);
        }
        if (deletePendingIntent != null) {
            downloadNotificationBuilder.setDeleteIntent(deletePendingIntent);
        }
        if (fullScreenPendingIntent != null) {
            downloadNotificationBuilder.setFullScreenIntent(fullScreenPendingIntent, isFullScreenIntentHighPriority);
        }
        if (smallIcon != 0) {
            downloadNotificationBuilder.setSmallIcon(smallIcon);
        }
        if (largeIcon != null) {
            downloadNotificationBuilder.setLargeIcon(largeIcon);
        }
        downloadNotificationBuilder.setPriority(priority);
        if (pendingIntent != null) {
            downloadNotificationBuilder.setContentIntent(pendingIntent);
        }
        downloadNotificationBuilder.setAutoCancel(isAutoCancel);
        if (deskNumber != 0) {
            downloadNotificationBuilder.setNumber(deskNumber);//桌面通知数量
        }
        if (action != null) {
            downloadNotificationBuilder.addAction(action);// 通知上的操作
        }
        if (!TextUtils.isEmpty(category)) {
            downloadNotificationBuilder.setCategory(category);
        }
        downloadNotificationBuilder.setVisibility(visibility);
        // 第3个参数indeterminate，false表示确定的进度，比如100，true表示不确定的进度，
        // 会一直显示进度动画，直到更新状态下载完成，或删除通知
        downloadNotificationBuilder.setProgress(progressMax, progressCurrent, false);
        notificationManager.notify(notificationId, downloadNotificationBuilder.build());
        return this;
    }

    public NotificationUtils updateProgressNotification() {
        if (notificationManager == null) {
            throw new NullPointerException("NotificationManager cannot null");
        }
        if (downloadNotificationBuilder != null) {
            downloadNotificationBuilder.setContentText(contentText);
            downloadNotificationBuilder.setProgress(progressMax, progressCurrent, false);
            notificationManager.notify(notificationId, downloadNotificationBuilder.build());
        }
        return this;
    }

    public NotificationUtils createBigTextNotification(String bigContent) {
        if (notificationManager == null) {
            throw new NullPointerException("NotificationManager cannot null");
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);
        if (!TextUtils.isEmpty(contentTitle)) {
            builder.setContentTitle(contentTitle);
        }
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(bigContent));
        if (!TextUtils.isEmpty(subText)) {
            builder.setSubText(subText);
        }
        builder.setShowWhen(isShowWhen);
        if (isShowWhen) {
            builder.setWhen(when);
        }
        if (sound != null) {
            builder.setSound(sound);
        }
        if (vibrate != null) {
            builder.setVibrate(vibrate);
        }
        if (lightArgb != 0) {
            builder.setLights(lightArgb, onMs, offMs);
        }
        if (deletePendingIntent != null) {
            builder.setDeleteIntent(deletePendingIntent);
        }
        if (fullScreenPendingIntent != null) {
            builder.setFullScreenIntent(fullScreenPendingIntent, isFullScreenIntentHighPriority);
        }
        if (smallIcon != 0) {
            builder.setSmallIcon(smallIcon);
        }
        if (largeIcon != null) {
            builder.setLargeIcon(largeIcon);
        }
        builder.setPriority(priority);
        if (pendingIntent != null) {
            builder.setContentIntent(pendingIntent);
        }
        builder.setAutoCancel(isAutoCancel);
        if (deskNumber != 0) {
            builder.setNumber(deskNumber);//桌面通知数量
        }
        if (action != null) {
            builder.addAction(action);// 通知上的操作
        }
        if (!TextUtils.isEmpty(category)) {
            builder.setCategory(category);
        }
        builder.setVisibility(visibility);

        notificationManager.notify(notificationId, builder.build());
        return this;
    }

    public NotificationUtils createBigPictureNotification(Bitmap bigPicture) {
        if (notificationManager == null) {
            throw new NullPointerException("NotificationManager cannot null");
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);
        if (!TextUtils.isEmpty(contentTitle)) {
            builder.setContentTitle(contentTitle);
        }
        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bigPicture));
        if (!TextUtils.isEmpty(subText)) {
            builder.setSubText(subText);
        }
        builder.setShowWhen(isShowWhen);
        if (isShowWhen) {
            builder.setWhen(when);
        }
        if (sound != null) {
            builder.setSound(sound);
        }
        if (vibrate != null) {
            builder.setVibrate(vibrate);
        }
        if (lightArgb != 0) {
            builder.setLights(lightArgb, onMs, offMs);
        }
        if (deletePendingIntent != null) {
            builder.setDeleteIntent(deletePendingIntent);
        }
        if (fullScreenPendingIntent != null) {
            builder.setFullScreenIntent(fullScreenPendingIntent, isFullScreenIntentHighPriority);
        }
        if (smallIcon != 0) {
            builder.setSmallIcon(smallIcon);
        }
        if (largeIcon != null) {
            builder.setLargeIcon(largeIcon);
        }
        builder.setPriority(priority);
        if (pendingIntent != null) {
            builder.setContentIntent(pendingIntent);
        }
        builder.setAutoCancel(isAutoCancel);
        if (deskNumber != 0) {
            builder.setNumber(deskNumber);//桌面通知数量
        }
        if (action != null) {
            builder.addAction(action);// 通知上的操作
        }
        if (!TextUtils.isEmpty(category)) {
            builder.setCategory(category);
        }
        builder.setVisibility(visibility);

        notificationManager.notify(notificationId, builder.build());
        return this;
    }


    public NotificationUtils createCustomNotification(RemoteViews remoteViews) {
        if (notificationManager == null) {
            throw new NullPointerException("NotificationManager cannot null");
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);
        if (!TextUtils.isEmpty(contentTitle)) {
            builder.setContentTitle(contentTitle);
        }
        if (!TextUtils.isEmpty(contentText)) {
            builder.setContentText(contentText);
        }
        if (!TextUtils.isEmpty(subText)) {
            builder.setSubText(subText);
        }
        builder.setShowWhen(isShowWhen);
        if (isShowWhen) {
            builder.setWhen(when);
        }
        if (sound != null) {
            builder.setSound(sound);
        }
        if (vibrate != null) {
            builder.setVibrate(vibrate);
        }
        if (lightArgb != 0) {
            builder.setLights(lightArgb, onMs, offMs);
        }
        if (deletePendingIntent != null) {
            builder.setDeleteIntent(deletePendingIntent);
        }
        if (fullScreenPendingIntent != null) {
            builder.setFullScreenIntent(fullScreenPendingIntent, isFullScreenIntentHighPriority);
        }
        if (smallIcon != 0) {
            builder.setSmallIcon(smallIcon);
        }
        if (largeIcon != null) {
            builder.setLargeIcon(largeIcon);
        }


        builder.setPriority(priority);
        if (pendingIntent != null) {
            builder.setContentIntent(pendingIntent);
        }
        builder.setAutoCancel(isAutoCancel);
        if (deskNumber != 0) {
            builder.setNumber(deskNumber);//桌面通知数量
        }
        if (action != null) {
            builder.addAction(action);// 通知上的操作
        }
        if (!TextUtils.isEmpty(category)) {
            builder.setCategory(category);
        }
        builder.setVisibility(visibility);
        builder.setCustomContentView(remoteViews);
        builder.setCustomBigContentView(remoteViews);
        notificationManager.notify(notificationId, builder.build());
        return this;
    }
}
