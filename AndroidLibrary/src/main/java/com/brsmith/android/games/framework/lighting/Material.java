package com.brsmith.android.games.framework.lighting;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by AndroidDev on 10/29/13.
 */
public class Material
{
    float[] ambient = {0.2f, 0.2f, 0.2f, 1.0f};
    float[] diffuse = {1.0f, 1.0f, 1.0f, 1.0f};
    float[] specular = {0.0f, 0.0f, 0.0f, 1.0f};

    private void setLight(float[] light, float r, float g, float b, float a)
    {
        light[0] = r;
        light[1] = g;
        light[2] = b;
        light[3] = a;
    }

    public void setAmbient(float r, float g, float b, float a)
    {
        setLight(ambient, r, g, b, a);
    }

    public void setDiffuse(float r, float g, float b, float a)
    {
        setLight(diffuse, r, g, b, a);
    }

    public void setSpecular(float r, float g, float b, float a)
    {
        setLight(specular, r, g, b, a);
    }


    public void enable(GL10 gl)
    {
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, ambient, 0);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, diffuse, 0);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, specular, 0);
    }
}
