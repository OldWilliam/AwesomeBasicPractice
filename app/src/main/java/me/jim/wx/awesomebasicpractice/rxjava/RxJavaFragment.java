package me.jim.wx.awesomebasicpractice.rxjava;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.jim.wx.awesomebasicpractice.R;

/**
 * 练习RxJava
 */
public class RxJavaFragment extends Fragment {

    public static RxJavaFragment newInstance() {
        RxJavaFragment fragment = new RxJavaFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rx_java, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        main();
    }

    private void main() {

    }
}
