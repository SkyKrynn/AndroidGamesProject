package com.brsmith.android.games.basicsstarter.test2d;

import com.brsmith.android.games.framework.GameObject;

public class Cannon extends GameObject 
{
	public float angle;

	public Cannon(float x, float y, float width, float height) 
	{
		super(x, y, width, height);
		angle = 0;
	}
}
