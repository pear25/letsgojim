package edu.cuhk.csci3310.a3310_proj_final;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ExerciseInstructions extends AppCompatActivity {

    String name, instructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_instructions);

        name = getIntent().getStringExtra("NAME");
        instructions = getIntent().getStringExtra("INSTRUCTIONS");

        TextView exerciseName = findViewById(R.id.exercise_title);
        TextView exerciseInstruction = findViewById(R.id.exercise_instruction);

        exerciseName.setText(name);
        exerciseInstruction.setText(instructions);

    }
}
