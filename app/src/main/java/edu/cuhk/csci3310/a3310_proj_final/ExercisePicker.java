package edu.cuhk.csci3310.a3310_proj_final;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ExercisePicker extends AppCompatActivity implements ExercisePickerAdapter.OnWorkoutListener {

    private Context context;
    private String filename;
    private String type;
    private String text;
    private String description;

    private List<String> muscleList;
    private List<JSONArray> muscleGroups;
    private Integer position;

    private Set<String> setExercises = new HashSet<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_picker);

        Intent intent = getIntent();

        filename = intent.getStringExtra("FILE_NAME");
        type = intent.getStringExtra("TYPE");
        text = intent.getStringExtra("TEXT");
        description = intent.getStringExtra("DESCRIPTION");
        position = intent.getIntExtra("POSITION", 9);

        ImageView wPickerImg = findViewById(R.id.wpicker_img);
        TextView wPickerText = findViewById(R.id.wpicker_txt);
        TextView wPickerDesc = findViewById(R.id.wpicker_desc);

        FloatingActionButton nextBut = findViewById(R.id.proceed_workout);

        Uri imageUri = Uri.parse(filename);
        wPickerImg.setImageURI(imageUri);

        wPickerText.setText(text);
        wPickerDesc.setText(description);

        try {
            readJSONFile();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RecyclerView muscleRecycler = findViewById(R.id.muscle_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ExercisePicker.this);
        ExercisePickerAdapter exercisePickerAdapter = new ExercisePickerAdapter(muscleList, muscleGroups, this);
        muscleRecycler.setAdapter(exercisePickerAdapter);
        muscleRecycler.setLayoutManager(layoutManager);

        nextBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ExerciseTodo.class);
                String intentStr = "";
                for(String str: setExercises){
                    if(intentStr.equals("")) {
                        intentStr = str;
                    } else{
                        intentStr = intentStr + "," + str;
                    }
                }
                intent.putExtra("EXERCISES", intentStr);

                startActivity(intent);
            }
        });

    }
    private void readJSONFile() throws JSONException {
        InputStream inputStream = getResources().openRawResource(R.raw.exercise_list);
        Scanner scanner = new Scanner(inputStream);
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNextLine()) {
            builder.append(scanner.nextLine());
        }
        String jsonString = builder.toString();

        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONArray(jsonString);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray exerciseByWorkout = new JSONArray();

        for(int i = 0; i< jsonArray.length(); i++) {
            JSONObject curObj = jsonArray.getJSONObject(i);
            if(curObj.getString("type").equals(type)){
                exerciseByWorkout.put(curObj);
            }
        }
        //same index to indicate muscle and their exercises
        //type of muscle used
        muscleList = new ArrayList<>();
        //exercises for each muscle
        muscleGroups = new ArrayList<>();

        for(int i = 0; i < exerciseByWorkout.length(); i++){
            JSONObject curObj = exerciseByWorkout.getJSONObject(i);
            String currentMuscle = curObj.getString("muscle");
            if(! muscleList.contains(currentMuscle)){
                muscleList.add(currentMuscle);
                JSONArray j = new JSONArray();
                muscleGroups.add(j.put(curObj));
            }
            else {
                int index = muscleList.indexOf(currentMuscle);
                JSONArray j = muscleGroups.get(index);
                muscleGroups.set(index, j.put(curObj));
            }
        }
    }

    @Override
    public void onWorkoutListener(Intent intent) {
        System.out.println("THIS IS MAIN");
        for(String muscle: muscleList){
            if(intent.hasExtra(muscle)){
                System.out.println(intent.getStringExtra(muscle));
                String extras = intent.getStringExtra(muscle);
                String[] values = extras.split(",");
                setExercises.addAll(Arrays.asList(values));
            }
        }
        if(setExercises.size() > 0){
            FloatingActionButton nextBut = findViewById(R.id.proceed_workout);
            nextBut.setVisibility(View.VISIBLE);
        }
    }
}
