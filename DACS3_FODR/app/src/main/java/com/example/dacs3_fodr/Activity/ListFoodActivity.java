package com.example.dacs3_fodr.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dacs3_fodr.Adapter.FoodListAdapter;
import com.example.dacs3_fodr.Foods;
import com.example.dacs3_fodr.databinding.ActivityListFoodBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListFoodActivity extends AppCompatActivity {
    private ActivityListFoodBinding binding;
    private RecyclerView.Adapter adapter;
    private int categoryId;
    private String categoryName;
    private String searchText;
    private boolean isSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_list_food);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        binding = ActivityListFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getIntentExtra();
        initList();
    }
    public void initList(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Foods");
        binding.progressBar4.setVisibility(View.VISIBLE);
        ArrayList<Foods> list =  new ArrayList<>();
        Query query;
     //   Query query = databaseReference.orderByChild("CatagoryId");
        if(isSearch){
            query =  databaseReference.orderByChild("title").startAt(searchText).endAt(searchText+'\uf8ff');
        }else {
            query = databaseReference.orderByChild("categoryId").equalTo(categoryId);
        }
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot issue: dataSnapshot.getChildren()){
                        Foods foods = issue.getValue(Foods.class);
                        list.add(foods);
                    }
                    if(list.size()>0){

                        binding.foodListRv.setLayoutManager(new GridLayoutManager(ListFoodActivity.this,2));
                        RecyclerView.Adapter adapter = new FoodListAdapter(list);
                        binding.foodListRv.setAdapter(adapter);
                      //  binding.txtTitle.setText(""+query);
                    }
                    binding.progressBar4.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        Query query1 =  iduser.orderByKey().limitToLast(1);
//
//        query1.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                    String lastUserId =  userSnapshot.getKey();
//                    // Đây là iduser cuối cùng trong mục "user"
//                    Toast.makeText(ListFoodActivity.this,lastUserId,Toast.LENGTH_SHORT).show();
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

    }
    public void getIntentExtra(){
        categoryId = getIntent().getIntExtra("CategoryId",0);
        categoryName = getIntent().getStringExtra("CategoryName");
        searchText = getIntent().getStringExtra("text");
        isSearch = getIntent().getBooleanExtra("isSearch",false);

        binding.txtTitle.setText(categoryName);
        binding.backFL.setOnClickListener(v -> finish());
    }
}