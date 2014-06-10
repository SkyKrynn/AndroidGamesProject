package com.brsmith.android.games.vanityplates;

import com.brsmith.android.games.vanityplates.Models.Plate;

public class World {

    public interface WorldListener {
        void Solved();
    }

    private WorldListener listener;
    private Plate currentPlate;
    private String stateName;
    private String guess;
    private boolean solved;

    public World(WorldListener listener) {
        this.listener = listener;
        solved = false;
        guess = "";
    }

    public void setPlate(Plate plate) {
        currentPlate = plate;
        solved = false;
        guess = "";
    }

    public void setStateName(String state) {
        stateName = state;
    }

    public String getPlateText() {
        return currentPlate.getText();
    }
}
