package me.jim.wx.javamodule.javassist;


import com.google.common.base.Joiner;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.AttributeInfo;
import javassist.bytecode.Descriptor;
import javassist.bytecode.MethodInfo;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import me.jim.wx.javamodule.javassist.biz.Activity;
import me.jim.wx.javamodule.javassist.biz.ShadowActivity;

public class JavassitReader {
    public static void main(String[] args) throws CannotCompileException, IOException, NotFoundException {

        ClassPool cp = ClassPool.getDefault();
        CtClass ctClass = cp.makeClass(new FileInputStream("/Users/weixin/Documents/AwesomeBasicPractice/android/JavaModule/build/classes/java/main/me/jim/wx/javamodule/javassist/biz/Application.class"));
        CtMethod[] declaredMethods = ctClass.getDeclaredMethods();
        for (CtMethod ctMethod : declaredMethods) {
            for (AttributeInfo attribute : ctMethod.getMethodInfo2().getAttributes()) {

            }
//            boolean b = ctMethod.hasAnnotation(KeepForShadow.class.getTypeName());
//
//            if (b) {
//                renameMethod(ctMethod, cp);
//            }
        }
        ctClass.replaceClassName(Activity.class.getName(), ShadowActivity.class.getName());
        String directoryName = "/Users/weixin/Documents/AwesomeBasicPractice/android/JavaModule/build/classes/java/main/me/jim/wx/javamodule/javassist/biz/";
        ctClass.writeFile();
    }

    private static void renameMethod(CtMethod ctMethod, ClassPool classPool) throws NotFoundException, CannotCompileException, IOException {

        MethodInfo methodInfo2 = ctMethod.getMethodInfo2();

        List<AttributeInfo> attributes = methodInfo2.getAttributes();


        try {
            Class<? extends AttributeInfo> aClass = AttributeInfo.class;
            Method renameClassMethod = aClass.getDeclaredMethod("renameClass", List.class, String.class, String.class);
            renameClassMethod.setAccessible(true);
            renameClassMethod.invoke(null, attributes, ShadowActivity.class.getName(), Activity.class.getName());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        ctMethod.insertBefore("System.out.println($0);");
//
//        ctMethod.useCflow("fact");
//        ctMethod.insertBefore("if ($cflow(fact) == 0)"
//                + "    System.out.println(\"fact \" + $1);");


        ctMethod.instrument(new ExprEditor() {
            @Override
            public boolean doit(CtClass clazz, MethodInfo minfo) throws CannotCompileException {
                return super.doit(clazz, minfo);
            }

            @Override
            public void edit(MethodCall methodCall) throws CannotCompileException {
                super.edit(methodCall);

                String methodName = methodCall.getMethodName();
                String className = methodCall.getClassName();
                String signature = methodCall.getSignature();
                MethodInfo methodInfo2 = null;

                try {
                    methodInfo2 = methodCall.getMethod().getMethodInfo2();
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
                if (className.equals("me.jim.wx.javamodule.javassist.biz.AppManager")) {

                    boolean voidType =
                            false;
                    try {
                        voidType = Descriptor.getReturnType(signature, classPool) == CtClass.voidType;
                    } catch (NotFoundException e) {
                        e.printStackTrace();
                    }
//                    methodCall.replace(("$_="   + "me.jim.wx.javamodule.javassist.biz.AppManager2." + methodName + "($$);"));

                    /**  没用
                     if (methodInfo2 != null) {
                     System.out.println(methodInfo2.getDescriptor()); //()Lme/jim/wx/javamodule/javassist/biz/ShadowActivity;
                     methodInfo2.setDescriptor("()Lme/jim/wx/javamodule/javassist/biz/Activity;");
                     }
                     **/
                }



                try {
                    CtClass[] parameterTypes = Descriptor.getParameterTypes(signature, classPool);
                    if (parameterTypes != null) {

                        String[] paramArray = new String[parameterTypes.length];
                        for (int i = 0; i < parameterTypes.length; i++) {
                            paramArray[i] = "$" + i;
                        }

                        boolean isNeed = false;
                        for (int i = 0; i < parameterTypes.length; i++) {
                            String name = parameterTypes[i].getClassFile().getName();
                            System.out.println(name + ": " + methodName);
                            if (name.equals("me.jim.wx.javamodule.javassist.biz.ShadowActivity")) {
                                paramArray[i] = "me.jim.wx.javamodule.javassist.biz.AppManager2.getActivity()";
                                isNeed = true;
                            }
                        }

                        if (isNeed) {
                            String classWrapper = className + "Wrapper";
                            String method2 = methodName + "2";
                            String params = Joiner.on(',').join(paramArray);
                            String s1 = String.format("((%1s)$0).%2s( %3s);", classWrapper, method2, params);
                            System.out.println(className + " " + s1);
                            methodCall.replace(s1);
                        }
                    }

                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
            }
        });


    }


}
