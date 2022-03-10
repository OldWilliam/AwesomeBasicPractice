package me.jim.wx.javamodule.asm;

import net.bytebuddy.jar.asm.ClassReader;
import net.bytebuddy.jar.asm.ClassWriter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ASMReader {
    public static void main(String[] args) throws IOException {
        String file = "/Users/weixin/Documents/AwesomeBasicPractice/android/me/jim/wx/javamodule/javassist/biz/Application.class";
        ClassReader cr = new ClassReader(new FileInputStream(file));
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);

        cr.accept(new MyClassVisitor(cw),ClassReader.EXPAND_FRAMES);

        byte[] bytes = cw.toByteArray();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes);
        fos.close();
    }
}
