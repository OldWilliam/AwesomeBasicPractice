package me.jim.wx.awesomebasicpractice;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;

import me.jim.wx.FragmentBinder;
import me.jim.wx.awesomebasicpractice.util.Utils;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Utils.setFullscreen(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
         */
        NavigationDrawerFragment mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                findViewById(R.id.drawer_layout));


    }

    private SparseArray<Fragment> fragments = new SparseArray<>();

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment frag = fragments.get(position);
        if (frag == null) {
            String className = FragmentBinder.ins().select(position);
            try {
                Class<?> fragClazz = Class.forName(className);
                frag = (Fragment) fragClazz.newInstance();
                fragments.put(position, frag);

            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        fragmentManager.beginTransaction()
                .replace(R.id.container, frag)
                .commit();
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        getSupportFragmentManager().getFragments().forEach(fragment -> fragment.onRequestPermissionsResult(requestCode, permissions, grantResults));
    }

    private static final String TAG = "MainActivity";

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent: " + event.getAction());
        return super.onTouchEvent(event);
    }
}
