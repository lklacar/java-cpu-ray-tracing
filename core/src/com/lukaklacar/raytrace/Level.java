package com.lukaklacar.raytrace;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

public class Level {

    private final List<AbstractEntity> entities;

    public Level() {
        entities = Arrays.asList(
            new SphereEntity(new Vector3(50, -10, 50), 10, Color.GREEN),
            new SphereEntity(new Vector3(80, -10, 60), 10, Color.BLUE),
            new SphereEntity(new Vector3(110, -10, 55), 10, Color.BROWN),
            new PlaneEntity(new Vector3(0, 1, 0), new Vector3(0, -25, 0), Color.RED)
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

    public List<AbstractEntity> getEntities() {
        return entities;
    }
}
