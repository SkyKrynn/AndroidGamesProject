package com.brsmith.android.games.vanityplates.controls;

import com.brsmith.android.games.framework.gl.Camera2D;
import com.brsmith.android.games.framework.interfaces.IInput.TouchEvent;
import com.brsmith.android.games.framework.math.OverlapTester;
import com.brsmith.android.games.framework.math.Vector2;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TouchCommandHandler {
    private final Map<String, TouchCommand> controlMap = new HashMap<String, TouchCommand>();
    private Vector2 touchPoint;
    Camera2D guiCam;

    public TouchCommandHandler(Camera2D camera)
    {
        guiCam = camera;
        touchPoint = new Vector2();
    }

    public void add(String name, TouchCommand command)
    {
        controlMap.put(name, command);
    }

    public void remove(String name)
    {
        if(!controlMap.containsKey(name)) return;

        controlMap.remove(name);
    }

    public void processTouchEvents(List<TouchEvent> touchEvents)
    {
        try
        {
            Iterator<TouchEvent> i = touchEvents.iterator();
            while(i.hasNext())
            {
                TouchEvent event = i.next();
                if(event.type == TouchEvent.TOUCH_UP)
                {
                    processTouch(event);
                }
            }
        }
        catch (ConcurrentModificationException ex)
        {}
    }
//        int len = touchEvents.size();
//        for(int i = 0; i < len; i++)
//        {
//            IInput.TouchEvent event = touchEvents.get(i);
//            if(event.type == IInput.TouchEvent.TOUCH_UP)
//            {
//                touchPoint.set(event.x, event.y);
//                guiCam.touchToWorld(touchPoint);
//
//                if(OverlapTester.pointInRectangle(playBounds, touchPoint))
//                {
//                    Assets.playSound(Assets.honkSound);
//                    game.setScreen(new SinglePlayerGameScreen(game));
//                    return;
//                }
//            }
//        }

    public boolean processTouch(TouchEvent event)
    {
        touchPoint.set(event.x, event.y);
        guiCam.touchToWorld(touchPoint);

        for(Map.Entry<String, TouchCommand> entry : controlMap.entrySet())
        {
            if(OverlapTester.pointInRectangle(entry.getValue().area.rectangle, touchPoint))
            {
                entry.getValue().run();
                return true;
            }
        }

        return false;
    }
}
