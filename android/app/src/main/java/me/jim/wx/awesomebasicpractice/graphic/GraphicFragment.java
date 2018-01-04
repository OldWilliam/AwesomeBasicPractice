package me.jim.wx.awesomebasicpractice.graphic;


import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.List;

import me.jim.wx.awesomebasicpractice.R;
import me.jim.wx.awesomebasicpractice.http.ResponseListener;
import me.jim.wx.awesomebasicpractice.recyclerview.model.hero.BaseHeroBean;
import me.jim.wx.awesomebasicpractice.recyclerview.model.hero.HeroModel;
import me.jim.wx.awesomebasicpractice.view.primary.CircleImageView;
import me.jim.wx.awesomebasicpractice.view.primary.HexagonImageView;

/**
 * 图形相关
 */
public class GraphicFragment extends Fragment {
    public static GraphicFragment newInstance() {
        GraphicFragment fragment = new GraphicFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_graphic, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LinearLayout ll = view.findViewById(R.id.ll_screen);
        final HexagonImageView imageView = new HexagonImageView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(160, 160);
        params.gravity = Gravity.CENTER;
        ll.addView(imageView, params);
        HeroModel.getHeros(new ResponseListener<List<BaseHeroBean.AllHerosBean.AllHeroBean.ItemInfoBean>>() {
            @Override
            public void onResult(final List<BaseHeroBean.AllHerosBean.AllHeroBean.ItemInfoBean> itemInfoBeans) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        Glide.with(getContext()).load(itemInfoBeans.get(0).image_url).into(imageView);
                    }
                };
                imageView.post(runnable);
            }
        });
    }
}
