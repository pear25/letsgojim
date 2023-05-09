package edu.cuhk.csci3310.a3310_proj_final;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    static int deletePosition = -1;

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
        holder.docId.setText(exerciseModels.get(position).getDocumentId());
        if(exerciseModels.get(position).getCustomExercise()) holder.deleteButton.setVisibility(View.VISIBLE);
        else holder.deleteButton.setVisibility(View.GONE);
        if(position == deletePosition) {
            exerciseModels.remove(position);
            deletePosition = -1;
            notifyItemRemoved(position);
        }
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
        TextView tvName, docId;
        ImageView deleteButton;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            tvName = itemView.findViewById(R.id.textView);
            docId = itemView.findViewById(R.id.documentId);
            deleteButton = itemView.findViewById(R.id.delete_exercise);
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
