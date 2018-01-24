package me.jim.wx.awesomebasicpractice.rxjava.abc;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by wx on 2017/12/1.
 *
 * 入门
 */

public class LessonOne {
    private static final String TAG = "LessonOne";
    private static final LessonOne ourInstance = new LessonOne();

    public static LessonOne ins() {
        return ourInstance;
    }

    private LessonOne() {
    }



    /**
     * http://www.jianshu.com/p/464fa025229e
     * <p>
     * 上游发送时间是不受下游影响的；
     * 下游在接收onError和onComplete事件后不再接受事件；
     * 上游不可发送两个onError事件，会导致崩溃
     */
    public void caseOne() {
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
        Observable.just(1, 2, 3, 4, 5)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept: " + integer.intValue());
                    }
                });
    }

    /**
     * empty不能调到onNext，直接通知onComplete
     */
    public void caseEmpty() {
        Observable.empty().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                Log.d(TAG, "onNext: Empty");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: Empty");
            }
        });
    }
}
