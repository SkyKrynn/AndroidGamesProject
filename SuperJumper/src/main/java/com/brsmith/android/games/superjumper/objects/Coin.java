package com.brsmith.android.games.superjumper.objects;

import com.brsmith.android.games.framework.GameObject;

public class Coin extends GameObject 
{
	public static final int COIN_NORMAL = 1;
	public static final int COIN_DOUBLE = 2;
	public static final int COIN_GROW_PLATFORMS = 3;
	public static final int COIN_SHRINK_PLATFORMS = 4;
	public static final float COIN_WIDTH = 0.5f;
	public static final float COIN_HEIGHT = 0.8f;
	public static final int COIN_SCORE = 10;
	
	public float stateTime;
	public int coinType;

	public Coin(int coinType, float x, float y) 
	{
		super(x, y, COIN_WIDTH, COIN_HEIGHT);
		stateTime = 0;
		this.coinType = coinType;
	}
	
	public void update(float deltaTime)
	{
		stateTime += deltaTime;
	}
}
