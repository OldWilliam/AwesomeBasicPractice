package me.jim.wx.awesomebasicpractice.compose;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.jim.wx.fragmentannotation.AttachFragment;

@AttachFragment("Compose")
public class ComposeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull @androidx.annotation.NonNull LayoutInflater inflater, @Nullable @androidx.annotation.Nullable ViewGroup container, @Nullable @androidx.annotation.Nullable Bundle savedInstanceState) {
        return new CallToActionViewButton(container.getContext(), null, 0);
    }
}
