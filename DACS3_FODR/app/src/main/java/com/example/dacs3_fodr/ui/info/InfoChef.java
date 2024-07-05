package com.example.dacs3_fodr.ui.info;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dacs3_fodr.Activity.HomeActivity;
import com.example.dacs3_fodr.Activity.LoginActivity;
import com.example.dacs3_fodr.R;
import com.example.dacs3_fodr.databinding.FragmentInfoChefBinding;
import com.example.dacs3_fodr.databinding.FragmentNotificationsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class InfoChef extends Fragment {
    private FragmentInfoChefBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentInfoChefBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Đã đăng xuất",Toast.LENGTH_SHORT).show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BackMainActivity();
                    }
                },1000);

            }
        });

//        final TextView textView = binding.textNotifications;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
    private void BackMainActivity(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

}