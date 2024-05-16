package com.example.syndicat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePage extends AppCompatActivity {
    FirebaseAuth auth;
    Button btn;
    TextView textView;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        auth = FirebaseAuth.getInstance();
        btn = findViewById(R.id.logoutbut);
        textView= findViewById(R.id.user);
        user = auth.getCurrentUser();
        if(user == null)
        {
            Intent intent=new Intent(getApplicationContext(),Authentification.class);
            startActivity(intent);
            finish();
        }
        else{
            textView.setText(user.getEmail());

        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getApplicationContext(),Authentification.class);
                startActivity(intent);
                finish();
            }
        });
    }
}