package com.example.poker2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.poker2.R;
import com.example.poker2.Classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name, et_email, et_password;
    private Button btn_register;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_name = (EditText) findViewById(R.id.editText_name);
        et_email = (EditText) findViewById(R.id.editText_email);
        et_password = (EditText) findViewById(R.id.editText_passw);
        mAuth = FirebaseAuth.getInstance();


        findViewById(R.id.button).setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null)
        {
            //handle the already login user
        }
    }

    private void registerUser()
    {
        final String name = et_name.getText().toString().trim();
        final String email = et_email.getText().toString().trim();
        final String password = et_password.getText().toString().trim();

        if(name.isEmpty())
        {
            et_name.setError(getString(R.string.regNameError));
            et_name.requestFocus();
            return;
        }
        if(email.isEmpty())
        {
            et_email.setError(getString(R.string.regEmailError));
            et_email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            et_email.setError(getString(R.string.invalidEmail));
            et_email.requestFocus();
            return;
        }

        if(password.isEmpty())
        {
            et_password.setError(getString(R.string.regPassError));
            et_password.requestFocus();
            return;
        }

        if(password.length() < 6)
        {
            et_password.setError(getString(R.string.invalidPassword));
            et_password.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    // we will store the additional fields in firebase db
                    User user = new User(name,email,password,0);
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() { // a usert a sajat id-ja ala tarolom melyet az authentication soran kap
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(loginIntent);
                            }
                            else
                            {
                                Toast.makeText(RegisterActivity.this, "Registration failed, please try again!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(RegisterActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.button:
                registerUser();
                break;
        }
    }


}

