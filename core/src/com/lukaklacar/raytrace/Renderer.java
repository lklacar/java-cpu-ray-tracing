package com.lukaklacar.raytrace;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

public class Renderer {

    private final Ray ray;
    private final int width;
    private final int height;
    private final Level level;
    private final Pixmap pixmap;
    private final Vector3 rotationAxis = new Vector3(0, 1, 0);

    public Renderer(int width, int height, Level level) {
        this.width = width;
        this.height = height;
        this.level = level;
        ray = new Ray();
        pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
    }

    float rotation = 0f;

    public void render(SpriteBatch spriteBatch) {
        rotation += 1;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                ray.direction.set(width / 2f - i, height / 2f - j, 500);
                ray.direction.set(ray.direction.nor());
                ray.direction.rotate(rotationAxis, rotation);
                var intersectedEntity = level.getIntersectedEntity(ray);

                var renderColor = intersectedEntity
                    .map(AbstractEntity::getColor)
                    .orElse(Color.BLACK);

                pixmap.setColor(renderColor);
                pixmap.drawPixel(i, j);
            }
        }

        spriteBatch.draw(new Texture(pixmap), 0, 0);
    }

}
