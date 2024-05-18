package com.example.syndicat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    EditText editTextusername, editTextemail,editTextpwd,editTextpwd2,editTexttel;
    Button bouttonreg;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView already;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent= new Intent(getApplicationContext(),HomePage.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        editTextusername=findViewById(R.id.inputusername);
        editTextemail=findViewById(R.id.inputEmail);
        editTexttel=findViewById(R.id.inputtel);
        editTextpwd=findViewById(R.id.inputPassword);
        //editTextpwd2=findViewById(R.id.inputConfirmPassword);
        bouttonreg=findViewById(R.id.buttonRegister);
        progressBar = findViewById(R.id.progressBar);
        already=findViewById(R.id.textView2);
        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),Authentification.class);
                startActivity(intent);
                finish();
            }
        });

        bouttonreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String username,email,tel,pwd,pwd2;
                username = String.valueOf(editTextusername.getText());
                email = String.valueOf(editTextemail.getText());
                tel = String.valueOf(editTexttel.getText());
                pwd = String.valueOf(editTextpwd.getText());

                //pwd2 = String.valueOf(editTextpwd2.getText());
                if(TextUtils.isEmpty(username) )
                {
                    Toast.makeText(Register.this,"Enter UserName",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(email) )
                {
                    Toast.makeText(Register.this,"Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(tel) )
                {
                    Toast.makeText(Register.this,"Enter Telephone",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pwd) )
                {
                    Toast.makeText(Register.this,"Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                /*if(TextUtils.isEmpty(pwd2) )
                {
                    Toast.makeText(Register.this,"Confirm your Password",Toast.LENGTH_SHORT).show();
                    return;
                }*/
                mAuth.createUserWithEmailAndPassword(email, pwd)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(Register.this, "Account Created.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), HomePage.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Si l'inscription échoue, afficher le message d'erreur
                                    Log.e("REGISTER_ERROR", "Erreur d'inscription : " + task.getException().getMessage());
                                    Toast.makeText(Register.this, "Erreur lors de la création du compte : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });


    }
}

