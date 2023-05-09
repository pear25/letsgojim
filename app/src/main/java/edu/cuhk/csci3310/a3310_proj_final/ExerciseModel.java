package edu.cuhk.csci3310.a3310_proj_final;

import java.net.URL;

public class ExerciseModel {
    String exerciseName;
    String muscleTargeted;
    String movementType;
    String movementDescription;
    String movementURL;
    String equipmentRequired;
    String movementDifficulty;
    boolean customExercise;
    String documentId;
    int image;

    public ExerciseModel(String exerciseName, String muscleTargeted, String movementType, int image,
                         String movementDescription,
                         String movementDifficulty,
                         String equipmentRequired,
                         String movementURL,
                         boolean customExercise,
                         String documentId
    ) {
        this.exerciseName = exerciseName;
        this.muscleTargeted = muscleTargeted;
        this.movementType = movementType;
        this.image = image;
        this.movementDescription = movementDescription;
        this.movementDifficulty = movementDifficulty;
        this.equipmentRequired = equipmentRequired;
        this.movementURL = movementURL;
        this.customExercise = customExercise;
        this.documentId = documentId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public String getMuscleTargeted() {
        return muscleTargeted;
    }

    public String getMovementType() {
        return movementType;
    }

    public int getImage() {
        return R.drawable.bench_press_svgrepo_com;
    }

    public String getMovementDescription() {
        return movementDescription;
    }

    public String getEquipmentRequired() {return  equipmentRequired ;}

    public  String getMovementDifficulty() { return  movementDifficulty ;}

    public String getMovementURL() { return movementURL; }

    public boolean getCustomExercise() { return customExercise; }

    public String getDocumentId() {
        return documentId;
    }
}
