package me.jim.wx.awesomebasicpractice.basiccamera;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import me.jim.wx.awesomebasicpractice.R;
import me.jim.wx.awesomebasicpractice.widget.RecordButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoverFragment extends Fragment {

    private TextView tvDoc;

    public CoverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cover, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvDoc = view.findViewById(R.id.tv_text);
        tvDoc.setMovementMethod(ScrollingMovementMethod.getInstance());
    }
}
