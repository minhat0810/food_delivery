package com.example.dacs3_fodr.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dacs3_fodr.Category;
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

public class SignUpForCheef extends AppCompatActivity {
    private EditText signupAddress,signupemail,signupPhone,signuppassword,editShopName;
    private Button btnSignUp;
    private DatabaseReference databaseReferenceS;
    private ArrayList<User> listShop = new ArrayList<>();
    private int idShop = 0;
    private Button btnBack;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_for_cheef);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        signupemail = findViewById(R.id.editEmail);
//        signupusername = findViewById(R.id.editAcc);
        signuppassword = findViewById(R.id.editPass);
        signupAddress = findViewById(R.id.editAddress);
        signupPhone = findViewById(R.id.editphone);
        btnSignUp = findViewById(R.id.btnSignUp);
        editShopName = findViewById(R.id.editShopName);
        databaseReferenceS = FirebaseDatabase.getInstance().getReference("User");
        fetchUser();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSignUp();
            }
        });
    }
    private void fetchUser() {
        Query query = databaseReferenceS.orderByChild("IdShop");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listShop.clear();
                for (DataSnapshot issue : dataSnapshot.getChildren()) {
                    User user = issue.getValue(User.class);
                    listShop.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
    private void onClickSignUp(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String user = signupemail.getText().toString().trim();
        String password = signuppassword.getText().toString().trim();
        String address = signupAddress.getText().toString().trim();
        String phone = signupPhone.getText().toString().trim();
        String shopName = editShopName.getText().toString().trim();
//        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(user, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (listShop.size() > 0) {
                            idShop = listShop.size() + 1;
                        } else {
                            idShop = 1;
                        }
                        FirebaseUser userAuth = task.getResult().getUser();
                        String uid = userAuth.getUid();
                        Toast.makeText(SignUpForCheef.this,"Đăng kí thành công",Toast.LENGTH_SHORT).show();
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
                        DatabaseReference childRes = databaseReference.push();
                        User adduser = new User(uid,"Cheef",user,2,address,phone,shopName,idShop);
                        childRes.setValue(adduser, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                Toast.makeText(SignUpForCheef.this,"Thành công",Toast.LENGTH_SHORT).show();
                            }
                        });
//                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent ac = new Intent(SignUpForCheef.this, LoginActivity.class);
                                    startActivity(ac);
                                }
                            },1000);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpForCheef.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//            DatabaseReference iduser = FirebaseDatabase.getInstance().getReference("User");
//            iduser.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    String value = dataSnapshot.getValue(String.class);
//                    Toast.makeText(SignUpActivity.this,value,Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });


    }
}