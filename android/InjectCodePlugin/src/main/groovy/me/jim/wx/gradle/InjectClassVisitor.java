/**
 * http://www.wangyuwei.me/2017/03/05/ASM%E5%AE%9E%E6%88%98%E7%BB%9F%E8%AE%A1%E6%96%B9%E6%B3%95%E8%80%97%E6%97%B6/#more
 */
package me.jim.wx.gradle;


import net.bytebuddy.jar.asm.ClassVisitor;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import net.bytebuddy.jar.asm.commons.AdviceAdapter;

/**
 * Date: 2019/1/10
 * Name: wx
 * Description:
 */
public class InjectClassVisitor extends ClassVisitor {
    public InjectClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM6, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);

        return new AdviceAdapter(Opcodes.ASM6, methodVisitor, access, name, desc) {
            @Override
            protected void onMethodEnter() {
                super.onMethodEnter();

                if (name.equalsIgnoreCase("onCreateView")) {
                    methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                    methodVisitor.visitLdcInsn("========start=========");
                }
            }

            @Override
            protected void onMethodExit(int opcode) {
                super.onMethodExit(opcode);
            }
        };
    }
}
