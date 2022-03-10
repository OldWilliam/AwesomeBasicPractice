//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.jim.wx.javamodule.javassist.biz;

import me.jim.wx.javamodule.javassist.KeepForShadow;

public class Application {
    public Application() {
    }

    public static void main(String[] args) {
        Application application = new Application();
        application.test("", 2, AppManager.getActivity());
    }

    @KeepForShadow
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
}
