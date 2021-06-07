package com.example.adminpanelfurniture.Admin.homeAdmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanelfurniture.AdapterAdmin.ItemGridViewAdapterAdmin;
import com.example.adminpanelfurniture.ModelAdmin.ItemGridViewAdminViewModel;
import com.example.adminpanelfurniture.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeAdminFragment extends Fragment {

    private RecyclerView recyclerView;
    private ItemGridViewAdapterAdmin itemGridViewAdapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private ArrayList<ItemGridViewAdminViewModel> list;
    private HomeAdminViewModel homeViewModel;

//    RecyclerView catagoriesRecyclerView;
//    ArrayList<CatagoriesModel> catagorieslist;
//    CatagoriesAdapter catagoriesAdapter;
    FirebaseFirestore firestore;
    CollectionReference collectionReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeAdminViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home_admin, container, false);
        //  final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //  textView.setText(s);
            }
        });

//-------------------------------------------
        recyclerView = root.findViewById(R.id.gridview_recyclerview);
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        reference = firebaseDatabase.getReference("Products");
        firestore=FirebaseFirestore.getInstance();
        collectionReference=firestore.collection("Catagoires");

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        list = new ArrayList<>();

       // collectionReference.document(categorystore).collection(Pbrand).document(ID)
             collectionReference .document("chair").collection("wooden").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Toast.makeText(getContext(), "added", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return root;
    }

}