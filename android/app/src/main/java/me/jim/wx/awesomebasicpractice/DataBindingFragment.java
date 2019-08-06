package me.jim.wx.awesomebasicpractice;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.jim.wx.awesomebasicpractice.databinding.FragmentDataBindingBinding;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DataBindingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DataBindingFragment extends Fragment {

    public DataBindingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DataBindingFragment.
     */
    public static DataBindingFragment newInstance() {
        DataBindingFragment fragment = new DataBindingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentDataBindingBinding dataBinding = FragmentDataBindingBinding.inflate(inflater, container, false);
        HelloViewModel viewModel = new HelloViewModel("");
        viewModel.doReadFile(getActivity());
        dataBinding.setViewModel(viewModel);
        return dataBinding.getRoot();
    }

}
