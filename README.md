# Java Real-Time Ray Tracer

## Description

This repository contains a proof of concept implementation of real-time path tracing algorithm. The algorithm is
implemented completely in java while the rendering is done with libgdx and OpenGL. Note that no parts of the algorithm
other than rendering the 2D image are done in OpenGL.

The whole algorithm is computed completely on the CPU and is running on a single thread, so the performance is far from
perfect.

## Example

![](assets/example.gif)