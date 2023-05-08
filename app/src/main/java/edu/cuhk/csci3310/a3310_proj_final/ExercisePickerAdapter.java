package edu.cuhk.csci3310.a3310_proj_final;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ExercisePickerAdapter extends RecyclerView.Adapter<ExercisePickerAdapter.MultiViewHolder> implements RecyclerViewInterface, ExerciseCardListener {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    private List<String> muscleList;
    private List<JSONArray> muscleGroups;
    private OnWorkoutListener onWorkoutListener;

    private Boolean showButtonSelected = false;

    ExercisePickerAdapter(List<String> muscleList, List<JSONArray> muscleGroups, Context context){
        this.muscleList = muscleList;
        this.muscleGroups = muscleGroups;

        try{
            this.onWorkoutListener = ((OnWorkoutListener) context);
        } catch (ClassCastException e){
            throw new ClassCastException(e.getMessage());
        }
    }

    @NonNull
    @Override
    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.muscle_exercises, viewGroup, false);
        return new MultiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiViewHolder multiViewHolder, int i) {
        multiViewHolder.muscleText.setText(uppercaser(muscleList.get(i)));
//        Item item = itemList.get(i);
//        itemViewHolder.tvItemTitle.setText(item.getItemTitle());
//
//        // Create layout manager with initial prefetch item count
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                multiViewHolder.exercisesRecycler.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );

        JSONArray muscleGroup = muscleGroups.get(i);

        layoutManager.setInitialPrefetchItemCount(muscleGroup.length());

        List<ExerciseCard> exerciseCards = new ArrayList<>();
        for(int j = 0; j < muscleGroup.length(); j++){
            JSONObject exercise;
            try {
                exercise = (JSONObject) muscleGroup.get(j);
                String name = exercise.getString("name");
                String difficulty = exercise.getString("difficulty");
                exerciseCards.add(new ExerciseCard(name, difficulty));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        ExerciseByMuscleAdapter exerciseByMuscleAdapter = new ExerciseByMuscleAdapter(exerciseCards, this);

        multiViewHolder.exercisesRecycler.setLayoutManager(layoutManager);
        multiViewHolder.exercisesRecycler.setAdapter(exerciseByMuscleAdapter);
        multiViewHolder.exercisesRecycler.setRecycledViewPool(viewPool);
        multiViewHolder.addExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder exerciseNames = new StringBuilder();
                List<ExerciseCard> selectedExerciseList = exerciseByMuscleAdapter.getSelectedExercise();

                for(int i = 0; i < selectedExerciseList.size(); i++){
                    if(i == 0){
                        exerciseNames.append(selectedExerciseList.get(i).name);
                    } else {
                        exerciseNames.append(",").append(selectedExerciseList.get(i).name);
                    }
                }
                System.out.println(exerciseNames);

                // CHANGE HERE TO SEND INTENT TO MAIN
                Toast.makeText(view.getContext(), "Sending intent to main", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.putExtra(muscleList.get(i), exerciseNames.toString());
                onWorkoutListener.onWorkoutListener(intent);
            }
        });
    }

    @Override
    public void onExerciseCardAction(Boolean isSelected) {
        System.out.println("onExerciseCardAction");
    }

    @Override
    public int getItemCount() {
        return muscleList.size();
    }


    public String uppercaser(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    class MultiViewHolder extends RecyclerView.ViewHolder {
        private TextView muscleText;
        private RecyclerView exercisesRecycler;

        private Button addExercises;


        public MultiViewHolder(View itemView){
            super(itemView);
            muscleText = itemView.findViewById(R.id.muscle_txt);
            exercisesRecycler = itemView.findViewById(R.id.exercises_muscle);
            addExercises = itemView.findViewById(R.id.add_exercises);
        }

    }

    public interface OnWorkoutListener {
        public void onWorkoutListener(Intent intent);
    }

    @Override
    public void onExerciseClick(int position) {
    }

}
