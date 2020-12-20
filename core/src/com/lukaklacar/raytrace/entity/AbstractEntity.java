package com.lukaklacar.raytrace.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.lukaklacar.raytrace.math.IntersectionResult;

public abstract class AbstractEntity {

    public abstract IntersectionResult intersect(Ray ray);

    public abstract Ray bounce(Ray ray, Vector3 intersectionPoint);

    public abstract Vector3 getNormal(Vector3 point);

    public abstract Color getColorAtPosition(Vector3 point);

    public abstract Vector3 getPosition();
}
