package com.zhq.devtools.ui.java_basic;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.zhq.devtools.R;
import com.zhq.devtools.base.BaseActivity;
import com.zhq.devtools.databinding.ActivityJavaReflectBinding;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author ZhangHuiQiang
 * @Date 2024/3/6 14:29
 * Description
 */
public class JavaReflectActivity extends BaseActivity<ActivityJavaReflectBinding> {
    private static final String TAG = "JavaReflectActivity";

    public static void start(Context context) {
        Intent intent = new Intent(context, JavaReflectActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_java_reflect;
    }

    @Override
    protected void initView() {

        //调用空参构造
        mBinding.btn1.setOnClickListener(v -> {
            try {
                Class<?> clazz = Class.forName("com.zhq.devtools.ui.java_basic.TestReflectBean");
                Object o = clazz.newInstance();

                showLog("Class<?> clazz = Class.forName(\"com.zhq.devtools.ui.java_basic.TestReflectBean\");\n" +
                        "Object o = clazz.newInstance();", "成功调用");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            }
        });
        //调用含参构造
        mBinding.btn2.setOnClickListener(v -> {
            try {
                Class<?> clazz = Class.forName("com.zhq.devtools.ui.java_basic.TestReflectBean");
                Constructor<?> constructor = clazz.getConstructor(int.class, String.class, int.class);
                Object o = constructor.newInstance(200, "张三", 1001);

                showLog("Class<?> clazz = Class.forName(\"com.zhq.devtools.ui.java_basic.TestReflectBean\");\n" +
                        "Constructor<?> constructor = clazz.getConstructor(int.class, String.class, int.class);\n" +
                        "Object o = constructor.newInstance(200, \"张三\", 1001);", "成功调用");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            }
        });
        //获取指定成员nickname的值，不包含私有
        mBinding.btn3.setOnClickListener(v -> {
            try {
                Class<?> clazz = Class.forName("com.zhq.devtools.ui.java_basic.TestReflectBean");
                Object o = clazz.newInstance();
                Field field = clazz.getField("nickname");
                String s = field.get(o).toString();

                showLog("Class<?> clazz = Class.forName(\"com.zhq.devtools.ui.java_basic.TestReflectBean\");\n" +
                        "Object o = clazz.newInstance();\n" +
                        "Field field = clazz.getField(\"nickname\");\n" +
                        "String s = field.get(o).toString();", s);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            }

        });
        //获取指定私有成员status的值
        mBinding.btn4.setOnClickListener(v -> {
            try {
                Class<?> clazz = Class.forName("com.zhq.devtools.ui.java_basic.TestReflectBean");
                Object o = clazz.newInstance();
                Field field = clazz.getDeclaredField("status");
                field.setAccessible(true);
                String s = field.get(o).toString();
                showLog(" Class<?>  clazz = Class.forName(\"com.zhq.devtools.ui.java_basic.TestReflectBean\");\n" +
                        "                Object o = clazz.newInstance();\n" +
                        "                Field field = clazz.getDeclaredField(\"status\");\n" +
                        "                field.setAccessible(true);\n" +
                        "                String s = field.get(o).toString();", s);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            }

        });
        //获取全部成员，不包含私有
        mBinding.btn5.setOnClickListener(v -> {
            try {
                Class<?> clazz = Class.forName("com.zhq.devtools.ui.java_basic.TestReflectBean");
                Object o = clazz.newInstance();
                StringBuilder sb = new StringBuilder();
                for (Field field : clazz.getFields()) {
                    sb.append(field.getName() + "：" + field.get(o).toString() + "\n");
                }
                showLog("Class<?>  clazz = Class.forName(\"com.zhq.devtools.ui.java_basic.TestReflectBean\");\n" +
                        " Object o = clazz.newInstance();\n" +
                        " StringBuilder sb=new StringBuilder();\n" +
                        " for (Field field : clazz.getFields()) {\n" +
                        "  sb.append(field.getName()+\"：\"+field.get(o).toString());\n" +
                        "  }", sb.toString());

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            }
        });

        //获取全部成员，包含私有
        mBinding.btn6.setOnClickListener(v -> {
            try {
                Class<?> clazz = Class.forName("com.zhq.devtools.ui.java_basic.TestReflectBean");
                Object o = clazz.newInstance();
                StringBuilder sb = new StringBuilder();
                for (Field field : clazz.getDeclaredFields()) {
                    field.setAccessible(true);
                    sb.append(field.getName() + "：" + field.get(o).toString() + "\n");
                }
                showLog("Class<?>  clazz = Class.forName(\"com.zhq.devtools.ui.java_basic.TestReflectBean\");\n" +
                        " Object o = clazz.newInstance();\n" +
                        " StringBuilder sb=new StringBuilder();\n" +
                        " for (Field field : clazz.getDeclaredFields()) {\n" +
                        "field.setAccessible(true);\n" +
                        "  sb.append(field.getName()+\"：\"+field.get(o).toString());\n" +
                        "  }", sb.toString());

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            }
        });
        //获取全部公共方法 包含父类
        mBinding.btn8.setOnClickListener(v -> {
            try {
                Class<?> clazz = Class.forName("com.zhq.devtools.ui.java_basic.TestReflectBean");
                StringBuilder sb = new StringBuilder();
                for (Method method : clazz.getMethods()) {
                   sb.append(method.toString()+"\n");
                }
                showLog("Class<?> clazz = Class.forName(\"com.zhq.devtools.ui.java_basic.TestReflectBean\");\n" +
                        "                StringBuilder sb = new StringBuilder();\n" +
                        "                for (Method method : clazz.getMethods()) {\n" +
                        "                   sb.append(method.getName()+\"\\n\");\n" +
                        "                }",sb.toString());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        //获取本类中全部方法包含私有
        mBinding.btn9.setOnClickListener(v -> {
            try {
                Class<?> clazz = Class.forName("com.zhq.devtools.ui.java_basic.TestReflectBean");
                StringBuilder sb = new StringBuilder();
                for (Method method : clazz.getDeclaredMethods()) {
                    sb.append(method.toString()+"\n");
                }
                showLog("Class<?> clazz = Class.forName(\"com.zhq.devtools.ui.java_basic.TestReflectBean\");\n" +
                        "                StringBuilder sb = new StringBuilder();\n" +
                        "                for (Method method : clazz.getDeclaredMethods()) {\n" +
                        "                   sb.append(method.getName()+\"\\n\");\n" +
                        "                }",sb.toString());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        //获取指定公共方法
        mBinding.btn10.setOnClickListener(v -> {
            try {
                Class<?> clazz = Class.forName("com.zhq.devtools.ui.java_basic.TestReflectBean");
                Method method = clazz.getMethod("returnInfoPublic", int.class);
                Object o = clazz.newInstance();
                Object invoke = method.invoke(o, 666);
                showLog("Class<?> clazz = Class.forName(\"com.zhq.devtools.ui.java_basic.TestReflectBean\");\n" +
                        "                Method method = clazz.getMethod(\"returnInfoPublic\", int.class);\n" +
                        "                Object o = clazz.newInstance();\n" +
                        "                Object invoke = method.invoke(o, 666);",invoke.toString());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });

        //获取指定私有方法
        mBinding.btn11.setOnClickListener(v -> {
            try {
                Class<?> clazz = Class.forName("com.zhq.devtools.ui.java_basic.TestReflectBean");
                Method method = clazz.getDeclaredMethod("returnInfoPrivate", String.class);
                method.setAccessible(true);
                Object o = clazz.newInstance();
                Object invoke = method.invoke(o, "王五啊");
                showLog("Class<?> clazz = Class.forName(\"com.zhq.devtools.ui.java_basic.TestReflectBean\");\n" +
                        "                Method method = clazz.getMethod(\"returnInfoPrivate\", String.class);\n" +
                        "                method.setAccessible(true);\n" +
                        "                Object o = clazz.newInstance();\n" +
                        "                Object invoke = method.invoke(o, \"王五啊\");",invoke.toString());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void showLog(String code, String log) {
        mBinding.tvShow.setText("代码：\n" + code + "\n结果：\n" + log);
    }
}
