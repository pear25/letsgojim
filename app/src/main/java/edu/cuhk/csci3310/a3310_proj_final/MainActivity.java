package edu.cuhk.csci3310.a3310_proj_final;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import edu.cuhk.csci3310.a3310_proj_final.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {
    FirebaseFirestore firestore;
    ArrayList<ExerciseModel> exerciseModels = new ArrayList<>();
    int[] image = { R.drawable.flat_bench };

    FloatingActionButton createExerciseBtn, startWorkoutBtn;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String exerciseName, exerciseCategory, targetMuscle, exerciseDifficulty, exerciseEquipment, exerciseInstruction, exerciseGif;

    private static final int CREATE_EXERCISE_REQUEST_CODE = 100;

    Exercise_RecycleViewAdapter mAdapter = new Exercise_RecycleViewAdapter
            (this,
                    exerciseModels,
                    this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        testFirestore();
        setContentView(R.layout.activity_main);

        ActivityResultLauncher<Intent> getCustomExercise =
                registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            String intentName = data.getStringExtra("exerciseName");
                            String intentCategory = data.getStringExtra("exerciseCategory");
                            String intentMuscle = data.getStringExtra("targetMuscle");
                            String intentDifficulty = data.getStringExtra("exerciseDifficulty");
                            String intentEquipment = data.getStringExtra("exerciseEquipment");
                            String intentInstruction = data.getStringExtra("exerciseInstruction");
                            String intentGifURL = data.getStringExtra("exerciseGifURL");

                            exerciseModels.add(new ExerciseModel(
                                    intentName,
                                    intentMuscle,
                                    intentCategory,
                                    image[0],
                                    intentInstruction,
                                    intentDifficulty,
                                    intentEquipment,
                                    intentGifURL
                            ));
                            mAdapter.notifyItemInserted(exerciseModels.toArray().length - 1);
                        }
                    }
                }
                );
        Log.d("GOOWYFY", "Length of model is: " + exerciseModels.toArray().length);
        setSupportActionBar(findViewById(R.id.toolbar));
        mAuth = FirebaseAuth.getInstance();
        createExerciseBtn = findViewById(R.id.createExercise_btn);
        user = mAuth.getCurrentUser();

        if (user == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        } else{
            RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            getExtraExercise();
        }


        createExerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateExercise.class);
                getCustomExercise.launch(intent);
            }
        });

        startWorkoutBtn = findViewById(R.id.workout_btn);

        startWorkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StartWorkout.class);

                getCustomExercise.launch(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.Logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void testFirestore() {
        Log.d("HEHEHHEHE", "HEHEHEHEHHE000");
        firestore = FirebaseFirestore.getInstance();
        Map<String, Object> users = new HashMap<>();
        users.put("Test", "Firebase");

        firestore.collection("users").add(users).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getExtraExercise() {
        firestore = FirebaseFirestore.getInstance();
        CollectionReference exerciseRef = firestore.collection("exercises");
        Query exerciseQuery = exerciseRef.whereEqualTo("uid", user.getUid());

        exerciseQuery.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map <String, Object> data = document.getData();
                                exerciseName = data.get("exerciseName").toString();
                                exerciseCategory = data.get("exerciseCategory").toString();
                                targetMuscle = data.get("targetMuscle").toString();
                                exerciseEquipment = data.get("exerciseEquipment").toString();
                                exerciseDifficulty = data.get("exerciseDifficulty").toString();
                                exerciseInstruction = data.get("exerciseInstruction").toString();
                                exerciseGif = data.get("exerciseGifURL").toString();
                                exerciseModels.add(new ExerciseModel(
                                        exerciseName,
                                        targetMuscle,
                                        exerciseCategory,
                                        image[0],
                                        exerciseInstruction,
                                        exerciseDifficulty,
                                        exerciseEquipment,
                                        exerciseGif
                                ));
                                mAdapter.notifyItemInserted(exerciseModels.toArray().length - 1);
                            }
                        } else {
                            Log.d("OHNOOOOOO", "Error getting documents: ", task.getException());
                        }
                    }
                });
        readJSONFile();
    }
    private void readJSONFile() {
        InputStream inputStream = getResources().openRawResource(R.raw.exercise_list);
        Scanner scanner = new Scanner(inputStream);
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNextLine()) {
            builder.append(scanner.nextLine());
        }
        String jsonString = builder.toString();

        try {
            JSONArray jsonArray = new JSONArray (jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String name = jsonObject.getString("name");
                String type = jsonObject.getString("type");
                String equipment = jsonObject.getString("equipment");
                String muscle = jsonObject.getString("muscle");
                String difficulty = jsonObject.getString("difficulty");
                String instruction = jsonObject.getString("instructions");
                String url = jsonObject.getString("gifUrl");

                exerciseModels.add(new ExerciseModel(
                        name,
                        muscle,
                        type,
                        image[0],
                        instruction,
                        difficulty,
                        equipment,
                        url
                ));
            }
            // Use the JSON object as needed
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }
    @Override
    public void onExerciseClick(int position) {
        Intent intent = new Intent(MainActivity.this, ExerciseDetails.class);

        intent.putExtra("NAME", exerciseModels.get(position).getExerciseName());
        intent.putExtra("TARGET", exerciseModels.get(position).getMuscleTargeted());
        intent.putExtra("IMAGE", exerciseModels.get(position).getImage());
        intent.putExtra("DESCRIPTION", exerciseModels.get(position).getMovementDescription());
        intent.putExtra("DIFFICULTY", exerciseModels.get(position).getMovementDifficulty());
        intent.putExtra("EQUIPMENT", exerciseModels.get(position).getEquipmentRequired());
        intent.putExtra("TYPE", exerciseModels.get(position).getMovementType());
        intent.putExtra("URL", exerciseModels.get(position).getMovementURL());
        startActivity(intent);
    }

}