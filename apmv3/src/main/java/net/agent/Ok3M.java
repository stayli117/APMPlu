package net.agent;

import android.util.Log;

/**
 * author: people_yh_Gao
 * time  : 2017/4/7
 * desc  :
 */

public class Ok3M {
    private static final String TAG = "Ok3M";

    //  ok3
    public static void holdUrl(String tag, String url) {
        Log.e(TAG, "holdUrl: " + "Tag -->" + tag + ": url -->" + url);
    }

    // ok3
    public static void holdResult(String tag, String url, String result) {
        Log.e(TAG, "holdResult: " + "Tag -->" + tag + ": url -->" + url + ":result -->" + result);
    }

    // okutils
    public static void holdStart(Object tag, Object url) {
        Log.e(TAG, "holdStart: " + "Tag -->" + tag + ": url -->" + url);
    }

    // okutils
    public static void holdResponse(String tag, String result, int id) {
        Log.e(TAG, "holdResponse: " + "Tag -->" + tag + ": url -->" + result + ":id -->" + id);
    }

    // okutils
    public static void holdError(String tag, String result, Exception e, int id) {
        Log.e(TAG, "holdError: " + "Tag -->" + tag + ": url -->" + result + ":id -->" + id);
    }


}
