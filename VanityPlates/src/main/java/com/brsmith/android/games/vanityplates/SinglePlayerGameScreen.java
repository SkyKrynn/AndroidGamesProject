package com.brsmith.android.games.vanityplates;

import com.brsmith.android.games.framework.gl.Camera2D;
import com.brsmith.android.games.framework.gl.SpriteBatcher;
import com.brsmith.android.games.framework.impl.GLScreen;
import com.brsmith.android.games.framework.interfaces.IGame;
import com.brsmith.android.games.framework.math.Rectangle;
import com.brsmith.android.games.framework.math.Vector2;
import com.brsmith.android.games.vanityplates.Models.Plate;

import javax.microedition.khronos.opengles.GL10;

public class SinglePlayerGameScreen extends GLScreen {

    Camera2D guiCam;
    SpriteBatcher batcher;
    World world;
    World.WorldListener worldListener;
    WorldRenderer renderer;
    Rectangle playBounds;
    Vector2 touchPoint;

    public SinglePlayerGameScreen(IGame game) {
        super(game);
        guiCam = new Camera2D(glGraphics, 320, 480);
        batcher = new SpriteBatcher(glGraphics, 100);

        worldListener = new World.WorldListener()
        {
            @Override
            public void Solved() { return; }
        };

        world = new World(worldListener);
        world.setPlate(new Plate("LUV LIF", "LOVE LIFE"));
        renderer = new WorldRenderer(glGraphics, batcher, world);

        playBounds = new Rectangle(60 - 50, 70 - 25, 100, 50);
        touchPoint = new Vector2();
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void present(float deltaTime) {
        renderer.render();

        //GL10 gl = glGraphics.getGL();
        //gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        //guiCam.setViewportAndMatrices();

        //batcher.beginBatch(Assets.plates);
        //batcher.drawSprite(160, 400, 300, 150, Assets.plateRegion);
        //batcher.endBatch();

        //gl.glDisable(GL10.GL_BLEND);
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
