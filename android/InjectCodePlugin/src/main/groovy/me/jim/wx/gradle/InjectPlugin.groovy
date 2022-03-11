/**
 * http://www.wangyuwei.me/2017/03/05/ASM%E5%AE%9E%E6%88%98%E7%BB%9F%E8%AE%A1%E6%96%B9%E6%B3%95%E8%80%97%E6%97%B6/#more
 */
package me.jim.wx.gradle

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import javassist.ClassPool
import javassist.CtClass
import me.jim.wx.gradle.digest.DigestUtils
import me.jim.wx.javamodule.asm.MyClassVisitor
import net.bytebuddy.jar.asm.ClassReader
import net.bytebuddy.jar.asm.ClassVisitor
import net.bytebuddy.jar.asm.ClassWriter
import org.gradle.api.Plugin
import org.gradle.api.Project

class InjectPlugin extends Transform implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def android = project.extensions.getByType(com.android.build.gradle.AppExtension)
        android.registerTransform(this)
    }

    //name or task name
    @Override
    String getName() {
        return "InjectCode"
    }

    //specify input type. can use as a filter
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        println '//===============asm start===============//'
        def startTime = System.currentTimeMillis()
        transformInvocation.inputs.each { TransformInput input ->

            input.directoryInputs.each { DirectoryInput directoryInput ->

                //use asm
                if (directoryInput.file.isDirectory()) {
                    directoryInput.file.eachFileRecurse { File file ->
                        def name = file.name
                        if (name.endsWith(".class") && !name.startsWith("R\$") &&
                                !"R.class".equals(name) && !"BuildConfig.class".equals(name)) {
                            println name + "is changing..."

                            def s = file.parentFile.getAbsolutePath() + File.separator + name

                            ClassPool cp = ClassPool.getDefault();
                            CtClass ctClass = cp.makeClass(file.newInputStream());

                            String oldName = "android.app.Activity";
                            String newName = "me.jim.wx.awesomebasicpractice.other.bytecode.biz.ShadowActivity";
                            if (!ctClass.hasAnnotation("android.support.annotation.Keep")) {
                                ctClass.replaceClassName(oldName, newName);
                            }
                            def code = ctClass.toBytecode();
                            ctClass.defrost();


                            ClassReader cr = new ClassReader(code)
                            println "asm" + cr.getClassName()
                            if (cr.getClassName() == "me/jim/wx/awesomebasicpractice/other/bytecode/biz/Application") {
                                ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS)
                                ClassVisitor cv = new MyClassVisitor(cw) //asm

                                cr.accept(cv, ClassReader.EXPAND_FRAMES)

                                code = cw.toByteArray()

                            }
                            FileOutputStream fos = new FileOutputStream(s)
                            fos.write(code)
                            fos.close()
                        }
                    }
                }

                def dest = transformInvocation.outputProvider.getContentLocation(directoryInput.name,
                        directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.file, dest)
            }

            input.jarInputs.each { JarInput jarInput ->
                def jarName = jarInput.name
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                def dest = transformInvocation.outputProvider.getContentLocation(jarName + md5Name,
                        jarInput.contentTypes, jarInput.scopes, Format.JAR)

                FileUtils.copyFile(jarInput.file, dest)
            }
        }

        def totalTime = (System.currentTimeMillis() - startTime) / 1000

        println '#inject time: ' + totalTime + "s#"
        println '//===============asm stop===============//'
    }
}