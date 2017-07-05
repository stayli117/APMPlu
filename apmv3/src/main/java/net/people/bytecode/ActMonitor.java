package net.people.bytecode;

import android.util.Log;

/**
 * author: people_yh_Gao
 * time  : 2017/4/10
 * desc  :
 */

public class ActMonitor {
    private static final String TAG = "ActMonitor";

    public static void actOnCreate(Object o) {
        Log.e(TAG, "ActOnCreate: " + o.getClass().getSimpleName());
    }
}
