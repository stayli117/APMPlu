package net.people.agent.actfrag

import javassist.CtClass
import javassist.CtMethod

/**
 * author: people_yh_Gao
 * time  : 2017/3/14
 * desc  :
 */

public class FragmentUtil {

    def insertCode = [
            "android.app.Fragment.onResume()": "FragMonitor.onFragmentResumed(this);",
            "android.app.Fragment.onPause()" : "FragMonitor.onFragmentResumed(this);"

    ]


    public static void regFragment(CtClass ctClass) {
        println("Frag -- >" + ctClass.name)
    }
    // 处理 App Fragment
    public static void regAppFragment(CtClass ctClass) {
        // 插入包
        // 获取指定的方法
        CtMethod frResumeMethod = ctClass.getMethod("onResume", "()V")
        insertCode(frResumeMethod, "android.app.Fragment.onResume()", "FragMonitor.onFragmentResumed(this);")
        CtMethod frPauseMethod = ctClass.getMethod("onPause", "()V")
        insertCode(frPauseMethod, "android.app.Fragment.onPause()", "FragMonitor.onFragmentResumed(this);")

    }

    public static void regSupFragment(CtClass ctClass) {

    }


    private static void insertCode(CtMethod ctMethod, String longName, String insertCode) {
        // 再次对方法名进行判断进行确定
        if (ctMethod.getLongName().equals(longName)) {
            // 进行代码插入
            ctMethod.insertAfter(insertCode)
            println(longName + "<---F -- > insert Success")
        } else {
            println("方法名校验失败")
        }
    }
}
