package edu.cuhk.csci3310.a3310_proj_final;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ExerciseTodoAdapter extends RecyclerView.Adapter<ExerciseTodoAdapter.MultiViewHolder> implements RecyclerViewInterface {
    private List<TodoItem> exerciseList;
    private OnTodoListener onTodoListener;

    private Context context;

    ExerciseTodoAdapter(List<TodoItem> exerciseList, Context context){
        this.exerciseList = exerciseList;
        this.context = context;

        try{
            this.onTodoListener = ((OnTodoListener) context);
        } catch (ClassCastException e){
            throw new ClassCastException(e.getMessage());
        }
    }

    @NonNull
    @Override
    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.todo_item, viewGroup, false);
        return new MultiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiViewHolder multiViewHolder, int i) {
        TodoItem todoItem;

        todoItem = exerciseList.get(i);
        multiViewHolder.bindCard(todoItem);

//        multiViewHolder.checkButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(exerciseCard.isSelected){
//                    exerciseCard.isSelected = false;
//                    if(getSelectedExercise().size() == 0){
//                        exerciseCardListener.onExerciseCardAction(false);
//                    }
//                }
//                else {
//                    exerciseCard.isSelected = true;
//                    exerciseCardListener.onExerciseCardAction(true);
//                }
//
//                // CHANGE HERE TO SEND INTENT TO MAIN
//                Toast.makeText(view.getContext(), "Sending intent to main", Toast.LENGTH_SHORT).show();
//
//                Intent intent = new Intent();
//                intent.putExtra(muscleList.get(i), exerciseNames.toString());
//                onWorkoutListener.onWorkoutListener(intent);
//            }
//        });
    }

//    @Override
//    public void onExerciseCardAction(Boolean isSelected) {
//        System.out.println("onExerciseCardAction");
//    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public List<TodoItem> getCompletedExercise () {
        List<TodoItem> completedExercise = new ArrayList<>();
        for(TodoItem todoItem : exerciseList){

            if (todoItem.isComplete){
                completedExercise.add(todoItem);
            }
        }
        return completedExercise;
    }

    @Override
    public void onExerciseClick(int position) {

    }

    class MultiViewHolder extends RecyclerView.ViewHolder {

        private ImageButton checkButton;

        private TextView exerciseText;

        private ImageButton infoButton;

        public MultiViewHolder(View itemView){
            super(itemView);
            exerciseText = itemView.findViewById(R.id.exercise_todo);
            checkButton = itemView.findViewById(R.id.complete_button);
            infoButton = itemView.findViewById(R.id.info_button);
        }

        void bindCard(final TodoItem todoItem){
            exerciseText.setText(todoItem.name);
            checkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(todoItem.isComplete){
                        checkButton.setImageResource(R.drawable.unchecked_asset);
                        todoItem.isComplete = false;
                        onTodoListener.onTodoListenerAction(false);

                    }
                    else {
                        checkButton.setImageResource(R.drawable.check_asset);
                        todoItem.isComplete = true;
                        System.out.println(getCompletedExercise());
                        if(getCompletedExercise().size() == getItemCount()){
                            onTodoListener.onTodoListenerAction(true);
                        }
                    }
                }
            });
            infoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ExerciseInstructions.class);

                    String instructions = getInstruction(todoItem.name);

                    intent.putExtra("NAME", todoItem.name );
                    intent.putExtra("INSTRUCTIONS", instructions);

                    Activity act = ( Activity ) context;
                    act.startActivityForResult( intent , 1 );
                }
            });
        }
    }

    public interface OnTodoListener {
        public void onTodoListenerAction(Boolean isComplete);
    }

    public String getInstruction(String exName){
        InputStream inputStream = context.getResources().openRawResource(R.raw.exercise_list);
        Scanner scanner = new Scanner(inputStream);
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNextLine()) {
            builder.append(scanner.nextLine());
        }
        String jsonString = builder.toString();

        try {
            JSONArray jsonArray = new JSONArray (jsonString);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                if(name.equals(exName)){
                    return jsonObject.getString("instructions");
                }
            }

        } catch(JSONException e){
            e.printStackTrace();
        }
        return "Instruction not available";
    }

}
