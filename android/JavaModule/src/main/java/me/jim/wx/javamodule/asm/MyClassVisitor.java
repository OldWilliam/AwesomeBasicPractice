package me.jim.wx.javamodule.asm;

import net.bytebuddy.jar.asm.ClassVisitor;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;

public class MyClassVisitor extends ClassVisitor {
    public MyClassVisitor(ClassVisitor api) {
        super(Opcodes.ASM6, api);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        System.out.println(name);
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals("test")) {
            return new MyMethodVisitor(api, methodVisitor, access, name, desc, signature, exceptions);
        }
        return methodVisitor;
    }

    private class MyMethodVisitor extends MethodVisitor {
        public MyMethodVisitor(int api, MethodVisitor methodVisitor, int access, String name, String desc, String signature, String[] exceptions) {
            super(api, methodVisitor);
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
            System.out.println(owner + " " + name + " " + desc);

            if (desc.equals("(Lme/jim/wx/javamodule/javassist/biz/ShadowActivity;)V")) {
                desc = "(Lme/jim/wx/javamodule/javassist/biz/Activity;)V";
            }

            super.visitMethodInsn(opcode, owner, name, desc, itf);
        }
    }
}
