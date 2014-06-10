package com.brsmith.android.games.framework.control.decorator;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;

import com.brsmith.android.games.framework.impl.AndroidPixmap;
import com.brsmith.android.games.framework.enums.PixmapFormat;
import com.brsmith.android.games.framework.interfaces.IPixmap;

public class PixmapTileDecorator implements ITileDecorator
{
	int numRows;
	int numCols;
	int maxWidth;
	int maxHeight;
	IPixmap pixmap;
	
	int tileWidth;
	int tileHeight;
	
	public PixmapTileDecorator(int numRows, int numCols, int maxWidth, int maxHeight, IPixmap pixmap)
	{
		this.numRows = numRows;
		this.numCols = numCols;
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
		this.pixmap = pixmap;
		
		tileWidth = maxWidth / numRows;
		tileHeight = maxHeight / numCols;
	}

	public IPixmap getTilePixmap(int rank)
	{
		int row;
		int col;
		
		col = (rank-1) / numCols;
		row = (rank-1) % numCols;
		
		Bitmap tile = Bitmap.createBitmap(pixmap.getBitmap(), row * tileWidth, col * tileHeight, tileWidth, tileHeight);
		Canvas tileCanvas = new Canvas(tile);
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(3);
		Rect rect = new Rect(0, 0, tileWidth, tileHeight);
		tileCanvas.drawRect(rect, paint);
		
		return new AndroidPixmap(tile, PixmapFormat.RGB565);
	}

	public int getTileWidth() 
	{
		return tileWidth;
	}

	public int getTileHeight()
	{
		return tileHeight;
	}

}
