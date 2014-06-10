package com.brsmith.android.games.framework.gl;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.opengl.GLUtils;

import com.brsmith.android.games.framework.impl.GLGame;
import com.brsmith.android.games.framework.impl.GLGraphics;
import com.brsmith.android.games.framework.interfaces.IFileIO;

public class Texture 
{
	GLGraphics glGraphics;
	IFileIO fileIO;
	String filename;
    Bitmap bitmap;
	int textureId;
	int minFilter;
	int magFilter;
	public int width;
	public int height;
    boolean mipmapped;

    public Texture(GLGame glGame, String filename)
    {
        this(glGame, filename, false);
    }

	public Texture(GLGame glGame, String filename, boolean mipmapped)
	{
        this.glGraphics = glGame.getGLGraphics();
        this.fileIO = glGame.getFileIO();
        this.mipmapped = mipmapped;
		this.filename =  filename;
        this.bitmap = read(filename);
        load();
	}

    public Texture(GLGame glGame, Bitmap bitmap) { this(glGame, bitmap, false); }

    public Texture(GLGame glGame, Bitmap bitmap, boolean mipmapped)
    {
        this.glGraphics = glGame.getGLGraphics();
        this.fileIO = glGame.getFileIO();
        this.mipmapped = mipmapped;
        this.bitmap = bitmap;
        load();
    }

    private Bitmap read(String filename)
    {
        InputStream in = null;
        Bitmap bitmap = null;
        try
        {
            in = fileIO.readAsset(filename);
            bitmap = BitmapFactory.decodeStream(in);
        }
        catch (IOException ex)
        {
            throw new RuntimeException("Couldn't load texture '" + filename + "'", ex);
        }
        finally
        {
            if(in != null)
                try { in.close(); } catch (IOException ex) {}
        }

        return bitmap;
    }

	private void load()
	{
		GL10 gl = glGraphics.getGL();
		int[] textureIds = new int[1];
		gl.glGenTextures(1, textureIds, 0);
		textureId = textureIds[0];
		
         if(mipmapped)
         {
             createMipmaps(gl, bitmap);
         }
         else
         {
             gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
             GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
             setFilters(GL10.GL_NEAREST, GL10.GL_NEAREST);
             gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
             width = bitmap.getWidth();
             height = bitmap.getHeight();
             if(!filename.isEmpty())
                 bitmap.recycle();
         }
	}

    private void createMipmaps(GL10 gl, Bitmap bitmap)
    {
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        setFilters(GL10.GL_LINEAR_MIPMAP_NEAREST, GL10.GL_LINEAR);

        int level = 0;
        int newWidth = width;
        int newHeight = height;
        while(true)
        {
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, level, bitmap, 0);
            newWidth = newWidth / 2;
            newHeight = newHeight / 2;
            if(newWidth <= 0)
                break;

            Bitmap newBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
            Canvas canvas = new Canvas(newBitmap);
            canvas.drawBitmap(bitmap,
                    new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()),
                    new Rect(0, 0, newWidth, newHeight), null);
            bitmap.recycle();
            bitmap = newBitmap;
            level++;
        }

        gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
        if(!filename.isEmpty())
            bitmap.recycle();
    }
	
	public void setFilters(int minFilter, int magFilter)
	{
		this.minFilter = minFilter;
		this.magFilter = magFilter;
		GL10 gl = glGraphics.getGL();
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, minFilter);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, magFilter);
	}
	
	public void reload()
	{
        if(!filename.isEmpty())
            bitmap = read(filename);
		load();
		bind();
		setFilters(minFilter, magFilter);
		glGraphics.getGL().glBindTexture(GL10.GL_TEXTURE_2D, 0);
	}
	
	public void bind()
	{
		GL10 gl = glGraphics.getGL();
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
	}
	
	public void dispose()
	{
		GL10 gl = glGraphics.getGL();
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		int[] textureIds = { textureId };
		gl.glDeleteTextures(1, textureIds, 0);
	}
}
