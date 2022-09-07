package me.jim.wx.awesomebasicpractice;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import me.jim.wx.FragmentBinder;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setFullscreen();

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

    public void setFullscreen() {

            int uiFlags = 0; // hide status bar
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        //                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        //                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN;
            }
            if (android.os.Build.VERSION.SDK_INT >= 19) {
                uiFlags |= 0x00001000;    //SYSTEM_UI_FLAG_IMMERSIVE_STICKY: hide navigation bars - compatibility: building API level is lower thatn 19, use magic number directly for higher API target level
            } else {
                uiFlags |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
            }
            getWindow().getDecorView().setSystemUiVisibility(uiFlags);

            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏s
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
