package me.jim.wx.fragmentprocessor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;

import me.jim.wx.fragmentannotation.AttachFragment;


@SuppressWarnings("unused")
@AutoService(Processor.class)
public class FragmentProcessor extends AbstractProcessor {


    public FragmentProcessor() {
        System.out.println("TestAnnotationProcessor constrator");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {


        FieldSpec nameField = FieldSpec.builder(ClassName.get(ArrayList.class).box(), "names", Modifier.PUBLIC, Modifier.STATIC).build();
        FieldSpec fragField = FieldSpec.builder(ClassName.get(ArrayList.class).box(), "frags", Modifier.PUBLIC, Modifier.STATIC).build();

        CodeBlock.Builder blockBuilder = CodeBlock.builder();
        blockBuilder.addStatement("names = new ArrayList<String>()");
        blockBuilder.addStatement("frags = new ArrayList<String>()");

        for (Element element : roundEnv.getElementsAnnotatedWith(AttachFragment.class)) {
            // 判断元素的类型为Class
            if (element.getKind() == ElementKind.CLASS) {
                // 显示转换元素类型
                TypeElement typeElement = (TypeElement) element;
                // 输出元素名称
                Name qualifiedName = typeElement.getQualifiedName();
                // 输出注解属性值
                String name = typeElement.getAnnotation(AttachFragment.class).value();

                blockBuilder.addStatement("frags.add($S)", qualifiedName);
                blockBuilder.addStatement("names.add($S)", name);
            }
        }

        TypeSpec typeSpec = TypeSpec.classBuilder("FragmentBinderImpl")
                .addModifiers(Modifier.PUBLIC)
                .addField(nameField)
                .addField(fragField)
                .addStaticBlock(blockBuilder.build())
                .build();

        JavaFile javaFile = JavaFile.builder("me.jim.wx", typeSpec)
                .build();

        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException ignored) {

        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        LinkedHashSet<String> annotations = new LinkedHashSet<>();
        annotations.add(AttachFragment.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
