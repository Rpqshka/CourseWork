package com.example.coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    EditText etEmail,etPassword, etPassword2,etPhone,etFio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Инициализация
        mAuth = FirebaseAuth.getInstance();
        /*mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //Пользователь вошел
                    Intent intent = new Intent(SignUp.this, MainWindow.class);
                    startActivity(intent);
                } else {
                    //Пользователь вышел
                }
            }
        };*/
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPassword2 = (EditText) findViewById(R.id.etPassword2);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etFio = (EditText) findViewById(R.id.etFIO);
        findViewById(R.id.btnSignUp1).setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSignUp1) {
            if (!etPassword.getText().toString().equals(etPassword2.getText().toString()))
                Toast.makeText(SignUp.this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
            else if (etPassword.getText().toString().length() < 6 || etPassword.getText().toString().length() > 16)
                Toast.makeText(SignUp.this, "Пароль должен содержать от 6 до 16 символов", Toast.LENGTH_SHORT).show();
            else if(etFio.getText().toString().isEmpty()){
                Toast.makeText(SignUp.this, "Введите фио", Toast.LENGTH_SHORT).show();
            }
            else if(etEmail.getText().toString().isEmpty()){
                Toast.makeText(SignUp.this, "Введите почту", Toast.LENGTH_SHORT).show();
            }
            else if(etPhone.getText().toString().isEmpty()){
                Toast.makeText(SignUp.this, "Введите номер телефона", Toast.LENGTH_SHORT).show();
            }
            else
                registration(etEmail.getText().toString(), etPassword.getText().toString());
        }
    }

    public void registration(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    User user = new User(etFio.getText().toString(),etPhone.getText().toString(),etEmail.getText().toString());
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(SignUp.this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(SignUp.this, SignIn.class));
                                            }
                                            else{
                                                Toast.makeText(SignUp.this, "Регистрация провалена", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                } else {
                    Toast.makeText(SignUp.this, "Регистрация провалена", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
