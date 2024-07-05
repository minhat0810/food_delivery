package com.example.dacs3_fodr.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dacs3_fodr.Activity.HomeActivity;
import com.example.dacs3_fodr.Activity.LoginActivity;
import com.example.dacs3_fodr.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Button btnLogin1;
    private Button btnSignUp;
    private FirebaseAuth mAuth;
    private Button btnFb;
//    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin1 = findViewById(R.id.btnlogin);
        btnFb = findViewById(R.id.btnloginFB);
        btnLogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginActivity();
            }
        });
//        FirebaseUser user = mAuth.getCurrentUser();
//        if(user==null){
//            Intent i =  new Intent(this,LoginActivity.class);
//            startActivity(i);
//        }else{
//            Intent i =  new Intent(this,HomeActivity.class);
//            startActivity(i);
//        }
//        btnSignUp = findViewById(R.id.btnSignUp);
        btnFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeActivity();
            }
        });
    }
    public void loginActivity(){
        Intent i =  new Intent(this, LoginActivity.class);
        startActivity(i);
    }
    public void homeActivity(){
        Intent i =  new Intent(this, HomeActivity.class);
        startActivity(i);
    }
}