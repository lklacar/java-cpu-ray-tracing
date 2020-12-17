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
            new SphereEntity(new Vector3(50, 0, 50), 10, Color.WHITE),
            new PlaneEntity(new Vector3(0, 1, 0), new Vector3(0, -25, 0), Color.RED));
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

}
