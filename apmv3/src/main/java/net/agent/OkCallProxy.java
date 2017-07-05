package net.agent;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * author: people_yh_Gao
 * time  : 2017/4/6
 * desc  :
 */

public class OkCallProxy implements InvocationHandler {

    private Object proxied;
    private static final String TAG = "OkCallProxy";

    public OkCallProxy(Object proxied) {
        this.proxied = proxied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.e(TAG, "invoke: --->" + method.getName());
//        for (Object arg : args) {
//            Log.e(TAG, "invoke: --->" + arg.toString());
//        }
//        if (method.getName().equals("onResponse")) {
//            Call call = (Call) args[0];
//            Response response = (Response) args[1];
//            OkHttpBean httpBean = new OkHttpBean();
//            httpBean.startTime = PeoAgent.calls.get(call);
//            httpBean.endTime = System.currentTimeMillis() + "";
//            httpBean.httppro = response.message();
//            httpBean.url = call.request().url().toString();
//        } else if ("convertSuccess".equals(method.getName())) {
//            Log.e(TAG, "invoke: OK Go" + method.toGenericString());
//        }


        return method.invoke(proxied, args);
    }
}
