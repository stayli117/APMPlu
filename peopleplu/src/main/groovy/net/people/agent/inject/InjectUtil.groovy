package net.people.agent.inject

import javassist.expr.MethodCall

/**
 * author: people_yh_Gao
 * time  : 2017/3/20
 * desc  :
 */

public class InjectUtil {

    private static final String pack = "net.people.bytecode"

    public static void injectOnCreate(MethodCall m) {
        m.replace("{" + pack + ".ActMonitor.actOnCreate(this);" +
                "\$_ = \$proceed(\$\$);" +
                "}");

    }


}
