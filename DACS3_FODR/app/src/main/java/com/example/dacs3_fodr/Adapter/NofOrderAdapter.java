package com.example.dacs3_fodr.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dacs3_fodr.Activity.ShipInf;
import com.example.dacs3_fodr.Activity.ui.OrderInf;
import com.example.dacs3_fodr.Activity.ui.ShipActivity;
import com.example.dacs3_fodr.Order;
import com.example.dacs3_fodr.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NofOrderAdapter extends RecyclerView.Adapter<NofOrderAdapter.ViewHolder> {
    ArrayList<Order> orderList;
    ArrayList<Cart> cartList;
    private String titleO;
    Context context;
    private Double price0;
    private int quantity0;
    public NofOrderAdapter(ArrayList<Order> orderList,Context context) {

        //  this.orderList = orderList;
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_order,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orderList.get(position);
        //Cart cart = cartList.get(position);

//        holder.title.setText(cart.getTitle());
//        holder.quantity.setText(cart.getQuantity()+" ");
//        holder.price.setText(String.format("%,.0f VNĐ", cart.getPrice()*1000));
//        holder.adress.setText(cart.getAddressShop()+" ");
        holder.name.setText(order.getName());
        holder.address.setText(order.getAddress());
        holder.phone.setText(order.getPhone());
        holder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference childRef = FirebaseDatabase.getInstance().getReference("Orders");
                childRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<Cart> carts = new ArrayList<>();
                        ArrayList<Order> orders = new ArrayList<>();
                        for (DataSnapshot issue : dataSnapshot.getChildren()) {
                            Order order = issue.getValue(Order.class);
                            orders.add(order);
                            carts = (ArrayList<Cart>) order.getCartList();
                            //   Toast.makeText(getActivity(),idShopAd+"",Toast.LENGTH_SHORT).show();
                        }
                        if(orders.size()>0){
                            if(order.getStatus()==0){
                                Intent intent = new Intent(context, OrderInf.class);
                                context.startActivity(intent);
                            }
                            else if(order.getStatus()==1||order.getStatus()==2){
                                Intent intent = new Intent(context, ShipInf.class);
                                context.startActivity(intent);
                            }
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

//        Glide.with(context)
//                .load(cart.getImage())
//                .into(holder.image);
//        CartAdapter cartAdapter = new CartAdapter((ArrayList<Cart>) cartList);
//        holder.cartRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//        holder.cartRecyclerView.setAdapter(cartAdapter);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //        TextView orderId, status;
        // Other views
        TextView name,address,phone;
        RecyclerView cartRecyclerView;
        AppCompatButton btnView;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            title = itemView.findViewById(R.id.tvtTitleOrder);
//            quantity = itemView.findViewById(R.id.tvtQuantityOrder);
//            adress = itemView.findViewById(R.id.tvtAddressShopOrder);
//            price = itemView.findViewById(R.id.tvtPriceOrder);
//            image = itemView.findViewById(R.id.imageOrder);
            name = itemView.findViewById(R.id.tvtNameUser);
            address = itemView.findViewById(R.id.tvtAddressUser);
            phone = itemView.findViewById(R.id.tvtPhoneUser);
            btnView = itemView.findViewById(R.id.btnView);
            //   cartRecyclerView = itemView.findViewById(R.id.recyclerViewCartItems);
            // Initialize other views
        }
    }
}

