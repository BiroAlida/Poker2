package com.example.poker2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.poker2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_register, btn_login;
    private EditText et_loginEmail, et_password;
    private String email, password;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Boolean saveLogin;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference rootRef, itemsRef;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_register = findViewById(R.id.button_register);
        btn_login = findViewById(R.id.button_login);
        et_loginEmail = findViewById(R.id.editText_loginEmail);
        et_password = findViewById(R.id.editText_password);
        auth = FirebaseAuth.getInstance();


        firebaseDatabase = FirebaseDatabase.getInstance();
        rootRef = firebaseDatabase.getReference();
        itemsRef = rootRef.child("Users");


        btn_login.setOnClickListener(this);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    public void onClick(View v) {
        if (v == btn_login) {
            email = et_loginEmail.getText().toString().trim();
            password = et_password.getText().toString().trim();
            if(email.isEmpty())
            {
                et_loginEmail.setError(getString(R.string.regNameError));
                et_loginEmail.requestFocus();
                return;
            }
            if(password.isEmpty())
            {
                et_password.setError(getString(R.string.regEmailError));
                et_password.requestFocus();
                return;
            }

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful())
                    {
                        Toast.makeText(LoginActivity.this, "Invalid emsil or password", Toast.LENGTH_LONG).show();
                    }
                    else{
                        startActivity(new Intent(LoginActivity.this, GroupActivity.class));
                        finish();
                    }
                }
            });

        }
    }

}
