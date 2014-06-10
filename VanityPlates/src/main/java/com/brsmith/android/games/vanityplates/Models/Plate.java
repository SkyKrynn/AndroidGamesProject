package com.brsmith.android.games.vanityplates.Models;

public class Plate {
    private String plateText;
    private String hint;
    private String solution;

    public Plate(String text, String solution) {
        this(text, solution, "");
    }

    public Plate(String text, String solution, String hintText) {
        this.plateText = text;
        this.solution = solution;
        this.hint = hintText;
    }

    public String getText() { return plateText; }
    public String getSolution() { return solution; }
    public String getHint() { return hint; }
}
