package edu.cuhk.csci3310.a3310_proj_final;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExercisePickerAdapter extends RecyclerView.Adapter<ExercisePickerAdapter.MultiViewHolder> implements RecyclerViewInterface {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    private List<String> muscleList;
    private List<JSONArray> muscleGroups;

    ExercisePickerAdapter(List<String> muscleList, List<JSONArray> muscleGroups){
        this.muscleList = muscleList;
        this.muscleGroups = muscleGroups;
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
        System.out.println("musclegroup");
        System.out.println(muscleGroup.length());

        layoutManager.setInitialPrefetchItemCount(muscleGroup.length());

        ExerciseByMuscleAdapter exerciseByMuscleAdapter = new ExerciseByMuscleAdapter(muscleGroup);

        multiViewHolder.exercisesRecycler.setLayoutManager(layoutManager);
        multiViewHolder.exercisesRecycler.setAdapter(exerciseByMuscleAdapter);
        multiViewHolder.exercisesRecycler.setRecycledViewPool(viewPool);

    }

    @Override
    public int getItemCount() {
        return muscleList.size();
    }

    @Override
    public void onExerciseClick(int position) {

    }

    public String uppercaser(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    class MultiViewHolder extends RecyclerView.ViewHolder {
        private TextView muscleText;
        private RecyclerView exercisesRecycler;

        public MultiViewHolder(View itemView){
            super(itemView);
            muscleText = itemView.findViewById(R.id.muscle_txt);
            exercisesRecycler = itemView.findViewById(R.id.exercises_muscle);
        }
    }

}
