package me.jim.wx.awesomebasicpractice.rxjava.abc;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jim.wx.awesomebasicpractice.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wx on 2017/12/1.
 *
 * 线程切换
 */

public class LessonTwo {
    private static final LessonTwo ourInstance = new LessonTwo();
    private static String TAG = "LessonTwo";

    public static LessonTwo ins() {
        return ourInstance;
    }

    private LessonTwo() {
    }

    /**
     * 没有subscribeOn和observeOn 默认都是在当前线程
     */
    public void caseOne() {
        Runnable r = new Runnable() {
            public void run() {
                Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        Log.d(TAG, "subscribe: Observable thread is : " + Thread.currentThread().getName());
                        e.onNext(1);
                    }
                });
                observable.subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept: Observer thread is : " + Thread.currentThread().getName());
                    }
                });
            }
        };
        new Thread(r).start();
    }

    /**
     * 不同线程调度
     * subscribeOn 对上游（被观察者）起作用，多次调用只会有第一次起作用
     * observeOn 对下游（观察者）起作用，多次调用每次都会起作用
     */
    public void caseTwo() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.d(TAG, "subscribe: Observable thread is : " + Thread.currentThread().getName());
                e.onNext(1);
            }
        });

        Consumer consumer = new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                Log.d(TAG, "accept: Observer thread is : " + Thread.currentThread().getName());
            }
        };

        observable
                //有用，使得上游被观察者在computation线程，也使得后续的直到observeOn的流操作都是在computation线程
                .subscribeOn(Schedulers.computation())
                //没用，对上下都没有影响
                .subscribeOn(Schedulers.newThread())
                //在computation线程，不受上面的subscribeOn影响，但受第一个subscribeOn
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept: Between subscribeOn and observableOn " + Thread.currentThread().getName());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                //在mainThread
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept: After observeOn mainThread " + Thread.currentThread().getName());
                    }
                })
                .observeOn(Schedulers.newThread())
                //在newThread
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept: After observeOn newThread " + Thread.currentThread().getName());
                    }
                })
                //消费者在newThread
                .subscribe(consumer);
    }

    public Observable<Api.LoginResponse> caseThree() {
        Api api = create().create(Api.class);
        Api.LoginRequest request = new Api.LoginRequest();
        return api.login(request);
    }


    /**
     * 创建一个Retrofit客户端
     */
    private static Retrofit create() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(10, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }
        return new Retrofit.Builder().baseUrl("http://www.google.com")
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * 需要直接在工作线程的操作，只起到切换线程的作用
     */
    public Observable<String> caseFour() {
        return readFile();
    }

    private Observable<String> readFile() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                String result = internalReadFile();
                e.onNext(result);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io());
    }

    private String internalReadFile() throws InterruptedException {
        Thread.sleep(10000);
        return "Read File Success!";
    }

    /**
     * 只有subscribeOn,会影响之前和之后的
     */
    public void caseFive() {
        Observable
                //在ioThread
                .create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(ObservableEmitter<Object> e) throws Exception {
                        Log.d("five", "subscribe: Observable thread is " + Thread.currentThread().getName());
                        e.onNext(new Object());
                    }
                })
                .subscribeOn(Schedulers.io())
                //在ioThread
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.d("five", "accept: Consumer thread is " + Thread.currentThread().getName());
                    }
                });
    }

    /**
     * 只有observeOn，只会影响之后的
     */
    public void caseSix() {
        Observable
                //在mainThread
                .create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(ObservableEmitter<Object> e) throws Exception {
                        Log.d("six", "subscribe: Observable thread is " + Thread.currentThread().getName());
                        e.onNext(new Object());
                    }
                })
                .observeOn(Schedulers.io())
                //在ioThread
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.d("six", "accept: Consumer thread is " + Thread.currentThread().getName());
                    }
                });
    }
}



