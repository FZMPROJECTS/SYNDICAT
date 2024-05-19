package com.example.syndicat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Houses extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fb;
    ImageButton imageButton;
    HouseAdapter houseAdapter;
    List<House> houseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_houses);
        setTitle("Search here ..");

        // Initialisation de la liste des maisons
        houseList = new ArrayList<>();

        // Initialisation de l'adaptateur
        houseAdapter = new HouseAdapter(this, houseList);

        // Initialisation de la RecyclerView
        recyclerView = findViewById(R.id.recyclerViewHouses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(houseAdapter);

        fb = findViewById(R.id.add);
        imageButton = findViewById(R.id.backButton);

        // Gestionnaire de clic sur le bouton de retour
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Houses.this, Home.class);
                startActivity(intent);
            }
        });

        // Gestionnaire de clic sur le bouton d'ajout de maison
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Houses.this, AddHouse.class);
                startActivity(intent);
            }
        });

        // Initialisez la base de données Firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        // Obtenez une référence à votre nœud "houses" dans la base de données
        DatabaseReference databaseReference = firebaseDatabase.getReference("houses");

        // Ajoutez un écouteur pour récupérer les données de la base de données en temps réel
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Effacez les données existantes de la liste
                houseList.clear();
                // Parcourez toutes les données dans le DataSnapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Récupérez chaque maison et ajoutez-la à la liste
                    House house = snapshot.getValue(House.class);
                    house.setId(snapshot.getKey());  // Assurez-vous que l'ID est correctement défini
                    houseList.add(house);
                }
                // Notifiez à l'adaptateur que les données ont changé
                houseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Gestion des erreurs de base de données
                Log.e("Houses", "Error fetching houses", databaseError.toException());
                // Afficher un message à l'utilisateur pour l'informer de l'échec de la récupération des données
                Toast.makeText(Houses.this, "Erreur lors de la récupération des maisons", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
