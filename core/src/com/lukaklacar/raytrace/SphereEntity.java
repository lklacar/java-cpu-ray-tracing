package com.lukaklacar.raytrace;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.math.collision.Sphere;

public class SphereEntity extends AbstractEntity {

    private final Sphere sphere;

    public SphereEntity(Vector3 center, float radius, Color color) {
        super(color);
        sphere = new Sphere(center, radius);
    }

    @Override
    public IntersectionResult intersect(Ray ray) {
        Vector3 intersectionPoint = new Vector3(0, 0, 0);
        boolean intersects = Intersector.intersectRaySphere(ray, sphere.center, sphere.radius, intersectionPoint);
        return new IntersectionResult(intersects, intersectionPoint);
    }
}
