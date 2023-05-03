package edu.cuhk.csci3310.a3310_proj_final;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.LinkedList;

public class WorkoutListAdapter extends RecyclerView.Adapter<WorkoutListAdapter.WorkoutViewHolder> {
    private Context context;
    private LayoutInflater mInflater;

    private final LinkedList<String> mImagePathList;
    private final LinkedList<String> typeList;
    private final LinkedList<String> textList;
    private final LinkedList<String> descriptionList;


    class WorkoutViewHolder extends RecyclerView.ViewHolder {

        ImageView workoutImageView;
        TextView workoutTextView;

        final WorkoutListAdapter mAdapter;

        public WorkoutViewHolder(View itemView, WorkoutListAdapter adapter) {
            super(itemView);
            workoutImageView = itemView.findViewById(R.id.workout_img);
            workoutTextView = itemView.findViewById(R.id.workout_text);
            this.mAdapter = adapter;

            // Event handling registration, page navigation goes here
            // Event handling registration, page navigation goes here
//            flowerImageItemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // Get the position of the item that was clicked.
//                    int position = getLayoutPosition();
////                    Toast t = Toast.makeText(v.getContext(), "Position " + position + " is clicked", Toast.LENGTH_SHORT);
////                    t.show();
//                    Intent intent = new Intent(v.getContext(), DetailActivity.class);
//                    intent.putExtra("FILE_NAME", mImagePathList.get(position));
//                    intent.putExtra("FLOWER_NAME", flowerNameList.get(position));
//                    intent.putExtra("GENUS", genusList.get(position));
//                    intent.putExtra("RICHNESS", richnessList.get(position));
//                    intent.putExtra("POSITION", position);
//
//                    Activity act = ( Activity ) context;
//                    act.startActivityForResult( intent , 1 );
//                }
//            });

            // End of ViewHolder initialization
        }
    }

    public WorkoutListAdapter(Context context,
                             LinkedList<String> imagePathList,
                             LinkedList<String> typeList,
                             LinkedList<String> textList,
                             LinkedList<String> descriptionList) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mImagePathList = imagePathList;
        this.typeList = typeList;
        this.textList = textList;
        this.descriptionList = descriptionList;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mCardView = mInflater.inflate(R.layout.workout_type_card, parent, false);
        return new WorkoutViewHolder(mCardView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        String mImagePath = mImagePathList.get(position);
        Uri uri = Uri.parse(mImagePath);

        String text = textList.get(position);
        // Update the following to display correct information based on the given position

        // Set up View items for this row (position), modify to show correct information read from the CSV
        holder.workoutImageView.setImageURI(uri);
        holder.workoutTextView.setText(text);

    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mImagePathList.size();
    }

}
