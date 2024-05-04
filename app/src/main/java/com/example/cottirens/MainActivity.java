package com.example.cottirens;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cottirens.Screens.AccountActivity;
import com.example.cottirens.Screens.AddCardActivity;
import com.example.cottirens.Screens.ChatActivity;
import com.example.cottirens.Screens.MakeOrderActivity;
import com.example.cottirens.Screens.TrackOrderActivity;
import com.example.cottirens.Utils.Constant;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void trackOrder(View view){
        startActivity(new Intent(this, TrackOrderActivity.class));
    }
    public void MakeOrder(View view){
        startActivity(new Intent(this, MakeOrderActivity.class));
    }
    public void OnlineChat(View view){
        startActivity(new Intent(this, ChatActivity.class)
                .putExtra("userId",Constant.getUserId(MainActivity.this)));
    }
    public void MakePayment(View view){
        startActivity(new Intent(this, AddCardActivity.class));
    }
    public void showLogoutAlert(View view){
        final CharSequence[] options = {"LogOut", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Select option");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("LogOut")) {
                    Constant.setAdminLoginStatus(MainActivity.this,false);
                    Constant.setUserLoginStatus(MainActivity.this,false);
                    startActivity(new Intent(MainActivity.this, AccountActivity.class)
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