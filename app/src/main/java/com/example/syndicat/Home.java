package com.example.syndicat;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Actions à effectuer en fonction de l'élément du menu cliqué
        switch (id) {
            case R.id.menu_home:
                // Action pour le menu "HOME"
                Toast.makeText(this, "Home selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_contact:
                // Action pour le menu "CONTACT"
                Toast.makeText(this, "Contact selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_signout:
                // Action pour le menu "Logout"
                Toast.makeText(this, "Sign out selected", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
