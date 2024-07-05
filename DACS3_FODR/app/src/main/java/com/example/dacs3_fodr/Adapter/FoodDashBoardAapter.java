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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dacs3_fodr.ChefActivity;
import com.example.dacs3_fodr.Foods;
import com.example.dacs3_fodr.R;
import com.example.dacs3_fodr.ui.home.HomeChefFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FoodDashBoardAapter extends RecyclerView.Adapter<FoodDashBoardAapter.viewholder> {
    ArrayList<Foods> items;
    Context context;
    @NonNull
    @Override
    public FoodDashBoardAapter.viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_food_chef,viewGroup,false);
        return new viewholder(view);
    }
    public FoodDashBoardAapter(ArrayList<Foods> items){

        this.items = items;
    }
    private void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, items.size());
    }
    @Override
    public void onBindViewHolder(@NonNull FoodDashBoardAapter.viewholder viewholder, @SuppressLint("RecyclerView") int i) {
        viewholder.title.setText(items.get(i).getTitle());
        viewholder.price.setText(items.get(i).getPrice()+"00VNĐ");
        viewholder.address.setText(items.get(i).getAddress());
        Glide.with(context)
                .load(items.get(i).getImagePath())
                .into(viewholder.image);
//        viewholder.btnDeleteFood.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), "hi",Toast.LENGTH_SHORT).show();
//            }
//        });
        viewholder.btnDeleteFood.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int value = items.get(i).getId();

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Foods");
                Query query = databaseReference.orderByChild("id").equalTo(value);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   //     items.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                          //  snapshot.getRef().removeValue();
                            Toast.makeText(v.getContext(), "Xóa thành công",Toast.LENGTH_SHORT).show();
                            snapshot.getRef().removeValue(new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                    Intent intent = new Intent(context, ChefActivity.class);
                                    context.startActivity(intent);

                                }
                            }); // Xóa đối tượng từ cơ sở dữ liệu
                            //snapshot.getRef().removeValue();
                        }
                        removeItem(i);
//                        items.remove(i);
//                   //     notifyDataSetChanged();
//                        notifyItemRemoved(i);
                        notifyItemRangeChanged(i, items.size());

                        Toast.makeText(context, "Đã xóa mục", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(context,CartActivity.class);
//                        context.startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public class viewholder extends  RecyclerView.ViewHolder{
        TextView title,price,address;
        ImageView image,increase,reduce, btnDeleteFood;
        public viewholder(@NonNull View itemView) {

            super(itemView);

            //id = itemView.findViewById(R.id.tvtID);
            title = itemView.findViewById(R.id.tvtTitleFChef);
            price = itemView.findViewById(R.id.tvtPriceFChef);
            address = itemView.findViewById(R.id.tvtAddressFChef);
            image = itemView.findViewById(R.id.imageCart);
            btnDeleteFood = itemView.findViewById(R.id.btnDeleteFood);
//               increase = itemView.findViewById(R.id.tangSLC);
//               reduce = itemView.findViewById(R.id.giamSLC);


        }
    }

}
