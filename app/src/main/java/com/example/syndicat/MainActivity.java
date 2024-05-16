package com.example.syndicat;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 2000; // Durée du splash screen en millisecondes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageViewLogo = findViewById(R.id.imageViewLogo);
        Button bouttonlogin;
        bouttonlogin = findViewById(R.id.buttonLogin);
        // Animation pour l'effet de splash
        Animation splashAnimation = AnimationUtils.loadAnimation(this, R.anim.animation);
        imageViewLogo.startAnimation(splashAnimation);

        bouttonlogin.setOnClickListener(v-> {
            Intent intent = new Intent(MainActivity.this,Authentification.class);
            startActivity(intent);
        } );
    }
}