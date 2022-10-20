package me.jim.wx.awesomebasicpractice.compose;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.jim.wx.fragmentannotation.AttachFragment;

@AttachFragment("Compose")
public class ComposeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new CallToActionViewButton(container.getContext(), null, 0);
    }
}
