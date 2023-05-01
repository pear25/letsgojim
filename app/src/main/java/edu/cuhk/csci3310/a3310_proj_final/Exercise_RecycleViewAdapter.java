package edu.cuhk.csci3310.a3310_proj_final;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Exercise_RecycleViewAdapter extends RecyclerView.Adapter<Exercise_RecycleViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<ExerciseModel> exerciseModels;

    public Exercise_RecycleViewAdapter(Context context, ArrayList<ExerciseModel> exerciseModels) {
        this.context = context;
        this.exerciseModels = exerciseModels;
    }

    @NonNull
    @Override
    public Exercise_RecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new Exercise_RecycleViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Exercise_RecycleViewAdapter.MyViewHolder holder, int position) {
        holder.tvName.setText(exerciseModels.get(position).getExerciseName());
        holder.tvTarget.setText(exerciseModels.get(position).getMuscleTargeted());
        holder.tvType.setText(exerciseModels.get(position).getMovementType());
        holder.imageView.setImageResource(exerciseModels.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return exerciseModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvName, tvTarget, tvType;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            tvName = itemView.findViewById(R.id.textView);
            tvTarget = itemView.findViewById(R.id.textView2);
            tvType = itemView.findViewById(R.id.textView3);
        }
    }
}
