package me.jim.wx.awesomebasicpractice.recyclerview.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.jim.wx.awesomebasicpractice.R;
import me.jim.wx.awesomebasicpractice.http.ResponseListener;
import me.jim.wx.awesomebasicpractice.recyclerview.layoutmanager.ABC1LayoutManager;
import me.jim.wx.awesomebasicpractice.recyclerview.anim.DefaultItemAnimator;
import me.jim.wx.awesomebasicpractice.recyclerview.decoration.SimpleDecor;
import me.jim.wx.awesomebasicpractice.recyclerview.model.hero.BaseHeroBean;
import me.jim.wx.awesomebasicpractice.recyclerview.model.hero.HeroModel;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MyAdapter();
        recyclerView.setAdapter(mAdapter);

        fetchData();

        SimpleDecor decor = new SimpleDecor();
        Drawable drawable = getContext().getResources().getDrawable(R.drawable.simple_decor);
        decor.setDrawable(drawable);
        recyclerView.addItemDecoration(decor);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        findViewById(R.id.btn_add).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData();
            }
        });
    }

    private void fetchData() {
        HeroModel.getHeros(new ResponseListener<List<BaseHeroBean.AllHerosBean.AllHeroBean.ItemInfoBean>>() {
            @Override
            public void onResult(List<BaseHeroBean.AllHerosBean.AllHeroBean.ItemInfoBean> beans) {
                datas = beans;
                post(new Runnable() {
                    @Override
                    public void run() {
                        datas.get(1).name = "卫鑫";
                        mAdapter.notifyItemChanged(1);
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
        }

        public void setData(BaseHeroBean.AllHerosBean.AllHeroBean.ItemInfoBean data) {
            this.data = data;
            tvName.setText(data.name);
        }
    }
}
