package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomePage extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        //Если пользователь уже авторизован в приложении
        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(HomePage.this, Main.class);
            startActivity(intent);
        }
    }

    public void showSignUpWindow(View view) {
        Intent intent = new Intent(this,SignUp.class);
        startActivity(intent);
    }
    public void showSignInWindow(View view) {
        Intent intent = new Intent(this,SignIn.class);
        startActivity(intent);
    }

}