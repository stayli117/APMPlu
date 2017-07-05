package net.people.agent

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformOutputProvider
import javassist.ClassPool
import net.people.agent.util.JarZipUtil

/**
 * author: people_yh_Gao
 * time  : 2017/4/5
 * desc  :
 */

public class SourceClass {

    public static ClassPool pool;

    public static String tempSrc;

    private static ClassPool initClassPool() {
        if (pool == null) {
            pool = ClassPool.getDefault();
            // android 目录注入
            pool.insertClassPath(PPluConfig.getAndroidBootPath())
            pool.appendSystemPath()
        }
        return pool;
    }

    public static ClassPool getPool() {
        if (pool == null) pool = initClassPool();
        return pool;
    }

    /**
     *
     * @param inputs 添加 依赖包和编译目录的搜索路径
     */
    public
    static void addClassPool(Collection<TransformInput> inputs, TransformOutputProvider outputProvider) {
        inputs.each { TransformInput input ->
            //对类型为jar文件的input进行遍历
            input.jarInputs.each { JarInput jarInput ->
                String jarPath = jarInput.file.absolutePath;
                if (jarPath.endsWith("classes.jar") && jarPath.contains("exploded-aar\\")) {
                    // 添加搜索路径 Jar
                    addJarClass(jarPath)
                }
            }

            //对类型为“文件夹”的input进行遍历
            input.directoryInputs.each { DirectoryInput directoryInput ->
                // 添加搜索路径 不可进行注入 原因是 可能会依赖Jar
                addDirClassPool(directoryInput.file.absolutePath)
            }
        }

    }


    public static ClassPool addDirClassPool(String libPath) {
        if (pool == null) {
            // 初始化 ClassPool
            initClassPool()
        }
        pool.appendClassPath(libPath)
        return pool;
    }

    /**
     * 思路 ：
     * 利用原Jar Copy出 New Jar 将New Jar 加入到pool
     * @param srcPath
     * @param project
     * @return 此方法的目的仅是将依赖的Jar添加到类搜索路径
     */
    public static ClassPool addJarClass(String srcPath) {
        if (srcPath.endsWith(".jar")) {
            File srcJarFile = new File(srcPath)
            println("Jar--->" + srcPath)

            //Jar加入类搜索路径
//            File jarNewFile = new File("E:/work_tool/ex/" + srcJarFile.getParent().substring(4) + srcJarFile.getName())
//            FileUtils.copyFile(srcJarFile, jarNewFile)
            // jar包解压后的保存路径  暂时这么处理
            tempSrc = srcJarFile.getParent();
            String jarZipDir = tempSrc + "/" + srcJarFile.getName().replace('.jar', '')
            println("inject JarDir" + jarZipDir)
            // 解压jar包, 返回jar包中所有class的完整类名的集合（带.class后缀）
            JarZipUtil.unzipJar(srcPath, jarZipDir)
            // 添加搜索路径
            addDirClassPool(jarZipDir)

//            // 做其他操作需保证磁盘上有次路径，因此 操作完才能删除 删除目录
//            FileUtils.deleteDirectory(new File())
        }
    }

}
