package com.brsmith.android.games.basicsstarter.testgl;

import javax.microedition.khronos.opengles.GL10;

import com.brsmith.android.games.framework.Screen;
import com.brsmith.android.games.framework.gl.FPSCounter;
import com.brsmith.android.games.framework.gl.Texture;
import com.brsmith.android.games.framework.gl.Vertices;
import com.brsmith.android.games.framework.impl.GLGame;
import com.brsmith.android.games.framework.impl.GLGraphics;
import com.brsmith.android.games.framework.interfaces.IGame;

public class BobTest extends GLGame
{

	@Override
	public Screen getStartScreen() 
	{
		return new BobScreen(this);
	}

	class BobScreen extends Screen
	{
		static final int NUM_BOBS = 100;
		GLGraphics glGraphics;
		Texture bobTexture;
		Vertices bobModel;
		Bob[] bobs;
		
		FPSCounter fps = new FPSCounter();

		public BobScreen(IGame game)
		{
			super(game);
			glGraphics = ((GLGame)game).getGLGraphics();
			
			bobTexture = new Texture((GLGame)game, "bob.jpg");
			
			bobModel = new Vertices(glGraphics, 4, 12, false, true);
			bobModel.setVertices(new float[] { -16, -16, 0, 1,
					16, -16, 1, 1,
					16, 16, 1, 0,
					-16, 16, 0, 0}, 0, 16);
			bobModel.setIndices(new short[] {0, 1, 2, 2, 3, 0}, 0, 6);
			
			bobs = new Bob[NUM_BOBS];

			for(int i = 0; i < NUM_BOBS; i++)
			{
				bobs[i] = new Bob();
			}
			
		}

		@Override
		public void update(float deltaTime)
		{
			game.getInput().getTouchEvents();
			game.getInput().getKeyEvents();
			
			for(int i = 0; i < NUM_BOBS; i++)
			{
				bobs[i].update(deltaTime);
			}
		}

		@Override
		public void present(float deltaTime) 
		{
			GL10 gl = glGraphics.getGL();
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

			bobModel.bind();
			for(int i = 0; i < NUM_BOBS; i++)
			{
				gl.glLoadIdentity();
				gl.glTranslatef(bobs[i].x, bobs[i].y, 0);
				//gl.glRotatef(45, 0, 0, 1);
				//gl.glScalef(2, 05.f, 0);
				bobModel.draw(GL10.GL_TRIANGLES, 0, 6);
			}
			bobModel.unbind();
			
			fps.logFrame();
		}

		@Override
		public void pause() { }

		@Override
		public void resume() 
		{
			GL10 gl = glGraphics.getGL();
			gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
			gl.glClearColor(1, 0, 0, 1);
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glOrthof(0, 320, 0, 480, 1, -1);
			
			bobTexture.reload();
			gl.glEnable(GL10.GL_TEXTURE_2D);
			bobTexture.bind();
			
			gl.glMatrixMode(GL10.GL_MODELVIEW);
		}

		@Override
		public void dispose() { }
		
	}
}
