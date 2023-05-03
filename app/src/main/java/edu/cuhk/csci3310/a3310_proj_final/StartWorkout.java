package edu.cuhk.csci3310.a3310_proj_final;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.LinkedList;

public class StartWorkout extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private WorkoutListAdapter mAdapter;

    private JSONObject workoutObject;

    private LinkedList<String> mImagePathList = new LinkedList<>();
    private LinkedList<String> typeList  = new LinkedList<>();
    private LinkedList<String> textList = new LinkedList<>();
    private LinkedList<String> descriptionList = new LinkedList<>();

    private final String mDrawableFilePath = "android.resource://edu.cuhk.csci3310.a3310_proj_final/drawable/";

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            jsonReader();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_workout);

        JSONArray workoutArray;
        try {
            workoutArray = workoutObject.getJSONArray("workout");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        int num = workoutArray.length();
        for(int i=1; i<=num; i++) {
            JSONObject currentWorkout;
            try {
                currentWorkout = workoutArray.getJSONObject(i-1);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            try {
                String type = currentWorkout.getString("type");
                String filename = currentWorkout.getString("filename");
                String text = currentWorkout.getString("text");
                String description = currentWorkout.getString("description");

                typeList.addLast(type);
                mImagePathList.addLast(mDrawableFilePath + filename.substring(0, filename.length()-4));
                textList.addLast(text);
                descriptionList.addLast(description);

            } catch (JSONException e) {
                throw new RuntimeException();
            }

            mRecyclerView = findViewById(R.id.recyclerview);
            mAdapter = new WorkoutListAdapter(this, mImagePathList, typeList, textList, descriptionList);
            mRecyclerView.setAdapter(mAdapter);

            int numColumns = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2;
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, numColumns));
        }

    }

    public void jsonReader() throws Exception {

        InputStream inputStream = getResources().openRawResource(R.raw.workout_types);
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();
        String workoutDatas = new String(buffer, "UTF-8");
        // turn string into JSONObject

        try{
            workoutObject = new JSONObject(workoutDatas);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }



}
