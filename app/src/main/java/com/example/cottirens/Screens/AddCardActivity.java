package com.example.cottirens.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.cottirens.MainActivity;
import com.example.cottirens.R;
import com.example.cottirens.Utils.Constant;

public class AddCardActivity extends AppCompatActivity {
     EditText user_name,card_number,cvc,expire_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        user_name=findViewById(R.id.user_name);
        card_number=findViewById(R.id.card_number);
        expire_date=findViewById(R.id.expire_date);
        cvc=findViewById(R.id.cvc);

    }

    public void addCard(View view){
       if(user_name.getText().toString().isEmpty()){
           user_name.setError("required");
       }else if(card_number.getText().toString().isEmpty()){
           card_number.setError("required");
       }else if(expire_date.getText().toString().isEmpty()){
           expire_date.setError("required");
       }else if(cvc.getText().toString().isEmpty()){
           cvc.setError("required");
       }else {

           Toast.makeText(AddCardActivity.this,"Your order  is placed",Toast.LENGTH_LONG).show();
           startActivity(new Intent(AddCardActivity.this, TrackOrderActivity.class)
                   .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
       }
    }
    public void finish(View view){
        finish();
    }
}