package edu.cuhk.csci3310.a3310_proj_final;

public class ExerciseModel {
    String exerciseName;
    String muscleTargeted;
    String movementType;
    String movementDescription;
    int image;

    public ExerciseModel(String exerciseName, String muscleTargeted, String movementType, int image, String movementDescription) {
        this.exerciseName = exerciseName;
        this.muscleTargeted = muscleTargeted;
        this.movementType = movementType;
        this.image = image;
        this.movementDescription = movementDescription;
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
        return image;
    }

    public String getMovementDescription() {
        return movementDescription;
    }
}
