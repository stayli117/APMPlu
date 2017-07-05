package net.act;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * author: people_yh_Gao
 * time  : 2017/4/7
 * desc  :
 */

public class OkGoUtil {
    private static final String TAG = "OkGoUtil";

    /**
     * 异步
     *
     * @param url
     */
    public static void exceNet(String url) {
        OkGo.get(url).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                System.out.println("66666");

            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                System.out.println("66666");
            }
        });

    }
}
