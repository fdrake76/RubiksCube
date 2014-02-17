package com.freddrake.rubikscube;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class Scrambler {
    private Random random;
    private int lowerBoundRotations;
    private int upperBoundRotations;

    public Scrambler() {
        random = new SecureRandom();
        lowerBoundRotations = 50;
        upperBoundRotations = 100;
    }

    public void scramble(Cube cube) {
        int rotations = random.nextInt((upperBoundRotations -
                lowerBoundRotations) + 1) + lowerBoundRotations;
        for(int i=0; i<rotations; i++) {
            Face face = randomFace();
            Direction direction = randomDirection();
            cube.rotate(face, direction);
        }
    }

    private Face randomFace() {
        int rand = random.nextInt(Face.values().length);
        return Face.values()[rand];
    }

    private Direction randomDirection() {
        int rand = random.nextInt(Direction.values().length);
        return Direction.values()[rand];
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public void setLowerBoundRotations(int lowerBoundRotations) {
        this.lowerBoundRotations = lowerBoundRotations;
    }

    public void setUpperBoundRotations(int upperBoundRotations) {
        this.upperBoundRotations = upperBoundRotations;
    }
}
