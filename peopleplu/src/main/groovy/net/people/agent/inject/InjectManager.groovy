package net.people.agent.inject

import com.android.build.api.transform.*
import net.people.agent.SourceClass
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils

/**
 * author: people_yh_Gao
 * time  : 2017/4/6
 * desc  :
 */

public class InjectManager {
    public
    static void modifyClass(Collection<TransformInput> inputs, TransformOutputProvider outputProvider) {
        inputs.each { TransformInput input ->
            //对类型为jar文件的input进行遍历
            input.jarInputs.each { JarInput jarInput ->
                String jarPath = jarInput.file.absolutePath;
//                String projectName = mProject.rootProject.name;
                if (jarPath.endsWith("classes.jar") && jarPath.contains("exploded-aar\\")) {
//                     进行注入 修改
//                    JarInject.injectJar(jarPath)
                    // 注入引入的Jar文件
                }
                //jar文件一般是第三方依赖库jar文件
                // 重命名输出文件（同目录copyFile会冲突）
                def jarName = jarInput.name
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                //生成输出路径
                def dest = outputProvider.getContentLocation(jarName + md5Name, jarInput.contentTypes, jarInput.scopes, Format.JAR)
                //将输入内容复制到输出
                FileUtils.copyFile(jarInput.file, dest)
            }

            //对类型为“文件夹”的input进行遍历
            input.directoryInputs.each { DirectoryInput directoryInput ->
                injectDir(directoryInput, outputProvider)
            }
        }
    }

    private
    static void injectDir(DirectoryInput directoryInput, TransformOutputProvider outputProvider) {
        // 获取output目录
        def dest = outputProvider.getContentLocation(directoryInput.name,
                directoryInput.contentTypes, directoryInput.scopes,
                Format.DIRECTORY)
        //文件夹里面包含的是我们手写的类以及R.class、BuildConfig.class以及R$XXX.class等
        println("Hello---->Transform " + directoryInput.file.absolutePath)
        // 进行修改
        DirInject.injectDir(directoryInput.file.absolutePath)

        println("dir -- > " + directoryInput.file.absolutePath)
        // 将input的目录复制到output指定目录
        FileUtils.copyDirectory(directoryInput.file, dest)
    }

    public static int deInt = 0

    public static void isDelete() {
        if (deInt == 1 && SourceClass.tempSrc != null && SourceClass.tempSrc.length() > 0) {
            FileUtils.deleteDirectory(new File(SourceClass.tempSrc))
        }
    }
}
