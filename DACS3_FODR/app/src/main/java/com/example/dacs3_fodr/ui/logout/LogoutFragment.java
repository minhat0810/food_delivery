package com.example.dacs3_fodr.ui.logout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dacs3_fodr.databinding.FragmentLogoutBinding;


public class LogoutFragment extends Fragment {

    private FragmentLogoutBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        LogoutViewModel logoutViewModel =
//                new ViewModelProvider(this).get(LogoutViewModel.class);

        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textSlideshow;
//        logoutViewModel
//                .getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }



    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}