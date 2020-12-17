package com.lukaklacar.raytrace;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.math.collision.Sphere;

public class Raytracer extends ApplicationAdapter {
    private SpriteBatch batch;
    private Sphere sphere;
    private Ray ray;
    private int width;
    private int height;
    private Pixmap pixmap;
    private Plane plane;

    @Override
    public void create() {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        batch = new SpriteBatch();
        sphere = new Sphere(new Vector3(50, 0, 50), 10);
        ray = new Ray(
            new Vector3(0, 0, 0),
            new Vector3(0, 0, 0));

        plane = new Plane(new Vector3(0, 1, 0), new Vector3(0, -25, 0));

        pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
    }

    float rotate = 0;

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        rotate++;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                ray.direction.set(width / 2f - i, height / 2f - j, 500);
                ray.direction.rotate(new Vector3(0, 1, 0), rotate);
                ray.direction.set(ray.direction.nor());
                boolean intersectsSphere = Intersector.intersectRaySphere(ray, sphere.center, sphere.radius, null);
                if (intersectsSphere) {
                    pixmap.setColor(com.badlogic.gdx.graphics.Color.WHITE);
                } else {

                    boolean intersectsPlane = Intersector.intersectRayPlane(ray, plane, null);
                    if (intersectsPlane) {
                        pixmap.setColor(com.badlogic.gdx.graphics.Color.RED);
                    } else {
                        pixmap.setColor(com.badlogic.gdx.graphics.Color.BLACK);
                    }
                }
                pixmap.drawPixel(i, j);
            }
        }

        Texture tex = new Texture(pixmap);
        batch.draw(tex, 0, 0);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
