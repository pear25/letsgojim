package edu.cuhk.csci3310.a3310_proj_final;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.HashMap;
import java.util.Map;

public class ExerciseDetails extends AppCompatActivity {
    String name;
    String muscleTargeted;
    String movementDescription;
    String gifUrl;
    String movementDifficulty;
    String equipmentRequired;
    String movementType;
    int image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_detail);

        setUpFromIntent();

        TextView exerciseNameView = findViewById(R.id.exerciseHeader);
        TextView exerciseDetailView = findViewById(R.id.movementInstruction);
        TextView muscleTargetedView = findViewById(R.id.muscleTargeted);
        TextView movementDifficultyView = findViewById(R.id.difficulty);
        TextView equipmentRequiredView = findViewById(R.id.equipment);

        ImageView exerciseImage = (ImageView) findViewById(R.id.imageView2);
        ImageView muscleImage = (ImageView) findViewById(R.id.targetImage);
        ImageView difficultyImage = (ImageView) findViewById(R.id.difficultyImage);
        ImageView equipmentImage = (ImageView) findViewById(R.id.equipmentImage);
        ImageView movementTypeImage = (ImageView) findViewById(R.id.movementTypeImage);

        TextView movementTypeView = findViewById(R.id.movementType);
        Glide
            .with(this)
            .load(gifUrl)
            .thumbnail(
                Glide.with(this)
                .load(R.drawable.loading_gif))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Toast.makeText(getApplicationContext(), "GIF Failed to load.", Toast.LENGTH_LONG).show();
                        return true;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
            .error(R.drawable.barbell)
            .fallback(R.drawable.barbell)
            .into(exerciseImage);

        muscleImage.setImageResource(setMuscleImage(muscleTargeted));
        difficultyImage.setImageResource(setDifficultyImage(movementDifficulty));
        equipmentImage.setImageResource(setEquipmentImage(equipmentRequired));
        movementTypeImage.setImageResource(setExerciseType(movementType));

        exerciseNameView.setText(name);
        exerciseDetailView.setText(movementDescription);

    }

    public void setUpFromIntent() {
        name = getIntent().getStringExtra("NAME");
        muscleTargeted = getIntent().getStringExtra("TARGET");
        movementDescription = getIntent().getStringExtra("DESCRIPTION");
        gifUrl = getIntent().getStringExtra("URL");
        movementDifficulty = getIntent().getStringExtra("DIFFICULTY");
        equipmentRequired = getIntent().getStringExtra("EQUIPMENT");
        movementType = getIntent().getStringExtra("TYPE");
        image = getIntent().getIntExtra("IMAGE", 0);

        muscleTargeted = mapStringToDB(getIntent().getStringExtra("TARGET"));
        movementDescription = mapStringToDB(getIntent().getStringExtra("DESCRIPTION"));
        gifUrl = mapStringToDB(getIntent().getStringExtra("URL"));
        movementDifficulty = mapStringToDB(getIntent().getStringExtra("DIFFICULTY"));
        equipmentRequired = mapStringToDB(getIntent().getStringExtra("EQUIPMENT"));
        movementType = mapStringToDB(getIntent().getStringExtra("TYPE"));

    }

    public int setMuscleImage(String muscleTargeted) {
        if (muscleTargeted.equals("chest")) return R.drawable.chest;
        if (muscleTargeted.equals("biceps")) return R.drawable.bicep;
        if (muscleTargeted.equals("lats")) return R.drawable.upper_back;
        if (muscleTargeted.equals("quadriceps")) return R.drawable.quads;
        if (muscleTargeted.equals("triceps")) return R.drawable.bicep;
        if (muscleTargeted.equals("abdominals")) return R.drawable.abs;
        if (muscleTargeted.equals("lower_back")) return R.drawable.lower_back;
        if (muscleTargeted.equals("hamstrings")) return R.drawable.hamstring;
        if (muscleTargeted.equals("calves")) return R.drawable.calves;
        if (muscleTargeted.equals("shoulders")) return R.drawable.shoulder;

        return R.drawable.lower_back;
    }
    public int setDifficultyImage(String movementDifficulty) {
        if (movementDifficulty.equals("beginner")) return R.drawable.easy;
        if (movementDifficulty.equals("intermediate")) return R.drawable.medium;
        if (movementDifficulty.equals("expert")) return R.drawable.hard;

        return R.drawable.medium;

    }
    public int setEquipmentImage(String equipmentRequired) {
        if (equipmentRequired.equals("dumbbell")) return R.drawable.dumbbell;
        if (equipmentRequired.equals("barbell")) return R.drawable.barbell;
        if (equipmentRequired.equals("e-z_curl_bar")) return R.drawable.ezbar;
        if (equipmentRequired.equals("cable")) return R.drawable.cable_machine;
        if (equipmentRequired.equals("body_only") || equipmentRequired.equals("None")) return R.drawable.body_only;
        if (equipmentRequired.equals("other")) return R.drawable.other;
        if (equipmentRequired.equals("machine")) return R.drawable.machine;
        if (equipmentRequired.equals("medicine_ball") || equipmentRequired.equals("exercise_ball")) return R.drawable.med_ball;

        return R.drawable.body_only;
    }
    public int setExerciseType (String movementType) {
        if(movementType.equals("cardio")) return R.drawable.cardio;
        if(movementType.equals("olympic_weightlifting")) return R.drawable.olympic_weightlifting;
        if(movementType.equals("strength")) return R.drawable.strength_icon;
        if(movementType.equals("strongman")) return R.drawable.strongman_icon;
        if(movementType.equals("plyometrics")) return R.drawable.plyo_icon;
        if(movementType.equals("powerlifting")) return R.drawable.powerlifting_icon;
        if(movementType.equals("stretching")) return R.drawable.stretching;

        return R.drawable.strength_icon;
    }

    public String mapStringToDB(String capString) {
        if (capString.equals("None")) return "None";
        Map<String, String> mapper = new HashMap<>();
        mapper.put("Olympic Weightlifting", "olympic_weightlifting");
        mapper.put("EZ Curl Bar", "e-z_curl_bar");
        mapper.put("No Equipment", "body_only");
        mapper.put("Medicine Ball", "medicine_ball");
        mapper.put("Exercise Ball", "exercise_ball");
        mapper.put("Lower Back", "lower_back");

        if(!mapper.containsKey(capString)) {
            return capString.substring(0, 1).toLowerCase() + capString.substring(1);
        }

        return mapper.get(capString);

    }

}
