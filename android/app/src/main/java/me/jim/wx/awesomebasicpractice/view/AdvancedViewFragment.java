package me.jim.wx.awesomebasicpractice.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.jim.wx.awesomebasicpractice.R;
import me.jim.wx.awesomebasicpractice.http.ResponseListener;
import me.jim.wx.awesomebasicpractice.view.recyclerview.model.hero.BaseHeroBean;
import me.jim.wx.awesomebasicpractice.view.recyclerview.model.hero.HeroModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdvancedViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdvancedViewFragment extends Fragment {
    private static final String TAG = "AdvancedViewFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public AdvancedViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdvancedViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdvancedViewFragment newInstance(String param1, String param2) {
        AdvancedViewFragment fragment = new AdvancedViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_advanced_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HeroModel model = new HeroModel();
        model.getHeros(new ResponseListener<List<BaseHeroBean.AllHerosBean.AllHeroBean.ItemInfoBean>>() {
            @Override
            public void onResult(List<BaseHeroBean.AllHerosBean.AllHeroBean.ItemInfoBean> beans) {
                for (int i = 0; i < beans.size(); i++) {
                    Log.d(TAG, "onResult: " + beans.get(i).name);
                }
            }
        });
    }
}


