package com.example.dacs3_fodr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dacs3_fodr.Activity.SignUpActivity;
import com.example.dacs3_fodr.Activity.SignUpForCheef;
import com.example.dacs3_fodr.Activity.SignUpForShip;
import com.example.dacs3_fodr.databinding.ActivityLoginForBinding;

public class LoginFor extends AppCompatActivity {
    private Button signupForChef,signupForUser,signupShip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_for);
        signupForChef = findViewById(R.id.lgChef);
        signupForUser = findViewById(R.id.lgUser);
        signupShip = findViewById(R.id.lgShipper);
        signupForUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginFor.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        signupForChef.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(LoginFor.this, SignUpForCheef.class);
              startActivity(intent);
          }
      });
        signupShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginFor.this, SignUpForShip.class);
                startActivity(intent);
            }
        });

    }
}