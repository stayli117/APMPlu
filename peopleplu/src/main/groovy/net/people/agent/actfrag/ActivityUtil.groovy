package net.people.agent.actfrag

import javassist.CtClass
import javassist.CtMethod
import net.people.agent.PPluConfig

/**
 * author: people_yh_Gao
 * time  : 2017/3/14
 * desc  :
 */

public class ActivityUtil {
    private static final String pack = "net.people.bytecode"

    public static void regActLifeCallBack(CtClass ctClass) {






        CtMethod[] ctMethods = ctClass.getDeclaredMethods("onCreate")
        ctMethods.each { CtMethod ctMethod ->
            println("mName-->" + ctMethod.longName)
            println("mName-->" + ctMethod.methodInfo2.descriptor)
            println("mName-->" + PPluConfig.packageName)
            if (ctMethod.name.equals("onCreate") && ctMethod.methodInfo2.descriptor.equals("(Landroid/os/Bundle;)V")) {
                ctMethod.insertAfter(pack + ".ActMonitor.actOnCreate(this);")
//                ctMethod.insertBefore(pack + ".ActMonitor.actOnCreate(this);")
            }
        }

//        ctClass.instrument(new ExprEditor() {
//            @Override
//            void edit(MethodCall m) throws CannotCompileException {
//                super.edit(m)
//                if ("(Landroid/os/Bundle;)V".equals(m.signature) && "onCreate".equals(m.methodName)) {
//                    InjectUtil.injectOnCreate(m)
//                }
//            }
//        })

    }
}
