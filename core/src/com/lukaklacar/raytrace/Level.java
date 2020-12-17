package com.lukaklacar.raytrace;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    public Optional<AbstractEntity> getIntersectedEntity(final Ray ray) {
        return entities
            .stream()
            .filter(entity -> entity.intersect(ray).isIntersects())
            .findFirst();
    }

}
