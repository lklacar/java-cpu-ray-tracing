package com.lukaklacar.raytrace;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Raytracer extends ApplicationAdapter {
    private SpriteBatch batch;
    private Level level;
    private Renderer renderer;

    @Override
    public void create() {
        batch = new SpriteBatch();
        level = new Level();
        renderer = new Renderer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), level);
    }

    @Override
    public void render() {
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
