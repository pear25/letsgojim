package edu.cuhk.csci3310.a3310_proj_final;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditExercise extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String name, category, muscle, difficulty, equipment, docId, position;
    TextInputEditText editExercise;
    AutoCompleteTextView editCategory, editMuscle, editDifficulty, editEquipment;
    Button editButton;
    ArrayAdapter<String> categoryAdapter, muscleAdapter, difficultyAdapter, equipmentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_exercise);

        setUpFromIntent();

        setSelectAdapter(editCategory, R.id.edit_exercise_category, categoryAdapter, R.array.category_item);
        setSelectAdapter(editMuscle, R.id.edit_exercise_muscle, muscleAdapter, R.array.muscle_item);
        setSelectAdapter(editDifficulty, R.id.edit_exercise_difficulty, difficultyAdapter, R.array.difficulty_item);
        setSelectAdapter(editEquipment, R.id.edit_exercise_equipment, equipmentAdapter, R.array.equipment_item);


        editButton = findViewById(R.id.btnEdit);
        editExercise = findViewById(R.id.edit_exercise_name);
        editExercise.setText(name);

        editCategory = findViewById(R.id.edit_exercise_category);
        editCategory.setText(mapDBtoString(category), false);

        editMuscle = findViewById(R.id.edit_exercise_muscle);
        editMuscle.setText(mapDBtoString(muscle), false);

        editDifficulty = findViewById(R.id.edit_exercise_difficulty);
        editDifficulty.setText(mapDBtoString(difficulty), false);

        editEquipment = findViewById(R.id.edit_exercise_equipment);
        editEquipment.setText(mapDBtoString(equipment), false);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = editExercise.getText().toString();
                category = editCategory.getText().toString();
                muscle = editMuscle.getText().toString();
                difficulty = editDifficulty.getText().toString();
                equipment = editEquipment.getText().toString();
                Log.wtf("EDIT_SEND", name);
                Log.wtf("EDIT_SEND", category);
                Log.wtf("EDIT_SEND", muscle);
                Log.wtf("EDIT_SEND", difficulty);
                Log.wtf("EDIT_SEND", equipment);
                Log.wtf("EDIT_SEND", position);
                Log.wtf("EDIT_SEND", "ASD");
                DocumentReference documentReference =
                        db.collection("exercises")
                        .document(docId);

                Map<String, Object> updateObject = new HashMap<>();
                updateObject.put("exerciseName", name);
                updateObject.put("exerciseCategory", category);
                updateObject.put("targetMuscle", muscle);
                updateObject.put("exerciseDifficulty", difficulty);
                updateObject.put("exerciseEquipment", equipment);

                documentReference.update(updateObject)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("EDIT_DONE", "DocumentSnapshot successfully updated!");
                                Intent data = new Intent();
                                data.putExtra("name", name);
                                data.putExtra("category", category);
                                data.putExtra("muscle", muscle);
                                data.putExtra("difficulty", difficulty);
                                data.putExtra("equipment", equipment);
                                data.putExtra("position", position);
                                setResult(RESULT_OK, data);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("OHNOO", "DocumentSnapshot not successfully updated!");

                            }
                        });



            }
        });
        Log.wtf("EDIT", name);
        Log.wtf("EDIT", category);
        Log.wtf("EDIT", muscle);
        Log.wtf("EDIT", difficulty);
        Log.wtf("EDIT", equipment);
        Log.wtf("EDIT", docId);
        Log.wtf("EDIT", String.valueOf(position));

    }
    public void setUpFromIntent() {
        name = getIntent().getStringExtra("NAME");
        category = getIntent().getStringExtra("CATEGORY");
        muscle = getIntent().getStringExtra("MUSCLE");
        difficulty = getIntent().getStringExtra("DIFFICULTY");
        equipment = getIntent().getStringExtra("EQUIPMENT");
        docId = getIntent().getStringExtra("DOCID");
        position = getIntent().getStringExtra("POSITION");
        category = mapDBtoString(category);
        muscle = mapDBtoString(muscle);
        difficulty = mapDBtoString(difficulty);
        equipment = mapDBtoString(equipment);
    }

    public String mapDBtoString(String capString) {
        if (capString.equals("None")) return "None";
        Map<String, String> mapper = new HashMap<>();
        mapper.put("olympic_weightlifting", "Olympic Weightlifting");
        mapper.put("e-z_curl_bar", "EZ Curl Bar");
        mapper.put("body_only", "No Equipment");
        mapper.put("medicine_ball", "Medicine Ball");
        mapper.put("exercise_ball", "Exercise Ball");
        mapper.put("lower_back", "Lower Back");

        if(!mapper.containsKey(capString)) {
            return capString.substring(0, 1).toUpperCase() + capString.substring(1);
        }

        return mapper.get(capString);

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


    protected void setSelectAdapter(AutoCompleteTextView selectView, int id_num, ArrayAdapter<String> mAdapter, int stringArray) {
        selectView = findViewById(id_num);
        mAdapter = new ArrayAdapter<String>(this, R.layout.select_list_item, getResources().getStringArray(stringArray));
        selectView.setAdapter(mAdapter);

        if(id_num == R.id.exercise_category)
            selectView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    category = mapStringToDB(adapterView.getItemAtPosition(pos).toString());
                }
            });

        if(id_num == R.id.exercise_muscle)
            selectView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    muscle = mapStringToDB(adapterView.getItemAtPosition(pos).toString());
                }
            });

        if(id_num == R.id.exercise_difficulty)
            selectView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    difficulty = mapStringToDB(adapterView.getItemAtPosition(pos).toString());
                }
            });

        if(id_num == R.id.exercise_equipment)
            selectView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    equipment = mapStringToDB(adapterView.getItemAtPosition(pos).toString());
                }
            });

    }

}

