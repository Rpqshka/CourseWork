package com.example.coursework;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity implements View.OnClickListener{
    private FirebaseUser user;

    private String userId;

    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);




        user = FirebaseAuth.getInstance().getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();


        TextView textEmail = (TextView) findViewById(R.id.textEmailProfile);
        TextView textFio = (TextView) findViewById(R.id.textFIOProfile);
        TextView textPhone = (TextView) findViewById(R.id.textPhoneProfile);

        userRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){
                    String fio = userProfile.fio;
                    String email = userProfile.email;
                    String phone = userProfile.phone;

                    textEmail.setText(email);
                    textFio.setText(fio);
                    textPhone.setText(phone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this,"Что-то пошло не так",Toast.LENGTH_LONG).show();
            }
        });
        findViewById(R.id.btnLogout).setOnClickListener(this);
        findViewById(R.id.btnHome).setOnClickListener(this);
        findViewById(R.id.btnBooking).setOnClickListener(this);
        findViewById(R.id.bntChangePassword).setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLogout)
            logoutUser();
        else if (view.getId() == R.id.bntChangePassword)
            startActivity(new Intent(this, ResetPassword.class));
        else if(view.getId() == R.id.btnHome){
            Intent intent = new Intent(Profile.this, Main.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.btnBooking){
            Intent intent = new Intent(Profile.this, Booking.class);
            startActivity(intent);
        }


    }
    public void logoutUser() {
        Toast.makeText(Profile.this,"Вы успешно вышли из аккаунта",Toast.LENGTH_LONG).show();
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(Profile.this, HomePage.class);
        startActivity(intent);
    }
}
