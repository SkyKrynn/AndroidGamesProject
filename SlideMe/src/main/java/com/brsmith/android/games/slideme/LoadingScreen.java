package com.brsmith.android.games.slideme;

import com.brsmith.android.games.framework.Screen;
import com.brsmith.android.games.framework.impl.AndroidPixmap;
import com.brsmith.android.games.framework.interfaces.IGame;
import com.brsmith.android.games.framework.interfaces.IGraphics;
import com.brsmith.android.games.framework.enums.PixmapFormat;


public class LoadingScreen extends Screen 
{
	public LoadingScreen(IGame game)
	{
		super(game);
	}

	@Override
	public void update(float deltaTime)
	{
		IGraphics g = game.getGraphics();
	    
		Assets.greenSquare = g.newPixmap("square_green_button.png",  PixmapFormat.RGB565);
		Assets.orangeSquare = g.newPixmap("square_orange_button.png", PixmapFormat.RGB565);
		
		Assets.menuButton = new AndroidPixmap(Assets.orangeSquare, 103, 40);
		Assets.menuButtonSkinny = new AndroidPixmap(Assets.orangeSquare, 103, 15);
		Assets.tile3x3 = new AndroidPixmap(Assets.greenSquare, 106, 106);
		Assets.tile4x4 = new AndroidPixmap(Assets.greenSquare, 80, 80);
		Assets.tile5x5 = new AndroidPixmap(Assets.greenSquare, 64, 64);
		Assets.tile6x6 = new AndroidPixmap(Assets.greenSquare, 53, 53);
		Assets.tile7x7 = new AndroidPixmap(Assets.greenSquare, 45, 45);
		
		
		Assets.arrowLeft = new AndroidPixmap(g.newPixmap("blue arrow left.png", PixmapFormat.ARGB4444), 32, 32);
		Assets.arrowLeftDisabled = new AndroidPixmap(g.newPixmap("gray arrow left.png", PixmapFormat.ARGB4444), 32, 32);
		Assets.arrowRight = new AndroidPixmap(g.newPixmap("blue arrow right.png", PixmapFormat.ARGB4444), 32, 32);
		Assets.arrowRightDisabled = new AndroidPixmap(g.newPixmap("gray arrow right.png", PixmapFormat.ARGB4444), 32, 32);

		Assets.fontButton = g.newTypeface("Marquee Regular.ttf");
		//Assets.fontButton = g.newTypeface("TeknikohlRemix01  Normal.ttf");
		
		Settings.load(game.getFileIO());
		
		game.setScreen(new MainMenuScreen(game));
	}

	@Override
	public void present(float deltaTime) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {}

}
