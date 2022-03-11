package me.jim.wx.javamodule.javassist;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;

public class JavassitWriter {
    public static void main(String[] args) throws CannotCompileException, IOException {
        ClassPool cp = ClassPool.getDefault();
        CtClass ctClass = cp.makeClass("me.jim.wx.javamodule.javassist.MainDemo");

        createMethod(ctClass);
    }

    private static void createMethod(CtClass ctClass) throws CannotCompileException, IOException {

        ConstPool constPool = ctClass.getClassFile2().getConstPool();

        //添加一个hello1的方法
        CtMethod ctMethod = new CtMethod(CtClass.voidType, "hello1", new CtClass[]{CtClass.intType, CtClass.doubleType}, ctClass);
        ctMethod.setBody("System.out.println(\"fuck\");");
        ctMethod.setModifiers(Modifier.PUBLIC);

        MethodInfo methodInfo2 = ctMethod.getMethodInfo2();
        Annotation requestMapping = new Annotation(KeepForShadow.class.getName(), constPool);

        //方法附上注解
        AnnotationsAttribute methodAttr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        //Annotation annotation3 = new Annotation("org.springframework.web.bind.annotation.RequestMapping.RequestMapping",constPool);
        requestMapping.addMemberValue("value", new StringMemberValue("/register", constPool));

        Annotation responseBody = new Annotation(KeepForShadow.class.getName(), constPool);
        methodAttr.addAnnotation(requestMapping);
        methodAttr.addAnnotation(responseBody);

        methodInfo2.addAttribute(methodAttr);
        ctClass.addMethod(ctMethod);

        ctClass.writeFile("./");
    }
}
