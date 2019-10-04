package me.jim.wx.awesomebasicpractice.recyclerview.model.hero;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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

    public static OkHttpClient.Builder getUnsafeOkHttpClient(OkHttpClient client) {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = client.newBuilder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void getHeros(final ResponseListener<List<BaseHeroBean.AllHerosBean.AllHeroBean.ItemInfoBean>> responseListener) {
        String url = "https://service.inke.com//api//game_business//game_hero?type=all";
        OkHttpClient mClient = new OkHttpClient();
        OkHttpClient.Builder builder = getUnsafeOkHttpClient(mClient);
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = builder.build().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: ");
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
