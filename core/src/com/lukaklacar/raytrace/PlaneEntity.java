package com.lukaklacar.raytrace;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

public class PlaneEntity extends AbstractEntity {

    private final Plane plane;
    private Map<Vector3, Color> colorData;
    private Color color;

    public PlaneEntity(Vector3 normal, Vector3 point, Color color) {
        this.color = color;
        plane = new Plane(normal, point);
        colorData = new HashMap<>();
    }

    public IntersectionResult intersect(Ray ray) {
        Vector3 intersectionPoint = new Vector3(0, 0, 0);
        boolean intersects = Intersector.intersectRayPlane(ray, plane, intersectionPoint);

        if (intersects && ray.origin.dst(intersectionPoint) > 100) {
            return new IntersectionResult(false, null);
        }

        return new IntersectionResult(intersects, intersectionPoint);
    }

    @Override
    public Ray bounce(Ray ray, Vector3 intersectionPoint) {
        return new Ray(intersectionPoint, plane.normal.cpy().sub(ray.direction));

    }

    @Override
    public Vector3 getNormal(Vector3 point) {
        return this.plane.normal.cpy();
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
        throw new UnsupportedOperationException();
    }

}
