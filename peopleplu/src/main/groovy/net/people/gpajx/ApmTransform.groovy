package net.people.gpajx

import com.android.annotations.NonNull
import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import net.people.agent.SourceClass
import net.people.agent.inject.InjectManager
import org.gradle.api.Project

/**
 * author: people_yh_Gao
 * time  : 2017/3/13
 * desc  :
 */

public class ApmTransform extends Transform {
    Project mProject

    public ApmTransform(Project pro) {
        mProject = pro
        println("Hello Transform\n"
                + mProject.toString() + "\n"
                + mProject.buildDir.absolutePath + "\n"
                + mProject.buildFile.absolutePath + "\n"
                + mProject.projectDir.absolutePath + "\n"
                + mProject.buildscript.sourceFile.absolutePath + "\n"
                + mProject.buildscript.dependencies.localGroovy().name + "\n"
                + "\n"
        )


    }

    @Override
    public String getName() {
        return "APMPlugin";
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }

    @Override
    void transform(
            @NonNull Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {

        println("Hello ---- > T \n\n")

        // Transform的inputs有两种类型，一种是目录，一种是jar包，要分开遍历
        // 将工程内的所有类添加到搜索路径 包括了所有的依赖Jar
        SourceClass.addClassPool(inputs, outputProvider)

        // 进行修改
        InjectManager.modifyClass(inputs, outputProvider)

        // 删除拷贝的新Jar
//        FileUtils.deleteDirectory(new File("E:/work_tool/ex/"))


    }


}
