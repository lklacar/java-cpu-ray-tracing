package com.lukaklacar.raytrace;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.collision.Ray;

public class Renderer {

    private final Ray ray;
    private final int width;
    private final int height;
    private final Level level;
    private final Pixmap pixmap;
    private final Texture renderTexture;
    private static final int BOUNCE_COUNT = 2;

    public Renderer(int width, int height, Level level) {
        this.width = width;
        this.height = height;
        this.level = level;
        ray = new Ray(level.getCameraPosition(), level.getCameraLookAt());
        pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
        renderTexture = new Texture(pixmap);
    }

    public void render(SpriteBatch spriteBatch) {
        level.update();
        level.getCameraPosition().x += 0.1f;
        ray.origin.set(level.getCameraPosition());

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                ray.direction
                    .set(level.getCameraLookAt().x + width / 2f - i,
                        level.getCameraLookAt().y + height / 2f - j,
                        level.getCameraLookAt().z)
                    .nor();

                var pixelColor = castRay(ray, BOUNCE_COUNT);
                pixmap.setColor(pixelColor);

                pixmap.drawPixel(i, j);
            }
        }
        renderTexture.draw(pixmap, 0, 0);
        spriteBatch.draw(renderTexture, 0, 0);
    }

    private Color castRay(Ray ray, int bounce) {
        var result = level.getIntersectedEntity(ray);
        if (result == null) {
            return Color.BLACK;
        }
        var entity = result.getIntersectionEntity();
        var intersection = result.getIntersectionPoint();
        var directionToLight = level.getLightSource().cpy().sub(intersection);
        var originalColor = entity.getColorAtPosition(intersection);

        var shadowRay = new Ray(intersection.cpy(), directionToLight);
        var shadowResult = level.getIntersectedEntity(shadowRay);
        if (shadowResult != null && shadowResult.getIntersectionEntity() instanceof SphereEntity) {
            originalColor = new Color(
                originalColor.r * 0.2f,
                originalColor.g * 0.2f,
                originalColor.b * 0.2f,
                1f
            );
        }

        if (bounce == 0) {
            return originalColor;
        }

        var bounceRay = entity.bounce(ray, intersection);
        var bounceColor = castRay(bounceRay, bounce - 1);

        return new Color(
            originalColor.r * 0.6f + bounceColor.r * 0.4f,
            originalColor.g * 0.6f + bounceColor.g * 0.4f,
            originalColor.b * 0.6f + bounceColor.b * 0.4f,
            1f
        );
    }

}
