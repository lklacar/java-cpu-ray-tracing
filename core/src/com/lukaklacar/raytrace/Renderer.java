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
    private float currentCameraRotation = 0f;
    private final Texture renderTexture;
    private static final int BOUNCE_COUNT = 1;

    public Renderer(int width, int height, Level level) {
        this.width = width;
        this.height = height;
        this.level = level;
        ray = new Ray(new Vector3(50, 0, 0), new Vector3(0, 0, 0));
        pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
        renderTexture = new Texture(pixmap);
    }

    public void render(SpriteBatch spriteBatch) {
        currentCameraRotation += 0.1f;

        ((SphereEntity)level.getEntities().get(0)).getSphere().center.z -= 0.1f;
        ((SphereEntity)level.getEntities().get(1)).getSphere().center.x -= 0.1f;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                ray.direction.set(width / 2f - i, height / 2f - j, 500);
                ray.direction.set(ray.direction.nor());
                ray.direction.rotate(rotationAxis, currentCameraRotation);

                var levelIntersectionResult = level.getIntersectedEntity(ray);

                if (levelIntersectionResult != null) {
                    var intersectedEntity = levelIntersectionResult.getIntersectionEntity();

                    var bounceRay = intersectedEntity.bounce(ray, levelIntersectionResult.getIntersectionPoint());

                    if (bounceRay != null) {
                        var bounceLevelIntersectionResult = level.getIntersectedEntity(bounceRay);

                        if (bounceLevelIntersectionResult != null) {
                            var originalColor = levelIntersectionResult.getIntersectionEntity().getColor();
                            var reflectionColor = bounceLevelIntersectionResult.getIntersectionEntity().getColor();
                            var newColor = new Color(
                                originalColor.r * 0.6f + reflectionColor.r * 0.4f,
                                originalColor.g * 0.6f + reflectionColor.g * 0.4f,
                                originalColor.b * 0.6f + reflectionColor.b * 0.4f,
                                1f
                            );
                            pixmap.setColor(newColor);
                        } else {
                            pixmap.setColor(intersectedEntity.getColor());
                        }
                    } else {
                        pixmap.setColor(intersectedEntity.getColor());
                    }
                } else {
                    pixmap.setColor(Color.BLACK);
                }

                pixmap.drawPixel(i, j);

            }
        }
        renderTexture.draw(pixmap, 0, 0);
        spriteBatch.draw(renderTexture, 0, 0);
    }

    private Vector3 calculateBounce(Ray ray, Vector3 intersectionPoint) {
        return null;
    }

}
