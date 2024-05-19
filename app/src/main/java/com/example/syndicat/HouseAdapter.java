package com.example.syndicat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import de.hdodenhof.circleimageview.CircleImageView;

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.ViewHolder> {

    private List<House> houses;
    private Context context;

    public HouseAdapter(Context context, List<House> houses) {
        this.context = context;
        this.houses = houses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homedeco, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        House house = houses.get(position);
        holder.houseTextView.setText("House : " + house.getEtage());
        holder.etageTextView.setText("Etage : " + house.getNumero());
        holder.actionTextView.setText("Action : " + house.getAction());
        holder.metrageTextView.setText("Metrage : " + house.getMetrage());
        holder.prixTextView.setText("Prix : " + house.getPrix());

        // Chargement de l'image avec Picasso
        Picasso.get().load(house.getImageUrl()).into(holder.img1);

        // Gestionnaire de clic pour le bouton de suppression
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Référence à la base de données Firebase
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("houses").child(house.getId());

                // Suppression de la maison de Firebase
                ref.removeValue().addOnSuccessListener(aVoid -> {
                    // Suppression de la maison de la liste locale
                    houses.remove(position);
                    // Notification de la suppression à l'adaptateur
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, houses.size());
                    Toast.makeText(context, "House deleted", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to delete house", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return houses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView houseTextView, etageTextView, actionTextView, metrageTextView, prixTextView;
        ImageButton deleteButton;
        CircleImageView img1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            houseTextView = itemView.findViewById(R.id.house);
            etageTextView = itemView.findViewById(R.id.etage);
            actionTextView = itemView.findViewById(R.id.action);
            metrageTextView = itemView.findViewById(R.id.metrage);
            prixTextView = itemView.findViewById(R.id.prix);
            deleteButton = itemView.findViewById(R.id.deleteicon);
            img1 = itemView.findViewById(R.id.img1);
        }
    }
}
