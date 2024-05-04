package com.example.cottirens.Screens;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cottirens.Fragments.LoginFragment;
import com.example.cottirens.Fragments.SignUpFragment;
import com.example.cottirens.R;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        showLoginScreen();
    }
    // function to load login fragment in activity
    public void showLoginScreen(){
        getSupportFragmentManager().beginTransaction().replace(R.id.frag,new LoginFragment()).commit();
    }
    // function to call signup fragment in activity
    public void showSignUpScreen(){
        getSupportFragmentManager().beginTransaction().replace(R.id.frag,new SignUpFragment()).commit();
    }
}