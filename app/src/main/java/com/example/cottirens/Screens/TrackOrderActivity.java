package com.example.cottirens.Screens;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cottirens.Model.Cloths;
import com.example.cottirens.Model.UserOrder;
import com.example.cottirens.R;
import com.example.cottirens.Utils.Constant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TrackOrderActivity extends AppCompatActivity {
       RecyclerView recyclerView;
       ArrayList<UserOrder> userOrderArrayList=new ArrayList<UserOrder>();
    private Dialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);
        recyclerView=findViewById(R.id.recy);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
/////loading dialog
        loadingDialog=new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
         getData();
    }

    public void getData(){
        loadingDialog.show();
        userOrderArrayList.clear();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UserOrder")
                .child(Constant.getUserId(TrackOrderActivity.this));
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    userOrderArrayList.add(new UserOrder(
                            dataSnapshot1.child("RemainingTime").getValue(String.class)
                            , dataSnapshot1.child("ClothName").getValue(String.class)
                            , dataSnapshot1.child("OrderId").getValue(String.class)
                            , dataSnapshot1.child("UserId").getValue(String.class)
                    ));

                }

                recyclerView.setAdapter(new ArrayAdapter());
                loadingDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public class ArrayAdapter extends RecyclerView.Adapter<ArrayAdapter.ImageViewHoler> {

        public ArrayAdapter(){

        }
        @NonNull
        @Override
        public ArrayAdapter.ImageViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(TrackOrderActivity.this).inflate(R.layout.item_user_order,parent,false);
            return  new ArrayAdapter.ImageViewHoler(v);
        }
        @Override
        public void onBindViewHolder(@NonNull final ArrayAdapter.ImageViewHoler holder, @SuppressLint("RecyclerView") int position) {



            holder.cloth_name.setText(userOrderArrayList.get(position).getCloth());
            holder.order_number.setText("Order # "+userOrderArrayList.get(position).getOrderId());
            holder.remaining_time.setText(userOrderArrayList.get(position).getRemainingTime());

             if(userOrderArrayList.get(position).getCloth().equals("Shirt")){

             }else  if(userOrderArrayList.get(position).getCloth().equals("Shirt")){
                 holder.cloth_image.setImageDrawable(getResources().getDrawable(R.drawable.shirt));
             }else  if(userOrderArrayList.get(position).getCloth().equals("Skirt")){
                 holder.cloth_image.setImageDrawable(getResources().getDrawable(R.drawable.skirt));
             }else  if(userOrderArrayList.get(position).getCloth().equals("Hoodie")){
                 holder.cloth_image.setImageDrawable(getResources().getDrawable(R.drawable.hodie));
             }else  if(userOrderArrayList.get(position).getCloth().equals("Pent")){
                 holder.cloth_image.setImageDrawable(getResources().getDrawable(R.drawable.pent));
             }else  if(userOrderArrayList.get(position).getCloth().equals("Suit")){
                 holder.cloth_image.setImageDrawable(getResources().getDrawable(R.drawable.suit));
             }else  if(userOrderArrayList.get(position).getCloth().equals("Jacket")){
                 holder.cloth_image.setImageDrawable(getResources().getDrawable(R.drawable.jacket));
             }else  if(userOrderArrayList.get(position).getCloth().equals("Carpet")){
                 holder.cloth_image.setImageDrawable(getResources().getDrawable(R.drawable.carpet));
             }


        }

        @Override
        public int getItemCount() {
            return userOrderArrayList.size();
        }

        public class ImageViewHoler extends RecyclerView.ViewHolder {

            TextView cloth_name,remaining_time,order_number;
            CardView cardView;
            ImageView cloth_image;
            public ImageViewHoler(@NonNull View itemView) {
                super(itemView);
                cloth_name=itemView.findViewById(R.id.cloth_name);
                order_number=itemView.findViewById(R.id.order_number);
                remaining_time=itemView.findViewById(R.id.remaining_time);
                cloth_image=itemView.findViewById(R.id.cloth_image);
                cardView=itemView.findViewById(R.id.cardView);

            }
        }
    }

    public void finish(View view){
        finish();
    }
}