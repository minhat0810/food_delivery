package com.example.dacs3_fodr.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.dacs3_fodr.Adapter.Cart;
import com.example.dacs3_fodr.Adapter.CartAdapter;
import com.example.dacs3_fodr.Foods;
import com.example.dacs3_fodr.R;
import com.example.dacs3_fodr.databinding.ActivityDetailFactivityBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailFActivity extends AppCompatActivity {
    private ActivityDetailFactivityBinding binding;
   // ArrayList<Foods> items;
    private Foods object;
    private String userId;
    private int categoryId;
    private int productId;
    int quantity=1  ;
    Double subTotal;
    private int num = 1;
   // public DetailFActivity(ArrayList<Foods> items){
//        this.items = items;
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_detail_factivity);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        binding = ActivityDetailFactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));

        getIntentExtra();
        setVariable();
        //addCart();

    }
    private void getIdUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            String userId = user.getUid();
            Toast.makeText(DetailFActivity.this,userId,Toast.LENGTH_SHORT).show();
        }
    }
    private void addCart(){
//        binding.addbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DetailFActivity.this,CartActivity.class);
//
//            }
//        });
    }
    private void setVariable(){
        binding.btnBack.setOnClickListener(v->finish());
        ArrayList<Cart> carts = new ArrayList<>();

        Glide.with(DetailFActivity.this)
                .load(object.getImagePath())
                .into(binding.imageFood);

        //binding.tvtID.setText(""+object.getId());
        binding.tvtPrice.setText(""+object.getPrice()+"00VNĐ");
        binding.tvtTitle.setText(object.getTitle());
//        binding.tvtAdress.setText(object.getAddress());
        binding.tvtDetail.setText(object.getDescription());
        binding.ratingBar.setRating((float) object.getStar());
        binding.tvtTotal.setText((num*object.getPrice()+"00VNĐ"));
        binding.tvtAdress.setText(object.getAddress());
        categoryId = object.getCategoryId();
        productId = object.getId();
        binding.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Cart");
                String cartID = databaseReference.push().getKey();
            //    Toast.makeText(DetailFActivity.this,""+cartID,Toast.LENGTH_SHORT).show();

                DatabaseReference childRef = databaseReference.push();
                String id = childRef.getKey();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                 userId  = user.getUid();


                if(quantity==1){
                    subTotal = object.getPrice();
                }
                Cart cart = new Cart(id,userId,object.getImagePath(),object.getPrice(),quantity,object.getTitle(),subTotal,productId,categoryId,object.getAddress(),object.getIdShop());
                childRef.setValue(cart, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        Toast.makeText(DetailFActivity.this,"Thêm thành công",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        binding.tangSL.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                num += 1;
                binding.tvtNum.setText(num+" ");
                binding.tvtTotal.setText(num*object.getPrice()+"00VNĐ");
                quantity = num;
                subTotal = quantity*object.getPrice();
            }

        });


        binding.giamSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(num>1){
                    num -= 1;
                    binding.tvtNum.setText(num+" ");
                    binding.tvtTotal.setText(num*object.getPrice()+"00VNĐ");
                    quantity = num;
                    subTotal = quantity*object.getPrice();
                }
            }
        });
        binding.btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(DetailFActivity.this,HomeActivity.class);
            startActivity(intent);
        });

    }
    private void  getIntentExtra(){
            object = (Foods) getIntent().getSerializableExtra("object");
    }
}