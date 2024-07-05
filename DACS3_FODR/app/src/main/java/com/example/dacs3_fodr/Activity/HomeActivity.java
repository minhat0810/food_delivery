package com.example.dacs3_fodr.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dacs3_fodr.Adapter.SearchFoodAdapter;
import com.example.dacs3_fodr.Foods;
import com.example.dacs3_fodr.LoginFor;
import com.example.dacs3_fodr.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dacs3_fodr.databinding.ActivityHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class HomeActivity extends AppCompatActivity {
    private TextView nameHome,emailHome;
    SearchFoodAdapter foodAdapter;
    RecyclerView EsearchView;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
    private NavigationView navigationView;
    private MenuItem item;
//    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mAuth = FirebaseAuth.getInstance();
        nameHome = findViewById(R.id.usernamehome);
        emailHome = findViewById(R.id.emailHome);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        FirebaseUser user = mAuth.getCurrentUser();
//        if(user==null){
//            Toast.makeText(this,"Hí",Toast.LENGTH_SHORT);
//        }
        setSupportActionBar(binding.appBarHome.toolbar);
        binding.appBarHome.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        nameHome = navigationView.getHeaderView(0).findViewById(R.id.usernamehome);
        emailHome = navigationView.getHeaderView(0).findViewById(R.id.emailHome);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_logout,R.id.nav_info)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id){
                    case R.id.nav_logout:
                           Toast.makeText(HomeActivity.this,"Đã đăng xuất",Toast.LENGTH_SHORT).show();
                           Handler handler = new Handler();
                           handler.postDelayed(new Runnable() {
                               @Override
                               public void run() {
                                   BackMainActivity();
                               }
                           },1000);
                           break;
                    case R.id.nav_info:
                        Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        break;
                   }
                    return true;


            }
        });
        showInfomation();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void showInfomation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        emailHome.setText("HÍ");
        if(user==null){
            return;
        }
        else{
            String name = user.getDisplayName();
            String email = user.getEmail();

           if(name==null){
               nameHome.setVisibility(View.GONE);
           }else{
               nameHome.setVisibility(View.VISIBLE);
               nameHome.setText(name);
           }
            emailHome.setText(email);

        }
    }
    private void BackMainActivity(){
      FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

//    private void initCategoty(){
//        FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = firebaseDatabase.getReference();
//        binding.
//    }
}