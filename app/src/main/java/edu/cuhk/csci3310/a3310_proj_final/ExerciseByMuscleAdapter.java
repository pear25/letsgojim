package edu.cuhk.csci3310.a3310_proj_final;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExerciseByMuscleAdapter extends RecyclerView.Adapter<ExerciseByMuscleAdapter.ExerciseByMuscleViewHolder> {

//    private JSONArray muscleExercises;

    private List<ExerciseCard> exerciseCards;
    private ExerciseCardListener exerciseCardListener;


//    ExerciseByMuscleAdapter(JSONArray muscleGroup) {
//        this.muscleExercises = muscleGroup;
//    }

    ExerciseByMuscleAdapter(List<ExerciseCard> exerciseCards, ExerciseCardListener exerciseCardListener) {
//        this.muscleExercises = muscleGroup;
        this.exerciseCards = exerciseCards;
        this.exerciseCardListener = exerciseCardListener;
    }

    @NonNull
    @Override
    public ExerciseByMuscleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.exercise_selector, viewGroup, false);
        return new ExerciseByMuscleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseByMuscleViewHolder holder, int i) {
//        JSONObject exercise;
        ExerciseCard exercise;
//        try {
//            exercise = (JSONObject) muscleExercises.get(i);
            exercise = exerciseCards.get(i);
            holder.bindCard(exercise);
//            String exerciseName = exercise.getString("name");
//            holder.exerciseText.setText(exerciseName);
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public int getItemCount() {
//        return muscleExercises.length();
        return exerciseCards.size();
    }

    public List<ExerciseCard> getSelectedExercise () {
        List<ExerciseCard> selectedExercise = new ArrayList<>();
        for(ExerciseCard exerciseCard : exerciseCards){

            if (exerciseCard.isSelected){
                selectedExercise.add(exerciseCard);
            }
        }
        return selectedExercise;
    }

    public String uppercaser(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    class ExerciseByMuscleViewHolder extends RecyclerView.ViewHolder {
//        ImageView exerciseImg;
        ConstraintLayout cardLayout;
        TextView exerciseText;
        CardView exerciseView;

        TextView exerciseDiff;

        public ExerciseByMuscleViewHolder(View itemView) {
            super(itemView);
            cardLayout = itemView.findViewById(R.id.card_layout);
//            exerciseImg = itemView.findViewById(R.id.imageView);
            exerciseView = itemView.findViewById(R.id.exercise_card);
            exerciseText = itemView.findViewById(R.id.exercise_txt);
            exerciseDiff = itemView.findViewById(R.id.difficulty_text);

//            exerciseCard.setOnClickListener(new View.OnClickListener() {
//                @SuppressLint("ResourceAsColor")
//                @Override
//                public void onClick(View v) {
//                    // Get the position of the item that was clicked.
//                    int position = getLayoutPosition();
//
//                    Intent intent = new Intent(v.getContext(), ExercisePickerAdapter.class);
//                    JSONObject exercise;
//                    try {
//                        exercise = (JSONObject) muscleExercises.get(position);
//                        String exerciseName = exercise.getString("name");
//                        System.out.println("CLICKED");
//                        intent.putExtra(exerciseName, exercise.toString());
//                    } catch (JSONException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            });
        }

        @SuppressLint("ResourceAsColor")
        void bindCard(final ExerciseCard exerciseCard) {
            exerciseText.setText(exerciseCard.name);
            exerciseDiff.setText("Difficulty : " + uppercaser(exerciseCard.difficulty));
            if(exerciseCard.isSelected){
                exerciseView.setCardBackgroundColor(R.color.green_500);

            }
            else{
                exerciseView.setCardBackgroundColor(R.color.white);
            }
            cardLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(exerciseCard.isSelected){
                        exerciseCard.isSelected = false;
                        if(getSelectedExercise().size() == 0){
                            exerciseCardListener.onExerciseCardAction(false);
                        }
                    }
                    else {
                        exerciseCard.isSelected = true;
                        exerciseCardListener.onExerciseCardAction(true);
                    }
                }
            });

        }
    }
}
