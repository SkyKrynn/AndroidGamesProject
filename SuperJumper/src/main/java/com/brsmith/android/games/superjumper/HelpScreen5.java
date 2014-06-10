package com.brsmith.android.games.superjumper;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.brsmith.android.games.framework.gl.Camera2D;
import com.brsmith.android.games.framework.gl.SpriteBatcher;
import com.brsmith.android.games.framework.gl.Texture;
import com.brsmith.android.games.framework.gl.TextureRegion;
import com.brsmith.android.games.framework.impl.GLScreen;
import com.brsmith.android.games.framework.interfaces.IGame;
import com.brsmith.android.games.framework.interfaces.IInput.TouchEvent;
import com.brsmith.android.games.framework.math.OverlapTester;
import com.brsmith.android.games.framework.math.Rectangle;
import com.brsmith.android.games.framework.math.Vector2;

public class HelpScreen5 extends GLScreen 
{
	Camera2D guiCam;
	SpriteBatcher batcher;
	Rectangle nextBounds;
	Rectangle prevBounds;
	Vector2 touchPoint;
	Texture helpImage;
	TextureRegion helpRegion;

	public HelpScreen5(IGame game)
	{
		super(game);
		
		guiCam = new Camera2D(glGraphics, 320, 480);
		nextBounds = new Rectangle(320 - 64, 0, 64, 64);
		prevBounds = new Rectangle(0, 0, 64, 64);
		touchPoint = new Vector2();
		batcher = new SpriteBatcher(glGraphics, 2);
	}

	@Override
	public void update(float deltaTime) 
	{
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		
		int len = touchEvents.size();
		for(int i = 0; i < len; i++)
		{
			TouchEvent event = touchEvents.get(i);
			touchPoint.set(event.x, event.y);
			guiCam.touchToWorld(touchPoint);
			
			if(event.type == TouchEvent.TOUCH_UP)
			{
				if(OverlapTester.pointInRectangle(nextBounds, touchPoint))
				{
					Assets.playSound(Assets.clickSound);
					game.setScreen(new MainMenuScreen(game));
					return;
				}
				
				if(OverlapTester.pointInRectangle(prevBounds, touchPoint))
				{
					Assets.playSound(Assets.clickSound);
					game.setScreen(new HelpScreen4(game));
					return;
				}
			}
		}
	}

	@Override
	public void present(float deltaTime) 
	{
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCam.setViewportAndMatrices();
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		batcher.beginBatch(helpImage);
		batcher.drawSprite(160, 240, 320, 480, helpRegion);
		batcher.endBatch();
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		batcher.beginBatch(Assets.items);
		batcher.drawSprite(320 - 32, 32, -64, 64, Assets.arrow);
		batcher.drawSprite(32, 32, 64, 64, Assets.arrow);
		batcher.endBatch();
		
		gl.glDisable(GL10.GL_BLEND);
	}

	@Override
	public void pause() 
	{
		helpImage.dispose();	
	}

	@Override
	public void resume() 
	{
		helpImage = new Texture(glGame, "help5.png");
		helpRegion = new TextureRegion(helpImage, 0, 0, 320, 480);
	}

	@Override
	public void dispose() 
	{
	}

}
