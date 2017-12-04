package me.jim.wx.awesomebasicpractice.rxjava.abc;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by wx on 2017/12/4.
 */

public class LessonThree {
    private static final LessonThree ourInstance = new LessonThree();
    private static final String TAG = "LessonThree";

    public static LessonThree ins() {
        return ourInstance;
    }

    private LessonThree() {
    }

    /**
     * skip、skipLast
     */
    public void caseFive() {
        Observable.interval(1, TimeUnit.SECONDS)
                /**
                 * 跳过前n项数据
                 */
                .skip(6)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d(TAG, "accept: Operation skip result: " + aLong.toString());
                    }
                });

        Observable.range(0, 20)
                /**
                 * 跳过后n项数据
                 */
                .skipLast(8)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept: Operation skipLast result: " + integer.toString());

                    }
                });
    }

    /**
     * take、takeLast、takeUtil、takeWhile
     */
    public void caseFour() {
        Observable.range(0, 20)
                //前10个
                .take(10)
                //前10个中的后2个
                .takeLast(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept: Operation simple take result: " + integer.toString());
                    }
                });

        Observable.range(0, 20)
                /**
                 * 满足条件停止发送
                 * result：0，1，2，3
                 */
                .takeUntil(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer == 3;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept: Operation takeUtil result: " + integer);
                    }
                });

        Observable.range(0, 20)
                /**
                 * 满足条件才发送
                 * result：0，1，2
                 */
                .takeWhile(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer < 3;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept: Operation takeWhile result: " + integer);
                    }
                });
    }

    /**
     * mergeWith（无序组合）、concatWith（顺序组合）、zipWith（聚合）
     * <p>
     * 等同于merge、concat、zip
     */
    public void caseThree() {
        caseOne().mergeWith(caseTwo()).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "accept: Operation mergeWith result: " + integer.toString());
            }
        });

        caseOne().concatWith(caseTwo()).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "accept: Operation concatWith result: " + integer.toString());
            }
        });

        caseOne()
                .zipWith(caseTwo(), new BiFunction<Integer, Integer, String>() {
                    @Override
                    public String apply(Integer integer, Integer integer2) throws Exception {
                        return integer.toString() + " " + integer2.toString();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d(TAG, "accept: Operation zipWith result: " + s);
                    }
                });
    }

    /**
     * flatmap（flat之后不保证拆分后的顺序，要保证顺序用concatMap）、distinct（过滤重复项）
     */
    public Observable<Integer> caseTwo() {
        return Observable.range(0, 25)
                .flatMap(new Function<Integer, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(Integer integer) throws Exception {
                        return Observable.range(0, integer);
                    }
                })
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept: Operation flatmap result: " + integer.toString());
                    }
                })
                //去重之后只剩下0，1，2
                .distinct()
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept: After distinct result: " + integer.toString());
                    }
                });
    }

    /**
     * map、filter
     */
    public Observable<Integer> caseOne() {
        return Observable.range(0, 25)
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        return integer * integer;
                    }
                })
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept: Operation map result: " + integer);
                    }
                })
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer % 2 == 0;
                    }
                })
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept: Operation filter result: " + integer);
                    }
                });
    }
}
