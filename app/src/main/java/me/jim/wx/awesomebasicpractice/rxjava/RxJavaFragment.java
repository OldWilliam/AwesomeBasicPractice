package me.jim.wx.awesomebasicpractice.rxjava;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.jim.wx.awesomebasicpractice.R;

/**
 * 练习RxJava
 */
public class RxJavaFragment extends Fragment {
    private String TAG = "RxJavaFragment";

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
        lessonOne();
    }

    /**
     * http://www.jianshu.com/p/464fa025229e
     *
     * 上游发送时间是不受下游影响的；
     * 下游在接收onError和onComplete事件后不再接受事件；
     * 上游不可发送两个onError事件，会导致崩溃
     */

    private void lessonOne() {
        TAG = "lessonOne";
        //simple
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(0);
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            private Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
                mDisposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: " + integer.toString());
                if (integer.intValue() == 2) {
                    //取消订阅的意思
                    mDisposable.dispose();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });

        //consumer, only care onNext
        Observable.just(1,2,3,4,5)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept: "+integer.intValue());
                    }
                });
    }
}
