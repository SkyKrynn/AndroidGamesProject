package com.brsmith.android.games.framework.gl3d;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by AndroidDev on 10/29/13.
 */
public class HierarchicalObject
{
    public float x, y, z;
    public float scale = 1;
    public float rotationY, rotationParent;
    public boolean hasParent;
    public final List<HierarchicalObject> children = new ArrayList<HierarchicalObject>();
    public final Vertices3 mesh;

    public HierarchicalObject(Vertices3 mesh, boolean hasParent)
    {
        this.mesh = mesh;
        this.hasParent = hasParent;
    }

    public void update(float deltaTime)
    {
        rotationY += 45 * deltaTime;
        rotationParent += 20 * deltaTime;
        int len = children.size();
        for(int idx = 0; idx < len; idx++)
        {
            children.get(idx).update(deltaTime);
        }
    }

    public void render(GL10 gl)
    {
        gl.glPushMatrix();
        if(hasParent)
            gl.glRotatef(rotationParent, 0, 1, 0);
        gl.glTranslatef(x, y, z);
        gl.glPushMatrix();
        gl.glRotatef(rotationY, 0, 1, 0);
        gl.glScalef(scale, scale, scale);
        mesh.draw(GL10.GL_TRIANGLES, 0, 36);
        gl.glPopMatrix();

        int len = children.size();
        for(int idx = 0; idx < len; idx++)
        {
            children.get(idx).render(gl);
        }
        gl.glPopMatrix();
    }
}
