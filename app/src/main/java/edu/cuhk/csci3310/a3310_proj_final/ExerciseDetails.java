package edu.cuhk.csci3310.a3310_proj_final;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ExerciseDetails extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_detail);

        String name = getIntent().getStringExtra("NAME");
        String muscleTargeted = getIntent().getStringExtra("TARGET");
        String movementDescription = getIntent().getStringExtra("DESCRIPTION");
        int image = getIntent().getIntExtra("IMAGE", 0);

        TextView exerciseNameView = findViewById(R.id.textView4);
        TextView exerciseDetailView = findViewById(R.id.textView5);
        TextView muscleTargetedView = findViewById(R.id.textView2);
        ImageView exerciseImage = findViewById(R.id.imageView2);

        exerciseNameView.setText(name);
        exerciseDetailView.setText(movementDescription);
        muscleTargetedView.append(muscleTargeted);
        exerciseImage.setImageResource(image);
    }
}
