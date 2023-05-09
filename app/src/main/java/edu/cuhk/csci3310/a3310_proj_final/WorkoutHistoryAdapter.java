package edu.cuhk.csci3310.a3310_proj_final;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WorkoutHistoryAdapter extends RecyclerView.Adapter<WorkoutHistoryAdapter.WorkoutHolder>{

    private List<Map> workoutList;
    private LayoutInflater mInflater;

    private Context context;

    class WorkoutHolder extends RecyclerView.ViewHolder{

        CardView workoutCard;
        TextView workoutTitle, timeText, exerciseText;
        final WorkoutHistoryAdapter mAdapter;

        public WorkoutHolder(View itemView, WorkoutHistoryAdapter adapter){
            super(itemView);
            workoutCard = itemView.findViewById(R.id.workout_card);
            workoutTitle = itemView.findViewById(R.id.workout_title);
            timeText = itemView.findViewById(R.id.timestamp);
            exerciseText = itemView.findViewById(R.id.exercise_text);

            this.mAdapter = adapter;


        }


    }

    public WorkoutHistoryAdapter(Context context, List<Map> workoutList) {
        mInflater = LayoutInflater.from(context);
        this.workoutList = workoutList;
    }

    @NonNull
    @Override
    public WorkoutHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View workoutCard = mInflater.inflate(R.layout.workout_item, parent, false);
        return new WorkoutHolder(workoutCard, this);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutHolder holder, int position) {
        Map currentWorkout = workoutList.get(position);
        String title = (String) currentWorkout.get("name");
        Timestamp timestamp = (Timestamp) currentWorkout.get("timestamp");
        String exercises = currentWorkout.get("exercises").toString();

        Date date;
        date = timestamp.toDate();
        String formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(date);

        holder.workoutTitle.setText(title);
        holder.timeText.setText(formattedDate);
        holder.exerciseText.setText(exercises.substring(1, exercises.length() - 1));


    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }
}
