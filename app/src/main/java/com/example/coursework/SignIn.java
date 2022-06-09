package com.example.coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignIn extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    EditText etEmail;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        //Инициализация
        mAuth = FirebaseAuth.getInstance();
        /*
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //Пользователь вошел
                    Intent intent = new Intent(SignIn.this, MainWindow.class);
                    startActivity(intent);
                } else {
                    //Пользователь вышел
                }
            }
        };*/
        etEmail = (EditText) findViewById(R.id.etEmail_signin);
        etPassword = (EditText) findViewById(R.id.etPassword_signin);
        findViewById(R.id.btnSignIn1).setOnClickListener(this);

        //Если пользователь уже авторизован в приложении
        /*FirebaseUser user = mAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(SignIn.this, MainWindow.class);
            startActivity(intent);
        }*/

    }

    @Override
    public void onClick(View view) {
        if (etEmail.getText().toString().isEmpty()){
            Toast.makeText(SignIn.this, "Введите почту", Toast.LENGTH_SHORT).show();
        }
        else if (etPassword.getText().toString().length() < 6 || etPassword.getText().toString().length() > 16)
            Toast.makeText(SignIn.this, "Пароль должен содержать от 6 до 16 символов", Toast.LENGTH_SHORT).show();
        else
            signing(etEmail.getText().toString(), etPassword.getText().toString());

    }

    public void signing(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignIn.this, "Авторизация успешна", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignIn.this, Main.class));

                }
                else
                    Toast.makeText(SignIn.this, "Авторизация провалена", Toast.LENGTH_SHORT).show();
            }
        });

    }

}