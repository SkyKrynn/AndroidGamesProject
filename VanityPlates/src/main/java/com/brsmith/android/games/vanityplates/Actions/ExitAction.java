package com.brsmith.android.games.vanityplates.Actions;

import com.brsmith.android.games.framework.interfaces.IGame;

public class ExitAction implements IAction {
    IGame game;

    public ExitAction(IGame game)
    {
        this.game = game;
    }

    public void run() {
        game.exitGame();
    }
}
