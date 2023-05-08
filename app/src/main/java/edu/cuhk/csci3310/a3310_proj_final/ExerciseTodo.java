package edu.cuhk.csci3310.a3310_proj_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExerciseTodo extends AppCompatActivity implements ExerciseTodoAdapter.OnTodoListener{
    List<String> exerciseList;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference workoutCollectionRef = db.collection("workouts");

    private Button completeWorkoutButton;
    List<TodoItem> todoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_todo);

        completeWorkoutButton = findViewById(R.id.complete_workout);

        Intent intent = getIntent();

        String exercises = intent.getStringExtra("EXERCISES");
        exerciseList = Arrays.asList(exercises.split(","));

        for(String exercise: exerciseList){
            todoList.add(new TodoItem(exercise));
        }

        RecyclerView todoRecycler = findViewById(R.id.todo_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ExerciseTodo.this);
        ExerciseTodoAdapter exerciseTodoAdapter = new ExerciseTodoAdapter(todoList, this);
        todoRecycler.setAdapter(exerciseTodoAdapter);
        todoRecycler.setLayoutManager(layoutManager);

        //SET NEXT BUTTON if all isCompleted is selected

        completeWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(exerciseList.toString());
                sendWorkout();

            }
        });

    }

    @Override
    public void onTodoListenerAction(Boolean isComplete) {
        if(isComplete) {
            completeWorkoutButton.setVisibility(View.VISIBLE);
        } else{
            completeWorkoutButton.setVisibility(View.GONE);
        }
    }

    protected void sendWorkout(){
        if(currentUser != null){
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            String uid = currentUser.getUid();

            Map<String, Object> workoutData = new HashMap<>();
            workoutData.put("uid", uid);
            workoutData.put("timestamp", timestamp);

            workoutData.put("exercises", exerciseList);

            workoutCollectionRef.add(workoutData)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getApplicationContext(), "You have completed a workout.", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Error occurred while writing data
                            Toast.makeText(getApplicationContext(), "Your workout is not successfully added to the database", Toast.LENGTH_LONG).show();
                        }
                    });
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
