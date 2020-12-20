package com.lukaklacar.raytrace;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lukaklacar.raytrace.level.Level;
import com.lukaklacar.raytrace.render.Renderer;

public class Raytracer extends ApplicationAdapter {
    private SpriteBatch batch;
    private Renderer renderer;
    private static final int THREAD_COUNT = 8;

    @Override
    public void create() {
        batch = new SpriteBatch();
        var level = new Level();
        renderer = new Renderer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), level, THREAD_COUNT);
        renderer.startRendererThreads();
    }

    @Override
    public void render() {
        Gdx.graphics.setTitle("FPS: " + Gdx.graphics.getFramesPerSecond());
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        renderer.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
