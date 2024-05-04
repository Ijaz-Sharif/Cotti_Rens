package com.example.cottirens.Screens;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cottirens.Model.Cloths;
import com.example.cottirens.R;
import com.example.cottirens.Utils.Constant;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class MakeOrderActivity extends AppCompatActivity {
      TextView selectedDate;
      EditText user_comment;
      RadioButton btnRegular,btnDelicate,btnCotton,btnWool,btnSilk;
      RecyclerView recyclerView;
      ArrayList<Cloths> clothsArrayList=new ArrayList<Cloths>();
    private Calendar calendar;
    String selectedCloth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);
        // Initialize calendar with current date
        calendar = Calendar.getInstance();
        selectedDate=findViewById(R.id.selectedDate);
        user_comment=findViewById(R.id.user_comment);
        btnRegular=findViewById(R.id.btnRegular);
        btnDelicate=findViewById(R.id.btnDelicate);
        btnCotton=findViewById(R.id.btnCotton);
        btnWool=findViewById(R.id.btnWool);
        btnSilk=findViewById(R.id.btnSilk);
        recyclerView=findViewById(R.id.recy);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        addData();

    }

    public void addData(){
        clothsArrayList.clear();
        clothsArrayList.add(new Cloths("Shirt",R.drawable.shirt));
        clothsArrayList.add(new Cloths("Skirt",R.drawable.skirt));
        clothsArrayList.add(new Cloths("Hoodie",R.drawable.hodie));
        clothsArrayList.add(new Cloths("Pent",R.drawable.pent));
        clothsArrayList.add(new Cloths("Suit",R.drawable.suit));
        clothsArrayList.add(new Cloths("Jacket",R.drawable.jacket));
        clothsArrayList.add(new Cloths("Carpet",R.drawable.carpet));
        Date currentDate = new Date();
        // Format the date
        SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yy", Locale.getDefault());
        String formattedDate = dateFormat.format(currentDate);
        selectedDate.setText(formattedDate);
        recyclerView.setAdapter(new ArrayAdapter());
    }
public void selectDate(View view){
 showDatePickerDialog();

}

    private void showDatePickerDialog() {
        // Set minimum date to today
        Calendar minDate = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            // Update calendar with selected date
            calendar.set(year, month, dayOfMonth);

            // Update TextView with selected date
            updateTextView();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        // Set minimum date for DatePickerDialog
        datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());

        // Show the DatePickerDialog
        datePickerDialog.show();
    }

    private void updateTextView() {
        // Format the date
        SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yy", Locale.getDefault());
        String formattedDate = dateFormat.format(calendar.getTime());

        // Set the formatted date to TextView
        selectedDate.setText(formattedDate);
    }

    public String createFavId() throws Exception{
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }
public void makeOrder(View view){


        if(user_comment.getText().toString().isEmpty()){
            user_comment.setError("required");
        }else {

            String id = null;
            try {
                id = createFavId().substring(0,7);
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("UserOrder")
                        .child(Constant.getUserId(MakeOrderActivity.this))
                        .child(id);
                myRef.child("ClothName").setValue(selectedCloth);
                myRef.child("OrderId").setValue(id);
                myRef.child("UserId").setValue(Constant.getUserId(MakeOrderActivity.this));
                myRef.child("RemainingTime").setValue("Pending");
                myRef.child("SelectedDate").setValue(selectedDate.getText().toString());
                if(btnCotton.isChecked()){
                    myRef.child("ChooseMatrial").setValue("Cotton");
                }else if(btnSilk.isChecked()){
                    myRef.child("ChooseMatrial").setValue("Silk");
                }else if(btnWool.isChecked()){
                    myRef.child("ChooseMatrial").setValue("Wool");
                }


                if(btnDelicate.isChecked()){
                    myRef.child("DryCleanType").setValue("Delicate");

                }else if(btnRegular.isChecked()){
                    myRef.child("DryCleanType").setValue("Regular");

                }
                startActivity(new Intent(MakeOrderActivity.this,AddCardActivity.class));

            } catch (Exception e) {
                e.printStackTrace();
            }



        }
}
    public void finish(View view){
        finish();
    }


    public class ArrayAdapter extends RecyclerView.Adapter<ArrayAdapter.ImageViewHoler> {

        public ArrayAdapter(){

        }
        @NonNull
        @Override
        public ArrayAdapter.ImageViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(MakeOrderActivity.this).inflate(R.layout.item_cloths,parent,false);
            return  new ArrayAdapter.ImageViewHoler(v);
        }
        @Override
        public void onBindViewHolder(@NonNull final ArrayAdapter.ImageViewHoler holder, @SuppressLint("RecyclerView") int position) {



            holder.cloth_name.setText(clothsArrayList.get(position).getName());
            holder.cloth_image.setImageDrawable(getResources().getDrawable(clothsArrayList.get(position).getImage()));
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    selectedCloth=clothsArrayList.get(position).getName();


                }
            });
        }

        @Override
        public int getItemCount() {
            return clothsArrayList.size();
        }

        public class ImageViewHoler extends RecyclerView.ViewHolder {

            TextView cloth_name;
            CardView cardView;
            ImageView cloth_image;
            public ImageViewHoler(@NonNull View itemView) {
                super(itemView);
                cloth_name=itemView.findViewById(R.id.cloth_name);
                cloth_image=itemView.findViewById(R.id.cloth_image);
                cardView=itemView.findViewById(R.id.cardView);

            }
        }
    }
}