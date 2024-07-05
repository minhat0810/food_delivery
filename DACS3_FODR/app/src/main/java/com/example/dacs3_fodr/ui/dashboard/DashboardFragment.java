package com.example.dacs3_fodr.ui.dashboard;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dacs3_fodr.Activity.AllFoodActivity;
import com.example.dacs3_fodr.Activity.CartActivity;
import com.example.dacs3_fodr.Adapter.AllFoodAdapter;
import com.example.dacs3_fodr.Adapter.CategoryAdapter;
import com.example.dacs3_fodr.Adapter.FoodDashBoardAapter;
import com.example.dacs3_fodr.Adapter.FoodListAdapter;
import com.example.dacs3_fodr.Category;
import com.example.dacs3_fodr.Foods;
import com.example.dacs3_fodr.R;
import com.example.dacs3_fodr.Time;
import com.example.dacs3_fodr.User;
import com.example.dacs3_fodr.databinding.FragmentDashboardBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private Uri imageUri;
    private RecyclerView rvFood,rvCate;

    String nameShop;
    private int userIdShop;
    private String address;
    private int idnew = 0;
    private int idnewFood = 0;
    private int id,timeId,cateId;
    private TextView editname,categoryDr,editPrice,editDesc,editNameFood;
    private Spinner timeSpinner,cateSpinner;
    private String cateValue;
    private String timeValue;
    private ArrayList<Category> listCate = new ArrayList<>();
    private ArrayList<Time> times = new ArrayList<>();
    private ArrayList<Foods> listFoods = new ArrayList<>();
    StorageReference storageReference;
    private Button selectImage, uploadImage, addCate,addFood,selectImageFood;
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 123;
    private DatabaseReference databaseReference,timeDatabaseReference,foodRef;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        rvCate = binding.rvCate;
        rvFood= binding.rvFood;
        initCategory();
        //  initFood();
       // initList();
        databaseReference = FirebaseDatabase.getInstance().getReference("Category");
        timeDatabaseReference = FirebaseDatabase.getInstance().getReference("Time");
        foodRef = FirebaseDatabase.getInstance().getReference("Foods");
        fetchCategories();
        fetchTimes();
        fetchFood();
        fetchUserIdShop();
        binding.buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // initTime();
                final DialogPlus dialogPlus = DialogPlus.newDialog(getContext())
                        .setContentHolder(new ViewHolder(R.layout.add_food))
                        .setExpanded(true, 1800)
                        .create();
                dialogPlus.show();
                View viewholder = dialogPlus.getHolderView();
                timeSpinner = viewholder.findViewById(R.id.timeSp);
                cateSpinner = viewholder.findViewById(R.id.cateSpinner);
                editNameFood = viewholder.findViewById(R.id.editNameP);
                editDesc = viewholder.findViewById(R.id.editDesc);
                editPrice = viewholder.findViewById(R.id.editPrice);



                if (timeSpinner != null) {
                    ArrayAdapter<Time> adapter = new ArrayAdapter<>(getActivity(), R.layout.time_item, times);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    timeSpinner.setAdapter(adapter);
                }
                Spinner spinner = timeSpinner;
//                if(timeSpinner!=null){
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Time selectedTime = (Time) parent.getItemAtPosition(position);
                         timeValue = selectedTime.getValue();
                         timeId = selectedTime.getId();
                        //             Toast.makeText(getContext(),"Hí"+timeValue,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                if (cateSpinner != null) {
                    ArrayAdapter<Category> adapter = new ArrayAdapter<>(getActivity(), R.layout.time_item, listCate);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cateSpinner.setAdapter(adapter);
                }
                Spinner spinnerCate = cateSpinner;
//                if(timeSpinner!=null){
                spinnerCate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Category selectedCate = (Category) parent.getItemAtPosition(position);
                         cateValue = selectedCate.toString();
                         cateId = selectedCate.getId();
                                 //    Toast.makeText(getContext(),"Hí"+cateValue,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                    selectImageFood = viewholder.findViewById(R.id.selectImageFood);
                    addFood = viewholder.findViewById(R.id.addFood);
                    selectImageFood.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectImage();
                    }
                     });
                    addFood.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (imageUri != null) {
                            uploadImageFood();
                        } else {
                            Toast.makeText(getActivity(), "No image selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

        binding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(getContext())
                        .setContentHolder(new ViewHolder(R.layout.add_category))
                        .setExpanded(true, 1200)
                        .create();
                dialogPlus.show();
                View viewholder = dialogPlus.getHolderView();
                editname = viewholder.findViewById(R.id.editNameCagetory);
                selectImage = viewholder.findViewById(R.id.selectImage);
               // uploadImage = viewholder.findViewById(R.id.uploadImage);
                addCate = viewholder.findViewById(R.id.add);
                addFood = viewholder.findViewById(R.id.addFood);

                selectImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectImage();
                    }
                });


                addCate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (imageUri != null) {
                            uploadImageCate();
                        } else {
                            Toast.makeText(getActivity(), "No image selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return root;
    }

    private void fetchCategories() {
        Query query = databaseReference.orderByChild("Id");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listCate.clear();
                for (DataSnapshot issue : dataSnapshot.getChildren()) {
                    Category category = issue.getValue(Category.class);
                    listCate.add(category);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
    private void fetchFood() {
        Query query = foodRef.orderByChild("Id");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listFoods.clear();
                for (DataSnapshot issue : dataSnapshot.getChildren()) {
                    Foods foods = issue.getValue(Foods.class);
                    listFoods.add(foods);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
    private void fetchTimes() {
        Query query = timeDatabaseReference.orderByChild("Id");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                times.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        times.add(issue.getValue(Time.class));}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });

    }
    private void uploadImageCate() {
        if (imageUri != null) {
            String lastPathSegment = imageUri.getLastPathSegment();
            StorageReference storageRef = FirebaseStorage.getInstance().getReference("images/" + lastPathSegment);

            storageRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri downloadUri) {
                                    saveCate(downloadUri.toString());
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    Toast.makeText(getActivity(), "Failed to get download URL", Toast.LENGTH_SHORT).show();
                                }
                            });
                          //  Toast.makeText(getActivity(), "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Image upload failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    private void uploadImageFood() {
        if (imageUri != null) {
            String lastPathSegment = imageUri.getLastPathSegment();
            StorageReference storageRef = FirebaseStorage.getInstance().getReference("images/" + lastPathSegment);

            storageRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri downloadUri) {
                                    saveFood(downloadUri.toString());
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    Toast.makeText(getActivity(), "Failed to get download URL", Toast.LENGTH_SHORT).show();
                                }
                            });
                         //   Toast.makeText(getActivity(), "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Image upload failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void saveCate(String imageUrl) {
        if (listCate.size() > 0) {
            idnew = listCate.size() + 1;
        } else {
            idnew = 1;
        }

        String name = editname.getText().toString().trim();
        Category category = new Category(idnew, imageUrl, name);
        DatabaseReference childRef = databaseReference.push();
        childRef.setValue(category, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveFood(String imageUrl) {
        if (listFoods.size() > 0) {
            idnewFood = listFoods.size() + 1;
        } else {
            idnewFood = 1;
        }

        String name = editNameFood.getText().toString().trim();
        Double price = Double.valueOf(editPrice.getText().toString().trim())/1000;
        String desc = editDesc.getText().toString().trim();
        String cate = cateValue.toString().trim();
        int idC = cateId;
        int idT = timeId;
        String time = timeValue;
        DatabaseReference userdb = FirebaseDatabase.getInstance().getReference("User");

  //      String cate =
//        Category category = new Category(idnew, imageUrl, name);
        Foods food = new Foods(false, idC,desc,idnewFood,userIdShop,nameShop,imageUrl,1,price,1,0.0,idT,time,name,address);
        DatabaseReference childRef = foodRef.push();
        childRef.setValue(food, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
            }
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null && data.getData() != null) {
            imageUri = data.getData();
            // You can update your UI here to show the selected image
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void initCategory(){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Category");


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
                        rvCate.setLayoutManager(new GridLayoutManager(getActivity(),4));
                        RecyclerView.Adapter adapter = new CategoryAdapter(cateList);
//                        categoryAdapter = new CategoryAdapter(cateList);
                        rvCate.setAdapter(adapter);
//                        categoryAdapter.notifyDataSetChanged();
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
                    User user1 = issue.getValue(User.class);
                    users.add(user1);
                }
                if(users.size()>0){
                    for (User user2: users){
                        String name = user2.getName();
                        address = user2.getAddress();
                        String sdt = user2.getPhone();
                        String email = user2.getEmail();
                        userIdShop  = user2.getIdShop();
                        nameShop = user2.getShopName();
                        initFood();
                    }
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
     //   fetchUserIdShop();
        int idshop = userIdShop;
        //  Toast.makeText(getActivity(),""+idshop,Toast.LENGTH_SHORT).show();
        DatabaseReference dbF = FirebaseDatabase.getInstance().getReference("Foods");
        Query query = dbF.orderByChild("idShop").equalTo(userIdShop);

        ArrayList<Foods> foodList = new ArrayList<>();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        foodList.add(issue.getValue(Foods.class));

                    }
                    if(foodList.size()>0){
                        //     binding.tvtCate.setText(cateList.toString());
                        rvFood.setLayoutManager(new LinearLayoutManager(getActivity()));
                        RecyclerView.Adapter adapter = new FoodDashBoardAapter(foodList);
//                        categoryAdapter = new CategoryAdapter(cateList);
                        rvFood.setAdapter(adapter);
//                        categoryAdapter.notifyDataSetChanged();
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
