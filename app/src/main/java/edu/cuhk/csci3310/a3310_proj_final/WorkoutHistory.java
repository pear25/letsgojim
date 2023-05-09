package edu.cuhk.csci3310.a3310_proj_final;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WorkoutHistory extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference workoutCollectionRef = db.collection("workouts");
    List<Map> workoutList = new ArrayList<>();

    TextView loadingText;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_history);

        loadingText = findViewById(R.id.loading_text);

        workoutCollectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document: task.getResult()){
                        Map currentDoc = document.getData();
                        System.out.println(currentDoc);
                        System.out.println(currentUser.getUid());
                        if(currentDoc.get("uid").equals(currentUser.getUid())){
                            workoutList.add(currentDoc);
                        }
                        loadingText.setVisibility(View.GONE);

                        RecyclerView historyRecycler = findViewById(R.id.history_recycler);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(WorkoutHistory.this);
                        WorkoutHistoryAdapter workoutHistoryAdapter = new WorkoutHistoryAdapter( WorkoutHistory.this, workoutList);
                        historyRecycler.setAdapter(workoutHistoryAdapter);
                        historyRecycler.setLayoutManager(layoutManager);
                    }
                } else{
                    Log.d("ERROR", "Data fetch is unsuccessful");
                }
            }

        });


    }
}
