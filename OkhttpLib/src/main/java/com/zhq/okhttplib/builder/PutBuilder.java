package com.zhq.okhttplib.builder;


import com.zhq.okhttplib.MyOkHttp;
import com.zhq.okhttplib.callback.MyCallback;
import com.zhq.okhttplib.response.IResponseHandler;
import com.zhq.okhttplib.util.LogUtils;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;


public class PutBuilder extends OkHttpRequestBuilder<PutBuilder> {

    public PutBuilder(MyOkHttp myOkHttp){
        super(myOkHttp);
    }

    @Override
    public void enqueue(IResponseHandler responseHandler) {
        try {
            if(mUrl == null || mUrl.length() == 0) {
                throw new IllegalArgumentException("url can not be null !");
            }

            Request.Builder builder = new Request.Builder().url(mUrl);
            appendHeaders(builder, mHeaders);

            if (mTag != null) {
                builder.tag(mTag);
            }

            builder.put(RequestBody.create(MediaType.parse("text/plain;charset=utf-8"), ""));

            Request request = builder.build();

            mMyOkHttp.getOkHttpClient()
                    .newCall(request)
                    .enqueue(new MyCallback(responseHandler));
        } catch (Exception e) {
            LogUtils.e("Put enqueue error:" + e.getMessage());
            responseHandler.onFailure(0, e.getMessage());
        }
    }
}