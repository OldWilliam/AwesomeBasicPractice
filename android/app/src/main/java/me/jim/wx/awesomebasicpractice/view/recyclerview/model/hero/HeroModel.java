package me.jim.wx.awesomebasicpractice.view.recyclerview.model.hero;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import me.jim.wx.awesomebasicpractice.http.ResponseListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wx on 2017/12/13.
 */

public class HeroModel {
    private static final String TAG = "HeroModel";

    public void getHeros(final ResponseListener<List<BaseHeroBean.AllHerosBean.AllHeroBean.ItemInfoBean>> responseListener) {
        String url = "http://service.inke.com//api//game_business//game_hero?type=all";
        OkHttpClient mClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Gson gson = new Gson();
                final BaseHeroBean bean = gson.fromJson(s, BaseHeroBean.class);
                final List<BaseHeroBean.AllHerosBean.AllHeroBean.ItemInfoBean> beans = new ArrayList<>();
                Observable
                        .fromArray(bean.all_heros.all_hero.toArray())
                        .map(new Function<Object, BaseHeroBean.AllHerosBean.AllHeroBean>() {

                            @Override
                            public BaseHeroBean.AllHerosBean.AllHeroBean apply(Object o) throws Exception {
                                return (BaseHeroBean.AllHerosBean.AllHeroBean) o;
                            }
                        })
                        .flatMap(new Function<BaseHeroBean.AllHerosBean.AllHeroBean, ObservableSource<Object>>() {
                            @Override
                            public ObservableSource<Object> apply(BaseHeroBean.AllHerosBean.AllHeroBean allHeroBean) throws Exception {
                                return Observable.fromArray(allHeroBean.item_info.toArray());
                            }
                        })
                        .map(new Function<Object, BaseHeroBean.AllHerosBean.AllHeroBean.ItemInfoBean>() {
                            @Override
                            public BaseHeroBean.AllHerosBean.AllHeroBean.ItemInfoBean apply(Object o) throws Exception {
                                return (BaseHeroBean.AllHerosBean.AllHeroBean.ItemInfoBean) o;
                            }
                        })
                       .subscribe(new Observer<BaseHeroBean.AllHerosBean.AllHeroBean.ItemInfoBean>() {
                           @Override
                           public void onSubscribe(Disposable d) {
                               beans.clear();
                           }

                           @Override
                           public void onNext(BaseHeroBean.AllHerosBean.AllHeroBean.ItemInfoBean itemInfoBean) {
                               beans.add(itemInfoBean);
                           }

                           @Override
                           public void onError(Throwable e) {

                           }

                           @Override
                           public void onComplete() {

                           }
                       });
                Log.d(TAG, "onResponse: ");
                responseListener.onResult(beans);
            }
        });
    }
}
