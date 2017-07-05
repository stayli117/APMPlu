package net.act;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author: people_yh_Gao
 * time  : 2017/3/16
 * desc  :
 */

public class NetUtil2 extends Object {
    private static final String TAG = "NetUtil";

    public static void startWithOkHttp3(String url) throws IOException {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        Request request = new Request.Builder()
                .url(url)
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {

        }

    }


    /**
     * 异步
     *
     * @param url
     */
    public static void startRequestWithOkHttp3(String url) {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder().build();
        //创建一个Request
        Request request = new Request.Builder()
                .url(url)
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度 异步

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                System.out.println("66666");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("66666");
            }
        });


    }
}
