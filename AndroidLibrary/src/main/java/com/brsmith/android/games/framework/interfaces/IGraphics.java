package com.brsmith.android.games.framework.interfaces;

import android.graphics.Typeface;
import android.net.Uri;

import com.brsmith.android.games.framework.enums.PixmapFormat;

public interface IGraphics
{
	public IPixmap newPixmap(String filename, PixmapFormat format);
	public IPixmap newPixmap(Uri uri, PixmapFormat format);
	public Typeface newTypeface(String filename);
	public void clear(int color);
	public void drawPixel(int x, int y, int color);
	public void drawLine(int x, int y, int x2, int y2, int color);
	public void drawRect(int x, int y, int width, int height, int color);
	public void drawPixmap(IPixmap pixmap, int x, int y, int srcX, int srcY, int scrWidth, int srcHeight);
	public void drawPixmap(IPixmap pixmap, int x, int y);
	public int getWidth();
	public int getHeight();
}

// 32-95 upper
// 
