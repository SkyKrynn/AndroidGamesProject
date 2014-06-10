package com.brsmith.android.games.basicsstarter.testgl;

import javax.microedition.khronos.opengles.GL10;

import com.brsmith.android.games.framework.Screen;
import com.brsmith.android.games.framework.gl.Texture;
import com.brsmith.android.games.framework.gl.Vertices;
import com.brsmith.android.games.framework.impl.GLGame;
import com.brsmith.android.games.framework.impl.GLGraphics;
import com.brsmith.android.games.framework.interfaces.IGame;

public class IndexedTest extends GLGame
{

	@Override
	public Screen getStartScreen()
	{
		return new IndexedScreen(this);
	}

	class IndexedScreen extends Screen
	{
		GLGraphics glGraphics;
		
		Vertices vertices;
		Texture texture;

		public IndexedScreen(IGame game) {
			super(game);
			glGraphics = ((GLGame) game).getGLGraphics();
			
			vertices = new Vertices(glGraphics, 4, 6, false, true);
			vertices.setVertices(new float[] {100.0f, 100.0f, 0.0f, 1.0f,
	                  228.0f, 100.0f, 1.0f, 1.0f,
	                  228.0f, 228.0f, 1.0f, 0.0f,
	                  100.0f, 228.0f, 0.0f, 0.0f}, 0, 16);
			vertices.setIndices(new short[] {0, 1, 2,  2, 3, 0}, 0, 6);
			
			texture = new Texture((GLGame)game, "bob.jpg");
		}

		@Override
		public void present(float deltaTime) 
		{
			GL10 gl = glGraphics.getGL();
			gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glOrthof(0, 320, 0, 480, 1, -1);
			
			gl.glEnable(GL10.GL_TEXTURE_2D);
			texture.bind();
			
			vertices.draw(GL10.GL_TRIANGLES, 0, 6);
		}


		@Override
		public void update(float deltaTime) { }

		@Override
		public void pause() { }

		@Override
		public void resume() { }

		@Override
		public void dispose() { }
		
	}
}
