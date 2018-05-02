package me.jim.wx.annotationcompiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import me.jim.wx.annotations.AutoCreateAnnotation;
import me.jim.wx.annotations.PrinterAnnotation;

/**
 * Created by wx on 2018/5/2.
 */

@AutoService(Processor.class)//https://blog.csdn.net/github_35180164/article/details/52121038
public class AnnotationProcessorExample extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element: roundEnv.getElementsAnnotatedWith(PrinterAnnotation.class)) {
            if (element.getKind() == ElementKind.CLASS) {
                System.out.printf("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                System.out.printf(element.getSimpleName().toString());
                System.out.printf("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            }
        }

        for (Element element : roundEnv.getElementsAnnotatedWith(AutoCreateAnnotation.class)) {
            if (element.getKind() == ElementKind.CLASS) {
                MethodSpec main = MethodSpec.methodBuilder("main")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .returns(void.class)
                        .addParameter(String[].class, "args")
                        .addStatement("$T.out.println($S)", System.class, "Hello, ".concat(element.getSimpleName().toString()).concat("!"))
                        .build();

                TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .addMethod(main)
                        .build();

                JavaFile javaFile = JavaFile.builder("me.jim.wx.awesomebasicpractice", helloWorld)
                        .build();

                try {
                    javaFile.writeTo(processingEnv.getFiler());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * 注册支持的注解
     * 必须复写
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(PrinterAnnotation.class.getCanonicalName());
        annotations.add(AutoCreateAnnotation.class.getCanonicalName());
        return annotations;
    }
}
