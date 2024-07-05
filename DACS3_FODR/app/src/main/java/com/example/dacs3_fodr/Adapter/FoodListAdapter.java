package com.example.dacs3_fodr.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.dacs3_fodr.Activity.DetailFActivity;
import com.example.dacs3_fodr.Foods;
import com.example.dacs3_fodr.R;

import java.util.ArrayList;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.viewholder> {
    ArrayList<Foods> items;
    Context context;
    public FoodListAdapter(ArrayList<Foods> items){
        this.items = items;
    }
    @NonNull
    @Override
    public FoodListAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_food_list,viewGroup,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodListAdapter.viewholder viewholder, int i) {
       
        viewholder.title.setText(items.get(i).getTitle());
        viewholder.price.setText(items.get(i).getPrice()+"00 VNĐ");
        viewholder.time.setText(items.get(i).getTimeValue());
        viewholder.star.setText(""+items.get(i).getStar());

        Glide.with(context)
                .load(items.get(i).getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(viewholder.image);

        viewholder.itemView.setOnClickListener(v ->{
            Intent intent = new Intent(context, DetailFActivity.class);
            intent.putExtra("object",items.get(i));
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public static class viewholder extends RecyclerView.ViewHolder {
        TextView title,star,time,price;
        ImageView image;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            //id = itemView.findViewById(R.id.tvtID);
            title = itemView.findViewById(R.id.tvtTitle);
            star = itemView.findViewById(R.id.tvtStar);
            time = itemView.findViewById(R.id.tvtTime);
            price = itemView.findViewById(R.id.tvtPrice);
            image = itemView.findViewById(R.id.imageView);

        }
    };
}
