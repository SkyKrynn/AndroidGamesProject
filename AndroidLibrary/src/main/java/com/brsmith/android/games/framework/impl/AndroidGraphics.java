package com.brsmith.android.games.framework.impl;

import java.io.IOException;
import java.io.InputStream;

import android.content.ContentResolver;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;

import com.brsmith.android.games.framework.enums.PixmapFormat;
import com.brsmith.android.games.framework.interfaces.IGraphics;
import com.brsmith.android.games.framework.interfaces.IPixmap;

public class AndroidGraphics implements IGraphics
{
	AssetManager assets;
	Bitmap frameBuffer;
	Canvas canvas;
	Paint paint;
	Rect srcRect = new Rect();
	Rect dstRect = new Rect();
	ContentResolver resolver;
	
	public AndroidGraphics(AssetManager assets, Bitmap frameBuffer, ContentResolver resolver)
	{
		this.assets = assets;
		this.frameBuffer = frameBuffer;
		this.resolver = resolver;
		this.canvas = new Canvas(frameBuffer);
		this.paint = new Paint();
	}

	public Typeface newTypeface(String filename)
	{
		return Typeface.createFromAsset(assets, filename);
	}

	public IPixmap newPixmap(String filename, PixmapFormat format)
	{
		Config config = null;
		if(format == PixmapFormat.RGB565)
			config = Config.RGB_565;
		else if(format == PixmapFormat.ARGB4444)
			config = Config.ARGB_4444;
		else
			config = Config.ARGB_8888;
		
		Options options = new Options();
		options.inPreferredConfig = config;
		
		InputStream in = null;
		Bitmap bitmap = null;
		try
		{
			in = assets.open(filename);
			bitmap = BitmapFactory.decodeStream(in);
			if(bitmap == null)
				throw new RuntimeException("Could not load bitmap from asset '" + filename + "'");
		}
		catch (IOException e)
		{
			throw new RuntimeException("Could not load bitmap from asset '" + filename + "'.  " + e.toString());
		}
		finally
		{
			if (in != null)
			{
				try
				{
					in.close();
				}
				catch (IOException e)
				{
				}
			}
		}
			
		if(bitmap.getConfig() == Config.RGB_565)
			format = PixmapFormat.RGB565;
		else if(bitmap.getConfig() == Config.ARGB_4444)
			format = PixmapFormat.ARGB4444;
		else
			format = PixmapFormat.ARGB8888;
			
		return new AndroidPixmap(bitmap, format);
	}

	public IPixmap newPixmap(Uri uri, PixmapFormat format)
	{
		Config config = null;
		if(format == PixmapFormat.RGB565)
			config = Config.RGB_565;
		else if(format == PixmapFormat.ARGB4444)
			config = Config.ARGB_4444;
		else
			config = Config.ARGB_8888;
		
		Options options = new Options();
		options.inPreferredConfig = config;
		
		InputStream in = null;
		Bitmap bitmap = null;
		try
		{
			in = resolver.openInputStream(uri);
			bitmap = BitmapFactory.decodeStream(in);
			if(bitmap == null)
				throw new RuntimeException("Could not load bitmap from uri '" + uri.toString() + "'");
		}
		catch (IOException e)
		{
			throw new RuntimeException("Could not load bitmap from uri '" + uri.toString() + "'.  " + e.toString());
		}
		finally
		{
			if (in != null)
			{
				try
				{
					in.close();
				}
				catch (IOException e)
				{
				}
			}
		}
			
		if(bitmap.getConfig() == Config.RGB_565)
			format = PixmapFormat.RGB565;
		else if(bitmap.getConfig() == Config.ARGB_4444)
			format = PixmapFormat.ARGB4444;
		else
			format = PixmapFormat.ARGB8888;
			
		return new AndroidPixmap(bitmap, format);
	}

	public void clear(int color) 
	{
		canvas.drawRGB(
				(color & 0xff0000) >> 16,
				(color & 0xff00) >> 8,
				(color & 0xff));
	}

	public void drawPixel(int x, int y, int color) 
	{
		paint.setColor(color);
		canvas.drawPoint(x, y, paint);
	}

	public void drawLine(int x, int y, int x2, int y2, int color)
	{
		paint.setColor(color);
		canvas.drawLine(x, y, x2, y2, paint);
	}

	public void drawRect(int x, int y, int width, int height, int color)
	{
		paint.setColor(color);
		paint.setStyle(Style.FILL);
		canvas.drawRect(x, y, x + width - 1, y + width - 1, paint);
	}

	public void drawPixmap(IPixmap pixmap, int x, int y, int srcX, int srcY,
			int srcWidth, int srcHeight)
	{
		srcRect.left = srcX;
		srcRect.top = srcY;
		srcRect.right = srcX + srcWidth - 1;
		srcRect.bottom = srcY + srcHeight - 1;
		
		dstRect.left = x;
		dstRect.top = y;
		dstRect.right = x + srcWidth - 1;
		dstRect.bottom = y + srcHeight - 1;
		
		canvas.drawBitmap(((AndroidPixmap)pixmap).bitmap, srcRect, dstRect, null);
	}

	public void drawPixmap(IPixmap pixmap, int x, int y)
	{
		canvas.drawBitmap(((AndroidPixmap)pixmap).bitmap, x, y, null);
	}

	public int getWidth()
	{
		return frameBuffer.getWidth();
	}

	public int getHeight() 
	{
		return frameBuffer.getHeight();
	}

}
