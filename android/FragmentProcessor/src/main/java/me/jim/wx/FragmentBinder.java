package me.jim.wx;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2019-10-09
 * Name: wx
 * Description:
 */
public class FragmentBinder {
    private static final FragmentBinder ourInstance = new FragmentBinder();
    public static final String FRAGMENT_BINDER_IMPL = "FragmentBinderImpl";
    public static final String ME_JIM_WX = "me.jim.wx";

    private ArrayList names;
    private ArrayList frags;

    public static FragmentBinder ins() {
        return ourInstance;
    }

    private FragmentBinder() {
        try {
            Class<?> clazz = Class.forName(ME_JIM_WX.concat(".").concat(FRAGMENT_BINDER_IMPL));
            Object o = clazz.newInstance();

            Field namesField = clazz.getDeclaredField("names");
            names = (ArrayList) namesField.get(o);

            Field fragsField = clazz.getDeclaredField("frags");
            frags = (ArrayList) fragsField.get(o);

        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public String select(int position) {
        return (String) frags.get(position);
    }

    public List getItems() {
        return names;
    }
}
