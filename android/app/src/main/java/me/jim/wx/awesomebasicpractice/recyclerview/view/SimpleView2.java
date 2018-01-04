package me.jim.wx.awesomebasicpractice.recyclerview.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import me.jim.wx.awesomebasicpractice.R;
import me.jim.wx.awesomebasicpractice.http.ResponseListener;
import me.jim.wx.awesomebasicpractice.recyclerview.layoutmanager.ABC1LayoutManager;
import me.jim.wx.awesomebasicpractice.recyclerview.layoutmanager.HexagonLayoutManager;
import me.jim.wx.awesomebasicpractice.view.primary.HexagonImageView;
import me.jim.wx.awesomebasicpractice.recyclerview.layoutmanager.ABCLayoutManager;
import me.jim.wx.awesomebasicpractice.recyclerview.model.hero.BaseHeroBean;
import me.jim.wx.awesomebasicpractice.recyclerview.model.hero.HeroModel;

/**
 * Created by wx on 2018/1/3.
 */

public class SimpleView2 extends LinearLayout {
    private List<BaseHeroBean.AllHerosBean.AllHeroBean.ItemInfoBean> data = new ArrayList<>();

    public SimpleView2(Context context) {
        super(context);
        initView();
    }

    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private HexagonLayoutManager mLayout;

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_rv_simple_view_2, this);
        recyclerView = findViewById(R.id.rv);
        mAdapter = new MyAdapter();
        recyclerView.setAdapter(mAdapter);
        mLayout = new HexagonLayoutManager();
        recyclerView.setLayoutManager(mLayout);

        HeroModel.getHeros(new ResponseListener<List<BaseHeroBean.AllHerosBean.AllHeroBean.ItemInfoBean>>() {
            @Override
            public void onResult(List<BaseHeroBean.AllHerosBean.AllHeroBean.ItemInfoBean> beans) {
                SimpleView2.this.data = beans;
                post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        findViewById(R.id.btn_simple).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setLayoutManager(new ABCLayoutManager(getContext()));
            }
        });

        findViewById(R.id.btn_simple_opt).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setLayoutManager(new ABC1LayoutManager(getContext()));
            }
        });

        findViewById(R.id.btn_circle).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setLayoutManager(new HexagonLayoutManager());
            }
        });

        findViewById(R.id.btn_grid).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            }
        });

        findViewById(R.id.btn_stag).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL));
            }
        });
    }

    private class MyAdapter extends RecyclerView.Adapter<MyItemViewHolder>{
        @Override
        public MyItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyItemViewHolder(new HexagonImageView(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(MyItemViewHolder holder, int position) {
            holder.setData(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    private class MyItemViewHolder extends RecyclerView.ViewHolder{
        private HexagonImageView imageView;
        public MyItemViewHolder(View itemView) {
            super(itemView);
            imageView = (HexagonImageView) itemView;
            imageView.setLayoutParams(new ViewGroup.LayoutParams(dip2px(48), dip2px(48)));
        }

        public void setData(BaseHeroBean.AllHerosBean.AllHeroBean.ItemInfoBean data) {
            Glide.with(getContext()).load(data.image_url).into(imageView);
        }
    }

    public int dip2px(float dp) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (scale * dp + 0.5f);
    }
}
