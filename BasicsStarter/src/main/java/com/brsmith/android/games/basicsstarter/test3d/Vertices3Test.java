package com.brsmith.android.games.basicsstarter.test3d;

import com.brsmith.android.games.framework.Screen;
import com.brsmith.android.games.framework.gl3d.Vertices3;
import com.brsmith.android.games.framework.impl.GLGame;
import com.brsmith.android.games.framework.impl.GLScreen;
import com.brsmith.android.games.framework.interfaces.IGame;

import javax.microedition.khronos.opengles.GL10;

public class Vertices3Test extends GLGame
{
    @Override
    public Screen getStartScreen()
    {
        return new Vertices3Screen(this);
    }

    class Vertices3Screen extends GLScreen
    {
        Vertices3 vertices;

        public Vertices3Screen(IGame game)
        {
            super(game);

            vertices = new Vertices3(glGraphics, 6, 0, true, false);
            vertices.setVertices(new float[] { -0.5f, -0.5f, -3, 1, 0, 0, 1,
                                                0.5f, -0.5f, -3, 1, 0, 0, 1,
                                                0.0f,  0.5f, -3, 1, 0, 0, 1,

                                                0.0f, -0.5f, -5, 0, 1, 0, 1,

                                                1.0f, -0.5f, -5, 0, 1, 0, 1,
                                                0.5f,  0.5f, -5, 0, 1, 0, 1}, 0, 7 * 6);
        }

        @Override
        public void present(float deltaTime)
        {
            GL10 gl = glGraphics.getGL();

            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
            gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
            gl.glOrthof(-1, 1, -1, 1, 10, -10);
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();
            vertices.bind();
            vertices.draw(GL10.GL_TRIANGLES, 0, 6);
            vertices.unbind();
        }

        @Override
        public void update(float deltaTime) {

        }

        @Override
        public void pause() {

        }

        @Override
        public void resume() {

        }

        @Override
        public void dispose() {

        }
    }
}
