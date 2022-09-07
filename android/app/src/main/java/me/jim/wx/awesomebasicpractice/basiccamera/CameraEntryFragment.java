package me.jim.wx.awesomebasicpractice.basiccamera;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import me.jim.wx.fragmentannotation.AttachFragment;

/**
 * 相机入口
 */
@AttachFragment("相机")
public class CameraEntryFragment extends Fragment {


    public CameraEntryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getContext(), CameraActivity.class);
        startActivity(intent);
    }


}
