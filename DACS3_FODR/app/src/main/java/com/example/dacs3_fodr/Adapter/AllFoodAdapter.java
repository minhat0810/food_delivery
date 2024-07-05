package com.example.dacs3_fodr.Adapter;

import android.content.Context;
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
import com.example.dacs3_fodr.Foods;
import com.example.dacs3_fodr.R;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class AllFoodAdapter extends RecyclerView.Adapter<AllFoodAdapter.viewholder> {
    ArrayList<Foods> items;
    Context context;
    public AllFoodAdapter(ArrayList<Foods> items){
        this.items = items;
    }
    @NonNull
    @Override
    public AllFoodAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

//        View inflate = LayoutInflater.from()
        context = viewGroup.getContext();
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_food_item,viewGroup,false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull AllFoodAdapter.viewholder viewholder, int i) {
        viewholder.title.setText(items.get(i).getTitle());
        viewholder.price.setText(items.get(i).getPrice()+"VNĐ");
        viewholder.time.setText(items.get(i).getTimeValue()+"phút");
        viewholder.star.setText(""+items.get(i).getStar());
        Glide.with(context)
                .load(items.get(i).getImagePath())
                .into(viewholder.image);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public class viewholder extends RecyclerView.ViewHolder{
        TextView time,title,star,price;
        ImageView image;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.tvtPrice);
            time = itemView.findViewById(R.id.tvtTime);
            title = itemView.findViewById(R.id.tvtTitle);
            star = itemView.findViewById(R.id.tvtStar);
            image = itemView.findViewById(R.id.tvtImage);
        }
    }
}
