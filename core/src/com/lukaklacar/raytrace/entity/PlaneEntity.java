package com.lukaklacar.raytrace.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.lukaklacar.raytrace.math.IntersectionResult;

public class PlaneEntity extends AbstractEntity {

    private final Plane plane;
    private final Color color1;
    private final Color color2;

    public PlaneEntity(Vector3 normal, Vector3 point, Color color1, Color color2) {
        this.color1 = color1;
        this.color2 = color2;
        plane = new Plane(normal, point);
    }

    public IntersectionResult intersect(Ray ray) {
        Vector3 intersectionPoint = new Vector3(0, 0, 0);
        boolean intersects = Intersector.intersectRayPlane(ray, plane, intersectionPoint);

        if (intersects && ray.origin.dst(intersectionPoint) > 300) {
            return new IntersectionResult(false, null);
        }

        return new IntersectionResult(intersects, intersectionPoint);
    }

    @Override
    public Ray bounce(Ray ray, Vector3 intersectionPoint) {
        return new Ray(intersectionPoint, plane.normal.cpy().scl(2).sub(ray.direction));

    }

    @Override
    public Vector3 getNormal(Vector3 point) {
        return this.plane.normal.cpy();
    }

    @Override
    public Color getColorAtPosition(Vector3 point) {
        if (Math.abs(point.x) % 10 > 5 && Math.abs(point.z) % 10 < 5) {
            return color1;
        } else {
            return color2;
        }

    }

    @Override
    public Vector3 getPosition() {
        throw new UnsupportedOperationException();
    }

}
