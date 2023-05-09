package edu.cuhk.csci3310.a3310_proj_final;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

public class WorkoutDetail extends AppCompatActivity {

    private String title, timestamp, exercises;
    TextView workoutTitle, timestampText;

    ListView listExercisesView;

    String[] exercisesList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_detail);

        Intent intent = getIntent();

        title = intent.getStringExtra("NAME");
        timestamp = intent.getStringExtra("TIMESTAMP");
        exercises = intent.getStringExtra("EXERCISES");

        exercisesList = exercises.split(",");

        workoutTitle = findViewById(R.id.workout_title);
        timestampText = findViewById(R.id.timestamp_text);
        listExercisesView = findViewById(R.id.list_exercise);

        workoutTitle.setText(title);
        timestampText.setText(timestamp);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_view_item, exercisesList);
        listExercisesView.setAdapter(arrayAdapter);
    }

}
