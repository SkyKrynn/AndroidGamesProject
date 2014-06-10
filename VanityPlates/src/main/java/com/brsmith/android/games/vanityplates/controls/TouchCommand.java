package com.brsmith.android.games.vanityplates.controls;

import com.brsmith.android.games.vanityplates.Actions.IAction;

public class TouchCommand {
    TouchArea area;
    private IAction action;
    // Not sure if this is what I want
    //   but need to be able to tie an action to a command
    public TouchCommand(TouchArea area, IAction action)
    {
        this.area = area;
        this.action = action;
    }

    public void run()
    {
        action.run();
    }
}
