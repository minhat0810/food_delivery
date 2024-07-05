package com.example.dacs3_fodr.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dacs3_fodr.Activity.OrderWait;
import com.example.dacs3_fodr.Activity.ui.OrderInf;
import com.example.dacs3_fodr.Adapter.Cart;
import com.example.dacs3_fodr.Adapter.NofOrderAdapter;
import com.example.dacs3_fodr.Adapter.OrderAdapter;
import com.example.dacs3_fodr.Order;
import com.example.dacs3_fodr.User;
import com.example.dacs3_fodr.databinding.FragmentNotificationsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {
    private String idAD;
    private int idShopAd;
    private int idS;
    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        binding.btnView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), OrderInf.class);
//                startActivity(intent);
//            }
//        });

//        final TextView textView = binding.textNotifications;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        initCus();
        return root;
    }
    private void initCus(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        idAD = user.getUid();
        ArrayList<User> users = new ArrayList<>();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
        Query query = databaseReference.orderByChild("idUser").equalTo(idAD);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot issue: dataSnapshot.getChildren()){
                    User user1 = issue.getValue(User.class);
                    users.add(user1);
                    idShopAd = user1.getIdShop();
                    // initFood();
                      //  Toast.makeText(getActivity(),idShopAd+"",Toast.LENGTH_SHORT).show();
                        DatabaseReference shopRef = FirebaseDatabase.getInstance().getReference("Orders");
                        Query query1 = shopRef.orderByChild("status").equalTo(0);
                        query1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                ArrayList<Cart> carts = new ArrayList<>();
                                ArrayList<Order> orders = new ArrayList<>();
                                for (DataSnapshot issue: dataSnapshot.getChildren()){
                                    Order order = issue.getValue(Order.class);
                                    orders.add(order);
                                    carts = (ArrayList<Cart>) order.getCartList();
                                 //   Toast.makeText(getActivity(),idShopAd+"",Toast.LENGTH_SHORT).show();
                                }

                                if(orders.size()>0){
                                    binding.rvOrder.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    RecyclerView.Adapter adapter = new NofOrderAdapter(orders,getActivity());
                                    binding.rvOrder.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}