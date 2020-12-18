package com.lukaklacar.raytrace.entity;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.math.collision.Sphere;
import com.lukaklacar.raytrace.math.IntersectionResult;

public class SphereEntity extends AbstractEntity {

    private final Sphere sphere;
    private Map<Vector3, Color> colorData;
    private Color color;

    public SphereEntity(Vector3 center, float radius, Color color) {
        this.color = color;
        sphere = new Sphere(center, radius);
        colorData = new HashMap<>();
    }

    @Override
    public IntersectionResult intersect(Ray ray) {
        Vector3 intersectionPoint = new Vector3(0, 0, 0);
        boolean intersects = Intersector.intersectRaySphere(ray, sphere.center, sphere.radius, intersectionPoint);
        return new IntersectionResult(intersects, intersectionPoint);
    }

    @Override
    public Ray bounce(Ray ray, Vector3 intersectionPoint) {
        var normal2 = getNormal(intersectionPoint).scl(2);
        return new Ray(intersectionPoint, normal2.sub(ray.direction));
    }

    @Override
    public Vector3 getNormal(Vector3 point) {
        return point.cpy().sub(sphere.center);
    }

    @Override
    public Color getColorAtPosition(Vector3 point) {

        return color;
    }

    @Override
    public void setColor(Vector3 position, Color color) {
        colorData.put(position, color);
    }

    @Override
    public Vector3 getPosition() {
        return sphere.center;
    }
}
