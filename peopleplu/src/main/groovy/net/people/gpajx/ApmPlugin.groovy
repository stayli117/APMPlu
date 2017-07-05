package net.people.gpajx

import com.android.build.api.transform.Transform
import com.android.build.gradle.AppExtension
import groovy.util.slurpersupport.GPathResult
import net.people.agent.PPluConfig
import org.gradle.api.Plugin
import org.gradle.api.Project

public class ApmPlugin implements Plugin<Project>, GroovyObject {
    @Override
    void apply(Project project) {
        println("hello People  plugin!");
        boolean isApp = project.plugins.hasPlugin("com.android.application")
        // 没效果啊
        project.dependencies {
            compile 'com.squareup.okhttp3:okhttp:3.6.0'
        }
        if (isApp) {
            println("Hello ----->isApp")
            AppExtension android = project.extensions.findByType(AppExtension)
            Transform transform = new ApmTransform(project)

            android.applicationVariants.all { variant ->
                variant.outputs.each { output ->
//                    println(" V 1 --->" + output.name)
                    output.processManifest.doLast {
//                        println(" V 2 --->" + outputFile.name)
                        GPathResult rootManifest = new XmlSlurper().parse(outputFile)
//                        println(" V 2 --->" + rootManifest.@'android:versionName')
//                        println(" V 2 --->" + outputFile.getText("UTF-8"))
//                        println(" V 2 --->" + rootManifest['@package']) //获取包名
//                        println(" V 3 --->" + android.compileSdkVersion) // 获取sdk编译版本
//                        println(" V 3 --->" + android.buildToolsVersion) // 获取 编译工具版本
                        PPluConfig.compileSdkVersion = android.compileSdkVersion;
                        PPluConfig.packageName = rootManifest['@package'];
                        println("Pa--->" + PPluConfig.packageName)
                        PPluConfig.mProject = project;
                        // 必要
                        List<File> classPaths = android.bootClasspath// sdk 引导路径
                        if (!classPaths.isEmpty()) {
                            classPaths.each { File file ->
                                if (file.absolutePath.contains(android.compileSdkVersion)) {
                                    println(" V 3 --->" + file.absolutePath)
                                    PPluConfig.setAndroidBootPath(file.absolutePath)
                                }
                            }
                        }
                    }
                }
            }

            android.registerTransform(transform)
        } else {
            System.out.println("---->no_App");
        }
    }
}