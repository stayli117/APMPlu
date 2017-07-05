package net.act;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import okhttp3.Call;

/**
 * author: people_yh_Gao
 * time  : 2017/4/7
 * desc  :
 */

public class OkUtils {
    private static final String TAG = "OkUtils";

    public static void ex(String url) {


        RequestCall requestCall = OkHttpUtils.get().url(url).build();
        requestCall.execute(new StringCallback() {

            @Override
            public void onError(Call call, Exception e, int id) {
                System.out.println("66666");
            }

            @Override
            public void onResponse(String response, int id) {
                System.out.println("66666");
            }
        });
    }
}
