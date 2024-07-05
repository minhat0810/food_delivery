package com.example.dacs3_fodr;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.dacs3_fodr.Activity.HomeActivity;
import com.example.dacs3_fodr.Activity.LoginActivity;
import com.example.dacs3_fodr.Activity.ProfileActivity;
import com.example.dacs3_fodr.ui.dashboard.DashboardFragment;
import com.example.dacs3_fodr.ui.home.HomeChefFragment;
import com.example.dacs3_fodr.ui.home.HomeFragment;
import com.example.dacs3_fodr.ui.info.InfoChef;
import com.example.dacs3_fodr.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.dacs3_fodr.databinding.ActivityChefBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class ChefActivity extends AppCompatActivity {

    private ActivityChefBinding binding;
    private AppBarConfiguration appBarConfiguration;
    private FrameLayout frameLayout;
    private  BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_information)
//                .build();
        binding = ActivityChefBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
         loadFragment(new HomeChefFragment() );
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.navigation_home:
                    loadFragment(new HomeChefFragment());
                    break;
                case R.id.navigation_dashboard:
                    loadFragment(new DashboardFragment());
                    break;
                case R.id.navigation_notifications:
                    loadFragment(new NotificationsFragment());
                    break;
                case R.id.navigation_infomation:
                    loadFragment(new InfoChef());
                    break;

            }
            return true;
        });
    }
      //  navigationView = binding.bottomNavigation;
//            navigationView = findViewById(R.id.bottomNavigation);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_chef);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
//        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                int id = menuItem.getItemId();
//                switch (id){
//                    case R.id.navigation_information:
//                        Toast.makeText(ChefActivity.this,"Hí",Toast.LENGTH_SHORT).show();
//                        break;
//                }
//                return true;
//            }
//        });


    private void loadFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        if(isAppInitialized){
            fragmentTransaction.replace(R.id.frame_layout,fragment);
//        }

        fragmentTransaction.commit();
    }
}