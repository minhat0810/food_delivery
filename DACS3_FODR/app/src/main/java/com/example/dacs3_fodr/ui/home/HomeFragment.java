package com.example.dacs3_fodr.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dacs3_fodr.Activity.AllFoodActivity;
import com.example.dacs3_fodr.Adapter.AllFoodAdapter;
import com.example.dacs3_fodr.Adapter.BestFoodAdapter;
import com.example.dacs3_fodr.Adapter.CategoryAdapter;
import com.example.dacs3_fodr.Activity.CartActivity;
import com.example.dacs3_fodr.Adapter.SearchFoodAdapter;
import com.example.dacs3_fodr.Category;
import com.example.dacs3_fodr.Foods;
import com.example.dacs3_fodr.Location;
import com.example.dacs3_fodr.Price;
import com.example.dacs3_fodr.R;
import com.example.dacs3_fodr.Time;
import com.example.dacs3_fodr.databinding.FragmentHomeBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView cateView;
   // SearchView searchView;
    private CategoryAdapter categoryAdapter = null;
    private List<Category> mListCate;
    private FragmentHomeBinding binding;
     SearchFoodAdapter foodAdapter;
     ArrayList<Foods> item;
    SearchView searchView;
    private String userId;
    RecyclerView searchRv;
    private EditText search;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtSearch(query);
                return false;
            }
        });


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        binding.btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getActivity(),"Hí",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), CartActivity.class);
                intent.putExtra("UserID",userId);
                startActivity(intent);
            }
        });
        binding.showall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllFoodActivity.class);
                startActivity(intent);
            }
        });
        initCategory();
        initFood();
        initLocation();
        initTime();
        intiPrice();
        item = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Foods");
        Query query = databaseReference.orderByChild("title");
        FirebaseRecyclerOptions<Foods> options = new FirebaseRecyclerOptions.Builder<Foods>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Foods"), Foods.class)
                .build();
        foodAdapter = new SearchFoodAdapter(options,item,getActivity());
        binding.searchRv.setAdapter(foodAdapter);

      // getIdUser();
//        binding.recyclerViewCategory.setLayoutManager(new GridLayoutManager(getActivity(),4));
//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }


    @Override
    public void onStart() {
        super.onStart();
       if (foodAdapter != null) {
            foodAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (foodAdapter != null) {
            foodAdapter.stopListening();
        }
    }
    private void txtSearch(String str) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Foods");
        Query query;// = databaseReference.orderByChild("title").startAt(str).endAt(str + "\uf8ff");
        if (str.isEmpty()) {
            query = databaseReference.orderByChild("title").equalTo(""); // This query should return no results
        } else {
            query = databaseReference.orderByChild("title").startAt(str).endAt(str + "\uf8ff");
        }
        FirebaseRecyclerOptions<Foods> options = new FirebaseRecyclerOptions.Builder<Foods>()
                .setQuery(query, Foods.class)
                .build();
        binding.searchRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        foodAdapter.updateOptions(options);
        foodAdapter.startListening();
        binding.searchRv.setVisibility(View.VISIBLE);
        foodAdapter.notifyDataSetChanged();
    }

//    private void txtSearch(String str){
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Foods");
//        Query query = databaseReference.orderByChild("title").startAt(str).endAt(str+"~");
//        FirebaseRecyclerOptions<Foods> option =
//                new FirebaseRecyclerOptions.Builder<Foods>()
//                .setQuery(query, Foods.class)
//                .build();
//        foodAdapter = new SearchFoodAdapter(option);
//        foodAdapter.startListening();
//    //      binding.searchRv.setVisibility(View.VISIBLE);
//        binding.searchRv.setAdapter(foodAdapter);
////        binding.searchRv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
////        RecyclerView.Adapter adapter = new SearchFoodAdapter(option);
////        binding.searchRv.setAdapter(adapter);
////        adapter.notifyDataSetChanged();
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void getIdUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            String userId = user.getUid();
             Toast.makeText(getActivity(),userId,Toast.LENGTH_SHORT).show();
        }
    }
    private void initCategory(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Category");
        binding.progressBar3.setVisibility(View.VISIBLE);
        ArrayList<Category> cateList = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                             cateList.add(issue.getValue(Category.class));
                    }
                    if(cateList.size()>0){
                   //     binding.tvtCate.setText(cateList.toString());
                        binding.recyclerViewCategory.setLayoutManager(new GridLayoutManager(getActivity(),4));
                        RecyclerView.Adapter adapter = new CategoryAdapter(cateList);
//                        categoryAdapter = new CategoryAdapter(cateList);
                        binding.recyclerViewCategory.setAdapter(adapter);
//                        categoryAdapter.notifyDataSetChanged();
                          adapter.notifyDataSetChanged();
                        //Toast.makeText(getActivity(),"Hí"+ databaseReference,Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(),"Hí2",Toast.LENGTH_SHORT).show();
                    }
                    binding.progressBar3.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"Lỗi",Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void initFood(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Foods");
        binding.progressBar2.setVisibility(View.VISIBLE);
        ArrayList<Foods> foodList = new ArrayList<>();
        Query query = databaseReference.orderByChild("BestFood").equalTo(true);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        Foods foods = issue.getValue(Foods.class);
                        foodList.add(foods);
                    }
                    if(foodList.size()>0){
                        binding.recyclerViewFood.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
                        RecyclerView.Adapter adapter = new BestFoodAdapter(foodList);
                        binding.recyclerViewFood.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                      //  Toast.makeText(getActivity(),"Hí"+query,Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(),"Hí2",Toast.LENGTH_SHORT).show();
                    }
                    binding.progressBar2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"Lỗi",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initTime(){
//        FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Time");
        ArrayList<Time> times = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        times.add(issue.getValue(Time.class));
                    }
                    if (times.size() > 0) {
                        ArrayAdapter<Time> adapter = new ArrayAdapter<>(getActivity(),R.layout.time_item,times);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.timeSp.setAdapter(adapter);
                       // Toast.makeText(getActivity(),"His"+databaseReference,Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void initLocation(){
//        FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Location");
        ArrayList<Location> locations = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        locations.add(issue.getValue(Location.class));
                    }
                    if (locations.size() > 0) {
                        ArrayAdapter<Location> adapter = new ArrayAdapter<>(getActivity(),R.layout.time_item,locations);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.locationSp.setAdapter(adapter);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void intiPrice(){
//        FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Price");
        ArrayList<Price> prices = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        prices.add(issue.getValue(Price.class));
                    }
                    if (prices.size() > 0) {
                        ArrayAdapter<Price> adapter = new ArrayAdapter<>(getActivity(),R.layout.time_item,prices);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.priceSp.setAdapter(adapter);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Spinner spinner = binding.priceSp;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Price selectedPrice = (Price) parent.getItemAtPosition(position);

                String priceValue = selectedPrice.getValue();
              //  Toast.makeText(getActivity(),"Hí"+priceValue,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}