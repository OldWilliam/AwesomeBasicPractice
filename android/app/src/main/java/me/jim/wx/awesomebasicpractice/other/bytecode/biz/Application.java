//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.jim.wx.awesomebasicpractice.other.bytecode.biz;


import android.app.Activity;

public class Application {
    public Application() {
    }

    public void test(String a, int s, Activity activity) {
        IThirdPart thirdPartLib = createLib();
        thirdPartLib.bindActivity(activity);
        Object var7 = null;
    }

    private ThirdPartLib createLib() {
        return new ThirdPartLib();
    }

    public void test2() {
        ThirdPartLib thirdPartLib = createLib();
        Activity activity = AppManager.getActivity();
        thirdPartLib.bindActivity(activity);
    }

    public void process() {
        test("", 2, AppManager.getActivity());
    }
}
