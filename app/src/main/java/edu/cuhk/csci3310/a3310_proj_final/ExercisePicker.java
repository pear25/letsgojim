package edu.cuhk.csci3310.a3310_proj_final;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ExercisePicker extends AppCompatActivity {

    private String filename;
    private String type;
    private String text;
    private String description;

    private Integer position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_picker);

        Intent intent = getIntent();

        filename = intent.getStringExtra("FILE_NAME");
        type = intent.getStringExtra("TYPE");
        text = intent.getStringExtra("TEXT");
        description = intent.getStringExtra("DESCRIPTION");
        position = intent.getIntExtra("POSITION", 9);

        ImageView wPickerImg = findViewById(R.id.wpicker_img);
        TextView wPickerText = findViewById(R.id.wpicker_txt);
        TextView wPickerDesc = findViewById(R.id.wpicker_desc);

        Uri imageUri = Uri.parse(filename);
        wPickerImg.setImageURI(imageUri);

        wPickerText.setText(text);
        wPickerDesc.setText(description);

//        flowerEditor.setText(flowerName);
//        genusName.setText(genus);
//        richnessName.setText(richToTxt(richness));
//        Uri imageUri = Uri.parse(filename);
//        flowerImg.setImageURI(imageUri);
//
//        if (savedInstanceState != null) {
//            flowerEditor.setText(savedInstanceState.getString("FLOWER_STATE"));
//            richness = savedInstanceState.getInt("RICHNESS_STATE");
//            richnessName.setText(richToTxt(richness));
//        }
    }
}
