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
import com.example.dacs3_fodr.Category;
import com.example.dacs3_fodr.Activity.ListFoodActivity;
import com.example.dacs3_fodr.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewholder> {
    ArrayList<Category> items;

    Context context;
    public CategoryAdapter(ArrayList<Category> items){
        this.items = items;
    }
    @NonNull
    @Override
    public CategoryAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

//        View inflate = LayoutInflater.from()

        context = viewGroup.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.home_category,viewGroup,false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.viewholder viewholder, int i) {
//        Category category = items.get(i);
//        viewholder.name.setText(category.getNameCate());
//               viewholder.name.setText(category.getName());
                viewholder.name.setText(items.get(i).getName());
//                viewholder.imageView.setImageResource(items.get(i).getImagePath());
//        int drawableResource = context.getResources().getIdentifier(items.get(i).getImagePath(),"drawable",viewholder.itemView.getContext().getPackageName());
        int drawableResource = context.getResources().getIdentifier(items.get(i).getImagePath(),"drawable",viewholder.itemView.getContext().getPackageName());
//
        Glide.with(context)
                .load(items.get(i).getImagePath())
                .into(viewholder.imageView);
        viewholder.itemView.setOnClickListener(v->{
            Intent intent = new Intent(context, ListFoodActivity.class);
            intent.putExtra("CategoryId",items.get(i).getId());
            intent.putExtra("CategoryName",items.get(i).getName());
            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        if (items!=null){
            return items.size();
        }
        return 0;
    }
    public class viewholder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgCate);
            name = itemView.findViewById(R.id.nameCate);
        }
    }
}
