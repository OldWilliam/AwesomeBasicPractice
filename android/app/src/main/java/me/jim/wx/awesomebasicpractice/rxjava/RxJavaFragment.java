package me.jim.wx.awesomebasicpractice.rxjava;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import me.jim.wx.awesomebasicpractice.R;
import me.jim.wx.awesomebasicpractice.rxjava.abc.LessonOne;
import me.jim.wx.awesomebasicpractice.rxjava.abc.LessonThree;
import me.jim.wx.awesomebasicpractice.rxjava.abc.LessonTwo;

/**
 * 练习RxJava
 */
public class RxJavaFragment extends Fragment {
    private String TAG = "RxJavaFragment";

    private TextView tvDisplay;

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
        tvDisplay = view.findViewById(R.id.tv_display);
        main();
    }

    private void main() {
        LessonThree.ins().caseSeven();
        LessonThree.ins().caseSix();
        LessonThree.ins().caseFive();
        LessonThree.ins().caseFour();
        LessonThree.ins().caseThree();
        LessonThree.ins().caseTwo().subscribe();
        LessonThree.ins().caseOne().subscribe();
        LessonTwo.ins().caseOne();
        LessonTwo.ins().caseTwo();
        LessonTwo.ins().caseFour()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Context context = getContext();
                        if (context != null) {
                            Toast.makeText(null, s, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        LessonTwo.ins().caseFive();
        LessonTwo.ins().caseSix();
        LessonOne.ins().caseEmpty();
    }
}
