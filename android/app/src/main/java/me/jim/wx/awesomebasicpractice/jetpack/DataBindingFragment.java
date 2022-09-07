package me.jim.wx.awesomebasicpractice.jetpack;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.jim.wx.fragmentannotation.AttachFragment;
import me.jim.wx.awesomebasicpractice.databinding.FragmentDataBindingBinding;


@AttachFragment("DataBinding")
public class DataBindingFragment extends Fragment {

    public DataBindingFragment() {
        // Required empty public constructor
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
