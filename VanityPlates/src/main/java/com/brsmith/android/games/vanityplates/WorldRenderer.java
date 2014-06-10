package com.brsmith.android.games.vanityplates;

import com.brsmith.android.games.framework.gl.Camera2D;
import com.brsmith.android.games.framework.gl.SpriteBatcher;
import com.brsmith.android.games.framework.impl.GLGraphics;

import javax.microedition.khronos.opengles.GL10;

public class WorldRenderer {
    GLGraphics glGraphics;
    World world;
    Camera2D cam;
    SpriteBatcher batcher;

    public WorldRenderer(GLGraphics glGraphics, SpriteBatcher batcher, World world)
    {
        this.glGraphics = glGraphics;
        this.batcher = batcher;
        this.world = world;
        this.cam = new Camera2D(glGraphics, 320, 480);
    }

    public void render()
    {
        GL10 gl = glGraphics.getGL();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        cam.setViewportAndMatrices();

        renderPlate();
    }

    public void renderPlate() {
        batcher.beginBatch(Assets.plates);
        batcher.drawSprite(160, 400, 300, 150, Assets.plateRegion);
        batcher.endBatch();

        GL10 gl = glGraphics.getGL();
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        batcher.beginBatch(Assets.characters);
        Assets.font.drawText(batcher, world.getPlateText(), 160, 400);

        batcher.endBatch();

        gl.glDisable(GL10.GL_BLEND);
    }
}
