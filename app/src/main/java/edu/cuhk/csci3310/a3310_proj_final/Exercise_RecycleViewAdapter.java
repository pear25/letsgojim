package edu.cuhk.csci3310.a3310_proj_final;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Exercise_RecycleViewAdapter extends RecyclerView.Adapter<Exercise_RecycleViewAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<ExerciseModel> exerciseModels;
    private ActivityResultLauncher<Intent> launcher;

    public Exercise_RecycleViewAdapter(Context context,
                                       ArrayList<ExerciseModel> exerciseModels,
                                       RecyclerViewInterface recyclerViewInterface,
                                       ActivityResultLauncher<Intent> launcher
    ) {
        this.launcher = launcher;
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
        holder.docId.setText(exerciseModels.get(position).getDocumentId());
        holder.exerciseCategory.setText(exerciseModels.get(position).getMovementType());
        holder.exersiceMuscle.setText(exerciseModels.get(position).getMuscleTargeted());
        holder.exerciseDifficulty.setText(exerciseModels.get(position).getMovementDifficulty());
        holder.exerciseEquipment.setText(exerciseModels.get(position).getEquipmentRequired());
        if(exerciseModels.get(position).getCustomExercise()) {
            holder.deleteButton.setVisibility(View.VISIBLE);
            holder.editButton.setVisibility(View.VISIBLE);
        }
        else {
            holder.deleteButton.setVisibility(View.GONE);
            holder.editButton.setVisibility(View.GONE);

        }
    }

    public void setLauncher (ActivityResultLauncher<Intent> launcher) {
        this.launcher = launcher;
    }

    @Override
    public int getItemCount() {
        return exerciseModels.size();
    }

    public void removeAt(int pos) {
        exerciseModels.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, exerciseModels.size());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvName, docId, exerciseCategory, exersiceMuscle, exerciseDifficulty, exerciseEquipment;
        ImageView deleteButton, editButton;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            tvName = itemView.findViewById(R.id.textView);

            docId = itemView.findViewById(R.id.documentId);
            exerciseCategory = itemView.findViewById(R.id.hidden_exercise_category);
            exersiceMuscle = itemView.findViewById(R.id.hidden_exercise_muscle);
            exerciseDifficulty = itemView.findViewById(R.id.hidden_exercise_difficulty);
            exerciseEquipment = itemView.findViewById(R.id.hidden_exercise_equipment);

            editButton = itemView.findViewById(R.id.edit_exercise);
            deleteButton = itemView.findViewById(R.id.delete_exercise);

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), EditExercise.class);
                    int currPos = getAdapterPosition();
                    intent.putExtra("NAME", tvName.getText().toString());
                    intent.putExtra("CATEGORY", exerciseCategory.getText().toString());
                    intent.putExtra("MUSCLE", exersiceMuscle.getText().toString());
                    intent.putExtra("DIFFICULTY", exerciseDifficulty.getText().toString());
                    intent.putExtra("EQUIPMENT", exerciseEquipment.getText().toString());
                    intent.putExtra("DOCID", docId.getText().toString());
                    Log.wtf("HELLO BRO", "HEHHE");
                    Log.wtf("DOCID IS", docId.getText().toString());
                    intent.putExtra("POSITION", String.valueOf(currPos));
                    Log.wtf("POS", String.valueOf(currPos));
                    launcher.launch(intent);
                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    String docString = docId.getText().toString();
                    DocumentReference exerciseRef = db.collection("exercises").document(docString);
                    exerciseRef.delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("DELETE", "DocumentSnapshot successfully deleted!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("OHNO", "DocumentSnapshot unsuccessfully deleted!");
                                    return;
                                }
                            });
                            removeAt(getAdapterPosition());
                }
            });

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
