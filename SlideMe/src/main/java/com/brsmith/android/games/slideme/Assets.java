package com.brsmith.android.games.slideme;

import android.graphics.Typeface;

import com.brsmith.android.games.framework.control.IControlAssets;
import com.brsmith.android.games.framework.interfaces.IPixmap;

public class Assets implements IControlAssets
{
	public static IPixmap greenSquare;
	public static IPixmap orangeSquare;
	
	public static IPixmap menuButton;
	public static IPixmap menuButtonSkinny;
	public static IPixmap tile3x3;
	public static IPixmap tile4x4;
	public static IPixmap tile5x5;
	public static IPixmap tile6x6;
	public static IPixmap tile7x7;
	
	public static IPixmap arrowLeft;
	public static IPixmap arrowLeftDisabled;
	public static IPixmap arrowRight;
	public static IPixmap arrowRightDisabled;
	
	public static Typeface fontButton;
	
	public static IPixmap tileboardImage;

    @Override
    public Typeface getButtonFont() {
        return fontButton;
    }

    @Override
    public Typeface getLabelFont() {
        return fontButton;
    }
}
