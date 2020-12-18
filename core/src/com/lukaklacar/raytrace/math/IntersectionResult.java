package com.lukaklacar.raytrace.math;

import com.badlogic.gdx.math.Vector3;

public class IntersectionResult {

    private final boolean intersects;
    private final Vector3 intersectionPoint;

    public IntersectionResult(boolean intersects, Vector3 intersectionPoint) {
        this.intersects = intersects;
        this.intersectionPoint = intersectionPoint;
    }

    public boolean isIntersects() {
        return intersects;
    }

    public Vector3 getIntersectionPoint() {
        return intersectionPoint;
    }
}
