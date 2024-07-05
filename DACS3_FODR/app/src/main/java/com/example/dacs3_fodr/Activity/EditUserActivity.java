package com.example.dacs3_fodr.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dacs3_fodr.R;
import com.example.dacs3_fodr.User;
import com.example.dacs3_fodr.databinding.ActivityEditUserBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditUserActivity extends AppCompatActivity {
    private ActivityEditUserBinding binding;
    private String userId;
    private EditText email,username,phone,address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_edit_user);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        binding = ActivityEditUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        email = findViewById(R.id.tvtEmail);
        username = findViewById(R.id.tvtName);
        phone = findViewById(R.id.tvtPhone);
        address = findViewById(R.id.tvtAddress);
        initInformation();
        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   editInf();
            }
        });

    }
    private void initInformation(){
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
                        String name = user2.getName();
                        String address2 = user2.getAddress();
                        String sdt = user2.getPhone();
                        String email2 = user2.getEmail();



                        username.setText(name);
                        email.setText(email2);
                        address.setText(address2);
                        phone.setText(sdt);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void editInf() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        String nameUpdate = String.valueOf(username);
        String emailUpdate = String.valueOf(email);
        String phoneUpdate = String.valueOf(phone);
        String addressUpdate = String.valueOf(address);
        ArrayList<User> usersList = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
        Query query = databaseReference.orderByChild("idUser").equalTo(userId);

//        User user1 = new User(userId, nameUpdate, emailUpdate, 0, addressUpdate, phoneUpdate,);
////        String name = binding.tvtName.getText();
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot issue: dataSnapshot.getChildren()){
//                    databaseReference.setValue(user1, new DatabaseReference.CompletionListener() {
//                        @Override
//                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }
}