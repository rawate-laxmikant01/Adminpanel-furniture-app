package com.example.adminpanelfurniture;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.adminpanelfurniture.ModelAdmin.ItemGridViewAdminViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class AddProduct extends AppCompatActivity {

    ImageView productimg;
    EditText productname, productprice, productdescription, productmrp, productquantity,productbrand,productcolor;
    Spinner category;
    String categorylist[] = {"Outdoor","sofas","Beds","Accessories","Tables","Rugs"};

    ArrayAdapter<String> catagoryadapter;

    String categorystore;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Button addproduct;
    ItemGridViewAdminViewModel model;

    FirebaseFirestore firestore;
    CollectionReference collectionReference;


    String imgurl;
    FirebaseStorage storage;
    StorageReference reference;
    String Pname, PPrice, Pdes, PMrp, PQuantiy, ID,Pbrand,Pcolor,searchname;

    public Uri imageuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        productcolor=findViewById(R.id.color);
        category =findViewById(R.id.category);
        productbrand=findViewById(R.id.Brand);
        productmrp = findViewById(R.id.Setproductmrp);
        productimg = findViewById(R.id.SetProductImg);
        productname = findViewById(R.id.SetproductName);
        productprice = findViewById(R.id.SetProductPrice);
        productquantity = findViewById(R.id.SetproductQuantity);
        productdescription = findViewById(R.id.SetProductDescription);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Products");
        addproduct = findViewById(R.id.btnAddProduct);

        storage = FirebaseStorage.getInstance();
        reference = storage.getReference("Catagoires");

        firestore = FirebaseFirestore.getInstance();
        collectionReference = firestore.collection("Catagoires");

        catagoryadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categorylist);
        category.setAdapter(catagoryadapter);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categorystore = categorylist[position];

                //temperory value have been stored in ethinicity_store of list which user have been selected.
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AddProduct.this, "ethernity not selecteed", Toast.LENGTH_SHORT).show();
            }
        });



        productimg.setImageDrawable(getResources().getDrawable(R.drawable.addproduct));

        productimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImg();
            }
        });

        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pname = productname.getText().toString().trim();
                PPrice = productprice.getText().toString().trim();
                Pdes = productdescription.getText().toString().trim();
                PMrp = productmrp.getText().toString().trim();
                PQuantiy = productquantity.getText().toString().trim();
                Pbrand=productbrand.getText().toString().trim();
                Pcolor=productcolor.getText().toString().trim();

                searchname=Pname.toLowerCase().trim();

                //  ID = databaseReference.push().getKey();

                int orgprice = Integer.parseInt(productmrp.getText().toString());
                int price = Integer.parseInt(productprice.getText().toString());
                int i = 100;
                int per = (orgprice - price);
                int dis = per / i;

                String discount = String.valueOf(dis * i);


                model = new ItemGridViewAdminViewModel(Pname, imgurl, PPrice, PMrp, discount, PQuantiy, ID,categorystore,Pbrand,Pcolor,searchname);


//                databaseReference.child(ID).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(AddProduct.this, "Product Added", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(AddProduct.this, MainAdminActivity.class));
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(AddProduct.this, "Unable to add Product " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });

                collectionReference.document(categorystore).collection("local").document(ID).set(model).
                        addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(AddProduct.this, "Product Added", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AddProduct.this, MainAdminActivity.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddProduct.this, "ERROR" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private void chooseImg() {

        Intent intent = new Intent();
        intent.setType("Image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(intent, 1);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            imageuri = data.getData();
            productimg.setImageURI(imageuri);
            uploadpicture();
        }
    }

    private void uploadpicture() {
        final String randomID = UUID.randomUUID().toString();
        if (imageuri != null) {

            //   StorageReference riversRef = reference.child(randomID);
            StorageReference riversRef = reference.child(randomID);
            riversRef.putFile(imageuri)                    // Register observers to listen for when the download is done or if it fails
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            Toast.makeText(AddProduct.this, "Unable to Add product Photo" + exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    Toast.makeText(AddProduct.this, "ProductImage added", Toast.LENGTH_SHORT).show();
                                                    riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri downloadPhotoUrl) {
                                                            imgurl = downloadPhotoUrl.toString();
                                                            ID = randomID;
                                                        }
                                                    });
                                                }
                                            }
            );

        } else {
            Toast.makeText(this, "Unable to add image", Toast.LENGTH_SHORT).show();
        }
    }
}