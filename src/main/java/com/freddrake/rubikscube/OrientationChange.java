package com.freddrake.rubikscube;

/**
 *
 */
public class OrientationChange implements SolveStep {
    private Face face;
    private CubeColor color;

    public OrientationChange(Face face, CubeColor color) {
        this.face = face;
        this.color = color;
    }

    public Face getFace() {
        return face;
    }

    public CubeColor getColor() {
        return color;
    }
}
