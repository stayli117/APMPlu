package net.people.agent.inject

import javassist.*
import javassist.expr.ExprEditor
import javassist.expr.MethodCall
import net.people.agent.PPluConfig
import net.people.agent.SourceClass
import net.people.agent.http.OkHttpUtil

/**
 * author: people_yh_Gao
 * time  : 2017/4/5
 * desc  :
 */

public class DirInject {

    private static ClassPool pool = SourceClass.pool;
    private static final String pack = "net.people.bytecode"

    /**
     * 遍历该目录下的所有class，对所有class进行代码注入。
     * 其中以下class是不需要注入代码的：
     * --- 1. R文件相关
     * --- 2. 配置文件相关（BuildConfig）
     * --- 3. Application
     * @param path 目录的路径
     */
    public static void injectDir(String path) throws Exception {
        if (pool == null) {
            pool = SourceClass.getPool();
        }
        File dir = new File(path)
        if (dir.isDirectory()) {
            dir.eachFileRecurse { File file ->
                String filePath = file.absolutePath
                //确保当前文件是class文件，并且不是系统自动生成的class文件
                String pCName = getNormalClass(path, filePath)
                if (pCName != null) {
                    println("PCNAME -- >" + pCName)
                    // 判断manifest文件act的当前目录
                    boolean act = -1 != isPaAct(pCName)
                    println("ACT  -- >" + act)
                    if (act) { // act 必须在这个包下
                        //开始修改class文件
                        println("Class Name --- > " + pCName)
                        CtClass ctClass = pool.getCtClass(pCName)
                        println("pool---> Get Success")
                        if (ctClass != null) {
                            if (ctClass.isFrozen()) {
                                ctClass.defrost()
                            }
                            // 所有类均不是接口类
                            if (!ctClass.isInterface()) {
                                // 判断当前类是不是Act 的子类
                                println("pool---> 2Get Success")
//                                ctClass.getDeclaredMethods().each {
//                                    CtMethod ctMethod ->
//                                        String cName = ctMethod.name
//                                        String lName = ctMethod.longName
//                                        String descriptor = ctMethod.methodInfo2.descriptor
//
////                                        if (cName.equals("onCreate") &&
////                                                /*lName.contains(PPluConfig.packageName) &&*/
////                                                "(Landroid/os/Bundle;)V".equals(descriptor)) {
////                                            ctMethod.insertAt(2, pack + ".ActMonitor.actOnCreate(this);")
////                                        }
//                                }
                                if (isAct(ctClass)) {
                                    println("pool--->3 Get Success")
                                    ActivityUtil.regActLifeCallBack(ctClass)
                                    println("pool---> 4Get Success")
                                } else if (isFrag(ctClass)) {
//                                    FragmentUtil.regFragment(ctClass)
                                }
                                isOkHttp(ctClass)
                            }
                            ctClass.classFile.name
                            ctClass.writeFile(path)
                            ctClass.detach()/**从类池中移除该对象，避免加载过多造成内存溢出问题*/
                        }
                    } else {
                        println("Filepath -- 》 " + pCName)
                    }
                }
            }
            println("pool---> 5Get Success")
            // 删除目录
            InjectManager.isDelete()
            println("pool---> 6Get Success")
        }
    }

    /**
     *
     * @param ctClass
     */
    private static void isOkHttp(CtClass ctClass) {
        if (ctClass != null) {
            pool.insertClassPath(new ClassClassPath(okhttp3.Request));

//            使用 动态代理 ， 不使用这个方法了
//            CtClass[] inters = ctClass.getInterfaces()
//            if (inters != null || inters.size() > 0) {
//                inters.each { CtClass ctInter ->
//                    String supName = ctInter.name
//                    println("Inter -->" + ctClass.getName() + "->" + supName)
//                    if (supName.contains("okhttp3.Callback")) {
//
//                    }
//                }
//
//            }

//            ctClass.instrument(new ExprEditor() {
//                @Override
//                void edit(MethodCall m) throws CannotCompileException {
//                    super.edit(m)
//                    println("ctMethod---> " + m.methodName)
//                    OkHttpUtil.injectOkStart(m)
//                }
//            })

            // 遍历类中的每个方法
            ctClass.getDeclaredMethods().each { CtMethod ctMethod ->
//             每个方法的内容遍历
                ctMethod.instrument(new ExprEditor() {
                    @Override
                    void edit(MethodCall m) throws CannotCompileException {
                        super.edit(m)
//                        OkHttpUtil.injectOkStart(m, ctMethod)

                        OkHttpUtil.injectOkStart(m, ctMethod)
                    }
                })
//                println("ctMethod---> " + ctMethod.name)
//                OkHttpUtil.injectOkResult(ctMethod)
            }

            // 遍历类
//            ctClass.instrument(new ExprEditor() {
//                @Override
//                void edit(MethodCall m) throws CannotCompileException {
//                    super.edit(m)
//                    OkHttpUtil.injectOkStart(m)
//                }
//            })
        }


    }
    /**
     *
     * @param ctClass
     */
    private static boolean isAct(CtClass ctClass) {
        if (ctClass != null) {
            return ctClass.name.endsWith("Activity")
//            try {
//                CtClass supClass = ctClass.getSuperclass()
//                if (supClass != null) {
//                    println("Sup --- >" + supClass.name)
//                    return supClass.name.endsWith("Activity") && supClass.name.contains("android")
//                }
//            } catch (Exception e) {
//
//            }
        }
        return false;
    }
    /**
     *
     * @param ctClass
     */
    private static boolean isFrag(CtClass ctClass) {
        if (ctClass != null) {
            return ctClass.name.endsWith("Fragment")
//            CtClass supClass = ctClass.getSuperclass()
//            if (supClass != null) {
//                println("Sup --- >" + supClass.name)
//                return supClass.name.endsWith("Fragment") && supClass.name.contains("android")
//            }
        }
        return false;
    }

    /**
     *
     * @param srcPath 编译到的目录
     * @param filePath 文件路径
     * @return 包名 + 类名形式
     */
    public static String getNormalClass(String srcPath, String filePath) {
        if (filePath.endsWith(".class")
                && !filePath.contains('R$')
                && !filePath.contains('R.class')
                && !filePath.contains("BuildConfig.class")) {
            // 当前类编译到的目录
            int dirlen = srcPath.length()
            // 仅保留包名和类名
            filePath = filePath.substring(dirlen + 1)
            //字符替换
            filePath = filePath.replace('\\', '.').replace('/', '.')
            int end = filePath.length() - 6 // .class = 6
            String pCName = filePath.substring(0, end)
            return pCName;
        }
        return null;
    }

    /**
     *
     * @param pCName 全类名
     * @return 判断当前类是否存在于主包名中
     */
    public static int isPaAct(String pCName) {
        String pa;
        if (PPluConfig.packageName != null) {
            if (PPluConfig.packageName.indexOf(".") > -1) {
                pa = PPluConfig.packageName.split("\\.")[0];
            } else {
                pa = PPluConfig.packageName
            }
            return pCName.indexOf(pa);
        }
        return -1;
    }
}
