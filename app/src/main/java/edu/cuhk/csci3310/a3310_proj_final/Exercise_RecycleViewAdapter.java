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
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<ExerciseModel> exerciseModels;

    public Exercise_RecycleViewAdapter(Context context,
                                       ArrayList<ExerciseModel> exerciseModels,
                                       RecyclerViewInterface recyclerViewInterface
    ) {
        this.context = context;
        this.exerciseModels = exerciseModels;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public Exercise_RecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new Exercise_RecycleViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Exercise_RecycleViewAdapter.MyViewHolder holder, int position) {
        holder.tvName.setText(exerciseModels.get(position).getExerciseName());
        holder.imageView.setImageResource(exerciseModels.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return exerciseModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvName;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            tvName = itemView.findViewById(R.id.textView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onExerciseClick(pos);
                        }
                    }
                }
            });
        }
    }
}
