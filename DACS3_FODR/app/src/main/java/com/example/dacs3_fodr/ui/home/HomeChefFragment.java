package com.example.dacs3_fodr.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dacs3_fodr.Activity.AllFoodActivity;
import com.example.dacs3_fodr.Adapter.AllFoodAdapter;
import com.example.dacs3_fodr.Adapter.FoodDashBoardAapter;
import com.example.dacs3_fodr.Adapter.FoodListAdapter;
import com.example.dacs3_fodr.Adapter.HomeChefAdapter;
import com.example.dacs3_fodr.Foods;
import com.example.dacs3_fodr.R;
import com.example.dacs3_fodr.User;
import com.example.dacs3_fodr.databinding.FragmentDashboardBinding;
import com.example.dacs3_fodr.databinding.FragmentHomeChefBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeChefFragment extends Fragment {
    private int userIdShop;
    private  String nameShop;
    private String address;
    private FragmentHomeChefBinding binding;
    User user1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentHomeChefBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        fetchUserIdShop();
      //  initFood();
        return root;
    }
    private void fetchUserIdShop() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ArrayList<User> users = new ArrayList<>();
        //  Toast.makeText(getActivity(), ""+userId, Toast.LENGTH_SHORT).show();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User");
        Query query = userRef.orderByChild("idUser").equalTo(userId);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot issue: dataSnapshot.getChildren()){
                     user1 = issue.getValue(User.class);
                    users.add(user1);
                    userIdShop = user1.getIdShop();
                   // initFood();
                //    Toast.makeText(getActivity(),user1.getIdShop()+"",Toast.LENGTH_SHORT).show();
                }
                if(users.size()>0){
                    DatabaseReference dbF = FirebaseDatabase.getInstance().getReference("Foods");
                    Query query = dbF.orderByChild("idShop").equalTo(userIdShop);

                    ArrayList<Foods> foodList = new ArrayList<>();
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            foodList.clear();
                            if(dataSnapshot.exists()) {
                                for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                    foodList.add(issue.getValue(Foods.class));

                                }
                                if(foodList.size()>0){
                                    binding.foodListRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                                    RecyclerView.Adapter adapter = new HomeChefAdapter(foodList);
                                    binding.foodListRv.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                    //Toast.makeText(getActivity(),"Hí"+ databaseReference,Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getActivity(),"Hí2",Toast.LENGTH_SHORT).show();
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getActivity(),"Lỗi",Toast.LENGTH_SHORT).show();
                        }
                    });

//                    for (User user2: users){
////                        String name = user2.getName();
////                        address = user2.getAddress();
////                        String sdt = user2.getPhone();
////                        String email = user2.getEmail();
//                        userIdShop  = user2.getIdShop();
// //                       nameShop = user2.getShopName();
//                        initFood();
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
    private void initFood(){
       // int idshop = userIdShop;
       // fetchUserIdShop();
        //userIdShop =user1.getIdShop();

      //  int idshop = userIdShop;
      //  Toast.makeText(getActivity(),""+idshop,Toast.LENGTH_SHORT).show();
        DatabaseReference dbF = FirebaseDatabase.getInstance().getReference("Foods");
        Query query = dbF.orderByChild("idShop").equalTo(userIdShop);

        ArrayList<Foods> foodList = new ArrayList<>();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                foodList.clear();
                if(dataSnapshot.exists()) {
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        foodList.add(issue.getValue(Foods.class));

                    }
                    if(foodList.size()>0){
                        binding.foodListRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                        RecyclerView.Adapter adapter = new HomeChefAdapter(foodList);
                        binding.foodListRv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        //Toast.makeText(getActivity(),"Hí"+ databaseReference,Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(),"Hí2",Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"Lỗi",Toast.LENGTH_SHORT).show();
            }
        });

    }
}