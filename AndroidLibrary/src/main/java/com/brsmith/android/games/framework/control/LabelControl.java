package com.brsmith.android.games.framework.control;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;

import com.brsmith.android.games.framework.enums.CoordinatePosition;
import com.brsmith.android.games.framework.impl.AndroidPixmap;
import com.brsmith.android.games.framework.interfaces.IPixmap;
import com.brsmith.android.games.framework.enums.PixmapFormat;

public class LabelControl extends BaseControl
{
	String label;
	Typeface labelTypeface;
	int labelColor;
	int labelSize;

	public LabelControl(String label, int x, int y, IControlAssets assets)
	{
		super(null, x, y);
		init(label, assets.getLabelFont());
		move(x, y);
	}
	
	public LabelControl(String label, int x, int y, CoordinatePosition pos, IControlAssets assets)
	{
		super(null, x, y, pos);
		init(label, assets.getLabelFont());
		move(x, y, pos);
	}
	
	private void init(String caption, Typeface labelFont)
	{
		this.label = caption.toUpperCase();
		this.labelTypeface = labelFont;
		this.labelColor = Color.WHITE;
		this.labelSize = 20;

		setPixmap(createLabelPixmap(this.label));
	}

	public IPixmap createLabelPixmap(String caption)
	{
		if(caption.length() == 0)
			return null;
		
		Paint labelStyle = new Paint();
		labelStyle.setColor(labelColor);
		labelStyle.setTextSize(labelSize);
		labelStyle.setTypeface(labelTypeface);
		Rect captionRect = new Rect();
		labelStyle.getTextBounds(caption, 0, caption.length(), captionRect);

		Bitmap textBuffer = Bitmap.createBitmap(Math.abs(captionRect.right), Math.abs(captionRect.bottom-captionRect.top), Config.ARGB_4444);
		Canvas textCanvas = new Canvas(textBuffer);
		textCanvas.drawText(caption, 0, textBuffer.getHeight(), labelStyle);
		
		return new AndroidPixmap(textBuffer, PixmapFormat.ARGB4444);
	}
	
	public void setLabelTypeface(Typeface typeface)
	{
		this.labelTypeface = typeface;
		setPixmap(createLabelPixmap(this.label));
	}
	
	public void setLabelSize(int size)
	{
		this.labelSize = size;
		setPixmap(createLabelPixmap(this.label));
	}
	
	public void setLabelColor(int color)
	{
		this.labelColor = color;
		setPixmap(createLabelPixmap(this.label));
	}

	public void setLabel(String label)
	{
		this.label = label;
		setPixmap(createLabelPixmap(this.label));
	}


}
