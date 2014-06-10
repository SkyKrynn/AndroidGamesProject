package com.brsmith.android.games.basicsstarter.test2d;

import javax.microedition.khronos.opengles.GL10;

import com.brsmith.android.games.framework.DynamicGameObject;
import com.brsmith.android.games.framework.Screen;
import com.brsmith.android.games.framework.gl.Animation;
import com.brsmith.android.games.framework.gl.Camera2D;
import com.brsmith.android.games.framework.gl.SpriteBatcher;
import com.brsmith.android.games.framework.gl.Texture;
import com.brsmith.android.games.framework.gl.TextureRegion;
import com.brsmith.android.games.framework.impl.GLGame;
import com.brsmith.android.games.framework.impl.GLGraphics;
import com.brsmith.android.games.framework.interfaces.IGame;

public class AnimationTest extends GLGame 
{
	static final float WORLD_WIDTH = 4.8f;
	static final float WORLD_HEIGHT = 3.2f;
	
	@Override
	public Screen getStartScreen()
	{
		return new AnimationScreen(this);
	}
	

	public class Caveman extends DynamicGameObject
	{
		public float walkingTime = 0;
		
		public Caveman(float x, float y, float width, float height) 
		{
			super(x, y, width, height);
			this.position.set((float)Math.random() * WORLD_WIDTH, (float)Math.random() * WORLD_HEIGHT);
			this.velocity.set(Math.random() > 0.5f ? -0.5f : 0.5f, 0);
			this.walkingTime = (float)Math.random() * 10;
		}

		public void update(float deltaTime)
		{
			position.add(velocity.x * deltaTime, velocity.y * deltaTime);
			if(position.x < 0) position.x = WORLD_WIDTH;
			if(position.x > WORLD_WIDTH) position.x = 0;
			walkingTime += deltaTime;
		}
	}

	public class AnimationScreen extends Screen
	{
		static final int NUM_CAVEMEN = 10;
		GLGraphics glGraphics;
		Caveman[] cavemen;
		SpriteBatcher batcher;
		Camera2D camera;
		Texture texture;
		Animation walkAnim;

		public AnimationScreen(IGame game)
		{
			super(game);
			glGraphics = ((GLGame)game).getGLGraphics();
			cavemen = new Caveman[NUM_CAVEMEN];
			for(int i = 0; i < NUM_CAVEMEN; i++)
			{
				cavemen[i] = new Caveman((float)Math.random(), (float)Math.random(), 1, 1);
			}
			batcher = new SpriteBatcher(glGraphics, NUM_CAVEMEN);
			camera = new Camera2D(glGraphics, WORLD_WIDTH, WORLD_HEIGHT);
		}

		@Override
		public void update(float deltaTime) 
		{
			int len = cavemen.length;
			for(int i = 0; i < len; i++)
			{
				cavemen[i].update(deltaTime);
			}
		}

		@Override
		public void present(float deltaTime) 
		{
			GL10 gl = glGraphics.getGL();
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			camera.setViewportAndMatrices();
			
			gl.glEnable(GL10.GL_BLEND);
			gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			gl.glEnable(GL10.GL_TEXTURE_2D);
			
			batcher.beginBatch(texture);
			int len = cavemen.length;
			for(int i = 0; i < len; i++)
			{
				Caveman caveman = cavemen[i];
				TextureRegion keyFrame = walkAnim.getKeyFrame(caveman.walkingTime, Animation.ANIMATION_LOOPING);
				batcher.drawSprite(caveman.position.x, caveman.position.y, caveman.velocity.x < 0 ? 1 : -1, 1, keyFrame);
			}
			batcher.endBatch();
		}

		@Override
		public void pause() 
		{
			
		}

		@Override
		public void resume() 
		{
			texture = new Texture(((GLGame)game), "walkanim.png");
			walkAnim = new Animation(0.2f,
					new TextureRegion(texture, 0, 0, 64, 64),
					new TextureRegion(texture, 64, 0, 64, 64),
					new TextureRegion(texture, 128, 0, 64, 64),
					new TextureRegion(texture, 192, 0, 64, 64));
		}

		@Override
		public void dispose() {
			// TODO Auto-generated method stub
			
		}
		
	}
}
