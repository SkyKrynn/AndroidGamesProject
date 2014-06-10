package com.brsmith.android.games.vanityplates;

import com.brsmith.android.games.framework.interfaces.IFileIO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Settings {
    public static boolean soundEnabled = true;
    public final static String file = ".vanityplates";

    public static void load(IFileIO files)
    {
        BufferedReader in = null;
        try
        {
            in = new BufferedReader(new InputStreamReader(files.readFile(file)));
            soundEnabled = Boolean.parseBoolean(in.readLine());
        }
        catch (IOException e)
        {
            // It's ok, we have defaults
        }
        catch (NumberFormatException e)
        {
            // It's ok, we have defaults
        }
        finally
        {
            try
            {
                if(in != null)
                    in.close();
            }
            catch (IOException e)
            {

            }
        }
    }

    public static void save(IFileIO files)
    {
        BufferedWriter out = null;
        try
        {
            out = new BufferedWriter(new OutputStreamWriter(files.writeFile(file)));

            out.write(Boolean.toString(soundEnabled));
            out.write("\n");
        }
        catch (IOException e)
        {
        }
        finally
        {
            try
            {
                if(out != null)
                    out.close();
            }
            catch (IOException e)
            {

            }
        }
    }
}
