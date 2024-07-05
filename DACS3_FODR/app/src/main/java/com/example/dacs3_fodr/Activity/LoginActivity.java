package com.example.dacs3_fodr.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.dacs3_fodr.Activity.ui.ShipActivity;
import com.example.dacs3_fodr.ChefActivity;
import com.example.dacs3_fodr.LoginFor;
import com.example.dacs3_fodr.R;
import com.example.dacs3_fodr.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private TextView btnSignUp;
    private String userId;
    private ImageButton btnBack;
    private EditText editEmail,editPass;

//    private ProgressDialog progressDialog;

    private EditText txtUser,txtPass;
//    private FirebaseAuth mAuth;
    private FirebaseAuth mAuth;

    public LoginActivity() {
    }

    @SuppressLint({"MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        mAuth = FirebaseAuth.getInstance();
        btnSignUp = findViewById(R.id.tvSignUp);
        btnLogin = findViewById(R.id.btnLogin);
       // btnBack = findViewById(R.id.btnBack);
        editEmail = findViewById(R.id.editEmailLg);
        editPass = findViewById(R.id.editPassLg);
//        progressDialog = new ProgressDialog(this);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpActivity();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLogin();
            }
        });
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MainActivity();
//            }
//        });
        getIdUser();


    }

    private void getIdUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            String userId = user.getUid();
           // Toast.makeText(LoginActivity.this,"hí",Toast.LENGTH_SHORT).show();
        }
    }
    private void SignUpActivity(){
        Intent i = new Intent(this, LoginFor.class);
        startActivity(i);
    }
    private void onClickLogin(){


        mAuth = FirebaseAuth.getInstance();
        String email = editEmail.getText().toString().trim();
        String password = editPass.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
//                                    Intent home = new Intent(LoginActivity.this, HomeActivity.class);
//                                    startActivity(home);
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
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
                                                            Intent home = new Intent(LoginActivity.this, HomeActivity.class);
                                                            startActivity(home);
                                                            break;
                                                        case "1":
                                                            Intent ship = new Intent(LoginActivity.this, ShipActivity.class);
                                                            startActivity(ship);
                                                            break;
                                                        case "2":
                                                            Intent chef = new Intent(LoginActivity.this, ChefActivity.class);
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
                            },1000);


                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu sai.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        userId = user.getUid();
//     //   Toast.makeText(LoginActivity.this,"userId",Toast.LENGTH_SHORT).show();
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
//        DatabaseReference childRes = databaseReference.push();
//        User adduser = new User(userId,"User",email,0,"Chưa có","Chưa có");
//        Log.d("User id: ",userId);
//        childRes.setValue(adduser, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
//             //   Toast.makeText(LoginActivity.this,"Thành công",Toast.LENGTH_SHORT).show();
//            }
//        });
    //   FirebaseAuth


    }
    private void MainActivity(){
        Intent ac = new Intent(this, MainActivity.class);
        startActivity(ac);
    }
    public Boolean validateUsername(){
        String val = txtUser.getText().toString();
        if(val.isEmpty()){
            txtUser.setError("Không được để trống tài khoản");
            return false;
        }else {
            txtUser.setError(null);
            return true;
        }
    }
    public Boolean validatePassword(){
        String val = txtPass.getText().toString();
        if(val.isEmpty()){
            txtPass.setError("Không được để trống mật khẩu");
            return false;
        }else {
            txtPass.setError(null);
            return true;
        }
    }
    public void check(){
        String username = txtUser.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    }
}