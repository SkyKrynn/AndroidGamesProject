package com.brsmith.android.games.superjumper.objects;

import com.brsmith.android.games.framework.DynamicGameObject;
import com.brsmith.android.games.superjumper.World;

public class Platform extends DynamicGameObject 
{
	public static final float PLATFORM_WIDTH = 2;
	public static final float PLATFORM_HEIGHT = 0.5f;
	
	public static final float PLATFORM_DOUBLE = 2;
	public static final float PLATFORM_HALF = 0.5f;
	
	public static final int PLATFORM_TYPE_STATIC = 0;
	public static final int PLATFORM_TYPE_MOVING = 1;
	
	public static final int PLATFORM_STATE_NORMAL = 0;
	public static final int PLATFORM_STATE_SMALL = 1;
	public static final int PLATFORM_STATE_TINY = 2;
	public static final int PLATFORM_STATE_PULVERIZING = 3;
	
	public static final float PLATFORM_PULVERIZE_TIME = 0.2f * 4;
	public static final float PLATFORM_VELOCITY = 2;
	
	int type;
	public int state;
	public float stateTime;
	boolean hasGrown;

	public Platform(int type, float x, float y) 
	{
		super(x, y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
		this.type = type;
		this.state = PLATFORM_STATE_NORMAL;
		this.stateTime = 0;
		this.hasGrown = false;
		if(type == PLATFORM_TYPE_MOVING)
		{
			velocity.x = PLATFORM_VELOCITY;
		}
	}
	
	public void update(float deltaTime)
	{
		if(type == PLATFORM_TYPE_MOVING)
		{
			position.add(velocity.x * deltaTime, 0);
			bounds.lowerLeft.set(position).sub(PLATFORM_WIDTH / 2, PLATFORM_HEIGHT / 2);
			
			if(position.x < PLATFORM_WIDTH / 2)
			{
				velocity.x = -velocity.x;
				position.x = PLATFORM_WIDTH / 2;
			}
			
			if(position.x > World.WORLD_WIDTH - PLATFORM_WIDTH / 2)
			{
				velocity.x = -velocity.x;
				position.x = World.WORLD_WIDTH - PLATFORM_WIDTH / 2;
			}
		}
		
		stateTime += deltaTime;
	}
	
	public void resize(float delta)
	{
//		if(delta >= 1)
//		{
//			if(!hasGrown)
				bounds.width *= delta;
//			hasGrown = true;
//		}
//		else
//		{
//			if(hasGrown)
//				bounds.width *= delta;
//			hasGrown = false;
//		}
	}
	
	public void jumpedOn()
	{
		switch(state)
		{
		case PLATFORM_STATE_NORMAL:
			state = PLATFORM_STATE_SMALL;
			bounds.width = bounds.width / 2;
			break;
		case PLATFORM_STATE_SMALL:
			state = PLATFORM_STATE_TINY;
			bounds.width = bounds.width / 2;
			break;
		case PLATFORM_STATE_TINY:
			pulverize();
			break;
		}
	}
	
	public void pulverize()
	{
		state = PLATFORM_STATE_PULVERIZING;
		stateTime = 0;
		velocity.x = 0;
	}

}
