package com.example.dacs3_fodr.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dacs3_fodr.Adapter.Cart;
import com.example.dacs3_fodr.Adapter.CartAdapter;
import com.example.dacs3_fodr.Foods;
import com.example.dacs3_fodr.Order;
import com.example.dacs3_fodr.R;
import com.example.dacs3_fodr.databinding.ActivityCartBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private ActivityCartBinding binding;
    private String userId;
    private int idProduct,idShop;
    RecyclerView.Adapter adapter;
//    String userID;
    private String cartID;
    private Double orderFee = 0.0;
    private Double coupon = 0.0;
    private String name="",phone="",address="";
    private Foods object;
    private TextView tvtName,tvtPhone,tvtAdress;
    private Double price;
    private int quantity;
    private Double total = 0.0;

    private int foodId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getIntentExtra();
        setVarriable();
        initList();
        totalCart();

        //  finish();
       // DeleteCart();
        binding.btnAddInf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showAddInfoDialog();

            }
        });
        binding.btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initOrder();
            }
        });
        binding.btnWait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this,OrderWait.class);
                startActivity(intent);
            }
        });
    }
    private void saveOrder(Order order) {
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("Orders");
        String orderId = ordersRef.push().getKey();
        ordersRef.child(orderId).setValue(order)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(CartActivity.this, "Đặt hàng thành công. Vui lòng nhấn vào xem đơn hàng đã đặt", Toast.LENGTH_SHORT).show();

                })
                .addOnFailureListener(e -> Toast.makeText(CartActivity.this, "Failed to place order.", Toast.LENGTH_SHORT).show());
    }


    public void showAddInfoDialog() {
        DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.order_send))
                .setGravity(Gravity.CENTER)
                .setExpanded(true,1000)
                .create();

        // lấy các view từ layout và thực hiện thao tác với chúng
        View contentView = dialog.getHolderView();
        EditText editName = contentView.findViewById(R.id.editName);
        EditText editSDT = contentView.findViewById(R.id.editSDT);
        EditText editAddress = contentView.findViewById(R.id.editAddress);
        Button addBtn = contentView.findViewById(R.id.addInf);

        // Thêm sự kiện click cho nút "Thêm"
        addBtn.setOnClickListener(v -> {
            // Xử lý dữ liệu từ các EditText
             name = editName.getText().toString();
             phone = editSDT.getText().toString();
             address = editAddress.getText().toString();

            binding.tvtName.setText(name);
            binding.tvtSDT.setText(phone);
            binding.tvtAdress.setText(address);

            // Đóng dialog
            dialog.dismiss();
        });

        dialog.show();
    }

    private void initOrder(){
        if(name == "" ||phone == "" ||address == ""){
            showAddInfoDialog();
        //    Toast.makeText(CartActivity.this,"Vui lòng điền đủ thông tin",Toast.LENGTH_SHORT).show();
        }else{
//            Toast.makeText(CartActivity.this,"Lỗi rồi cụ",Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
            builder.setTitle("Xác nhận")
                    .setMessage("Xác nhận đặt hàng chứ?")
                    .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            userId = user.getUid();

                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Cart");
                            DatabaseReference childRef = databaseReference.push();
                            Query query = databaseReference.orderByChild("idUser").equalTo(userId);
                            query.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.exists()) {
                                        List<Cart> cartList = new ArrayList<>();
                                        double totalAmount = 0.0;

                                        for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                            Cart cart = issue.getValue(Cart.class);
                                            if (cart != null) {
                                                cartList.add(cart);
                                                totalAmount += cart.getPrice() * cart.getQuantity();
                                                idShop = cart.getIdShop();
                                            }
                                        }


                                        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("Orders");
//                                        String orderId = ordersRef.push().getKey();
                                        DatabaseReference childRef2 = ordersRef.push();
                                        String orderId = childRef2.getKey();
                                        Order order = new Order(userId, name, phone, address, cartList, totalAmount,0,orderId,idShop);
                                        childRef2.setValue(order, new DatabaseReference.CompletionListener() {
                                            @Override
                                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                                Toast.makeText(CartActivity.this,"Đặt hàng thành công",Toast.LENGTH_SHORT).show();
                                                //    Toast.makeText(DetailFActivity.this,"Thành công",Toast.LENGTH_SHORT).show();
//                        String key = childRef.getKey();
////                        Toast.makeText(DetailFActivity.this,key,Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(DetailFActivity.this, CartActivity.class);
//                        intent.putExtra("CartID",key);
                                            }
                                        });

                                     //   saveOrder(order);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(CartActivity.this, "Error calculating total price", Toast.LENGTH_SHORT).show();
                                }
                            });
//                            Order order = new Order(userID,name,);

                        }
                    })
             .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Xử lý khi người dùng bấm nút Cancel
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        }

    }
    private void initList(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Cart");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        userId  = user.getUid();

        Query query = databaseReference.orderByChild("idUser").equalTo(userId);

        binding.txtTitle.setText("Giỏ hàng của bạn");
        ArrayList<Cart> list = new ArrayList<>();
        binding.progressBar6.setVisibility(View.GONE);binding.progressBar6.setVisibility(View.VISIBLE);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    list.clear();
                   // TotalCart();
                    for(DataSnapshot issue: dataSnapshot.getChildren()){
                        Cart cart = issue.getValue(Cart.class);
                        list.add(cart);
                        if(list.size()>0){
                            binding.listRv.setLayoutManager(new LinearLayoutManager(CartActivity.this));
                             adapter = new CartAdapter(list);
                            binding.listRv.setAdapter(adapter);

                            adapter.notifyDataSetChanged();

                        }else {
                        }

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
    private void setVarriable(){
//        binding.txtTitle.setText(cartID);
    }

private void totalCart() {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Cart");
    Query query = databaseReference.orderByChild("idUser").equalTo(userId);

    query.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            double totalPrice = 0.0;
            if (dataSnapshot.exists()) {
                for (DataSnapshot issue : dataSnapshot.getChildren()) {
                    Cart cart = issue.getValue(Cart.class);
                    if (cart != null) {
                        totalPrice += cart.getPrice() * cart.getQuantity();
                    }
                }
                binding.subtotal.setText(String.format("%,.0f VNĐ", totalPrice*1000));

                if(totalPrice*1000 >= 300000){
                    orderFee = 30.0;
                    binding.orderFee.setText(String.format("%,.0f VNĐ", orderFee*1000));
                }else {
                    orderFee = totalPrice/10;
                    binding.orderFee.setText(String.format("%,.0f VNĐ", orderFee*1000));
                }

                binding.tvtCoupon.setText(String.format("%,.0f VNĐ", coupon*1000));
                binding.tvtTotal.setText(String.format("%,.0f VNĐ", (totalPrice + orderFee - coupon)*1000));
            } else {
                binding.subtotal.setText("0.00 VNĐ");
                binding.orderFee.setText(String.format("%,.0f VNĐ", orderFee*1000));
                binding.tvtCoupon.setText(String.format("%,.0f VNĐ", coupon*1000));
                binding.tvtTotal.setText(String.format("%,.0f VNĐ", (orderFee - coupon)*1000));
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(CartActivity.this, "Error calculating total price", Toast.LENGTH_SHORT).show();
        }
    });
}
    private void DeleteCart(){
        Toast.makeText(CartActivity.this,idProduct+"",Toast.LENGTH_SHORT).show();
    }
    private void  getIntentExtra(){

//        object = (Foods) getIntent().getSerializableExtra("FoodId");
//        binding.txtTitle.setText(object.getTitle());
        userId = getIntent().getStringExtra("UserID");
        idProduct = getIntent().getIntExtra("ProductID",0);
//        cartID = getIntent().getStringExtra("CartID");
    }

}