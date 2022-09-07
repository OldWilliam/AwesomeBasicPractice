package me.jim.wx.awesomebasicpractice.other;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.koin.core.context.KoinContextHandler;

import java.util.Collections;

import me.jim.wx.awesomebasicpractice.R;
import me.jim.wx.awesomebasicpractice.other.annotation.AnnotationManager;
import me.jim.wx.awesomebasicpractice.other.di.DiApplication;
import me.jim.wx.awesomebasicpractice.other.di.HelloDIKt;
import me.jim.wx.fragmentannotation.AttachFragment;

/**
 * 其他类型
 */
@AttachFragment("其他")
public class OtherFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KoinContextHandler.INSTANCE.get().loadModules(Collections.singletonList(HelloDIKt.getHelloModule()));
        new DiApplication().sayHello();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        KoinContextHandler.INSTANCE.get().unloadModules(Collections.singletonList(HelloDIKt.getHelloModule()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_other, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.tv_annotation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), AnnotationManager.ins().getPreamble(AnnotationManager.class), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
