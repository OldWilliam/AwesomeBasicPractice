package me.jim.wx.annotationcompiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.sun.source.util.Trees;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeScanner;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import me.jim.wx.annotations.AutoCreateAnnotation;
import me.jim.wx.annotations.GenLayout;
import me.jim.wx.annotations.PrinterAnnotation;

import static com.google.auto.common.MoreElements.getPackage;

import static java.util.Objects.requireNonNull;

/**
 * Created by wx on 2018/5/2.
 */

@AutoService(Processor.class)//https://blog.csdn.net/github_35180164/article/details/52121038
public class AnnotationProcessorExample extends AbstractProcessor {

    private static final String PACKAGE_NAME = "me.jim.wx.awesomebasicpractice";
    private static final ClassName CONTEXT = ClassName.get("android.content", "Context");


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(PrinterAnnotation.class)) {
            if (element.getKind() == ElementKind.CLASS) {
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                System.out.println(element.getSimpleName().toString());
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
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

                JavaFile javaFile = JavaFile.builder(PACKAGE_NAME, helloWorld)
                        .build();

                try {
                    javaFile.writeTo(processingEnv.getFiler());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        trees = Trees.instance(processingEnv);

        for (Element element : roundEnv.getElementsAnnotatedWith(GenLayout.class)) {
            if (element.getKind() == ElementKind.FIELD) {
                GenLayout annotation = element.getAnnotation(GenLayout.class);
                int layoutRes = annotation.value();


                Element enclosingElement = element.getEnclosingElement();//上一层级的元素

                System.out.println(enclosingElement.toString());
                String name = enclosingElement.getSimpleName().toString();


                String layoutXMLName = elementToId(element, annotation.getClass(), layoutRes).simpleName.concat(".xml");
                String prefix = "./app/src/main/res/layout/";
                File file = new File(prefix.concat(layoutXMLName));

                List<Layout> layouts = new ArrayList<>();
                try {
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    XmlPullParser parser = factory.newPullParser();

                    parser.setInput(new FileReader(file));
                    int eventType = parser.getEventType();

                    while (eventType != XmlPullParser.END_DOCUMENT) {

                        switch (eventType) {

                            case XmlPullParser.START_TAG:
                                String nodeName = parser.getName();
                                if (nodeName != null) {

                                    Layout.Builder builder = new Layout.Builder(nodeName);

                                    for (int i = 0; i < parser.getAttributeCount(); i++) {
                                        String attributeValue = parser.getAttributeValue(i);
                                        String attributeName = parser.getAttributeName(i);
                                        if (attributeName.startsWith("android:")) {
                                            builder.buildAttr(attributeName.replace("android:", ""), attributeValue);
                                        }
                                    }

                                    Layout parent = null;
                                    int index = parser.getDepth() - 1;
                                    if (index > 0) {//0是根结点
                                        parent = layouts.get(index - 1);
                                    }

                                    Layout layout = builder.parent(parent).build();
                                    layouts.add(index, layout);
                                }
                                break;
                            default:
                                break;
                        }
                        eventType = parser.next();
                    }

                    String s = layouts.get(0).toString();
                    System.out.println(s);


                } catch (IOException | XmlPullParserException e) {
                    e.printStackTrace();
                }


                MethodSpec constructor = MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(TypeName.get(enclosingElement.asType()), "target")
                        .addParameter(CONTEXT, "context")
                        .addStatement(layouts.get(0).getStatement())
                        .addStatement("target." + element.getSimpleName().toString() + " = root")
                        .build();

                MethodSpec getContext = MethodSpec.methodBuilder("getContext")
                        .returns(CONTEXT)
                        .addStatement("return null")
                        .build();

                TypeSpec className = TypeSpec.classBuilder(name + "_Layout")
                        .addModifiers(Modifier.PUBLIC)
                        .addMethod(constructor)
                        .addMethod(getContext)
                        .build();


                String packageName = getPackage(enclosingElement).getQualifiedName().toString();
                ClassName bindingClassName = ClassName.get(packageName, name + "_Layout");

                JavaFile javaFile = JavaFile.builder(PACKAGE_NAME, className)
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

    private Trees trees;
    private final RScanner rScanner = new RScanner();

    private Id elementToId(Element element, Class<? extends Annotation> annotation, int value) {
        JCTree tree = (JCTree) trees.getTree(element, getMirror(element, annotation));
        if (tree != null) { // tree can be null if the references are compiled types and not source
            rScanner.reset();
            tree.accept(rScanner);
            if (!rScanner.resourceIds.isEmpty()) {
                return rScanner.resourceIds.values().iterator().next();
            }
        }
        return new Id(value);
    }

    private static class RScanner extends TreeScanner {
        Map<Integer, Id> resourceIds = new LinkedHashMap<>();

        @Override
        public void visitSelect(JCTree.JCFieldAccess jcFieldAccess) {
            Symbol symbol = jcFieldAccess.sym;

            if (symbol.getEnclosingElement() != null
                    && symbol.getEnclosingElement().getEnclosingElement() != null
                    && symbol.getEnclosingElement().getEnclosingElement().enclClass() != null) {
                try {
                    int value = (Integer) requireNonNull(((Symbol.VarSymbol) symbol).getConstantValue());
                    resourceIds.put(value, new Id(value, symbol));
                } catch (Exception ignored) {
                }
            }
        }

        @Override
        public void visitLiteral(JCTree.JCLiteral jcLiteral) {
            try {
                int value = (Integer) jcLiteral.value;
                resourceIds.put(value, new Id(value));
            } catch (Exception ignored) {
            }
        }

        void reset() {
            resourceIds.clear();
        }
    }

    private static AnnotationMirror getMirror(Element element,
                                              Class<? extends Annotation> annotation) {
        for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            if (annotationMirror.getAnnotationType().toString().equals(annotation.getCanonicalName())) {
                return annotationMirror;
            }
        }
        return null;
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
        annotations.add(GenLayout.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
