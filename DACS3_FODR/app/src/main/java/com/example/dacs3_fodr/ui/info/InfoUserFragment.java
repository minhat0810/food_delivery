package com.example.dacs3_fodr.ui.info;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dacs3_fodr.R;
import com.example.dacs3_fodr.databinding.FragmentInfoUserBinding;
import com.example.dacs3_fodr.databinding.FragmentLogoutBinding;


public class InfoUserFragment extends Fragment {

    private FragmentInfoUserBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentInfoUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textSlideshow;
//        logoutViewModel
//                .getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
}