package com.example.dacs3_fodr.Activity.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dacs3_fodr.Activity.HomeActivity;
import com.example.dacs3_fodr.Activity.LoginActivity;
import com.example.dacs3_fodr.Adapter.Cart;
import com.example.dacs3_fodr.Adapter.NofOrderAdapter;
import com.example.dacs3_fodr.Order;
import com.example.dacs3_fodr.R;
import com.example.dacs3_fodr.User;
import com.example.dacs3_fodr.databinding.ActivityShipBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShipActivity extends AppCompatActivity {
    private ActivityShipBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_ship2);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        binding = ActivityShipBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initCus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.homeship,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.home){
//            Intent intent = new Intent()
        }
        if(id==R.id.logout){
//            Intent intent = new Intent()
            //Toast.makeText(ShipActivity.this, "hi", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(ShipActivity.this);
            builder.setTitle("Xác nhận")
                    .setMessage("Xác nhận đăng xuất?")
                    .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(ShipActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                    })
                    .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        return super.onOptionsItemSelected(item);
        //
//        if (id == R.id.shipping) {
//            // Handle settings action
//            return true;
//        } else if (id == R.id.action_about) {
//            // Handle about action
//            return true;
//        }
    }
    private void initCus(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    DatabaseReference shopRef = FirebaseDatabase.getInstance().getReference("Orders");
//                    Query query1 = shopRef.orderByChild("status").equalTo(1);
                    shopRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ArrayList<Cart> carts = new ArrayList<>();
                            ArrayList<Order> orders = new ArrayList<>();
                            for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                Order order = issue.getValue(Order.class);
                                orders.add(order);
                                carts = (ArrayList<Cart>) order.getCartList();
                                if (orders.size() > 0) {
                                    if(order.getStatus()==1||order.getStatus()==2) {
                                        binding.rvOrder.setLayoutManager(new LinearLayoutManager(ShipActivity.this));
                                        RecyclerView.Adapter adapter = new NofOrderAdapter(orders, ShipActivity.this);
                                        binding.rvOrder.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    }

                                }
                                //   Toast.makeText(getActivity(),idShopAd+"",Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
    }
}