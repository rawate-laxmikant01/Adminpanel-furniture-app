package com.example.adminpanelfurniture.AdapterAdmin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adminpanelfurniture.Detail;
import com.example.adminpanelfurniture.ModelAdmin.ItemGridViewAdminViewModel;
import com.example.adminpanelfurniture.R;

import java.util.ArrayList;

public class ItemGridViewAdapterAdmin extends RecyclerView.Adapter<ItemGridViewAdapterAdmin.itemViewHolder> {

    ArrayList<ItemGridViewAdminViewModel> item;
    Context mcontexr;

    public ItemGridViewAdapterAdmin(ArrayList<ItemGridViewAdminViewModel> item, Context mcontexr) {
        this.item = item;
        this.mcontexr = mcontexr;
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_gridview,parent,false);
        return new itemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {

        ItemGridViewAdminViewModel itemmodel=item.get(position);

        holder.itemname.setText(item.get(position).getName());

        holder.price.setText(item.get(position).getPrice());

        holder.mrpprice.setText(item.get(position).getMrpprice());

        holder.discount.setText(item.get(position).getDiscount());

        String category=item.get(position).getCategory();
        String brand=item.get(position).getCategory();


        String id=itemmodel.getId();
        String quentity=itemmodel.getTotalquantity();
        String img=itemmodel.getItemimg();
//
        Glide.with(mcontexr)
                .load(img)
                .into(holder.itemimg);


        holder.itemcardview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                    holder.edititem.setVisibility(View.VISIBLE);

               return true;
            }
        });

        holder.edititem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontexr, "Editing", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(mcontexr, Detail.class);
                intent.putExtra("id",id);
                intent.putExtra("quantity",quentity);
                intent.putExtra("img",img);
                intent.putExtra("category",category);
                intent.putExtra("brand",brand);
                mcontexr.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder {

        ImageView itemimg,edititem;
        TextView itemname,mrpprice,price,discount;
        View itemcardview;
        public itemViewHolder(@NonNull View itemView) {
            super(itemView);

            itemimg=itemView.findViewById(R.id.img_gridview_id);
            itemname=itemView.findViewById(R.id.tv_name);
            mrpprice=itemView.findViewById(R.id.tv_mrpprice);
            price=itemView.findViewById(R.id.tv_price);
            discount=itemView.findViewById(R.id.tv_discount);
            edititem=itemView.findViewById(R.id.edititem);

            itemcardview=itemView.findViewById(R.id.itemcardview);
           mrpprice.setPaintFlags(mrpprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);



         //   TextView someTextView = (TextView) findViewById(R.id.some_text_view);
//            someTextView.setText(someString);
//            someTextView.setPaintFlags(someTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
