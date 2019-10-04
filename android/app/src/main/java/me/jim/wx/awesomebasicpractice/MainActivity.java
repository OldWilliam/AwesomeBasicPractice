package me.jim.wx.awesomebasicpractice;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

import java.util.function.Consumer;

import me.jim.wx.awesomebasicpractice.graphic.GraphicFragment;
import me.jim.wx.awesomebasicpractice.other.OtherFragment;
import me.jim.wx.awesomebasicpractice.recyclerview.RecyclerViewFragment;
import me.jim.wx.awesomebasicpractice.rxjava.RxJavaFragment;
import me.jim.wx.awesomebasicpractice.view.NavigationDrawerFragment;
import me.jim.wx.awesomebasicpractice.view.PrimaryViewFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (position == 0) {
//            startActivity(new Intent(this, MyReactActivity2.class));
        } else if (position == 1) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, RecyclerViewFragment.newInstance())
                    .commit();
        } else if (position == 2) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, PrimaryViewFragment.newInstance())
                    .commit();
        } else if (position == 3) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, GraphicFragment.newInstance())
                    .commit();
        } else if (position == 4) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, RxJavaFragment.newInstance())
                    .commit();
        } else if (position == 5) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, OtherFragment.newInstance())
                    .commit();
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        getSupportFragmentManager().getFragments().stream().forEach(new Consumer<Fragment>() {
            @Override
            public void accept(Fragment fragment) {
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        });
    }

    private static final String TAG = "MainActivity";

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent: " + event.getAction());
        return super.onTouchEvent(event);
    }
}
