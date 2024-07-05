package com.example.dacs3_fodr.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dacs3_fodr.Activity.CartActivity;
import com.example.dacs3_fodr.Activity.OrderWait;
import com.example.dacs3_fodr.Adapter.Cart;
import com.example.dacs3_fodr.Adapter.OrderAdapter;
import com.example.dacs3_fodr.History;
import com.example.dacs3_fodr.Order;
import com.example.dacs3_fodr.R;
import com.example.dacs3_fodr.databinding.ActivityOrderInfBinding;
import com.example.dacs3_fodr.databinding.ActivityShipInfBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShipInf extends AppCompatActivity {
    RecyclerView.Adapter adapter;
    private ArrayList<Cart> cartList;
    private String idOrder,phone,name,address,userID;
    private Double totalAmount;

    private ActivityShipInfBinding binding;
    private int status,idShop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShipInfBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initList();

        binding.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Orders");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<Cart> carts = new ArrayList<>();
                        ArrayList<Order> orders = new ArrayList<>();
                        for(DataSnapshot issue: dataSnapshot.getChildren()) {
                            Order order = issue.getValue(Order.class);
                            idOrder = order.getOrderID();
                            address = order.getAddress();
                            name = order.getName();
                            phone = order.getPhone();
                            totalAmount = order.getTotalAmount();
                            userID = order.getUserId();
                            cartList = (ArrayList<Cart>) order.getCartList();
                            status = order.getStatus();
                        }
                        if(orders.size()!=0){
                            AlertDialog.Builder builder = new AlertDialog.Builder(ShipInf.this);
                            builder.setTitle("Xác nhận")
                                    .setMessage("Chấp nhận đơn chứ?")
                                    .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Query query = databaseReference.orderByChild("orderID").equalTo(idOrder);
                                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    Toast.makeText(ShipInf.this,"Xác nhận thành công",Toast.LENGTH_SHORT).show();
                                                    Map<String, Object> updates = new HashMap<>();
                                                    updates.put("status",2);
                                                    DatabaseReference databaseReferenceUpdate = FirebaseDatabase.getInstance().getReference("Orders")
                                                            .child(idOrder);
                                                    databaseReferenceUpdate.updateChildren(updates);
                                                    History history = new History(userID,name,phone,address,cartList,totalAmount,1,idOrder);
                                                    DatabaseReference databaseReferenceHis = FirebaseDatabase.getInstance().getReference("History");
                                                    DatabaseReference childRefHis = databaseReferenceHis.push();
                                                    childRefHis.setValue(history, new DatabaseReference.CompletionListener() {
                                                        @Override
                                                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
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
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
//        binding.btnDone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Orders");
//                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        ArrayList<Cart> carts = new ArrayList<>();
//                        ArrayList<Order> orders = new ArrayList<>();
//                        for(DataSnapshot issue: dataSnapshot.getChildren()) {
//                            Order order = issue.getValue(Order.class);
//                            idOrder = order.getOrderID();
//                            address = order.getAddress();
//                            name = order.getName();
//                            phone = order.getPhone();
//                            totalAmount = order.getTotalAmount();
//                            userID = order.getUserId();
//                            cartList = (ArrayList<Cart>) order.getCartList();
//                            status = order.getStatus();
//                        }
//                        if(orders.size()!=0){
//                            AlertDialog.Builder builder = new AlertDialog.Builder(ShipInf.this);
//                            builder.setTitle("Xác nhận")
//                                    .setMessage("Đã giao xong?")
//                                    .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            Query query = databaseReference.orderByChild("orderID").equalTo(idOrder);
//                                            query.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                    Toast.makeText(ShipInf.this,"Xác nhận thành công",Toast.LENGTH_SHORT).show();
//                                                    Map<String, Object> updates = new HashMap<>();
//                                                    updates.put("status",3);
//                                                    DatabaseReference databaseReferenceUpdate = FirebaseDatabase.getInstance().getReference("Orders")
//                                                            .child(idOrder);
//                                                    databaseReferenceUpdate.updateChildren(updates);
//                                                    History history = new History(userID,name,phone,address,cartList,totalAmount,1,idOrder);
//                                                    DatabaseReference databaseReferenceHis = FirebaseDatabase.getInstance().getReference("History");
//                                                    DatabaseReference childRefHis = databaseReferenceHis.push();
//                                                    childRefHis.setValue(history, new DatabaseReference.CompletionListener() {
//                                                        @Override
//                                                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
//
//                                                        }
//                                                    });
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//                                        }
//                                    })
//                                    .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//
//                                        }
//                                    });
//                            AlertDialog alertDialog = builder.create();
//                            alertDialog.show();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }
//        });
    }
    private void initList(){
        binding.txtTitle.setText("Đơn hàng chờ giao");
        ArrayList<Cart> list = new ArrayList<>();
        binding.progressBar6.setVisibility(View.GONE);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Orders");
//        Query query = databaseReference.orderByChild("status").equalTo(0);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<Order> orders = new ArrayList<>();
                ArrayList<Cart> carts = new ArrayList<>();
                if(dataSnapshot.exists()){

                    for(DataSnapshot issue: dataSnapshot.getChildren()){
                        Cart cart = issue.getValue(Cart.class);
                        Order order = issue.getValue(Order.class);
                        orders.add(order);
                        carts = (ArrayList<Cart>) order.getCartList();
                        if(carts.size()>0){
//                           if(order.getStatus()==1||order.getStatus()==2){
                               binding.recyclerViewCartItems.setLayoutManager(new LinearLayoutManager(ShipInf.this));
                               adapter = new OrderAdapter(carts,ShipInf.this);
                               binding.recyclerViewCartItems.setAdapter(adapter);
                               adapter.notifyDataSetChanged();
                               if(order.getStatus()==1){
                                   binding.btnDone.setVisibility(View.GONE);
                                   binding.btnFinish.setVisibility(View.GONE);
                                   binding.btnAccept.setVisibility(View.VISIBLE);
                               }
                               else  if(order.getStatus()==2){
                                   binding.btnDone.setVisibility(View.VISIBLE);
                                   binding.btnAccept.setVisibility(View.GONE);
                                   binding.btnFinish.setVisibility(View.GONE);
                               }
                               else  if(order.getStatus()==3){
                                   binding.btnDone.setVisibility(View.GONE);
                                   binding.btnAccept.setVisibility(View.GONE);
                                   binding.btnFinish.setVisibility(View.VISIBLE);
                               }
                      //     }

                            //  Toast.makeText(OrderWait.this,"yẹt",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ShipInf.this,"lỗi rồi cụ",Toast.LENGTH_SHORT).show();
                        }
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