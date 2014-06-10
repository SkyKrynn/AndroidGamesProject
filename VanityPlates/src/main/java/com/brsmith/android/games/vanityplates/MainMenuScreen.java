package com.brsmith.android.games.vanityplates;

import com.brsmith.android.games.framework.gl.Camera2D;
import com.brsmith.android.games.framework.gl.SpriteBatcher;
import com.brsmith.android.games.framework.impl.GLScreen;
import com.brsmith.android.games.framework.interfaces.IGame;
import com.brsmith.android.games.framework.interfaces.IInput;
import com.brsmith.android.games.framework.math.OverlapTester;
import com.brsmith.android.games.framework.math.Rectangle;
import com.brsmith.android.games.framework.math.Vector2;
import com.brsmith.android.games.vanityplates.Actions.ExitAction;
import com.brsmith.android.games.vanityplates.Actions.NewScreenAction;
import com.brsmith.android.games.vanityplates.controls.TouchArea;
import com.brsmith.android.games.vanityplates.controls.TouchCommand;
import com.brsmith.android.games.vanityplates.controls.TouchCommandHandler;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

public class MainMenuScreen extends GLScreen
{
    private static final String PLAY_BUTTON = "play_button";
    private static final String EXIT_BUTTON = "exit_button";

    Camera2D guiCam;
    SpriteBatcher batcher;
    Rectangle soundBounds;
    Rectangle playBounds;
    Rectangle exitBounds;
    TouchCommandHandler handler;

    public MainMenuScreen(IGame game) {
        super(game);
        guiCam = new Camera2D(glGraphics, 320, 480);
        handler = new TouchCommandHandler(guiCam);
        batcher = new SpriteBatcher(glGraphics, 100);
        soundBounds = new Rectangle(0, 0, 64, 64);

        playBounds = new Rectangle(60 - 50, 70 - 25, 100, 50);
        handler.add(PLAY_BUTTON, new TouchCommand(new TouchArea(playBounds),
                new NewScreenAction<SinglePlayerGameScreen>(game,SinglePlayerGameScreen.class)));

        exitBounds = new Rectangle(260 - 50, 70 - 25, 100, 50);
        handler.add(EXIT_BUTTON, new TouchCommand(new TouchArea(exitBounds),
                new ExitAction(game)));
    }

    @Override
    public void update(float deltaTime) {
        List<IInput.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        handler.processTouchEvents(touchEvents);
    }

    @Override
    public void present(float deltaTime) {
        GL10 gl = glGraphics.getGL();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        guiCam.setViewportAndMatrices();

        gl.glEnable(GL10.GL_TEXTURE_2D);

        batcher.beginBatch(Assets.background);
        batcher.drawSprite(160, 240, 320, 480, Assets.backgroundRegion);
        batcher.endBatch();

        batcher.beginBatch(Assets.plates);
        batcher.drawSprite(playBounds.lowerLeft.x + playBounds.width / 2,
                playBounds.lowerLeft.y + playBounds.height / 2,
                playBounds.width, playBounds.height, Assets.plateRegion);
        batcher.drawSprite(exitBounds.lowerLeft.x + exitBounds.width / 2,
                exitBounds.lowerLeft.y + exitBounds.height / 2,
                exitBounds.width, exitBounds.height, Assets.plateRegion);
        batcher.endBatch();

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        batcher.beginBatch(Assets.bubbleCar);
        batcher.drawSprite(160, 240, 221, 272, Assets.bubbleCarFrontRegion);
        batcher.endBatch();

        batcher.beginBatch(Assets.characters);
        Assets.font.drawText(batcher, "PLAY", 38, 68);
        Assets.font.drawText(batcher, "EXIT", 238, 68);
        batcher.endBatch();

        gl.glDisable(GL10.GL_BLEND);
    }

    @Override
    public void pause() {
        Settings.save(game.getFileIO());
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
