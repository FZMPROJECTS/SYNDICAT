package com.example.syndicat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class Calendrier extends AppCompatActivity {

    // HashMap pour stocker les commentaires associés aux dates
    private HashMap<String, String> comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendrier);

        // Initialiser la HashMap des commentaires
        comments = new HashMap<>();

        // Récupérer une référence au CalendarView
        CalendarView calendarView = findViewById(R.id.calendarView);

        // Définir un écouteur pour écouter les changements de date
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Formater la date sélectionnée
                final String date = dayOfMonth + "/" + (month + 1) + "/" + year;

                // Vérifier si la date sélectionnée a un commentaire associé
                if (comments.containsKey(date)) {
                    // Si oui, afficher le commentaire et permettre à l'utilisateur de le modifier
                    String comment = comments.get(date);
                    displayCommentDialog(date, comment);
                } else {
                    // Sinon, permettre à l'utilisateur d'ajouter un nouveau commentaire
                    displayCommentDialog(date, "");
                }
            }
        });
    }

    // Afficher une boîte de dialogue pour permettre à l'utilisateur d'ajouter ou de modifier un commentaire
    private void displayCommentDialog(final String date, String initialComment) {
        // Créer une boîte de dialogue personnalisée avec un champ d'édition pour le commentaire
        final EditText commentInput = new EditText(this);
        commentInput.setText(initialComment);

        // Afficher la boîte de dialogue
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ajouter un commentaire pour " + date)
                .setView(commentInput)
                .setPositiveButton("Enregistrer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Enregistrer le commentaire dans la HashMap
                        String newComment = commentInput.getText().toString();
                        comments.put(date, newComment);
                        Toast.makeText(Calendrier.this, "Commentaire enregistré pour " + date, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Annuler l'opération si l'utilisateur appuie sur "Annuler"
                        dialog.cancel();
                    }
                })
                .show();
    }
}
