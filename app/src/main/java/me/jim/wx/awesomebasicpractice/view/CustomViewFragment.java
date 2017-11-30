package me.jim.wx.awesomebasicpractice.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.jim.wx.awesomebasicpractice.R;
import me.jim.wx.awesomebasicpractice.view.primary.FlowLayout;

/**
 * 自定义View练习
 */
public class CustomViewFragment extends Fragment {

    public static Fragment newInstance() {
        return new CustomViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_custom_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        testFlowLayout(view);
    }

    private void testFlowLayout(View view) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        FlowLayout layout = view.findViewById(R.id.flow_layout);
        for (int i = 0; i < 19; i++) {
            layout.addView(inflater.inflate(R.layout.item_flow, null), 0);
        }
        layout.setArrow(true);
    }


}
