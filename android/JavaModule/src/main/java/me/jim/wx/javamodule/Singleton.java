package me.jim.wx.javamodule;

/**
 * Date: 2019/7/24
 * Name: wx
 * Description: 单例模式：双重校验锁
 */
@SuppressWarnings("unused")
class Singleton {
    //变量私有
    private static volatile Singleton instance;

    //构造方法私有
    private Singleton() {

    }

    @SuppressWarnings("WeakerAccess")
    public static Singleton getInstance() {
        if (instance == null) {
            //锁class对象
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
