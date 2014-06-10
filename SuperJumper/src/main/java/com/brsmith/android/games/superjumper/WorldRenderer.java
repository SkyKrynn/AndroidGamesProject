package com.brsmith.android.games.superjumper;

import javax.microedition.khronos.opengles.GL10;

import com.brsmith.android.games.framework.gl.Animation;
import com.brsmith.android.games.framework.gl.Camera2D;
import com.brsmith.android.games.framework.gl.SpriteBatcher;
import com.brsmith.android.games.framework.gl.TextureRegion;
import com.brsmith.android.games.framework.impl.GLGraphics;
import com.brsmith.android.games.superjumper.objects.Bob;
import com.brsmith.android.games.superjumper.objects.Castle;
import com.brsmith.android.games.superjumper.objects.Coin;
import com.brsmith.android.games.superjumper.objects.Platform;
import com.brsmith.android.games.superjumper.objects.Spring;
import com.brsmith.android.games.superjumper.objects.Squirrel;

public class WorldRenderer 
{
	static final float FRUSTRUM_WIDTH = 10;
	static final float FRUSTRUM_HEIGHT = 15;
	GLGraphics glGraphics;
	World world;
	Camera2D cam;
	SpriteBatcher batcher;
	
	public WorldRenderer(GLGraphics glGraphics, SpriteBatcher batcher, World world)
	{
		this.glGraphics = glGraphics;
		this.batcher = batcher;
		this.world = world;
		this.cam = new Camera2D(glGraphics, FRUSTRUM_WIDTH, FRUSTRUM_HEIGHT);
	}
	
	public void render()
	{
		if(world.bob.position.y > cam.position.y)
			cam.position.y = world.bob.position.y;
		cam.setViewportAndMatrices();
		renderBackground();
		renderObjects();
	}
	
	public void renderBackground()
	{
		GL10 gl = glGraphics.getGL();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		batcher.beginBatch(Assets.background);
		batcher.drawSprite(cam.position.x, cam.position.y, FRUSTRUM_WIDTH, FRUSTRUM_HEIGHT, Assets.backgroundRegion);
		batcher.drawSprite(
				(cam.position.x - FRUSTRUM_WIDTH / 2) + 0.25f, 
				(cam.position.y - FRUSTRUM_HEIGHT / 2) + 0.25f + (cam.position.y * 0.0416f), 
				0.5f, 0.5f, Assets.runner);
		batcher.endBatch();
	}
	
	public void renderObjects()
	{
		GL10 gl = glGraphics.getGL();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		batcher.beginBatch(Assets.items);
		renderBob();
		renderPlatforms();
		renderItems();
		renderSquirrels();
		renderCastle();
		batcher.endBatch();
		gl.glDisable(GL10.GL_BLEND);
	}
	
	private void renderBob()
	{
		TextureRegion keyFrame;
		switch(world.bob.state)
		{
		case Bob.BOB_STATE_FALL:
			keyFrame = Assets.bobFall.getKeyFrame(world.bob.stateTime, Animation.ANIMATION_LOOPING);
			break;
		case Bob.BOB_STATE_JUMP:
			keyFrame = Assets.bobJump.getKeyFrame(world.bob.stateTime, Animation.ANIMATION_LOOPING);
			break;
		case Bob.BOB_STATE_HIT:
		default:
			keyFrame = Assets.bobHit;	
		}
		
		float side = world.bob.velocity.x > 0 ? -1 : 1;
		batcher.drawSprite(world.bob.position.x, world.bob.position.y, side * -1, 1, keyFrame);
	}
	
	private void renderPlatforms()
	{
		int len = world.platforms.size();
		for(int i = 0; i < len; i++)
		{
			Platform platform = world.platforms.get(i);
			TextureRegion keyFrame = Assets.platform;
			if(platform.state == Platform.PLATFORM_STATE_PULVERIZING)
			{
				keyFrame = Assets.breakingPlatform.getKeyFrame(platform.stateTime, Animation.ANIMATION_NONLOOPING);
			}
			
			batcher.drawSprite(platform.position.x, platform.position.y, platform.bounds.width, platform.bounds.height, keyFrame);
		}
	}
	
	private void renderItems()
	{
		int len = world.springs.size();
		for(int i = 0; i < len; i++)
		{
			Spring spring = world.springs.get(i);
			batcher.drawSprite(spring.position.x, spring.position.y, 1, 1, Assets.spring);
		}
		
		len = world.coins.size();
		for(int i = 0; i < len; i++)
		{
			Coin coin = world.coins.get(i);
			TextureRegion keyFrame;
			switch(coin.coinType)
			{
			case Coin.COIN_GROW_PLATFORMS:
				keyFrame = Assets.coinBlueAnim.getKeyFrame(coin.stateTime, Animation.ANIMATION_LOOPING);
				break;
			case Coin.COIN_SHRINK_PLATFORMS:
				keyFrame = Assets.coinGreenAnim.getKeyFrame(coin.stateTime, Animation.ANIMATION_LOOPING);
				break;
			case Coin.COIN_NORMAL:
			default:
				keyFrame = Assets.coinAnim.getKeyFrame(coin.stateTime, Animation.ANIMATION_LOOPING);
				break;
			}

			batcher.drawSprite(coin.position.x, coin.position.y, 1, 1, keyFrame);
		}
	}
	
	private void renderSquirrels()
	{
		int len = world.squirrels.size();
		for(int i = 0; i < len; i++)
		{
			Squirrel squirrel = world.squirrels.get(i);
			TextureRegion keyFrame = Assets.squirrelFly.getKeyFrame(squirrel.stateTime, Animation.ANIMATION_LOOPING);
			float side = squirrel.velocity.x < 0 ? -1 : 1;
			batcher.drawSprite(squirrel.position.x, squirrel.position.y, side * 1, 1, keyFrame);
		}
	}
	
	private void renderCastle()
	{
		Castle castle = world.castle;
		batcher.drawSprite(castle.position.x, castle.position.y, 2, 2, Assets.castle);
	}
}
