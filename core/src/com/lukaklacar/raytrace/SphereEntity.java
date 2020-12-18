package com.lukaklacar.raytrace;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.math.collision.Sphere;

public class SphereEntity extends AbstractEntity {

    private final Sphere sphere;
    private Map<Vector3, Color> colorData;

    public SphereEntity(Vector3 center, float radius) {
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
        if (colorData.containsKey(point)) {
            return colorData.get(point);
        }

        return Color.RED;
    }

    @Override
    public void setColor(Vector3 position, Color color) {
        colorData.put(position, color);
    }
}
