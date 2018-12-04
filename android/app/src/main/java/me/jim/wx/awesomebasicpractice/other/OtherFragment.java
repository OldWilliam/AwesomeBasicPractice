package me.jim.wx.awesomebasicpractice.other;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import me.jim.wx.annotations.MyAnnotation;
import me.jim.wx.awesomebasicpractice.R;
import me.jim.wx.awesomebasicpractice.other.annotation.AnnotationManager;

/**
 * 其他类型
 */
@MyAnnotation
public class OtherFragment extends Fragment {

    public static OtherFragment newInstance() {
        return new OtherFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_other, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.tv_annotation_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), AnnotationManager.ins().getClassAnnotation(AnnotationManager.class), Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.tv_annotation_method).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), AnnotationManager.ins().getMethodAnnotation(AnnotationManager.class.getName()), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
