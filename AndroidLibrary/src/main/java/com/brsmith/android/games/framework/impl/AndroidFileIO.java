package com.brsmith.android.games.framework.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.res.AssetManager;
import android.os.Environment;

import com.brsmith.android.games.framework.interfaces.IFileIO;

public class AndroidFileIO implements IFileIO
{
	AssetManager assets;
	String externalStoragePath;
	
	public AndroidFileIO(AssetManager assets)
	{
		this.assets = assets;
		this.externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
	}

	public InputStream readAsset(String filename) throws IOException
	{
		return assets.open(filename);
	}

	public InputStream readFile(String filename) throws IOException
	{
		return new FileInputStream(externalStoragePath + filename);
	}

	public OutputStream writeFile(String filename) throws IOException
	{
		return new FileOutputStream(externalStoragePath + filename);
	}
}
