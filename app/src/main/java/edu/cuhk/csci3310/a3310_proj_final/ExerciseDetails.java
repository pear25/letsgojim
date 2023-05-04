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

        TextView exerciseNameView = findViewById(R.id.textView4);
        TextView exerciseDetailView = findViewById(R.id.textView5);
        TextView muscleTargetedView = findViewById(R.id.textView2);
        TextView movementDifficultyView = findViewById(R.id.textView3);
        TextView equipmentRequiredView = findViewById(R.id.textView6);
        ImageView exerciseImage = (ImageView) findViewById(R.id.imageView2);
        String url = "https://images.unsplash.com/photo-1575936123452-b67c3203c357?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8aW1hZ2V8ZW58MHx8MHx8&w=1000&q=80";
        Glide.with(this).load(url).placeholder(image).into(exerciseImage);
//        exerciseImage.setImageResource(image);

        exerciseNameView.setText(name);
        exerciseDetailView.setText(movementDescription);
        muscleTargetedView.append(muscleTargeted);
        movementDifficultyView.append(movementDifficulty);
        equipmentRequiredView.append(equipmentRequired);
    }

}
