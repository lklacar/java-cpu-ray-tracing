package com.lukaklacar.raytrace.level;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.lukaklacar.raytrace.math.LevelIntersectionResult;
import com.lukaklacar.raytrace.entity.AbstractEntity;
import com.lukaklacar.raytrace.entity.PlaneEntity;
import com.lukaklacar.raytrace.entity.SphereEntity;

public class Level {

    private final List<AbstractEntity> entities;
    private final Vector3 lightSource;
    private final Vector3 cameraPosition;
    private final Vector3 cameraLookAt;

    public Level() {
        lightSource = new Vector3(100, 100, 50);
        cameraPosition = new Vector3(0, 10, -20);
        cameraLookAt = new Vector3(0, 100, 1000);

        entities = Arrays.asList(
                new SphereEntity(new Vector3(0, 10, 50), 10, Color.BLUE),
                new SphereEntity(new Vector3(-20, 10, 60), 10, Color.GREEN),
                new SphereEntity(new Vector3(50, 10, 50), 10, Color.GOLD),
                new SphereEntity(new Vector3(10, 10, 90), 10, Color.RED),
                new PlaneEntity(new Vector3(0, 1, 0), new Vector3(0, -5, 0), Color.LIGHT_GRAY, Color.DARK_GRAY)
        );
    }

    public LevelIntersectionResult getIntersectedEntity(final Ray ray) {
        for (var entity : entities) {
            var intersectionResult = entity.intersect(ray);
            if (intersectionResult.isIntersects()) {
                return new LevelIntersectionResult(entity, intersectionResult.getIntersectionPoint());
            }
        }

        return null;
    }

    public void update() {
        cameraPosition.x += 0.1f;
        cameraLookAt.rotate(new Vector3(0, 1, 0), -0.03f);
        entities.get(0).getPosition().z -= 0.1f;
    }

    public Vector3 getLightSource() {
        return lightSource;
    }

    public Vector3 getCameraPosition() {
        return cameraPosition;
    }

    public Vector3 getCameraLookAt() {
        return cameraLookAt;
    }
}
