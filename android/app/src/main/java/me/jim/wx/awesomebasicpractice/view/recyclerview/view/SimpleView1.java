package me.jim.wx.awesomebasicpractice.view.recyclerview.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.jim.wx.awesomebasicpractice.R;
import me.jim.wx.awesomebasicpractice.http.ResponseListener;
import me.jim.wx.awesomebasicpractice.view.recyclerview.layoutmanager.ABC1LayoutManager;
import me.jim.wx.awesomebasicpractice.view.recyclerview.layoutmanager.ABCLayoutManager;
import me.jim.wx.awesomebasicpractice.view.recyclerview.model.hero.BaseHeroBean;
import me.jim.wx.awesomebasicpractice.view.recyclerview.model.hero.HeroModel;

/**
 * Created by wx on 2018/1/2.
 */

public class SimpleView1 extends LinearLayout {
    private static final String TAG = "SimpleView1";
    public SimpleView1(Context context) {
        super(context);
        initView();
    }

    private RecyclerView recyclerView;
    private List<BaseHeroBean.AllHerosBean.AllHeroBean.ItemInfoBean> datas = new ArrayList<>();
    private MyAdapter mAdapter;

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.layout_rv_simple_view_1, this);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new ABC1LayoutManager(getContext()));
        mAdapter = new MyAdapter();
        recyclerView.setAdapter(mAdapter);

        HeroModel.getHeros(new ResponseListener<List<BaseHeroBean.AllHerosBean.AllHeroBean.ItemInfoBean>>() {
            @Override
            public void onResult(List<BaseHeroBean.AllHerosBean.AllHeroBean.ItemInfoBean> beans) {
                datas = beans;
                post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private class MyAdapter extends RecyclerView.Adapter<MyItemHolder> {

        @Override
        public MyItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple, parent, false));
        }

        @Override
        public void onBindViewHolder(MyItemHolder holder, int position) {
            holder.setData(datas.get(position));
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }

    private static int count = 0;

    private class MyItemHolder extends RecyclerView.ViewHolder {
        private BaseHeroBean.AllHerosBean.AllHeroBean.ItemInfoBean data;

        private TextView tvName;

        public MyItemHolder(View view) {
            super(view);
            tvName = (TextView) view;
            Log.d(TAG, "MyItemHolder: " + count++);
        }

        public void setData(BaseHeroBean.AllHerosBean.AllHeroBean.ItemInfoBean data) {
            this.data = data;
            tvName.setText(data.name);
        }
    }
}
