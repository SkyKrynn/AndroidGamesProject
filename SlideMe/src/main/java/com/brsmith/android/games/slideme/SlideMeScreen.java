package com.brsmith.android.games.slideme;

import java.util.List;

import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;

import com.brsmith.android.games.framework.Screen;
import com.brsmith.android.games.framework.impl.AndroidPixmap;
import com.brsmith.android.games.framework.interfaces.IGame;
import com.brsmith.android.games.framework.interfaces.IGraphics;
import com.brsmith.android.games.framework.enums.PixmapFormat;
import com.brsmith.android.games.framework.interfaces.IInput.TouchEvent;
import com.brsmith.android.games.framework.interfaces.IPixmap;
import com.brsmith.android.games.framework.command.IActionCommand;
import com.brsmith.android.games.framework.command.NewScreenCommand;
import com.brsmith.android.games.framework.control.BaseControl;
import com.brsmith.android.games.framework.control.Button;
import com.brsmith.android.games.framework.enums.CoordinatePosition;
import com.brsmith.android.games.slideme.NumberTileDecorator;
import com.brsmith.android.games.framework.control.decorator.PixmapTileDecorator;
import com.brsmith.android.games.framework.control.decorator.ITileDecorator;
import com.brsmith.android.games.framework.control.TileSet;
import com.brsmith.android.games.framework.action.TouchAction;

public class SlideMeScreen extends Screen
{
	private static final String SHOW_IMAGE_BUTTON = "show_image_button";
	private static final String REMOVE_IMAGE_BUTTON = "remove_image_button";
	private static final String SHUFFLE_BUTTON = "shuffle_button";
	private static final String SETTINGS_BUTTON = "settings_button";
	private static final String RETURN_BUTTON = "return_button";
	private static final String TILES = "tiles";

	TileSet tiles;
	Settings.TileType tileType = Settings.tileType;
	String tileboardUri = Settings.tileboardImageUri;
	String cameraImageUri = Settings.cameraImageUri;
    IPixmap imagePixmap;
	boolean showPixmap;
    Assets assets = new Assets();
	
	public SlideMeScreen(IGame game)
	{
		super(game);

		Button showImageButton = new Button(Assets.menuButtonSkinny, 280, 330, CoordinatePosition.CENTER, "Show Image", assets);
		showImageButton.setCaptionSize(10);
		showImageButton.setCaptionTypeface(Typeface.SANS_SERIF);
		controlHandler.add(SHOW_IMAGE_BUTTON, new TouchAction(showImageButton, false, showImageCommand));

		Button removeImageButton = new Button(Assets.menuButtonSkinny, 280, 330, CoordinatePosition.CENTER, "Return", assets);
		removeImageButton.setCaptionSize(10);
		removeImageButton.setCaptionTypeface(Typeface.SANS_SERIF);
		controlHandler.add(REMOVE_IMAGE_BUTTON, new TouchAction(removeImageButton, false, removeImageCommand));

		createTiles();

		Button shuffleButton = new  Button(Assets.menuButton, 55, 455, CoordinatePosition.CENTER, "Shuffle", assets);
		controlHandler.add(SHUFFLE_BUTTON, new TouchAction(shuffleButton, true, shuffleCommand));
		
		Button settingsButton = new  Button(Assets.menuButton, 160, 455, CoordinatePosition.CENTER, "Settings", assets);
		controlHandler.add(SETTINGS_BUTTON, new TouchAction(settingsButton, true, new NewScreenCommand<SettingsScreen>(game, SettingsScreen.class)));

		Button returnButton = new  Button(Assets.menuButton, 265, 455, CoordinatePosition.CENTER, "Return", assets);
		controlHandler.add(RETURN_BUTTON, new TouchAction(returnButton, true, new NewScreenCommand<MainMenuScreen>(game, MainMenuScreen.class)));
	}

    IActionCommand shuffleCommand = new IActionCommand()
	{
		public void run(BaseControl control)
		{
			tiles.shuffle();
		}
	};

    IActionCommand tileSetCommand = new IActionCommand()
	{
		public void run(BaseControl control)
		{
			if(tiles.getTouchedTileIndex() < 0)
				return;
			
			tiles.slideTile(tiles.getTouchedTileIndex());
		}
		
	};
	
	IActionCommand showImageCommand = new IActionCommand()
	{
		public void run(BaseControl control)
		{
			controlHandler.actionDisabled(TILES);
			showPixmap = true;
			controlHandler.actionDisabled(SHOW_IMAGE_BUTTON);
			controlHandler.actionInvisable(SHOW_IMAGE_BUTTON);
			controlHandler.actionEnabled(REMOVE_IMAGE_BUTTON);
			controlHandler.actionVisable(REMOVE_IMAGE_BUTTON);
		}
	};
	
	IActionCommand removeImageCommand = new IActionCommand()
	{
		public void run(BaseControl control)
		{
			controlHandler.actionEnabled(TILES);
			showPixmap = false;
			controlHandler.actionEnabled(SHOW_IMAGE_BUTTON);
			controlHandler.actionVisable(SHOW_IMAGE_BUTTON);
			controlHandler.actionDisabled(REMOVE_IMAGE_BUTTON);
			controlHandler.actionInvisable(REMOVE_IMAGE_BUTTON);
		}
	};
	
	private void createTiles()
	{
		controlHandler.actionInvisable(REMOVE_IMAGE_BUTTON);
		ITileDecorator decorator;
		
		if(Settings.tileType == Settings.TileType.Image && Settings.tileboardImageUri.length() != 0)
		{
            IPixmap pixmap = game.getGraphics().newPixmap(Uri.parse(Settings.tileboardImageUri), PixmapFormat.RGB565);
			imagePixmap = new AndroidPixmap(pixmap, 320, 320);
			decorator = new PixmapTileDecorator(Settings.numRows, Settings.numCols, 320, 320, imagePixmap);
			controlHandler.actionVisable(SHOW_IMAGE_BUTTON);
			controlHandler.actionEnabled(SHOW_IMAGE_BUTTON);
		}
		else if(Settings.tileType == Settings.TileType.Camera && Settings.cameraImageUri.length() != 0)
		{
            IPixmap pixmap = game.getGraphics().newPixmap(Uri.parse(Settings.cameraImageUri), PixmapFormat.RGB565);
			imagePixmap = new AndroidPixmap(pixmap, 320, 320);
			decorator = new PixmapTileDecorator(Settings.numRows, Settings.numCols, 320, 320, imagePixmap);
			controlHandler.actionVisable(SHOW_IMAGE_BUTTON);
			controlHandler.actionEnabled(SHOW_IMAGE_BUTTON);
		}
		else
		{
			decorator = new NumberTileDecorator(Settings.numRows, Settings.numCols, 320, 320);
			controlHandler.actionInvisable(SHOW_IMAGE_BUTTON);
			controlHandler.actionDisabled(SHOW_IMAGE_BUTTON);
		}
		
		showPixmap = false;
		tiles = new TileSet(decorator, Settings.numRows, Settings.numCols);
		tiles.shuffle();
		controlHandler.add(TILES, new TouchAction(tiles, true, tileSetCommand));
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
		
		if(tiles.isSolved())
			g.drawRect(0, 0, 320, 320, Color.YELLOW);
	
		controlHandler.drawControls(g);
		
		if(showPixmap)
			g.drawPixmap(imagePixmap, 0, 0);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume()
	{
		boolean needTiles = false;
		
		if(tiles == null) needTiles = true;
		if(tiles.getRows() != Settings.numRows) needTiles = true;
		if(tiles.getCols() != Settings.numCols) needTiles = true;
		if(tileType != Settings.tileType) needTiles = true;
		if(tileboardUri != Settings.tileboardImageUri) needTiles = true;
		if(cameraImageUri != Settings.cameraImageUri) needTiles = true;
		
		if(needTiles)
		{
			tileType = Settings.tileType;
			tileboardUri = Settings.tileboardImageUri;
			cameraImageUri = Settings.cameraImageUri;
			createTiles();
		}
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
