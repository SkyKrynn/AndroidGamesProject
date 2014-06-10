package com.brsmith.android.games.vanityplates.controls;

import com.brsmith.android.games.framework.interfaces.IInput.TouchEvent;
import com.brsmith.android.games.framework.math.Rectangle;

public class TouchArea {
    private int left;
    private int right;
    private int top;
    private int bottom;
    Rectangle rectangle;

    public TouchArea(Rectangle rect)
    {
        this.rectangle = rect;
        left = (int)rect.lowerLeft.x;
        right = (int)(rect.lowerLeft.x + rect.width);
        top = (int)(rect.lowerLeft.y + rect.height);
        bottom = (int)rect.lowerLeft.y;
    }

    public boolean isHit(TouchEvent event)
    {
        return isHit(event.x, event.y);
    }

    public boolean isHit(float x, float y)
    {
        return isHit((int)x, (int)y);
    }

    public boolean isHit(int x, int y)
    {
        if ((x >= left) && (x <= right))
            if ((y >= bottom) && (y <= top))
                return true;

        return false;
    }
}
