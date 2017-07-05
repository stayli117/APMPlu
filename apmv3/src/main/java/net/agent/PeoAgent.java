package net.agent;

import android.util.Log;

import java.util.HashMap;

/**
 * author: people_yh_Gao
 * time  : 2017/2/20
 * desc  :
 */

public class PeoAgent {
    private static final String TAG = "PeoAgent";

    public static HashMap<Object, String> calls = new HashMap<>();

    public static void testPlu() {
        Log.e(TAG, "testPlu: " + System.currentTimeMillis());
    }

    public static void ActOnCreate(Object o) {
        Log.e(TAG, "ActOnCreate: " + o.toString());
    }

    /**
     * @param o okhttp3的call对象 执行 enqueue异步方法时
     */
//    public static void cutOkEnqueue(Object o) {
//        if (o instanceof Call) {
//            // call 对象
//            Log.e(TAG, "cutOkEnqueue: " + ((Call) o).request().url().toString());
//            calls.put(o, System.currentTimeMillis() + "");
//        }
//    }

    /**
     * @param o okhttp3的call对象 执行 enqueue异步方法时
     */
//    public static void cutOkRe(Object o) {
//        if (o instanceof Response) {
//            // call 对象
//            Log.e(TAG, "cutOkRe: " );
////            calls.put(o, System.currentTimeMillis() + "");
//        }
//    }

    /**
     * 动态代理
     *
     * @param o okhttp3的callback对象
     * @return callback的代理对象
     */
//    public static Callback proxyCB(Object o) {
//        return (Callback) Proxy.newProxyInstance(o.getClass().getClassLoader(), new Class[]{Callback.class},
//                new OkCallProxy(o));
//    }

    /**
     * 拦截OkGo
     *
     * @param o okGo的Request 对象
     */
//    public static void cutOkGo(Object o) {
//        if (o instanceof BaseRequest) {
//            Log.e(TAG, "cutOkEnqueue: " + ((BaseRequest) o).getUrl());
//            calls.put(o, System.currentTimeMillis() + "");
//        }
//    }

    /**
     * 静态代理
     *
     * @param callback okGo的AbsCallback对象
     * @return AbsCallback的代理对象
     */
//    public static AbsCallback proxyOG(final AbsCallback callback) {
//        return new AbsCallback() {
//            @Override
//            public void onSuccess(Object o, Call call, Response response) {
//                Log.e(TAG, "onSuccess: OkGo ");
//                callback.onSuccess(o, call, response);
//            }
//
//            @Override
//            public Object convertSuccess(Response response) throws Exception {
//                return callback.convertSuccess(response);
//            }
//        };
//    }


    /**
     * 拦截OkUtils
     *
     * @param o okGo的Request 对象
     */
//    public static void cutOkUtil(Object o) {
//        if (o instanceof BaseRequest) {
//            Log.e(TAG, "cutOkUtil: ");
//            calls.put(o, System.currentTimeMillis() + "");
//        }
//    }

    /**
     * 静态代理
     *
     * @param callback okUtils的Callback对象
     * @return Callback的代理对象
     */
//    public static com.zhy.http.okhttp.callback.Callback proxyOU(final com.zhy.http.okhttp.callback.Callback callback) {
//        return new com.zhy.http.okhttp.callback.Callback() {
//            @Override
//            public Object parseNetworkResponse(Response response, int id) throws Exception {
//                return callback.parseNetworkResponse(response, id);
//            }
//
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                callback.onError(call, e, id);
//            }
//
//            @Override
//            public void onResponse(Object response, int id) {
//                callback.onResponse(response, id);
//            }
//
//            @Override
//            public void onBefore(Request request, int id) {
//                super.onBefore(request, id);
//            }
//
//            @Override
//            public void onAfter(int id) {
//                callback.onAfter(id);
//            }
//
//            @Override
//            public void inProgress(float progress, long total, int id) {
//                callback.inProgress(progress, total, id);
//            }
//
//            @Override
//            public boolean validateReponse(Response response, int id) {
//                return callback.validateReponse(response, id);
//            }
//        };
//    }


}
