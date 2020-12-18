package com.lukaklacar.raytrace.math;

import com.badlogic.gdx.math.Vector3;
import com.lukaklacar.raytrace.entity.AbstractEntity;

public class LevelIntersectionResult {

    private final AbstractEntity intersectionEntity;
    private final Vector3 intersectionPoint;

    public LevelIntersectionResult(AbstractEntity intersectionEntity, Vector3 intersectionPoint) {
        this.intersectionEntity = intersectionEntity;
        this.intersectionPoint = intersectionPoint;
    }

    public AbstractEntity getIntersectionEntity() {
        return intersectionEntity;
    }

    public Vector3 getIntersectionPoint() {
        return intersectionPoint;
    }
}
