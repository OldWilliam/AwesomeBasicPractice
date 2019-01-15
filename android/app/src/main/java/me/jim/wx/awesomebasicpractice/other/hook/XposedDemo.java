package me.jim.wx.awesomebasicpractice.other.hook;

import android.widget.TextView;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Date: 2019/1/14
 * Name: wx
 * Description:
 */
public class XposedDemo implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        XposedBridge.log("handle loadpackage start");
        if (lpparam.packageName.equals("me.jim.wx.awesomebasicpractice")) {
            XposedBridge.log("hook start");

            XposedHelpers.findAndHookMethod(TextView.class, "setText", CharSequence.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    XposedBridge.log("before hook method");
                    param.args[0] = "hook" + param.args[0];
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    XposedBridge.log("after hook method");
                }
            });
        }
    }
}
