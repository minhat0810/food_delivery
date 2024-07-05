package com.example.dacs3_fodr.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dacs3_fodr.Order;
import com.example.dacs3_fodr.R;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
     List<Order> orderList;
     ArrayList<Cart> cartList;
     private String titleO;
     Context context;
     private Double price0;
     private int quantity0;
    public OrderAdapter(ArrayList<Cart> cartListList,Context context) {

      //  this.orderList = orderList;
        this.cartList = cartListList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.order_wait,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Order order = orderList.get(position);
        Cart cart = cartList.get(position);

        holder.title.setText(cart.getTitle());
        holder.quantity.setText(cart.getQuantity()+" ");
        holder.price.setText(String.format("%,.0f VNĐ", cart.getPrice()*1000));
        holder.adress.setText(cart.getAddressShop()+" ");

        Glide.with(context)
                .load(cart.getImage())
                .into(holder.image);
//        CartAdapter cartAdapter = new CartAdapter((ArrayList<Cart>) cartList);
//        holder.cartRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//        holder.cartRecyclerView.setAdapter(cartAdapter);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView orderId, status;
        // Other views
        TextView title,quantity,price,adress;
        RecyclerView cartRecyclerView;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvtTitleOrder);
            quantity = itemView.findViewById(R.id.tvtQuantityOrder);
            adress = itemView.findViewById(R.id.tvtAddressShopOrder);
            price = itemView.findViewById(R.id.tvtPriceOrder);
            image = itemView.findViewById(R.id.imageOrder);
         //   cartRecyclerView = itemView.findViewById(R.id.recyclerViewCartItems);
            // Initialize other views
        }
    }
}

