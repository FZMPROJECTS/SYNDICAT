package com.example.syndicat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.syndicat.R;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {
    FirebaseAuth auth;
    Button btn;
    TextView textView;
    FirebaseUser user;
    CardView cardView,cardView2,cardView3,cardView4,cardView5;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        textView= findViewById(R.id.user);
        cardView=findViewById(R.id.card5);
        cardView2=findViewById(R.id.card1);
        cardView3=findViewById(R.id.card4);
        cardView4=findViewById(R.id.card2);
        cardView5=findViewById(R.id.card6);
        bottomNavigationView=findViewById(R.id.bottom_navigation);
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

        cardView.setOnClickListener(v-> {
            Intent intent = new Intent(Home.this,Map.class);
            startActivity(intent);
        });
        cardView2.setOnClickListener(v-> {
            Intent intent = new Intent(Home.this,Houses.class);
            startActivity(intent);
        });
        cardView3.setOnClickListener(v-> {
            Intent intent = new Intent(Home.this,Contact.class);
            startActivity(intent);
        });
        cardView4.setOnClickListener(v-> {
            Intent intent = new Intent(Home.this,Video.class);
            startActivity(intent);
        });
        cardView5.setOnClickListener(v-> {
            Intent intent = new Intent(Home.this,Calendrier.class);
            startActivity(intent);
        });
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.menuhome) {
                // Action à effectuer lorsque "HOME" est sélectionné
                Toast.makeText(this, "Home selected", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.menucontact) {
                // Action à effectuer lorsque "CONTACT" est sélectionné
                Toast.makeText(this, "Contact selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, Contact.class);
                startActivity(intent);
                finish();
            } else if (itemId == R.id.menusignout) {
                // Action à effectuer lorsque "Logout" est sélectionné
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, Authentification.class);
                startActivity(intent);
                finish();
            }
         else {
            // Aucun des cas précédents n'a été rencontré
            return false;
        }
        // Retourne vrai si un des cas précédents a été rencontré
        return true;

        });

    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        // Si un élément du menu est sélectionné, effectuez l'action appropriée
        switch (id) {
            case R.id.menu_home:
                // Action à effectuer lorsque "HOME" est sélectionné
                Toast.makeText(this, "Home selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_contact:
                // Action à effectuer lorsque "CONTACT" est sélectionné
                Toast.makeText(this, "Contact selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_signout:
                // Action à effectuer lorsque "Logout" est sélectionné
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, Authentification.class);
                startActivity(intent);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
