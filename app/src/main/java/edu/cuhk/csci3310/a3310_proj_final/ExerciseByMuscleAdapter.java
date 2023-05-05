package edu.cuhk.csci3310.a3310_proj_final;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ExerciseByMuscleAdapter extends RecyclerView.Adapter<ExerciseByMuscleAdapter.ExerciseByMuscleViewHolder> {

    private JSONArray muscleExercises;

    ExerciseByMuscleAdapter(JSONArray muscleGroup) {
        this.muscleExercises = muscleGroup;
    }

    @NonNull
    @Override
    public ExerciseByMuscleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.exercise_selector, viewGroup, false);
        return new ExerciseByMuscleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseByMuscleViewHolder holder, int i) {
        JSONObject exercise;
        try {
            exercise = (JSONObject) muscleExercises.get(i);
            String exerciseName = exercise.getString("name");
            holder.exerciseText.setText(exerciseName);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return muscleExercises.length();
    }

    class ExerciseByMuscleViewHolder extends RecyclerView.ViewHolder {
//        ImageView exerciseImg;
        TextView exerciseText;

        ExerciseByMuscleViewHolder(View itemView) {
            super(itemView);
//            exerciseImg = itemView.findViewById(R.id.imageView);
            exerciseText = itemView.findViewById(R.id.exercise_txt);
        }
    }
}
