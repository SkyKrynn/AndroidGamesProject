package com.brsmith.android.games.slideme;

import java.util.List;
import android.graphics.Color;
import com.brsmith.android.games.framework.Screen;
import com.brsmith.android.games.framework.interfaces.IGame;
import com.brsmith.android.games.framework.interfaces.IGraphics;
import com.brsmith.android.games.framework.interfaces.IInput.TouchEvent;
import com.brsmith.android.games.framework.command.IActionCommand;
import com.brsmith.android.games.framework.command.ExitCommand;
import com.brsmith.android.games.framework.command.NewScreenCommand;
import com.brsmith.android.games.framework.control.BaseControl;
import com.brsmith.android.games.framework.control.Button;
import com.brsmith.android.games.framework.enums.CoordinatePosition;
import com.brsmith.android.games.framework.action.TouchAction;

public class MainMenuScreen extends Screen
{
	private static final String PLAY_BUTTON = "play_button";
	private static final String SETTINGS_BUTTON = "settings_button";
	private static final String EXIT_BUTTON = "exit_button";

    private Assets assets = new Assets();
	
	public MainMenuScreen(IGame game)
	{
		super(game);
		CreateControls();
	}

	IActionCommand playCommand = new IActionCommand()
	{
		public void run(BaseControl control)
		{
			controlHandler.actionEnabled(EXIT_BUTTON);
		}
	};
	
	private void CreateControls()
	{
		int height = 300;

		Button playButton = new Button(Assets.menuButton, 240, height, CoordinatePosition.CENTER, "Play", assets);
		controlHandler.add(PLAY_BUTTON, new TouchAction(playButton, true, new NewScreenCommand<SlideMeScreen>(game, SlideMeScreen.class)));
		height += Assets.menuButton.getHeight() + 5;
		
		Button settingsButton = new  Button(Assets.menuButton, 200, height, CoordinatePosition.CENTER, "Settings", assets);
		controlHandler.add(SETTINGS_BUTTON, new TouchAction(settingsButton, true, new NewScreenCommand<SettingsScreen>(game, SettingsScreen.class)));
		height += Assets.menuButton.getHeight() + 5;
		
		Button exitButton = new Button(Assets.menuButton, 160, height, CoordinatePosition.CENTER, "Done", assets);
		controlHandler.add(EXIT_BUTTON, new TouchAction(exitButton, true, new ExitCommand(game)));
	}

	@Override
	public void update(float deltaTime)
	{
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        
        sendTouchEventsThruControlHandler(touchEvents);
	}

	@Override
	public void present(float deltaTime)
	{
		IGraphics g = game.getGraphics();
		g.clear(Color.BLACK);
	
		controlHandler.drawControls(g);
	}

	@Override
	public void pause() 
	{
		Settings.save(game.getFileIO());
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
