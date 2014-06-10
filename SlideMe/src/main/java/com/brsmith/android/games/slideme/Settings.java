package com.brsmith.android.games.slideme;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.brsmith.android.games.framework.interfaces.IFileIO;

public class Settings 
{
	private final static String SETTINGS_FILENAME = ".slideme";
	
	public static boolean soundEnabled = true;
	public static int numCols = 4;
	public static int numRows = 4;
	public static String tileboardImageUri = "";
	public static String cameraImageUri = "";
	
	public static enum TileType
	{
		Numbered,
		Image,
		Camera
	};
	public static TileType tileType = TileType.Numbered;

	public static void load(IFileIO files)
	{
		BufferedReader in = null;
		try
		{
			in = new BufferedReader(new InputStreamReader(files.readFile(SETTINGS_FILENAME)));
			soundEnabled = Boolean.parseBoolean(in.readLine());
			numCols = Integer.parseInt(in.readLine());
			numRows = Integer.parseInt(in.readLine());
			tileType = TileType.valueOf(in.readLine());
			tileboardImageUri = in.readLine();
			cameraImageUri = in.readLine();
		}
		catch (IOException e)
		{
			// Ignore, we have defaults
		}
		catch (NumberFormatException e)
		{
			// Ignore, we have defaults
		}
		finally
		{
			try
			{
				if(in != null)
					in.close();
			}
			catch(IOException e)
			{
				
			}
		}
	}
	
	public static void save(IFileIO files)
	{
		BufferedWriter out = null;
		try
		{
			out = new BufferedWriter(new OutputStreamWriter(files.writeFile(SETTINGS_FILENAME)));
			out.write(Boolean.toString(soundEnabled));
			out.write(Integer.toString(numCols));
			out.write(Integer.toString(numRows));
			out.write(tileType.toString());
			out.write(tileboardImageUri);
			out.write(cameraImageUri);
		}
		catch (IOException e)
		{
			// Ignore, we have defaults
		}
		catch (NumberFormatException e)
		{
			// Ignore, we have defaults
		}
		finally
		{
			try
			{
				if(out != null)
					out.close();
			}
			catch(IOException e)
			{
				
			}
		}
	}
	
}

