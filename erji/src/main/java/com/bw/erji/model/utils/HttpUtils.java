package com.bw.erji.model.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 *  * @Auther: 赵维鸣
 *  * @Date: 2019/2/20 20:49:22
 *  * @Description:
 */
public class HttpUtils<T> {

    private final OkHttpClient okHttpClient;
    private HttpCallBackData httpCallBackData;

    private HttpUtils(){
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5,TimeUnit.SECONDS)
                .connectTimeout(5,TimeUnit.SECONDS)
                .build();
    }
    //单例模式
    public static HttpUtils getInstance(){
        return HttpUtilsInstance.httpUtils;
    }

    private static class HttpUtilsInstance{
        private static HttpUtils httpUtils = new HttpUtils();
    }

    //拦截器
    private class LoginInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            RequestBody body = request.body();
            Log.e("myMessage","");
            Response response = chain.proceed(request);
            return response;
        }
    }


    //handler接收子线程数据
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    T t = (T) msg.obj;

                    httpCallBackData.onResponse(t);

                    break;
                case 1:
                    T t2 = (T) msg.obj;
                    httpCallBackData.onResponse(t2);

                    break;

            }
        }
    };

    //get请求
    public void getData(String url, final Class<T> tClass,final HttpCallBackData httpCallBackData){
        this.httpCallBackData=httpCallBackData;
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功
                String string = response.body().string();
                Gson gson = new Gson();
                T t = gson.fromJson(string, tClass);
                Message message = handler.obtainMessage();
                message.what=0;
                message.obj=t;
                handler.sendMessage(message);

            }
        });


    }
    //post请求
    public void postData(String url, final HashMap<String,String> hashMap, final Class<T> tClass,final HttpCallBackData httpCallBackData){
        this.httpCallBackData = httpCallBackData;
        FormBody.Builder builder = new FormBody.Builder();
        //遍历循环 获取hashmap中的数据
        for (Map.Entry<String, String> stringStringEntry : hashMap.entrySet()){
            String key = stringStringEntry.getKey();
            String value = stringStringEntry.getValue();
            builder.add(key,value);
        }
        FormBody formBody = builder.build();

        Request request = new Request.Builder().url(url).post(formBody).build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                T t = gson.fromJson(string, tClass);
                Message message = handler.obtainMessage();
                message.what = 1;
                message.obj = t;
                handler.sendMessage(message);
            }
        });


    }
    //创建接口
    public interface HttpCallBackData<T>{
        void onResponse(T t);
        void onFailure(IOException e);
    }

}
