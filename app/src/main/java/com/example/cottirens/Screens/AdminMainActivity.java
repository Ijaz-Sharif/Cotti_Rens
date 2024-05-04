package com.example.cottirens.Screens;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cottirens.MainActivity;
import com.example.cottirens.Model.UserOrder;
import com.example.cottirens.R;
import com.example.cottirens.Utils.Constant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class AdminMainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<UserOrder> userOrderArrayList=new ArrayList<UserOrder>();
    private Dialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
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

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UserOrder");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                    for(DataSnapshot dataSnapshot2:dataSnapshot1.getChildren()){
                        userOrderArrayList.add(new UserOrder(
                                dataSnapshot2.child("RemainingTime").getValue(String.class)
                                , dataSnapshot2.child("ClothName").getValue(String.class)
                                , dataSnapshot2.child("OrderId").getValue(String.class)
                                , dataSnapshot2.child("UserId").getValue(String.class)
                        ));

                    }

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
            View v= LayoutInflater.from(AdminMainActivity.this).inflate(R.layout.item_user_order,parent,false);
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

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final CharSequence[] options = {"Start Chat","Update Time", "Cancel"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(AdminMainActivity.this);
                    builder.setTitle("Select option");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            if (options[item].equals("Start Chat")) {
                                startActivity(new Intent(AdminMainActivity.this, ChatActivity.class)
                                        .putExtra("userId",userOrderArrayList.get(position).getUserId()));
                                dialog.dismiss();
                            } else if (options[item].equals("Cancel")) {
                                dialog.dismiss();
                            } else if (options[item].equals("Update Time")) {
                                updateTime(userOrderArrayList.get(position).getUserId(),userOrderArrayList.get(position).getOrderId());
                                dialog.dismiss();
                            }

                        }
                    });
                    builder.show();



                }
            });


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


    public void updateTime(String userId,String orderId){

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("UserOrder")
                .child(userId).child(orderId);



        // Get the current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create a time picker dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Handle the selected time
                        String selectedTime = String.format("%02d:%02d", hourOfDay, minute);
                        myRef.child("RemainingTime").setValue(selectedTime);
                        getData();
                    }
                }, hour, minute, true); // true for 24-hour time format

        // Show the time picker dialog
        timePickerDialog.show();


    }

    public void showLogoutAlert(View view){
        final CharSequence[] options = {"LogOut", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminMainActivity.this);
        builder.setTitle("Select option");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("LogOut")) {
                    Constant.setAdminLoginStatus(AdminMainActivity.this,false);
                    Constant.setUserLoginStatus(AdminMainActivity.this,false);
                    startActivity(new Intent(AdminMainActivity.this, AccountActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    dialog.dismiss();
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }
        });
        builder.show();
    }
}