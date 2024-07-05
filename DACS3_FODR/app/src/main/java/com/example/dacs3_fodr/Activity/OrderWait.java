package com.example.dacs3_fodr.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dacs3_fodr.Adapter.Cart;
import com.example.dacs3_fodr.Adapter.CartAdapter;
import com.example.dacs3_fodr.Adapter.OrderAdapter;
import com.example.dacs3_fodr.Order;
import com.example.dacs3_fodr.R;
import com.example.dacs3_fodr.User;
import com.example.dacs3_fodr.databinding.ActivityOrderWaitBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderWait extends AppCompatActivity {

    private String userId;
    private ActivityOrderWaitBinding binding;
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    RecyclerView.Adapter adapter;
    private OrderAdapter orderAdapter;
    private ArrayList<Order> orderList;
    private ArrayList<Cart> cartList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderWaitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        orderList = new ArrayList<>();
//        orderAdapter = new OrderAdapter(orderList);
//        recyclerView.setAdapter(orderAdapter);

       // loadOrders();
        initList();
        recyclerView = findViewById(R.id.recyclerViewCartItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartList = new ArrayList<>();
        orderAdapter = new OrderAdapter(cartList,this);
//        cartAdapter = new OrderAdapter(cartList, this);
        recyclerView.setAdapter(cartAdapter);
        }
    private void loadOrders() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
       // ArrayList<Order> o
        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("Orders");
            Query query = orderRef.orderByChild("userId").equalTo(userId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderList.clear();
               if(dataSnapshot.exists()){
//                   ArrayList<Order>
//                   for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                       Order order = snapshot.getValue(Order.class);
//                       orderList.add(order);
//                   } if(or)
//                   if(dataSnapshot.exists()){
//                       for(DataSnapshot issue: dataSnapshot.getChildren()) {
//                           Order order = issue.getValue(Order.class);
//                           orders.add(order);
//                       }
//                       if(orders.size()>0){
//                           binding.tvtAdress.setText(order.getAddress());
//                           binding.tvtName.setText(order.getName());
//                           binding.tvtSDT.setText(order.getPhone());
//                           binding.textView.setVisibility(View.GONE);
//                           binding.progressBar6.setVisibility(View.GONE);
                     //  }
                 //  }
               }
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(OrderWait.this, "Error loading orders", Toast.LENGTH_SHORT).show();
            }
        });
    }





private void initList(){
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Orders");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    userId  = user.getUid();

    Query query = databaseReference.orderByChild("userId").equalTo(userId);

    binding.txtTitle.setText("Đơn hàng của bạn");
    ArrayList<Cart> list = new ArrayList<>();
    binding.progressBar6.setVisibility(View.GONE);
    //binding.progressBar6.setVisibility(View.VISIBLE);

    query.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            ArrayList<Order> orders = new ArrayList<>();
            ArrayList<Cart> carts = new ArrayList<>();
            if(dataSnapshot.exists()){

                for(DataSnapshot issue: dataSnapshot.getChildren()){
                    Cart cart = issue.getValue(Cart.class);
                    Order order = issue.getValue(Order.class);
                    orders.add(order);
                    if(order.getStatus()==1||order.getStatus()==2){
                        binding.btnDelete.setVisibility(View.GONE);
                    }
                    carts = (ArrayList<Cart>) order.getCartList();
                    if(carts.size()>0){
                        binding.recyclerViewCartItems.setLayoutManager(new LinearLayoutManager(OrderWait.this));
                        adapter = new OrderAdapter(carts,OrderWait.this);
                        binding.recyclerViewCartItems.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                       //  Toast.makeText(OrderWait.this,"yẹt",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(OrderWait.this,"lỗi rồi cụ",Toast.LENGTH_SHORT).show();
                    }
                    if (order!=null){
                        cartList.addAll(order.getCartList());
                        if(order.getStatus() == 0){
                            binding.btnAccept.setVisibility(View.GONE);
//                            binding.btnDelete.setVisibility(View.GONE);
                        }else if(order.getStatus() == 1){
                            binding.btnAccept.setVisibility(View.VISIBLE);
                            binding.btnWait.setVisibility(View.GONE);
                        }
                        else if(order.getStatus() == 2){
                            binding.btnAccept.setText("Chờ giao hàng");
                            binding.btnWait.setVisibility(View.GONE);
                        }
                    }else {
                        Toast.makeText(OrderWait.this,"lỗi rồi cụ",Toast.LENGTH_SHORT).show();
                    }
                   // orderAdapter.notifyDataSetChanged();
                    binding.tvtTotal.setText(String.format("%,.0f VNĐ", order.getTotalAmount()*1000));
                    binding.tvtAdress.setText(order.getAddress());
                    binding.tvtName.setText(order.getName());
                    binding.tvtSDT.setText(order.getPhone());
                    binding.textView.setVisibility(View.GONE);
                    binding.progressBar6.setVisibility(View.GONE);
                }

            }else{
                binding.progressBar6.setVisibility(View.GONE);
                binding.textView.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
}

}