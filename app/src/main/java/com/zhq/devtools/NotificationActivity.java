package com.zhq.devtools;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;

import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.zhq.devtools.databinding.ActivityNotificationBinding;
import com.zhq.toolslib.GlideUtils;
import com.zhq.toolslib.NotificationUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import kotlin.jvm.functions.Function1;

public class NotificationActivity extends AppCompatActivity {

    private com.zhq.devtools.databinding.ActivityNotificationBinding binding;
    private static final String TAG = "NotificationActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //创建NotificationManager
        binding.btnNotificationNormal.setOnClickListener(v -> {
            handleNotificationConfigAndCreate();
        });
        binding.btnNotificationHigh.setOnClickListener(v -> {

        });

        binding.btnDownloadNotification.setOnClickListener(v -> {


        });
        binding.btnBigTextNotification.setOnClickListener(v -> {

        });

        binding.btnBigPicNotification.setOnClickListener(v -> {

        });

        binding.btnCustomNotification.setOnClickListener(v -> {
            handleNotificationConfigAndCreate();
        });
    }

    private void handleNotificationConfigAndCreate() {
        Observable<Bitmap> observable = Observable.create(emitter -> {
            Bitmap bitmap = GlideUtils.getBitmapByUrl(NotificationActivity.this, "http://121.43.188.190:8080/secure/attachment/45631/45631_image-2023-02-01-15-14-23-918.png");
            emitter.onNext(bitmap);
            //发送完成
            emitter.onComplete();

        });

        Observer<Bitmap> observer = new Observer<Bitmap>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Bitmap bitmap) {
                //PendingIntent
                Log.d(TAG, "===bitmap: "+bitmap);
                Intent intent = new Intent(NotificationActivity.this, NotificationActivity.class);
                int flag = 0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    flag = PendingIntent.FLAG_IMMUTABLE;
                } else {
                    flag = PendingIntent.FLAG_UPDATE_CURRENT;
                }
                PendingIntent pi = PendingIntent.getActivity(NotificationActivity.this, 0, intent, flag);
                //Action
                NotificationCompat.Action action = new NotificationCompat.Action(R.mipmap.ic_launcher, "点击看看", pi);
                //RemoteViews
                RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_custom_notification_remote_view);
                remoteViews.setTextViewText(R.id.tv_remote_text, "这是自定义通知");
                remoteViews.setImageViewBitmap(R.id.iv_remove_image, bitmap);
                Intent intentStart = new Intent(NotificationActivity.this, NotificationActivity.class);
                PendingIntent startPendingIntent = PendingIntent.getBroadcast(NotificationActivity.this, 0, intentStart, flag);
                remoteViews.setOnClickPendingIntent(R.id.btn_start, startPendingIntent);
                Intent intentStop = new Intent(NotificationActivity.this, NotificationActivity.class);
                PendingIntent stopPendingIntent = PendingIntent.getBroadcast(NotificationActivity.this, 0, intentStop, flag);
                remoteViews.setOnClickPendingIntent(R.id.btn_stop, stopPendingIntent);

                NotificationUtils.getInstance()
                        .initNotificationManager(NotificationActivity.this)//创建NotificationManager、
                        .createNotificationChannel("channel_1",
                                "normal_channel",
                                "普通通知",
                                NotificationManager.IMPORTANCE_LOW, false)//创建NotificationChannel
                        .setNotificationId(1)//通知Id
                        .setContentTitle("普通通知标题")//标题
                        .setContentText("这是普通通知")//内容
                        .setSubText("子标题")//子标题
                        .isAutoCancel(true)//点击后是否自动取消
                        .isShowWhen(true)//是否显示通知时间
                        .setWhen(System.currentTimeMillis())//通知时间
                        .setNotificationDeskNumber(99)//桌面通知数量
                        .setSmallIcon(R.mipmap.ic_launcher)//小图标
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_test_img))//大图标
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)//优先级
                        .setNotificationAction(action)//操作通知的action
                        .setPendingIntent(pi)//点击意图
                        .setDeletePendingIntent(pi)//通知删除时的意图
                        .setFullScreenPendingIntent(pi)//全屏点击的意图
                        .isFullScreenIntentHighPriority(false)//全屏点击的意图是否高优先级
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)//NotificationCompat.CATEGORY_MESSAGE  通知类别，"勿扰模式"时系统会决定要不要显示你的通知
                        .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)//NotificationCompat.VISIBILITY_PRIVATE 屏幕可见性，锁屏时，显示icon和标题，内容隐藏
                        .setLightArgb(Color.GREEN)//呼吸灯颜色
                        .setLightOnMs(1000)//呼吸灯时间on
                        .setLightOffMs(1000)//呼吸灯时间off
                        .setSound(Uri.fromFile(new File("....")))//提示音
                        .setVibrate(new long[]{0, 1000, 1000, 1000})//震动 AndroidManifest.xml需要添加权限   <uses-permission android:name="android.permission.VIBRATE"/>
                        .setProgressMax(100)//进度通知 最大进度
                        .setProgressCurrent(50)//进度通知 当前进度
//                .createNormalNotification()//1.创建普通通知
//                .createBigTextNotification("长文本。。。")//2.创建长文本通知
//                .createBigPictureNotification(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))//3.创建大图通知
//                .createProgressNotification()//4.创建进度通知
//                .updateProgressNotification()//5.更新进度通知
                        .createCustomNotification(remoteViews);//6.创建自定义通知
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在io线程subscribe(observer);
                .subscribe(observer);

    }
}