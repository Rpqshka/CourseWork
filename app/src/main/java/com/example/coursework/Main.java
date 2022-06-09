package com.example.coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Main extends AppCompatActivity implements View.OnClickListener {
    private FirebaseUser user;
    DatabaseReference roomsRef,userRef;

    Booking booking;
    int bookingCounter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        booking = new Booking();
        bookingCounter = 0;

        findViewById(R.id.btnProfile).setOnClickListener(this);
        findViewById(R.id.btnBooking).setOnClickListener(this);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference("Users");
        roomsRef = FirebaseDatabase.getInstance().getReference("Rooms");

        findViewById(R.id.btnBookStandart).setOnClickListener(this);
        findViewById(R.id.btnBookSuit).setOnClickListener(this);
        findViewById(R.id.btnBookPresident).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnProfile) {
            Intent intent = new Intent(Main.this, Profile.class);
            startActivity(intent);
        } else if (view.getId() == R.id.btnBooking) {
            Intent intent = new Intent(Main.this, Booking.class);
            startActivity(intent);
        }
        else if(bookingCounter == 1){
            Toast.makeText(Main.this,"Вы уже забронировали номер",Toast.LENGTH_LONG).show();
        }
        else if (view.getId() == R.id.btnBookStandart){
            Room room1 = new Room("Standart");

            userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("BookingCounter").setValue(1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
            userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Room")
                    .setValue(room1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(Main.this,"Вы забронировали стандартный номер",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Main.this, Booking.class));
                        }
                    });
        }
        else if (view.getId() == R.id.btnBookSuit) {
            Room room2 = new Room("Suit");
            userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("BookingCounter").setValue(1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
            userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("Room").setValue(room2).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(Main.this,"Вы забронировали сьют номер",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Main.this, Booking.class));
                        }
                    });
        }
        else if (view.getId() == R.id.btnBookPresident) {
            Room room3 = new Room("President");
            userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("BookingCounter").setValue(1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
            userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Room")
                    .setValue(room3).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(Main.this,"Вы забронировали президентский номер",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Main.this, Booking.class));
                        }
                    });
        }
        else {
            Toast.makeText(Main.this,"Что-то пошло не так",Toast.LENGTH_LONG).show();
        }
    }
}