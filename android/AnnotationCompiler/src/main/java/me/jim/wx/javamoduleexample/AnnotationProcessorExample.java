package me.jim.wx.javamoduleexample;

import com.google.auto.service.AutoService;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

import me.jim.wx.annotations.MyAnnotation;

/**
 * Created by wx on 2018/5/2.
 */

@AutoService(Processor.class)//https://blog.csdn.net/github_35180164/article/details/52121038
public class AnnotationProcessorExample extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element: roundEnv.getElementsAnnotatedWith(MyAnnotation.class)) {
            if (element.getKind() == ElementKind.CLASS) {
                System.out.printf("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                System.out.printf(element.getSimpleName().toString());
                System.out.printf("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
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
        annotations.add(MyAnnotation.class.getCanonicalName());
        return annotations;
    }
}
