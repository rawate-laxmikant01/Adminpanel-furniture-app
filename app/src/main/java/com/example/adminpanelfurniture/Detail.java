package com.example.adminpanelfurniture;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Detail extends AppCompatActivity {
    ImageView productimg;
    TextView totalquantity;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabaseRef;
    String id,quantity,img,category,brand;

    FirebaseFirestore firestore;
    CollectionReference collectionReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        firebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseRef=firebaseDatabase.getReference("data");
        id=getIntent().getStringExtra("id");
        quantity=getIntent().getStringExtra("quantity");
        img=getIntent().getStringExtra("img");
        category=getIntent().getStringExtra("category");
        brand=getIntent().getStringExtra("brand");


        firestore = FirebaseFirestore.getInstance();
        collectionReference = firestore.collection("Catagoires");



        productimg=findViewById(R.id.detailProductimg);

        Glide.with(this).load(img).into(productimg);


    }

//    public void updateProduct(View view) {
//    }
//
    public void deleteProduct(View view) {
        collectionReference.document(category).collection(brand).document(id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(Detail.this, "Task deleted...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Detail.this, MainAdminActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Detail.this, "Unable to delete Task.."+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}