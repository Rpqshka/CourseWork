package com.example.coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Booking extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    DatabaseReference userRef;
    ImageView imageBooking1;
    View viewBooking1;
    TextView tvCard1;
    Button btnBookClose, btnGoToMain;
    TextView bookingTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference("Users");

        btnGoToMain = (Button) findViewById(R.id.btnGoToMain);
        bookingTitle = (TextView) findViewById(R.id.tvTitleBooking);
        imageBooking1 = (ImageView) findViewById(R.id.imageBooking1);
        viewBooking1 = (View) findViewById(R.id.viewBooking1);
        tvCard1 = (TextView) findViewById(R.id.tvCard1);
        btnBookClose = (Button) findViewById(R.id.btnBookStandart);

        userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Room").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Room room = snapshot.getValue(Room.class);
                if(room != null){
                    String roomType = room.room;
                    bookingTitle.setVisibility(View.INVISIBLE);
                    btnGoToMain.setVisibility(View.INVISIBLE);

                    viewBooking1.setVisibility(View.VISIBLE);

                    tvCard1.setVisibility(View.VISIBLE);

                    btnBookClose.setVisibility(View.VISIBLE);

                    imageBooking1.setVisibility(View.VISIBLE);
                    switch (roomType){
                        case "Standart":
                            tvCard1.setText("Номер Стандарт");
                            imageBooking1.setImageResource(R.drawable.standart);
                            break;
                        case "Suit":
                            tvCard1.setText("Номер Сьют");
                            imageBooking1.setImageResource(R.drawable.suite);
                            break;
                        case "President":
                            tvCard1.setText("Президентский номер");
                            imageBooking1.setImageResource(R.drawable.president);
                            break;
                    }
                }
                else{
                    bookingTitle.setVisibility(View.VISIBLE);
                    btnGoToMain.setVisibility(View.VISIBLE);
                }

            }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Booking.this,"Что-то пошло не так",Toast.LENGTH_LONG).show();
                    }
                });


        findViewById(R.id.btnBookStandart).setOnClickListener(this);
        findViewById(R.id.btnHome).setOnClickListener(this);
        findViewById(R.id.btnProfile).setOnClickListener(this);
        findViewById(R.id.btnGoToMain).setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnProfile) {
            Intent intent = new Intent(Booking.this, Profile.class);
            startActivity(intent);
        } else if (view.getId() == R.id.btnHome) {
            Intent intent = new Intent(Booking.this, Main.class);
            startActivity(intent);
        } else if (view.getId() == R.id.btnGoToMain) {
            Intent intent = new Intent(Booking.this, Main.class);
            startActivity(intent);
        } else if (view.getId() == R.id.btnBookStandart) {
            userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Room").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Room room = snapshot.getValue(Room.class);
                    if (room != null) {
                    }
                    viewBooking1.setVisibility(View.INVISIBLE);
                    tvCard1.setVisibility(View.INVISIBLE);
                    btnBookClose.setVisibility(View.INVISIBLE);
                    imageBooking1.setVisibility(View.INVISIBLE);

                    bookingTitle.setVisibility(View.VISIBLE);
                    btnGoToMain.setVisibility(View.VISIBLE);
                    userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Room").removeValue();
                    Toast.makeText(Booking.this,"Вы успешно отменили бронь номера",Toast.LENGTH_LONG).show();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Booking.this,"Что-то пошло не так",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}