package com.lukaklacar.raytrace.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.collision.Ray;
import com.lukaklacar.raytrace.entity.PlaneEntity;
import com.lukaklacar.raytrace.level.Level;
import com.lukaklacar.raytrace.entity.SphereEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Renderer {

    private final List<Ray> rays;
    private final int screenWidth;
    private final int screenHeight;
    private final Level level;
    private final int threadCount;
    private final Pixmap pixmap;
    private final Texture renderTexture;
    private static final int BOUNCE_COUNT = 2;

    public Renderer(int screenWidth, int screenHeight, Level level, int threadCount) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.level = level;
        this.threadCount = threadCount;
        rays = IntStream.range(0, threadCount).boxed().map(i -> new Ray(level.getCameraPosition(), level.getCameraLookAt())).collect(Collectors.toList());
        pixmap = new Pixmap(screenWidth, screenHeight, Pixmap.Format.RGB888);
        renderTexture = new Texture(pixmap);
    }

    public void startRendererThreads() {
        IntStream.range(0, threadCount)
                .forEach(i -> {
                    int offset = screenWidth / threadCount;
                    new Thread(() -> {
                        renderSegment(offset * i, 0, offset * (i + 1), screenHeight, rays.get(i));
                    }).start();
                });
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void renderSegment(int x, int y, int width, int height, Ray ray) {
        while (true) {
            for (int i = x; i < width; i++) {
                for (int j = y; j < height; j++) {
                    ray.direction
                            .set(level.getCameraLookAt().x - i,
                                    level.getCameraLookAt().y - j,
                                    level.getCameraLookAt().z)
                            .nor();

                    var pixelColor = castRay(ray, BOUNCE_COUNT);
                    pixmap.setColor(pixelColor);

                    pixmap.drawPixel(i, j);
                }
            }
        }


    }

    public void render(SpriteBatch spriteBatch) {
        level.update();
        rays.forEach(ray -> ray.origin.set(level.getCameraPosition()));

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

        // Don't bounce empty space back to the plane entity
        if (entity instanceof PlaneEntity && bounceColor.equals(Color.BLACK)) {
            return originalColor;
        }

        return new Color(
                originalColor.r * 0.6f + bounceColor.r * 0.4f,
                originalColor.g * 0.6f + bounceColor.g * 0.4f,
                originalColor.b * 0.6f + bounceColor.b * 0.4f,
                1f
        );
    }

}
