package edu.cuhk.csci3310.a3310_proj_final;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
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
    TextInputEditText exerciseNameInput, exerciseCategoryInput, targetMuscleInput, exerciseDifficultyInput, exerciseEquipmentInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_exercise);
        exerciseNameInput = findViewById(R.id.exercise_name);
        exerciseCategoryInput = findViewById(R.id.exercise_category);
        targetMuscleInput = findViewById(R.id.exercise_muscle);
        exerciseDifficultyInput = findViewById(R.id.exercise_difficulty);
        exerciseEquipmentInput = findViewById(R.id.exercise_equipment);


//        String exerciseCategory = exerciseCategoryInput.getEditText().getText().toString();
//        String targetMuscle = targetMuscleInput.getEditText().toString();
//        String exerciseDifficulty = exerciseDifficultyInput.getEditText().getText().toString();
//        String exerciseEquipment = exerciseEquipmentInput.getEditText().getText().toString();

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
            String exerciseCategory = exerciseCategoryInput.getText().toString();
            String targetMuscle = targetMuscleInput.getText().toString();
            String exerciseDifficulty = exerciseDifficultyInput.getText().toString();
            String exerciseEquipment = exerciseEquipmentInput.getText().toString();
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
            }
            else{
                Toast.makeText(getApplicationContext(), "Please fill out all the fields!", Toast.LENGTH_LONG).show();
            }

        }
        else {
            // User is not signed in, you can redirect them to a login screen
        }
    }

    protected boolean checkNotNull(String string) {
        if (string == null || string.equals("") ) return false;
        return true;
    }



}
