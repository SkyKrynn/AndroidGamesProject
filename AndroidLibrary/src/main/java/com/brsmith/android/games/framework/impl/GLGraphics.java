package com.brsmith.android.games.framework.impl;

import javax.microedition.khronos.opengles.GL10;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.opengl.GLSurfaceView;

public class GLGraphics 
{
	GLSurfaceView glView;
	GL10 gl;
    AssetManager assets;
	
	GLGraphics(AssetManager assets, GLSurfaceView view)
    {
        this.assets = assets;
        this.glView = view;
    }
	
	public GL10 getGL()
	{
		return gl;
	}
	
	public void setGL(GL10 gl)
	{
		this.gl = gl;
	}
	
	public int getWidth()
	{
		return glView.getWidth();
	}
	
	public int getHeight()
	{
		return glView.getHeight();
	}

    public Typeface newTypeface(String filename) { return Typeface.createFromAsset(assets, filename); }
}
