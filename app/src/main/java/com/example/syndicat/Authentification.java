package com.example.syndicat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Authentification extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button bouttonlogin;
    private Button button_signup;
    private ProgressBar pb;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Vérifier si l'utilisateur est déjà connecté
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Rediriger vers la page d'accueil
            startActivity(new Intent(Authentification.this, HomePage.class));
            finish(); // Terminer l'activité d'authentification
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);

        // Initialisation des vues et de FirebaseAuth
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        bouttonlogin = findViewById(R.id.bouttonlogin);
        button_signup = findViewById(R.id.sign_up);
        pb = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

        bouttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupération des valeurs des champs de saisie
                String emailStr = email.getText().toString();
                String passwordStr = password.getText().toString();

                // Vérification des champs non vides
                if (TextUtils.isEmpty(emailStr) || TextUtils.isEmpty(passwordStr)) {
                    Toast.makeText(Authentification.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Afficher la ProgressBar pendant la connexion
                pb.setVisibility(View.VISIBLE);

                // Authentification de l'utilisateur avec Firebase
                mAuth.signInWithEmailAndPassword(emailStr, passwordStr)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                pb.setVisibility(View.GONE); // Masquer la ProgressBar après l'opération

                                if (task.isSuccessful()) {
                                    // Connexion réussie, rediriger vers la page d'accueil
                                    startActivity(new Intent(Authentification.this, HomePage.class));
                                    finish(); // Terminer l'activité d'authentification
                                } else {
                                    // Échec de la connexion, afficher un message d'erreur
                                    Toast.makeText(Authentification.this, "Échec de l'authentification", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        // Gérer le clic sur le bouton d'inscription
        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rediriger vers l'activité d'inscription
                startActivity(new Intent(Authentification.this, Register.class));
            }
        });
    }
}
