package edu.cuhk.csci3310.a3310_proj_final;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {
    FirebaseFirestore firestore;
    ArrayList<ExerciseModel> exerciseModels = new ArrayList<>();

    int[] image = {
            R.drawable.flat_bench,
            R.drawable.incline_bench,
            R.drawable.chest_fly,
            R.drawable.dips,
            R.drawable.exercise,
            R.drawable.bo_rows,
            R.drawable.lat_pulldown,
            R.drawable.deadlift,
            R.drawable.squats,
            R.drawable.leg_extension,
            R.drawable.ic_android_black_24dp,
            };

    FloatingActionButton floatingActionButton;
    TextView textView;
    FirebaseAuth mAuth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        testFirestore();
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        textView = findViewById(R.id.user_details);
        floatingActionButton = findViewById(R.id.logout_btn);
        user = mAuth.getCurrentUser();

        if (user == null){
//            Intent intent = new Intent(getApplicationContext(), Login.class);
//            startActivity(intent);
//            finish();
        } else{
            textView.setText(user.getEmail());
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        setExerciseModels();
        Exercise_RecycleViewAdapter mAdapter = new Exercise_RecycleViewAdapter
                (this,
                exerciseModels,
                this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void testFirestore() {
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

    private void setExerciseModels() {
        String[] exerciseNames = getResources().getStringArray(R.array.gym_movement_list);
        String[] muscleTargeted = getResources().getStringArray(R.array.muscles_targeted);
        String[] exerciseTypes = getResources().getStringArray(R.array.exercise_type);
        String[] exerciseDescription = getResources().getStringArray(R.array.movement_description);

        for(int i = 0; i < exerciseNames.length; i++) {
            exerciseModels.add(new ExerciseModel(
                    exerciseNames[i],
                    muscleTargeted[i],
                    exerciseTypes[i],
                    image[i],
                    exerciseDescription[i]
            ));
        }
    }

    @Override
    public void onExerciseClick(int position) {
        Intent intent = new Intent(MainActivity.this, ExerciseDetails.class);

        intent.putExtra("NAME", exerciseModels.get(position).getExerciseName());
        intent.putExtra("TARGET", exerciseModels.get(position).getMuscleTargeted());
        intent.putExtra("IMAGE", exerciseModels.get(position).getImage());
        intent.putExtra("DESCRIPTION", exerciseModels.get(position).getMovementDescription());

        startActivity(intent);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}