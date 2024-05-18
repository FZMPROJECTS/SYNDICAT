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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    EditText editTextusername, editTextemail, editTextpwd, editTextHN, editTexttel;
    Button bouttonreg;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference; // Référence à la base de données Firebase
    ProgressBar progressBar;
    TextView already;

    @Override
    public void onStart() {
        super.onStart();
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
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
       // databaseReference = FirebaseDatabase.getInstance().getReference("users");

        editTextusername=findViewById(R.id.inputusername);
        editTextHN=findViewById(R.id.inputhomenumber);
        editTextemail=findViewById(R.id.inputEmail);
        editTexttel=findViewById(R.id.inputtel);
        editTextpwd=findViewById(R.id.inputPassword);
        bouttonreg=findViewById(R.id.buttonRegister);
        progressBar = findViewById(R.id.progressBar);
        already=findViewById(R.id.textView2);

        if(mAuth.getCurrentUser() != null)
        {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
            finish();

        }
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
                String email=editTextemail.getText().toString().trim();
                String pwd=editTextpwd.getText().toString().trim();
              /*  String username=editTextusername.getText().toString().trim();
                String homenum=editTextHN.getText().toString().trim();
                String tel=editTexttel.getText().toString().trim();*/
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Register.this,"Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pwd) )
                {
                    Toast.makeText(Register.this,"Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pwd.length() < 4){
                    editTextpwd.setError("Password must have at least 4 caracteres");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userId = user.getUid();
                            String email=editTextemail.getText().toString().trim();
                            String pwd=editTextpwd.getText().toString().trim();
                            String username = editTextusername.getText().toString().trim();
                            String homenum = editTextHN.getText().toString().trim();
                            String tel = editTexttel.getText().toString().trim();

                            // Créer un nouvel utilisateur avec les données supplémentaires
                            User newUser=new User(email,username, tel, pwd,  homenum);
                            //User newUser = new User(username, tel, homenum);

                            // Enregistrer l'utilisateur dans la base de données Firebase Realtime Database
                            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                            usersRef.child(userId).setValue(newUser);

                            Toast.makeText(Register.this, "Account Created.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Home.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Gérer les erreurs d'inscription
                            Log.e("REGISTER_ERROR", "Erreur d'inscription : " + task.getException().getMessage());
                            Toast.makeText(Register.this, "Erreur lors de la création du compte : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}
