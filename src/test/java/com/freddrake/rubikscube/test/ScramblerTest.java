package com.freddrake.rubikscube.test;

import com.freddrake.rubikscube.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import static com.freddrake.rubikscube.CubeColor.*;
import static com.freddrake.rubikscube.Face.*;
import static com.freddrake.rubikscube.Direction.*;


/**
 *
 */
public class ScramblerTest {
    private Cube cube;
    private TestUtils testUtils;
    private Scrambler scrambler;
    @Mock Random random;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        cube = new Cube();
        testUtils = new TestUtils(cube);
        scrambler = new Scrambler();
        scrambler.setRandom(random);
        scrambler.setLowerBoundRotations(10);
        scrambler.setUpperBoundRotations(10);
    }

    @Test
    public void testSimpleScramble() throws Exception {
        // given
        int faces = Face.values().length;
        int directions = Direction.values().length;
        when(random.nextInt(1)).thenReturn(-9); // force 1 rotation to scramble
        when(random.nextInt(faces)).thenReturn(4); // RIGHT
        when(random.nextInt(directions)).thenReturn(1); // COUNTER_CLOCKWISE

        // when
        scrambler.scramble(cube);

        // then
        CubeColor[][] colors;
        colors = cube.getColorMapByFace(TOP);
        assertArrayEquals(testUtils.singleRowCubeFace(WHITE, WHITE, GREEN), colors);
    }

    @Test
    public void testTwoRotationScramble() throws Exception {
        // given
        int faces = Face.values().length;
        int directions = Direction.values().length;
        when(random.nextInt(1)).thenReturn(-8); // force 2 rotations to scramble
        when(random.nextInt(faces)).thenReturn(faceIdx(TOP), faceIdx(BOTTOM));
        when(random.nextInt(directions)).thenReturn(directionIdx(COUNTER_CLOCKWISE),
                directionIdx(CLOCKWISE));

        // when
        scrambler.scramble(cube);

        // then
        CubeColor[][] colors;
        colors = cube.getColorMapByFace(FRONT);
        assertArrayEquals(testUtils.longListToCubeFace(RED, RED, RED,
                BLUE, BLUE, BLUE, RED, RED, RED), colors);
    }

    @Test
    public void testTenRotationScramble() throws Exception {
        // given
        int numFaces = Face.values().length;
        int numDirections = Direction.values().length;
        // Storing rotation moves in a list for ease of reading.
        int[][] rotations = new int[][]{
                new int[]{faceIdx(RIGHT), directionIdx(HALF_TURN)},
                new int[]{faceIdx(TOP), directionIdx(COUNTER_CLOCKWISE)},
                new int[]{faceIdx(BOTTOM), directionIdx(COUNTER_CLOCKWISE)},
                new int[]{faceIdx(LEFT), directionIdx(CLOCKWISE)},
                new int[]{faceIdx(BACK), directionIdx(HALF_TURN)},
                new int[]{faceIdx(FRONT), directionIdx(CLOCKWISE)},
                new int[]{faceIdx(RIGHT), directionIdx(COUNTER_CLOCKWISE)},
                new int[]{faceIdx(TOP), directionIdx(CLOCKWISE)},
                new int[]{faceIdx(BOTTOM), directionIdx(HALF_TURN)}, // Good up to this point with front face
                new int[]{faceIdx(LEFT), directionIdx(COUNTER_CLOCKWISE)}
        };
        when(random.nextInt(1)).thenReturn(rotations.length - 10); // Force random # of rotations
        when(random.nextInt(numFaces)).thenReturn(rotations[0][0], rotations[1][0],
                rotations[2][0], rotations[3][0], rotations[4][0], rotations[5][0],
                rotations[6][0], rotations[7][0], rotations[8][0], rotations[9][0]);
        when(random.nextInt(numDirections)).thenReturn(rotations[0][1], rotations[1][1],
                rotations[2][1], rotations[3][1], rotations[4][1], rotations[5][1],
                rotations[6][1], rotations[7][1], rotations[8][1], rotations[9][1]);

        // when
        scrambler.scramble(cube);

        // then
        CubeColor[][] colors;
        colors = cube.getColorMapByFace(FRONT);
        assertArrayEquals(testUtils.longListToCubeFace(RED, BLUE, BLUE, RED, BLUE, WHITE, YELLOW, ORANGE, ORANGE), colors);
        colors = cube.getColorMapByFace(TOP);
        assertArrayEquals(testUtils.longListToCubeFace(GREEN, GREEN, YELLOW, ORANGE, WHITE, YELLOW, BLUE, YELLOW, YELLOW), colors);
        colors = cube.getColorMapByFace(LEFT);
        assertArrayEquals(testUtils.longListToCubeFace(ORANGE, WHITE, WHITE, WHITE, RED, WHITE, WHITE, ORANGE, ORANGE), colors);
        colors = cube.getColorMapByFace(RIGHT);
        assertArrayEquals(testUtils.longListToCubeFace(RED, RED, RED, BLUE, ORANGE, GREEN, GREEN, RED, WHITE), colors);
        colors = cube.getColorMapByFace(BACK);
        assertArrayEquals(testUtils.longListToCubeFace(GREEN, RED, WHITE, YELLOW, GREEN, GREEN, ORANGE, GREEN, GREEN), colors);
        colors = cube.getColorMapByFace(BOTTOM);
        assertArrayEquals(testUtils.longListToCubeFace(BLUE, ORANGE, RED, BLUE, YELLOW, BLUE, YELLOW, YELLOW, BLUE), colors);

    }

    private int faceIdx(Face face) {
        List<Face> list = Arrays.asList(Face.values());
        return list.indexOf(face);
    }

    private int directionIdx(Direction direction) {
        List<Direction> list = Arrays.asList(Direction.values());
        return list.indexOf(direction);
    }
}
