package com.lukaklacar.raytrace;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

public abstract class AbstractEntity {

    private final Color color;

    public AbstractEntity(Color color) {
        this.color = color;
    }

    public abstract IntersectionResult intersect(Ray ray);

    public Color getColor() {
        return color;
    }

    public abstract Ray bounce(Ray ray, Vector3 intersectionPoint);

}
