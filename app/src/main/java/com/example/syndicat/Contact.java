package com.example.syndicat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Contact extends AppCompatActivity {

    EditText editTextRecipient, editTextSubject, editTextMessage;
    Button contactButton;
    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        editTextRecipient = findViewById(R.id.editTextRecipient);
        editTextSubject = findViewById(R.id.editTextSubject);
        editTextMessage = findViewById(R.id.editTextMessage);
        contactButton = findViewById(R.id.contact_button);

        imageButton=(ImageButton)findViewById(R.id.backButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Contact.this,Home.class);
                startActivity(intent);
            }
        });
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipient = editTextRecipient.getText().toString().trim();
                String subject = editTextSubject.getText().toString().trim();
                String message = editTextMessage.getText().toString().trim();

                if (recipient.isEmpty() || subject.isEmpty() || message.isEmpty()) {
                    Toast.makeText(Contact.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    sendEmail(recipient, subject, message);
                }
            }
        });
    }

    private void sendEmail(String recipient, String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            startActivity(Intent.createChooser(intent, "Choose an Email client"));
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

