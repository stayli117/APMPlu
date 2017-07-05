package net.people.agent

import org.gradle.api.InvalidUserDataException
import org.gradle.api.Project

/**
 * author: people_yh_Gao
 * time  : 2017/4/5
 * desc  : 解析配置类
 */

public class PPluConfig {

    public static String androidBootPath;
    public static String compileSdkVersion;

    public static String packageName;
    public static Project mProject;

    public static void setAndroidBootPath(String androidPath) {
        if (androidPath == null) {
            androidPath = getAndroidPath(mProject);
        } else {
            String re = "\\" + "\\platforms";
            String[] split = androidPath.split(re)
            androidPath = split[0] + "/platforms" + split[1].replace("\\", "/")
        }
        androidBootPath = androidPath;
    }

    static String getAndroidBootPath() {
        return androidBootPath
    }
/**
 * 获取android目录
 * @param project
 * @return
 */
    private static String getAndroidPath(def project) {
        def sdkDir;
        Properties properties = new Properties()
        File localProps = project.rootProject.file("local.properties")
        if (localProps.exists()) {
            properties.load(localProps.newDataInputStream())
            sdkDir = properties.getProperty("sdk.dir")
        } else {
            sdkDir = System.getenv("ANDROID_HOME")
        }
        if (sdkDir) {
            println("------>" + sdkDir.toString())
        } else {
            throw new InvalidUserDataException('$ANDROID_HOME is not defined')
        }
        return "${sdkDir}/platforms/" + compileSdkVersion + "/android.jar";
    }
}
