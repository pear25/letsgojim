package edu.cuhk.csci3310.a3310_proj_final;

public class ExerciseCard {
    String name;
    String difficulty;
    int image;
    Boolean isSelected = false;
    public ExerciseCard(String name, String difficulty) {
        this.name = name;
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }


}
