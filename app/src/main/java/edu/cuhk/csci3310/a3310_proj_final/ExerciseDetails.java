package edu.cuhk.csci3310.a3310_proj_final;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

public class ExerciseDetails extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_detail);

        String name = getIntent().getStringExtra("NAME");
        String muscleTargeted = getIntent().getStringExtra("TARGET");
        String movementDescription = getIntent().getStringExtra("DESCRIPTION");
        String gifUrl = getIntent().getStringExtra("URL");
        String movementDifficulty = getIntent().getStringExtra("DIFFICULTY");
        String equipmentRequired = getIntent().getStringExtra("EQUIPMENT");
        String movementType = getIntent().getStringExtra("TYPE");

//        System.out.println("ASDASDADS" + " " + gifUrl);
//        String gifUrl = uri.toString();
        int image = getIntent().getIntExtra("IMAGE", 0);

        TextView exerciseNameView = findViewById(R.id.exerciseHeader);
        TextView exerciseDetailView = findViewById(R.id.movementInstruction);
        TextView muscleTargetedView = findViewById(R.id.muscleTargeted);
        TextView movementDifficultyView = findViewById(R.id.difficulty);
        TextView equipmentRequiredView = findViewById(R.id.equipment);
        ImageView exerciseImage = (ImageView) findViewById(R.id.imageView2);
        TextView movementTypeView = findViewById(R.id.movementType);
        Glide.with(this).load(gifUrl).placeholder(image).into(exerciseImage);
//        exerciseImage.setImageResource(image);

        exerciseNameView.setText(name);
        exerciseDetailView.setText(movementDescription);
        muscleTargetedView.append(muscleTargeted);
        movementDifficultyView.append(movementDifficulty);
        equipmentRequiredView.append(equipmentRequired);
        movementTypeView.append(movementType);
    }

}
