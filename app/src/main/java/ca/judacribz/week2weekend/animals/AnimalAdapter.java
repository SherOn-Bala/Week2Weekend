package ca.judacribz.week2weekend.animals;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ca.judacribz.week2weekend.R;
import ca.judacribz.week2weekend.models.Animal;
import ca.judacribz.week2weekend.models.Category;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.ViewHolder> {
    private ArrayList<Animal> animals;
    private Map<String, Bitmap> animalBmps;
    private Activity context;

    AnimalAdapter(Activity context, ArrayList<Animal> animals) {
        this.context = context;
        this.animals = animals;
        animalBmps = new HashMap<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_animal,
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setAnimalData(animals.get(position));
    }

    @Override
    public int getItemCount() {
        return animals.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView
                tvAnimalName,
                tvScientificName,
                tvDiet,
                tvStatus,
                tvRange;

        ImageView ivAnimalImage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvAnimalName = itemView.findViewById(R.id.tvAnimalName);
            tvScientificName = itemView.findViewById(R.id.tvScientificName);
            tvDiet = itemView.findViewById(R.id.tvDiet);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvRange = itemView.findViewById(R.id.tvRange);

            ivAnimalImage = itemView.findViewById(R.id.ivAnimalImage);
        }

        int pos;
        Bitmap bmp;
        String aniName;
        void setAnimalData(final Animal animal) {
            tvAnimalName.setText(aniName = animal.getName());
            tvScientificName.setText(animal.getScientificName());
            tvDiet.setText(animal.getDiet());
            tvStatus.setText(animal.getStatus());
            tvRange.setText(animal.getRange());
            pos = animals.indexOf(animal);

            if (animalBmps.containsKey(aniName)) {
                setAnimalImage(animalBmps.get(aniName));
            } else {
                setAnimalImage(animal.getImgUrl());
            }
        }

        void setAnimalImage(final String url) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        animalBmps.put(
                                aniName,
                                bmp = BitmapFactory.decodeStream(new URL(url).openStream())
                        );
                        setAnimalImage(bmp);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        void setAnimalImage(Bitmap bmp) {
            ivAnimalImage.setImageBitmap(bmp);
        }
    }
}
