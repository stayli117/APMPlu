package net.people.agent.http

import javassist.CtMethod
import javassist.expr.MethodCall
/**
 * author: people_yh_Gao
 * time  : 2017/4/6
 * desc  :
 */

public class OkHttpUtil {

    private static final String pack = "net.people.bytecode"
    /**
     * ok 3
     * @param m
     * @param tag
     */
    private static void ok3Start(MethodCall m, int tag) {
        println(" OkHttp  inject -- >" + tag)
        if (tag == 2) {
            println(" Ok Http  inject -- > Method")

            m.replace("{" + "\$_ = \$proceed(\$\$);" +
                    pack + ".Ok3M.holdUrl(\"url\",\$0.request().toString());" +
                    "}")

//             m.where().insertAt(m.lineNumber,false,pack + ".Ok3M.holdUrl(\"url\",\$0.request().toString());")
//            m.method.insertBefore(pack + ".Ok3M.holdUrl(\"url\",\$0.request().toString());")
            println(" Ok Http  inject -- > MethodCall")

            // groovy

        }
        if (tag == 1) {
            m.replace("{" + pack + ".Ok3M.holdUrl(\"url\",\$0.request().toString());"
                    + "\$_ = \$proceed(\$\$);" +
                    pack + ".Ok3M.holdResult(\"result\",\$0.request().toString(),\$_.body().string());"
                    + "}")

//            m.method.insertBefore(pack + ".Ok3M.holdUrl(\"url\",\$0.request().toString());")
//            m.method.insertAfter(pack + ".Ok3M.holdResult(\"result\",\$0.request().toString(),\$_.body().string());")
        }
        println("inject OkHttp -- > Success")
    }
    /**
     * ok go
     * @param mC
     */
    private static void okGStart(MethodCall m) {

        m.replace("{" + "\$_ = \$proceed(\$\$);" +
                pack + ".Ok3M.holdStart(\"url\",\$0.toString());"
                + "}")
//        m.method.insertBefore(pack + ".Ok3M.holdStart(\"url\",\$0.toString());")
        println("inject Ok Util -- > Success")
    }

    /**
     *  ok Utils
     * @param m
     */
    private static void okUStart(MethodCall m) {
        // start request
        m.replace("{" + "\$_ = \$proceed(\$\$);" +
                pack + ".Ok3M.holdStart(\"url\",\$0.toString());"
                + "}")
//        m.method.insertBefore(pack + ".Ok3M.holdStart(\"url\",\$0.toString());")
        println("inject Ok Util -- > Success")
    }

    /**
     *
     * @param m MethodCall
     */
    public static void injectOkStart(MethodCall m, CtMethod ctMethod) {
        println("MetCall -->" + m.fileName + "->" + m.lineNumber)
        println("MetCall -->" + m.method.name)
        println("MetCall -->" + m.methodName + "->" + m.signature)
        println("ctMethod -->" + ctMethod.name + "->" + ctMethod.signature)

        String cName = m.fileName

        if ("1".equals("0")) {

        } else {
//            String mInfo = m.method.methodInfo2.toString()
            String mName = m.methodName

            String sig = m.signature
            // ok3 同步
            if ("()Lokhttp3/Response;".equals(sig) &&
                    "execute".equals(mName)) {
//                ok3Start(m, 1)

            } else

            // ok3 异步
            if ("enqueue".equals(mName) &&
                    "(Lokhttp3/Callback;)V".equals(sig)) {
                ok3Start(m, 2)

//                ctMethod.insertBefore(pack + ".Ok3M.holdUrl(\"url\",\"url55555\");")
            } else
            // okutils
            if ("execute".equals(mName) &&
                    "(Lcom/zhy/http/okhttp/callback/Callback;)V".equals(sig)) {
                okUStart(m)
            } else

            // okgo
            if ("execute".equals(mName) && "(Lcom/lzy/okgo/callback/AbsCallback;)V".equals(sig)) {
                okGStart(m)
            }

            /*--- Result ---- */
            // ok 3
            if ("onResponse".equals(mName) && "(Lokhttp3/Call;Lokhttp3/Response;)V".equals(sig)) {
                m.method.insertAfter(pack + ".Ok3M.holdResult(\"OK3_Re\", \$1.request().toString(), \$2.toString());")
                println("inject Ok3 onResponse-- > Success")
            }
            // ok3
            if ("onFailure".equals(mName) && "(Lokhttp3/Call;Ljava/io/IOException;)V".equals(sig)) {
                m.method.insertAfter(pack + ".Ok3M.holdResult(\"Ok3_Re\", \$1.request().toString(), \$2.getMessage());")
                println("inject Ok3 onFailure-- > Success")
            }

            //  okutils
            if ("onResponse".equals(mName) && "(Ljava/lang/String;I)V".equals(sig)) {
                m.method.insertAfter(pack + ".Ok3M.holdResponse(\"OKU_Re\", \$1,\$2);")
                println("inject Ok3 onFailure-- > Success")
            }
            if ("onError".equals(mName) && "(Lokhttp3/Call;Ljava/lang/Exception;I)V".equals(sig)) {
                m.method.insertAfter(pack + ".Ok3M.holdError(\"OkU_Re\", \$1.request().toString(),\$2,\$3);")
            }

            // okgo
            if ("onSuccess".equals(mName) &&
                    "(Ljava/lang/String;Lokhttp3/Call;Lokhttp3/Response;)V".equals(sig)) {
                m.method.insertAfter(pack + ".Ok3M.holdSuccess(\"OkGo_Re22\", \$1,\$2.request().toString(),\$3.toString());")
                println("Okgo -- > end Success")
            }
            if ("onError".equals(mName) && "(Lokhttp3/Call;Lokhttp3/Response;Ljava/lang/Exception;)V".equals(sig)) {
                m.method.insertAfter(pack + ".Ok3M.holdError(\"OkGo_Re\", \$1.request().toString(),\$2.toString(),\$3);")
            }
        }

    }

    /**
     *
     * @param ctMethod
     */
    public static void injectOkResult(CtMethod ctMethod) {
        println("CtMe--->" + ctMethod.name)
        println("CtMe--->" + ctMethod.signature)


        if ("0".equals("0")) {

        } else {
//            String mInfo = ctMethod.getMethodInfo2().toString()
            String mName = ctMethod.name
            String sig = ctMethod.signature
            // ok 3
            if ("onResponse".equals(mName) && "(Lokhttp3/Call;Lokhttp3/Response;)V".equals(sig)) {
                ctMethod.insertAfter(pack + ".Ok3M.holdResult(\"OK3_Re\", \$1.request().toString(), \$2.toString());")
            }
            // ok3
            if ("onFailure".equals(mName) && "(Lokhttp3/Call;Ljava/io/IOException;)V".equals(sig)) {
                ctMethod.insertAfter(pack + ".Ok3M.holdResult(\"Ok3_Re\", \$1.request().toString(), \$2.getMessage());")
            }
            // ok utils
            if ("onResponse".equals(mName) && "(Ljava/lang/String;I)V".equals(sig)) {
                ctMethod.insertAfter(pack + ".Ok3M.holdResponse(\"OKU_Re\", \$1,\$2);")
            }
            // ok utils
            if ("onError".equals(mName) && "(Lokhttp3/Call;Ljava/lang/Exception;I)V".equals(sig)) {
                ctMethod.insertAfter(pack + ".Ok3M.holdError(\"OkU_Re\", \$1.request().toString(),\$2,\$3);")
            }
            // ok go
            if ("onSuccess".equals(mName) && "(Ljava/lang/String;Lokhttp3/Call;Lokhttp3/Response;)V".equals(sig)) {
                ctMethod.insertAfter(pack + ".Ok3M.holdSuccess(\"OkGo_Re\", \$1,\$2.request().toString(),\$3.toString());")
            }
            // okgo
            if ("onError".equals(mName) && "(Lokhttp3/Call;Lokhttp3/Response;Ljava/lang/Exception;)V".equals(sig)) {
                ctMethod.insertAfter(pack + ".Ok3M.holdError(\"OkGo_Re\", \$1.request().toString(),\$2.toString(),\$3);")
            }
        }
    }
}
