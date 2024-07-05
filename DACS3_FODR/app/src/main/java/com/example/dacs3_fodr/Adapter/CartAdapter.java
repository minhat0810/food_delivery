package com.example.dacs3_fodr.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dacs3_fodr.Activity.OrderWait;
import com.example.dacs3_fodr.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.viewholder> {
    ArrayList<Cart> items;
    Context context;
    String cartItemId;
    Double currentTotal = 0.0;
    String IDCart;
    private int num;
    String cartID;
    String idProduct;
    int currentQuantity;
    public CartAdapter(ArrayList<Cart> items){

        this.items = items;
    }

    @NonNull
    @Override

    public CartAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart,viewGroup,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.viewholder viewholder, @SuppressLint("RecyclerView") int i) {
        Cart item = items.get(i);
        viewholder.price.setText(item.getPrice() + "00VNĐ");
        viewholder.quantity.setText(item.getQuantity() + "");
        viewholder.totalPrice.setText(item.getTotalPrice()+"00VNĐ");
        viewholder.title.setText(items.get(i).getTitle());
        viewholder.price.setText(items.get(i).getPrice()+"00VNĐ");
//        viewholder.quantity.setText(items.get(i).getQuantity()+"");
  //      viewholder.totalPrice.setText(items.get(i).getTotalPrice()+"00VNĐ");
        Glide.with(context)
                .load(items.get(i).getImage())
                .into(viewholder.image);



        viewholder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                currentQuantity = item.getQuantity();
                Double price = item.getPrice();
                Double currentTotal = 0.0;
                    currentQuantity += 1;
                    currentTotal += price*currentQuantity;
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("quantity",currentQuantity);
                    updates.put("totalPrice",currentTotal);
                    //updates.put("totalPrice",currentTotal);
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Cart")
                            .child(items.get(i).getIDCart());
                    databaseReference.updateChildren(updates);


                    item.setQuantity(currentQuantity);
                    viewholder.quantity.setText(currentQuantity + "");
                    item.setTotalPrice(currentTotal);
                    viewholder.totalPrice.setText(currentQuantity*currentTotal+"00VNĐ");

                    // Cập nhật item trong danh sách và thông báo cho adapter
                    items.set(i, item);
                    notifyItemChanged(i);

            }
        });

        viewholder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(v.getContext(), ""+items.get(i).getIDCart(),Toast.LENGTH_SHORT).show();

                 currentQuantity = item.getQuantity();
                Double price = item.getPrice();
                Double currentTotal = 0.0;
               if(currentQuantity>1){
                   currentQuantity -= 1;
                   currentTotal += price*currentQuantity;
                   Map<String, Object> updates = new HashMap<>();
                   updates.put("quantity",currentQuantity);
                   updates.put("totalPrice",currentTotal);
                   //updates.put("totalPrice",currentTotal);
                   DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Cart")
                           .child(items.get(i).getIDCart());
                   databaseReference.updateChildren(updates);


                   item.setQuantity(currentQuantity);
                   viewholder.quantity.setText(currentQuantity + "");
                   item.setTotalPrice(currentTotal);
                   viewholder.totalPrice.setText(currentQuantity*currentTotal+"00VNĐ");

                   // Cập nhật item trong danh sách và thông báo cho adapter
                   items.set(i, item);
                   notifyItemChanged(i);

               }
            }
        });
        viewholder.btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String value = String.valueOf(items.get(i).getIDCart());
                FirebaseDatabase.getInstance().getReference().child("Cart");
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận")
                        .setMessage("Xác nhận xóa khỏi giỏ hàng chứ?")
                        .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Cart");
                                Query query = databaseReference.orderByChild("idcart").equalTo(value);
                                query.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            snapshot.getRef().removeValue(); // Xóa đối tượng từ cơ sở dữ liệu
                                        }
//                        Intent intent = new Intent(context,CartActivity.class);
//                        context.startActivity(intent);
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
                        })  ;
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });

    }


    @Override
    public int getItemCount() {
        return items.size();
    }
    public static class viewholder extends  RecyclerView.ViewHolder{
        TextView title,price,quantity,totalPrice,total;
        ImageView image,increase,reduce, btnDelete;
        public viewholder(@NonNull View itemView) {

            super(itemView);

                //id = itemView.findViewById(R.id.tvtID);
               title = itemView.findViewById(R.id.tvtTitleCart);
               price = itemView.findViewById(R.id.tvtPriceCart);
               quantity = itemView.findViewById(R.id.quantityCart);
               totalPrice = itemView.findViewById(R.id.tvtSubPC);
               image = itemView.findViewById(R.id.imageCart);
               btnDelete = itemView.findViewById(R.id.btnDelete);
               increase = itemView.findViewById(R.id.tangSLC);
               reduce = itemView.findViewById(R.id.giamSLC);
        }
    }


}
