package me.jim.wx.awesomebasicpractice.other.aspect;

import android.widget.Toast;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import me.jim.wx.awesomebasicpractice.ContextHelper;

/**
 * Created by wx on 2018/4/21.
 * <p>
 * 拦截onClick事件
 */

@Aspect
public class ClickAspect {

    /**
     * 切点：执行注解
     */
    @Pointcut("execution(@me.jim.wx.awesomebasicpractice.other.aspect.VisitorAnnotation * *(..))")
    public void triggerByAnnotation() {
    }

    /**
     * 切点：返回值为void的，方法名为onClick的，参数为一个切类型为View
     */
    @Pointcut("execution(void onClick(android.view.View))")
    public void triggerByClick() {

    }

    /**
     * 指定类或者包名
     */
    @Pointcut("within(me.jim.wx.awesomebasicpractice.view.*)")
    public void withPackage(){

    }

    /**
     * 可以使用逻辑运算符，&&、||、！等
     */
    @Around("triggerByClick() && triggerByAnnotation() && withPackage()")
    public Object test(ProceedingJoinPoint joinPoint) throws Throwable {
        String clzName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Toast.makeText(ContextHelper.getContext(), clzName + ": " + methodName, Toast.LENGTH_SHORT).show();
        return joinPoint.getTarget();
    }
}
