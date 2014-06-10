package com.brsmith.android.games.framework.control;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.brsmith.android.games.framework.enums.CoordinatePosition;
import com.brsmith.android.games.framework.interfaces.IGraphics;
import com.brsmith.android.games.framework.interfaces.IPixmap;
import com.brsmith.android.games.framework.impl.AndroidPixmap;

public class Button extends TouchControl
{
	String buttonCaption;
	IPixmap captionPixmap;
	Rect captionRect;
	Typeface captionTypeface;
	int captionColor;
	int captionSize;
    IControlAssets assets;
	
	public Button(IPixmap pixmap, int x, int y, CoordinatePosition pos, String caption, IControlAssets assets)
	{
		super(pixmap, x, y, pos);
		init(caption, assets.getButtonFont());
        this.assets = assets;
	}
	
	public Button(IPixmap pixmap, int x, int y, String caption, IControlAssets assets)
	{
		super(pixmap, x, y);
		init(caption, assets.getButtonFont());
	}
	
	private void init(String caption, Typeface buttonFont)
	{
		this.buttonCaption = caption.toUpperCase();
		this.captionTypeface = buttonFont;
		this.captionColor = Color.WHITE;
		this.captionSize = 20;

		this.captionPixmap = createCaptionPixmap(buttonCaption);
		this.captionRect = calculateRect(captionPixmap);
	}
	
	private Rect calculateRect(IPixmap pixmap)
	{
		if(pixmap == null)
			return null;
		
		int buttonWidth = rect.right - rect.left;
		int buttonHeight = rect.bottom - rect.top;
		int captionX = (buttonWidth - pixmap.getWidth()) / 2;
		int captionY = (buttonHeight - pixmap.getHeight()) / 2;
		
		return new Rect(rect.left + captionX, rect.top + captionY, pixmap.getWidth(), pixmap.getHeight());
	}
	
	private IPixmap createCaptionPixmap(String caption)
	{
		if(caption.length() == 0)
			return null;
		
		Paint textColor = new Paint();
		textColor.setColor(captionColor);
		textColor.setTextSize(captionSize);
		textColor.setTypeface(captionTypeface);
		Rect captionRect = new Rect();
		textColor.getTextBounds(caption, 0, caption.length(), captionRect);

		Bitmap textBuffer = Bitmap.createBitmap(Math.abs(captionRect.right), Math.abs(captionRect.bottom-captionRect.top), Config.ARGB_4444);
		Canvas textCanvas = new Canvas(textBuffer);
		textCanvas.drawText(caption, 0, textBuffer.getHeight(), textColor);
		
		if((textBuffer.getWidth() > (pixmap.getWidth() - 20)) || (textBuffer.getHeight() > (pixmap.getHeight() - 10)))
		{
			int newWidth = textBuffer.getWidth();
			if( textBuffer.getWidth() > (pixmap.getWidth() - 20))
				newWidth = pixmap.getWidth() - 20;
			
			int newHeight = textBuffer.getHeight();
			if(textBuffer.getHeight() > (pixmap.getHeight() - 10))
				newHeight = pixmap.getHeight() - 10;
				
			textBuffer = Bitmap.createScaledBitmap(textBuffer, newWidth, newHeight, false);
		}

		return new AndroidPixmap(textBuffer, pixmap.getFormat());
	}
	
	public void setCaptionTypeface(Typeface typeface)
	{
		this.captionTypeface = typeface;
		this.captionPixmap = createCaptionPixmap(buttonCaption);
		this.captionRect = calculateRect(captionPixmap);
	}
	
	public void setCaptionSize(int size)
	{
		this.captionSize = size;
		this.captionPixmap = createCaptionPixmap(buttonCaption);
		this.captionRect = calculateRect(captionPixmap);
	}
	
	public void setCaptionColor(int color)
	{
		this.captionColor = color;
		this.captionPixmap = createCaptionPixmap(buttonCaption);
		this.captionRect = calculateRect(captionPixmap);
	}

	@Override
	public void move(int x, int y)
	{
		super.move(x, y);
		this.captionRect = calculateRect(captionPixmap);
	}

	@Override
	public void move(int x, int y, CoordinatePosition pos)
	{
		super.move(x, y, pos);
		this.captionRect = calculateRect(captionPixmap);
	}

	@Override
	public void draw(IGraphics g)
	{
		super.draw(g);

		if(captionPixmap != null && captionRect != null)
			g.drawPixmap(captionPixmap, captionRect.left, captionRect.top);
	}
}
