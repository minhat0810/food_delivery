package com.example.dacs3_fodr.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dacs3_fodr.Adapter.AllFoodAdapter;
import com.example.dacs3_fodr.Adapter.BestFoodAdapter;
import com.example.dacs3_fodr.Adapter.FoodListAdapter;
import com.example.dacs3_fodr.Foods;
import com.example.dacs3_fodr.R;
import com.example.dacs3_fodr.databinding.ActivityAllFoodBinding;
import com.example.dacs3_fodr.databinding.ActivityListFoodBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllFoodActivity extends AppCompatActivity {
    private ActivityAllFoodBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initList();
        binding.backFL.setOnClickListener(v -> {
            finish();
        });
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_all_food);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;

    }
    public void initList() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Foods");
        binding.progressBar4.setVisibility(View.VISIBLE);
        ArrayList<Foods> list = new ArrayList<>();
        Query query;

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        Foods foods = issue.getValue(Foods.class);
                        list.add(foods);
                    }
                    if (list.size() > 0) {

                        binding.foodListRv.setLayoutManager(new GridLayoutManager(AllFoodActivity.this, 2));
                        RecyclerView.Adapter adapter = new FoodListAdapter(list);
                        binding.foodListRv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        //  binding.txtTitle.setText(""+query);
                    }
                    binding.progressBar4.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}