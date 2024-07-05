package com.example.dacs3_fodr.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dacs3_fodr.Activity.DetailFActivity;
import com.example.dacs3_fodr.Foods;
import com.example.dacs3_fodr.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

public class SearchFoodAdapter extends FirebaseRecyclerAdapter<Foods, SearchFoodAdapter.viewholder> {
    Context context;
    ArrayList<Foods> items;
    public SearchFoodAdapter(@NonNull FirebaseRecyclerOptions<Foods> options, ArrayList<Foods> items,Context context) {
        super(options);
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchFoodAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_item, viewGroup, false);
        return new viewholder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull SearchFoodAdapter.viewholder viewholder, @SuppressLint("RecyclerView") int position, @NonNull Foods foods) {
        viewholder.title.setText(foods.getTitle());
//        viewholder.price.setText(foods.getPrice() + "VNĐ");
//        viewholder.time.setText(foods.getTimeValue() + "phút");
//        viewholder.star.setText(String.valueOf(foods.getStar()));
        Glide.with(viewholder.itemView.getContext())
                .load(foods.getImagePath())
                .into(viewholder.image);
        viewholder.title.setOnClickListener(v ->{
            Intent intent = new Intent(context, DetailFActivity.class);
            intent.putExtra("object",foods);
            context.startActivity(intent);
//            Toast.makeText(context, "hi", Toast.LENGTH_SHORT).show();
        });
    }

//        viewholder.itemView.setOnClickListener(v ->{
////            Intent intent = new Intent(context, DetailFActivity.class);
////            intent.putExtra("object",foods);
////            context.startActivity(intent);
//            Toast.makeText(context, "hi", Toast.LENGTH_SHORT).show();
//        });
//        viewholder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, DetailFActivity.class);
//            intent.putExtra("object",items.get(position));
//            context.startActivity(intent);
//
//            }
//        });
//        viewholder.title.setOnClickListener(v -> {
//            Toast.makeText(v.getContext(), "hi", Toast.LENGTH_SHORT).show();
//        });
//    }

        public static class viewholder extends RecyclerView.ViewHolder {
            TextView time, title, star, price;
            ImageView image;

            public viewholder(@NonNull View itemView) {
                super(itemView);
//            price = itemView.findViewById(R.id.tvtPrice);
//            time = itemView.findViewById(R.id.tvtTime);
                title = itemView.findViewById(R.id.tvtTitleCart);
                //       star = itemView.findViewById(R.id.tvtStar);
                image = itemView.findViewById(R.id.imageCart);
            }
        }
    }

