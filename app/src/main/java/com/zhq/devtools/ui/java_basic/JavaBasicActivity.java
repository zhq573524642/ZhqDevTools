package com.zhq.devtools.ui.java_basic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.zhq.devtools.R;
import com.zhq.devtools.base.BaseActivity;
import com.zhq.devtools.databinding.ActivityJavaBasicBinding;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class JavaBasicActivity extends BaseActivity<ActivityJavaBasicBinding> {

    private static final String TAG = "JavaBasicActivity";

    public static void start(Context context){
        Intent intent = new Intent(context, JavaBasicActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_java_basic;
    }

    @Override
    protected void initView() {
        mBinding.btnMultiThread.setOnClickListener(v -> {

        });
        mBinding.btnJavaReflect.setOnClickListener(v -> {
            JavaReflectActivity.start(mContext);
        });


    }
    private static int index=0;
//    public static void main(String[] args) {

//        FutureTask<Integer> stringFutureTask = new FutureTask<Integer>(new Callable<Integer>() {
//            @Override
//            public Integer call() throws Exception {
//                while (index > 10) {
//                    Thread.sleep(100);
//                    index++;
//                }
//                return index;
//            }
//        });
//        new Thread(stringFutureTask).start();
//        Timer timer = new Timer();
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//
//            }
//        };
//        timer.schedule(timerTask,100,100);
//        try {
//            Integer integer = stringFutureTask.get();
//            System.out.println("===========" + integer);
//        } catch (ExecutionException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void mySyncMethod() {
//        PipedInputStream pipedInputStream = new PipedInputStream();
//        PipedOutputStream pipedOutputStream = new PipedOutputStream();
//        try {
//            pipedInputStream.connect(pipedOutputStream);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        MyThread myThread = new MyThread();
//        myThread.start();
//
//        ThreadGroup parentThreadGroup = new ThreadGroup("ParentThreadGroup");
//
//        MyCallable myCallable = new MyCallable();
//        try {
//            myCallable.call();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }


    }




    public static class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
        }
    }

    public static class MyCallable implements Callable<String> {


        @Override
        public String call() throws Exception {
            return null;
        }
    }

    public static class MyFuture implements Future<String> {

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean isDone() {
            return false;
        }

        @Override
        public String get() throws ExecutionException, InterruptedException {
            return null;
        }

        @Override
        public String get(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
            return null;
        }
    }
}