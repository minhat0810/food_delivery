package com.example.dacs3_fodr.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.dacs3_fodr.Activity.HomeActivity;
import com.example.dacs3_fodr.Activity.LoginActivity;
import com.example.dacs3_fodr.Activity.ui.ShipActivity;
import com.example.dacs3_fodr.ChefActivity;
import com.example.dacs3_fodr.LoginFor;
import com.example.dacs3_fodr.R;
import com.example.dacs3_fodr.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

// ...
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
//        mAuth = FirebaseAuth.getInstance();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }
        },2000);
    }
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
////        FirebaseUser currentUser = mAuth.getCurrentUser();
//
//    }
    private void nextActivity(){
        FirebaseUser user = mAuth.getCurrentUser();

        if(user==null){
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
        }else{


            userId = user.getUid();
            ArrayList<User> users = new ArrayList<>();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
            Query query = databaseReference.orderByChild("idUser").equalTo(userId);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot issue: dataSnapshot.getChildren()){
                        User user1 = issue.getValue(User.class);
                        users.add(user1);
                    }
                    if(users.size()>0){
                        for (User user2: users){
                            String role = String.valueOf(user2.getRole());
                            //Toast.makeText(LoginActivity.this,""+role,Toast.LENGTH_SHORT).show();
                            switch (role){
                                case "0":
                                    Intent home = new Intent(SplashActivity.this, HomeActivity.class);
                                    startActivity(home);
                                    break;
                                case "1":
                                    Intent ship = new Intent(SplashActivity.this, ShipActivity.class);
                                    startActivity(ship);
                                    break;
                                case "2":
                                    Intent chef = new Intent(SplashActivity.this, ChefActivity.class);
                                    startActivity(chef);
                                    break;
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}