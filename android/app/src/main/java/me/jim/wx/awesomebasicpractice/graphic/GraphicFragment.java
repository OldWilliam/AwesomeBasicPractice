package me.jim.wx.awesomebasicpractice.graphic;


import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
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

    private RecyclerView rvDrawable;
    private SimpleAdapter drawableAdapter;

    private RecyclerView rvAnim;
    private SimpleAdapter animAdapter;

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

        rvDrawable = view.findViewById(R.id.rv_drawable);
        rvDrawable.setLayoutManager(new LinearLayoutManager(getContext()));
        drawableAdapter = new SimpleAdapter();
        rvDrawable.setAdapter(drawableAdapter);
        drawableAdapter.setData(Arrays.asList(getContext().getResources().getStringArray(R.array.drawable_item)));

        rvAnim = view.findViewById(R.id.rv_anim);
        rvAnim.setLayoutManager(new LinearLayoutManager(getContext()));
        animAdapter = new SimpleAdapter();
        rvAnim.setAdapter(animAdapter);
        animAdapter.setData(Arrays.asList(getContext().getResources().getStringArray(R.array.anim_item)));
    }

    private class SimpleAdapter extends RecyclerView.Adapter {
        private List<String> texts = new ArrayList<>();
        @Override
        public SimpleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SimpleHolder(new TextView(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            SimpleHolder h = (SimpleHolder) holder;
            h.data(texts.get(position));
        }

        @Override
        public int getItemCount() {
            return texts.size();
        }

        public void setData(List<String> texts) {
            this.texts = texts;
            notifyDataSetChanged();
        }
    }

    private class SimpleHolder extends RecyclerView.ViewHolder {
        private TextView tvText;

        public SimpleHolder(View itemView) {
            super(itemView);
            tvText = (TextView) itemView;
        }

        public void data(String s) {
            tvText.setText(s);
        }
    }
}
