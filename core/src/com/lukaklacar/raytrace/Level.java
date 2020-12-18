package com.lukaklacar.raytrace;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

public class Level {

    private final List<AbstractEntity> entities;
    private final Vector3 lightSource;
    private final Vector3 cameraPosition;
    private final Vector3 cameraLookAt;

    public Level() {
        lightSource = new Vector3(100, 100, 50);
        cameraPosition = new Vector3(0, 20, 0);
        cameraLookAt = new Vector3(0, -100, 554.26f);

        entities = Arrays.asList(
            new SphereEntity(new Vector3(0, 10, 50), 10),
            new SphereEntity(new Vector3(30, 10, 50), 10),
            new SphereEntity(new Vector3(50, 10, 50), 10),
            new PlaneEntity(new Vector3(0, 1, 0), new Vector3(0, 0, 0))
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
