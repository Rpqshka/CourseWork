package com.example.coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity implements View.OnClickListener {
    private EditText etEmail;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        etEmail = (EditText) findViewById(R.id.etEmailChangePassword);
        findViewById(R.id.btnResetPass).setOnClickListener(this);
        auth = FirebaseAuth.getInstance();

    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnResetPass)
            resetPassword();
    }

    private void resetPassword() {
        String email = etEmail.getText().toString().trim();
        if(email.isEmpty()){
            Toast.makeText(ResetPassword.this, "Введите почту", Toast.LENGTH_SHORT).show();
        }
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ResetPassword.this, "Проверьте почту", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(ResetPassword.this, "Что-то пошло не так", Toast.LENGTH_SHORT).show();
            }
        });
    }
}