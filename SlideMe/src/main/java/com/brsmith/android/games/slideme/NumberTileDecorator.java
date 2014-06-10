package com.brsmith.android.games.slideme;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.graphics.Typeface;

import com.brsmith.android.games.framework.impl.AndroidPixmap;
import com.brsmith.android.games.framework.interfaces.IPixmap;
import com.brsmith.android.games.framework.enums.PixmapFormat;
import com.brsmith.android.games.slideme.Assets;
import com.brsmith.android.games.framework.control.decorator.ITileDecorator;

public class NumberTileDecorator implements ITileDecorator
{
	int numRows;
	int numCols;
	int maxWidth;
	int maxHeight;
	int pixmapWidth;
	int pixmapHeight;
    IPixmap basePixmap;
	
	int labelColor = Color.WHITE;
	int labelSize = 20;
	Typeface labelTypeface = Assets.fontButton;
	
	public NumberTileDecorator(int numRows, int numCols, int maxWidth, int maxHeight)
	{
		this.numRows = numRows;
		this.numCols = numCols;
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
		
		this.basePixmap = getBasePixmap();
		
		this.pixmapWidth = this.basePixmap.getWidth();
		this.pixmapHeight = this.basePixmap.getHeight();
	}
	
	private IPixmap getBasePixmap()
	{
        IPixmap pix;
		
		switch(numRows)
		{
		case 3:
			pix = Assets.tile3x3;
			break;
		case 4:
			pix = Assets.tile4x4;
			break;
		case 5:
			pix = Assets.tile5x5;
			break;
		case 6:
			pix = Assets.tile6x6;
			break;
		case 7:
			pix = Assets.tile7x7;
			break;
		default:
			return null;
		}
		
		pixmapWidth = maxWidth / numRows;
		pixmapHeight = maxHeight / numCols;
		
		return new AndroidPixmap(pix, pixmapWidth, pixmapHeight);
	}

	public IPixmap getTilePixmap(int rank)
	{
		String rankCaption = Integer.toString(rank);
		
		Paint labelStyle = new Paint();
		labelStyle.setColor(labelColor);
		labelStyle.setTextSize(labelSize);
		labelStyle.setTypeface(labelTypeface);
		Rect captionRect = new Rect();
		labelStyle.getTextBounds(rankCaption, 0, rankCaption.length(), captionRect);

		Bitmap captionBuffer = Bitmap.createBitmap(Math.abs(captionRect.right), Math.abs(captionRect.bottom-captionRect.top), Config.ARGB_4444);
		Canvas captionCanvas = new Canvas(captionBuffer);
		captionCanvas.drawText(rankCaption, 0, captionBuffer.getHeight(), labelStyle);
		
		Bitmap tile = Bitmap.createBitmap(basePixmap.getBitmap());
		Canvas tileCanvas = new Canvas(tile);
		Bitmap scaledCaption = Bitmap.createScaledBitmap(captionBuffer, (int)(tile.getWidth()*0.3), (int)(tile.getHeight()*0.3), false);
		int x = (tile.getWidth() - scaledCaption.getWidth()) / 2;
		int y = (tile.getHeight() - scaledCaption.getHeight()) / 2;
		tileCanvas.drawBitmap(scaledCaption, x, y, null);
		
		return new AndroidPixmap(tile, PixmapFormat.ARGB4444);
	}

	public int getTileWidth() 
	{
		return pixmapWidth;
	}

	public int getTileHeight()
	{
		return pixmapHeight;
	}

}
