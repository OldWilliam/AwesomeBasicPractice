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

    private ArrayList names;
    private ArrayList frags;

    public static FragmentBinder ins() {
        return ourInstance;
    }

    private FragmentBinder() {
        try {
            Class<?> clazz = Class.forName("me.jim.wx".concat(".").concat("FragmentBinderImpl"));
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
