package edu.cuhk.csci3310.a3310_proj_final;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateExercise extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference exerciseCollectionRef = db.collection("exercises");
    TextInputEditText exerciseNameInput;
    AutoCompleteTextView exerciseCategoryInput, targetMuscleInput, exerciseDifficultyInput, exerciseEquipmentInput;;
    ArrayAdapter<String> categoryAdapter, muscleAdapter, difficultyAdapter, equipmentAdapter;
    String exerciseCategory, targetMuscle, exerciseDifficulty, exerciseEquipment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_exercise);
        exerciseNameInput = findViewById(R.id.exercise_name);

        setSelectAdapter(exerciseCategoryInput, R.id.exercise_category, categoryAdapter, R.array.category_item);
        setSelectAdapter(targetMuscleInput, R.id.exercise_muscle, muscleAdapter, R.array.muscle_item);
        setSelectAdapter(exerciseDifficultyInput, R.id.exercise_difficulty, difficultyAdapter, R.array.difficulty_item);
        setSelectAdapter(exerciseEquipmentInput, R.id.exercise_equipment, equipmentAdapter, R.array.equipment_item);

        Button submitExerciseBtn = findViewById(R.id.btnSend);
        submitExerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserInfo();
            }
        });
    }

    protected void getUserInfo(){
        if (currentUser != null) {
            String uid = currentUser.getUid();
            String exerciseName = exerciseNameInput.getText().toString();
            Map<String, Object> newExercise = new HashMap<>();
            newExercise.put("uid", uid);
            newExercise.put("exerciseName", exerciseName);
            newExercise.put("exerciseCategory", exerciseCategory);
            newExercise.put("targetMuscle", targetMuscle);
            newExercise.put("exerciseDifficulty", exerciseDifficulty);
            newExercise.put("exerciseEquipment", exerciseEquipment);

            if( checkNotNull(exerciseName) &&
                checkNotNull(exerciseCategory) &&
                checkNotNull(targetMuscle) &&
                checkNotNull(exerciseDifficulty) &&
                checkNotNull(exerciseEquipment)
            )
            {

                exerciseCollectionRef.add(newExercise)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(), "New exercise has been successfully added!", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error occurred while writing data
                                Toast.makeText(getApplicationContext(), "DATA NOT ADDED", Toast.LENGTH_LONG).show();

                            }
                        });
                Intent data = new Intent();
                data.putExtra("exerciseName", exerciseName);
                data.putExtra("exerciseCategory", exerciseCategory);
                data.putExtra("targetMuscle", targetMuscle);
                data.putExtra("exerciseDifficulty", exerciseDifficulty);
                data.putExtra("exerciseEquipment", exerciseEquipment);
                setResult(RESULT_OK, data);
                finish();

            }
            else{
                Toast.makeText(getApplicationContext(), "Please fill out all the fields!", Toast.LENGTH_LONG).show();
            }

        }
    }

    protected boolean checkNotNull(String string) {
        if (string == null || string.equals("") ) return false;
        return true;
    }

    protected void setSelectAdapter(AutoCompleteTextView selectView, int id_num, ArrayAdapter<String> mAdapter, int stringArray) {
        selectView = findViewById(id_num);
        mAdapter = new ArrayAdapter<String>(this, R.layout.select_list_item, getResources().getStringArray(stringArray));
        selectView.setAdapter(mAdapter);

        if(id_num == R.id.exercise_category)
            selectView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    exerciseCategory = adapterView.getItemAtPosition(pos).toString();
                }
            });

        if(id_num == R.id.exercise_muscle)
            selectView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    targetMuscle = adapterView.getItemAtPosition(pos).toString();
                }
            });

        if(id_num == R.id.exercise_difficulty)
            selectView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    exerciseDifficulty = adapterView.getItemAtPosition(pos).toString();
                }
            });

        if(id_num == R.id.exercise_equipment)
            selectView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    exerciseEquipment = adapterView.getItemAtPosition(pos).toString();
                }
            });

    }

}
