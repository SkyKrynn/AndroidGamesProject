package com.brsmith.android.games.basicsstarter.testgl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.brsmith.android.games.framework.Screen;
import com.brsmith.android.games.framework.impl.GLGame;
import com.brsmith.android.games.framework.impl.GLGraphics;
import com.brsmith.android.games.framework.interfaces.IGame;

public class FirstTriangleTest extends GLGame
{

	@Override
	public Screen getStartScreen() 
	{
		return new FirstTriangleScreen(this);
	}
	
	class FirstTriangleScreen extends Screen
	{
		GLGraphics glGraphics;
		FloatBuffer vertices;
		FloatBuffer vertices1;
		FloatBuffer vertices2;

		public FirstTriangleScreen(IGame game)
		{
			super(game);
			glGraphics = ((GLGame)game).getGLGraphics();
			
			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(3 * 2 * 4);
			byteBuffer.order(ByteOrder.nativeOrder());
			
			vertices = byteBuffer.asFloatBuffer();
			vertices.put( new float[] { 0.0f, 0.0f,
					319.0f, 0.0f,
					160.0f, 479.0f});
			vertices.flip();
			
			//0,80,160,240,320
			ByteBuffer byteBuffer1 = ByteBuffer.allocateDirect(3 * 2 * 4);
			byteBuffer1.order(ByteOrder.nativeOrder());
			vertices1 = byteBuffer1.asFloatBuffer();
			vertices1.put( new float[] { 0.0f, 239.0f,
					160.0f, 239.0f,
					80.0f, 479.0f});
			vertices1.flip();

			ByteBuffer byteBuffer2 = ByteBuffer.allocateDirect(3 * 2 * 4);
			byteBuffer2.order(ByteOrder.nativeOrder());
			vertices2 = byteBuffer2.asFloatBuffer();
			vertices2.put( new float[] { 159.0f, 239.0f,
					319.0f, 239.0f,
					240.0f, 479.0f});
			vertices2.flip();
		}
 
		@Override
		public void update(float deltaTime)
		{
			game.getInput().getTouchEvents();
			game.getInput().getKeyEvents();
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
			
			gl.glColor4f(1, 0, 0, 1);
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices);
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);

			gl.glColor4f(0, 1, 0, 1);
			//gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices1);
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);

			gl.glColor4f(0, 0, 1, 1);
			//gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices2);
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
		}

		@Override
		public void pause() 
		{
		}

		@Override
		public void resume() 
		{
		}

		@Override
		public void dispose() 
		{
		}
		
	}

}
