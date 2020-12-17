package com.lukaklacar.raytrace;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

public class PlaneEntity extends AbstractEntity {

    private final Plane plane;

    public PlaneEntity(Vector3 normal, Vector3 point, Color color) {
        super(color);
        plane = new Plane(normal, point);
    }

    public IntersectionResult intersect(Ray ray) {
        Vector3 intersectionPoint = new Vector3(0, 0, 0);
        boolean intersects = Intersector.intersectRayPlane(ray, plane, intersectionPoint);
        return new IntersectionResult(intersects, intersectionPoint);
    }

    @Override
    public Ray bounce(Ray ray, Vector3 intersectionPoint) {
        return new Ray(intersectionPoint, plane.normal.cpy().scl(2).sub(ray.direction));

    }

}
