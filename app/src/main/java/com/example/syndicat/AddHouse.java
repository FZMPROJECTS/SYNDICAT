package com.example.syndicat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddHouse extends AppCompatActivity {

    // Références à Firebase Database et Storage
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    // Vues du formulaire
    private EditText etageEditText;
    private EditText numeroEditText;
    private EditText prixEditText;
    private EditText actionEditText;
    private EditText metrageEditText;
    private ProgressBar progressBar;
    ImageButton imageButton;

    // Constante pour identifier le choix de l'image
    private static final int PICK_IMAGE_REQUEST = 1;

    // URI de l'image sélectionnée
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_house);

        // Initialise les références à Firebase Database et Storage
        databaseReference = FirebaseDatabase.getInstance().getReference("houses");
        storageReference = FirebaseStorage.getInstance().getReference("house_images");

        // Initialiser les vues du formulaire
        etageEditText = findViewById(R.id.etage);
        numeroEditText = findViewById(R.id.numero);
        prixEditText=findViewById(R.id.prix);
        actionEditText = findViewById(R.id.action);
        metrageEditText = findViewById(R.id.metrage);
        progressBar = findViewById(R.id.progressBar);
        imageButton=(ImageButton)findViewById(R.id.backButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddHouse.this,Houses.class);
                startActivity(intent);
            }
        });
        // Bouton Save
        Button saveButton = findViewById(R.id.buttonRegister);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveHouse(v);
            }
        });
    }

    // Méthode appelée lorsque le bouton "Select Image" est cliqué
    public void selectImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    // Méthode appelée lorsque l'utilisateur a sélectionné une image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
        }
    }

    // Méthode appelée lorsque le bouton "Save" est cliqué
    public void saveHouse(View view) {
        // Vérifier si une image a été sélectionnée
        if (imageUri == null) {
            Toast.makeText(this, "Veuillez sélectionner une image", Toast.LENGTH_SHORT).show();
            return;
        }
        // Ajout de l'instruction Log pour vérifier l'URI de l'image
        Log.d("AddHouse", "URI de l'image : " + imageUri);

        // Récupérer les valeurs saisies dans le formulaire
        String etage = etageEditText.getText().toString().trim();
        String numero = numeroEditText.getText().toString().trim();
        String action = actionEditText.getText().toString().trim();
        String prixStr = prixEditText.getText().toString().trim();
        String metrageStr = metrageEditText.getText().toString().trim();

        // Vérifier si toutes les informations sont saisies
        if (etage.isEmpty() || numero.isEmpty() || action.isEmpty() || metrageStr.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convertir le métrage en Double
        Double metrage = Double.parseDouble(metrageStr);
        Double prix = Double.parseDouble(prixStr);

        // Afficher la barre de progression
        progressBar.setVisibility(View.VISIBLE);

        // Créer une référence dans Firebase Storage avec un nom unique pour l'image
        StorageReference imageRef = storageReference.child(System.currentTimeMillis() + ".jpg");

        // Téléverser l'image dans Firebase Storage
        imageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Récupérer l'URL de l'image téléchargée
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        // Créer un nouvel objet House avec les informations saisies et l'URL de l'image
                        House house = new House(numero, etage, action, metrage,prix, uri.toString());

                        // Enregistrer la maison dans la base de données Firebase
                        String houseId = databaseReference.push().getKey();
                        databaseReference.child(houseId).setValue(house)
                                .addOnCompleteListener(task -> {
                                    // Masquer la barre de progression
                                    progressBar.setVisibility(View.GONE);

                                    if (task.isSuccessful()) {
                                        Toast.makeText(AddHouse.this, "Maison enregistrée avec succès", Toast.LENGTH_SHORT).show();
                                        // Effacer les champs du formulaire après l'enregistrement réussi
                                        numeroEditText.setText("");
                                        etageEditText.setText("");
                                        actionEditText.setText("");
                                        metrageEditText.setText("");
                                        prixEditText.setText("");
                                        // Réinitialiser l'URI de l'image sélectionnée
                                        imageUri = null;
                                    } else {
                                        Toast.makeText(AddHouse.this, "Erreur lors de l'enregistrement de la maison", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    });
                })
                .addOnFailureListener(e -> {
                    // Masquer la barre de progression en cas d'échec du téléversement
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(AddHouse.this, "Erreur lors du téléversement de l'image", Toast.LENGTH_SHORT).show();
                });
    }
}
