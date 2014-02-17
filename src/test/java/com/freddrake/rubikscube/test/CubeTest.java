package com.freddrake.rubikscube.test;

import com.freddrake.rubikscube.Cube;
import com.freddrake.rubikscube.CubeColor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static com.freddrake.rubikscube.CubeColor.*;
import static com.freddrake.rubikscube.Face.*;
import static com.freddrake.rubikscube.Direction.*;

/**
 *
 */
public class CubeTest {
    private Cube cube;
    private TestUtils testUtils;

    @Before
    public void setUp() {
        cube = new Cube();
        testUtils = new TestUtils(cube);
    }

    @Test
    public void testInitialColorCheck() throws Exception {
        CubeColor[][] colors;
        colors = cube.getColorMapByFace(TOP);
        assertArrayEquals(testUtils.singleColorCubeFace(WHITE), colors);
        colors = cube.getColorMapByFace(FRONT);
        assertArrayEquals(testUtils.singleColorCubeFace(BLUE), colors);
        colors = cube.getColorMapByFace(RIGHT);
        assertArrayEquals(testUtils.singleColorCubeFace(ORANGE), colors);
        colors = cube.getColorMapByFace(LEFT);
        assertArrayEquals(testUtils.singleColorCubeFace(RED), colors);
        colors = cube.getColorMapByFace(BACK);
        assertArrayEquals(testUtils.singleColorCubeFace(GREEN), colors);
        colors = cube.getColorMapByFace(BOTTOM);
        assertArrayEquals(testUtils.singleColorCubeFace(YELLOW), colors);
    }

    @Test
    public void testRotateRightClockwise() throws Exception {
        cube.rotate(RIGHT, CLOCKWISE);
        CubeColor[][] colors;
        colors = cube.getColorMapByFace(TOP);
        assertArrayEquals(testUtils.singleRowCubeFace(WHITE, WHITE, BLUE), colors);
        colors = cube.getColorMapByFace(FRONT);
        assertArrayEquals(testUtils.singleRowCubeFace(BLUE, BLUE, YELLOW), colors);
        colors = cube.getColorMapByFace(RIGHT);
        assertArrayEquals(testUtils.singleColorCubeFace(ORANGE), colors);
        colors = cube.getColorMapByFace(LEFT);
        assertArrayEquals(testUtils.singleColorCubeFace(RED), colors);
        colors = cube.getColorMapByFace(BACK);
        assertArrayEquals(testUtils.singleRowCubeFace(WHITE, GREEN, GREEN), colors);
        colors = cube.getColorMapByFace(BOTTOM);
        assertArrayEquals(testUtils.singleRowCubeFace(YELLOW, YELLOW, GREEN), colors);
    }

    @Test
    public void testRotateRightCounterClockwise() throws Exception {
        cube.rotate(RIGHT, COUNTER_CLOCKWISE);
        CubeColor[][] colors;
        colors = cube.getColorMapByFace(TOP);
        assertArrayEquals(testUtils.singleRowCubeFace(WHITE, WHITE, GREEN), colors);
        colors = cube.getColorMapByFace(FRONT);
        assertArrayEquals(testUtils.singleRowCubeFace(BLUE, BLUE, WHITE), colors);
        colors = cube.getColorMapByFace(RIGHT);
        assertArrayEquals(testUtils.singleColorCubeFace(ORANGE), colors);
        colors = cube.getColorMapByFace(LEFT);
        assertArrayEquals(testUtils.singleColorCubeFace(RED), colors);
        colors = cube.getColorMapByFace(BACK);
        assertArrayEquals(testUtils.singleRowCubeFace(YELLOW, GREEN, GREEN), colors);
        colors = cube.getColorMapByFace(BOTTOM);
        assertArrayEquals(testUtils.singleRowCubeFace(YELLOW, YELLOW, BLUE), colors);
    }

    @Test
    public void testRotateLeftCounterClockwise() throws Exception {
        cube.rotate(LEFT, COUNTER_CLOCKWISE);
        CubeColor[][] colors;
        colors = cube.getColorMapByFace(TOP);
        assertArrayEquals(testUtils.singleRowCubeFace(BLUE, WHITE, WHITE), colors);
        colors = cube.getColorMapByFace(FRONT);
        assertArrayEquals(testUtils.singleRowCubeFace(YELLOW, BLUE, BLUE), colors);
        colors = cube.getColorMapByFace(RIGHT);
        assertArrayEquals(testUtils.singleColorCubeFace(ORANGE), colors);
        colors = cube.getColorMapByFace(LEFT);
        assertArrayEquals(testUtils.singleColorCubeFace(RED), colors);
        colors = cube.getColorMapByFace(BACK);
        assertArrayEquals(testUtils.singleRowCubeFace(GREEN, GREEN, WHITE), colors);
        colors = cube.getColorMapByFace(BOTTOM);
        assertArrayEquals(testUtils.singleRowCubeFace(GREEN, YELLOW, YELLOW), colors);
    }

    @Test
    public void testRotateTopClockwise() throws Exception {
        cube.rotate(TOP, CLOCKWISE);
        CubeColor[][] colors;
        colors = cube.getColorMapByFace(TOP);
        assertArrayEquals(testUtils.singleColorCubeFace(WHITE), colors);
        colors = cube.getColorMapByFace(FRONT);
        assertArrayEquals(testUtils.longListToCubeFace(ORANGE, ORANGE, ORANGE, BLUE, BLUE,
                BLUE, BLUE, BLUE, BLUE), colors);
        colors = cube.getColorMapByFace(RIGHT);
        assertArrayEquals(testUtils.longListToCubeFace(GREEN, GREEN, GREEN, ORANGE, ORANGE,
                ORANGE, ORANGE, ORANGE, ORANGE), colors);
        colors = cube.getColorMapByFace(LEFT);
        assertArrayEquals(testUtils.longListToCubeFace(BLUE, BLUE, BLUE, RED, RED, RED,
                RED, RED, RED), colors);
        colors = cube.getColorMapByFace(BACK);
        assertArrayEquals(testUtils.longListToCubeFace(RED, RED, RED, GREEN, GREEN, GREEN,
                GREEN, GREEN, GREEN), colors);
        colors = cube.getColorMapByFace(BOTTOM);
        assertArrayEquals(testUtils.singleColorCubeFace(YELLOW), colors);
    }

    @Test
    public void testRotateTopCounterClockwise() throws Exception {
        cube.rotate(TOP, COUNTER_CLOCKWISE);
        CubeColor[][] colors;
        colors = cube.getColorMapByFace(TOP);
        assertArrayEquals(testUtils.singleColorCubeFace(WHITE), colors);
        colors = cube.getColorMapByFace(FRONT);
        assertArrayEquals(testUtils.longListToCubeFace(RED, RED, RED, BLUE, BLUE,
                BLUE, BLUE, BLUE, BLUE), colors);
        colors = cube.getColorMapByFace(RIGHT);
        assertArrayEquals(testUtils.longListToCubeFace(BLUE, BLUE, BLUE, ORANGE, ORANGE,
                ORANGE, ORANGE, ORANGE, ORANGE), colors);
        colors = cube.getColorMapByFace(LEFT);
        assertArrayEquals(testUtils.longListToCubeFace(GREEN, GREEN, GREEN, RED, RED, RED,
                RED, RED, RED), colors);
        colors = cube.getColorMapByFace(BACK);
        assertArrayEquals(testUtils.longListToCubeFace(ORANGE, ORANGE, ORANGE, GREEN, GREEN, GREEN,
                GREEN, GREEN, GREEN), colors);
        colors = cube.getColorMapByFace(BOTTOM);
        assertArrayEquals(testUtils.singleColorCubeFace(YELLOW), colors);
    }

    @Test
    public void testRotateBottomClockwise() throws Exception {
        cube.rotate(BOTTOM, CLOCKWISE);
        CubeColor[][] colors;
        colors = cube.getColorMapByFace(TOP);
        assertArrayEquals(testUtils.singleColorCubeFace(WHITE), colors);
        colors = cube.getColorMapByFace(FRONT);
        assertArrayEquals(testUtils.longListToCubeFace(BLUE, BLUE, BLUE, BLUE, BLUE, BLUE,
                RED, RED, RED), colors);
        colors = cube.getColorMapByFace(RIGHT);
        assertArrayEquals(testUtils.longListToCubeFace(ORANGE, ORANGE, ORANGE, ORANGE,
                ORANGE, ORANGE, BLUE, BLUE, BLUE), colors);
        colors = cube.getColorMapByFace(LEFT);
        assertArrayEquals(testUtils.longListToCubeFace(RED, RED, RED, RED, RED, RED,
                GREEN, GREEN, GREEN), colors);
        colors = cube.getColorMapByFace(BACK);
        assertArrayEquals(testUtils.longListToCubeFace(GREEN, GREEN, GREEN, GREEN, GREEN,
                GREEN, ORANGE, ORANGE, ORANGE), colors);
        colors = cube.getColorMapByFace(BOTTOM);
        assertArrayEquals(testUtils.singleColorCubeFace(YELLOW), colors);
    }

    @Test
    public void testRotateBottomCounterClockwise() throws Exception {
        cube.rotate(BOTTOM, COUNTER_CLOCKWISE);
        CubeColor[][] colors;
        colors = cube.getColorMapByFace(TOP);
        assertArrayEquals(testUtils.singleColorCubeFace(WHITE), colors);
        colors = cube.getColorMapByFace(FRONT);
        assertArrayEquals(testUtils.longListToCubeFace(BLUE, BLUE, BLUE, BLUE, BLUE, BLUE,
                ORANGE, ORANGE, ORANGE), colors);
        colors = cube.getColorMapByFace(RIGHT);
        assertArrayEquals(testUtils.longListToCubeFace(ORANGE, ORANGE, ORANGE, ORANGE,
                ORANGE, ORANGE, GREEN, GREEN, GREEN), colors);
        colors = cube.getColorMapByFace(LEFT);
        assertArrayEquals(testUtils.longListToCubeFace(RED, RED, RED, RED, RED, RED,
                BLUE, BLUE, BLUE), colors);
        colors = cube.getColorMapByFace(BACK);
        assertArrayEquals(testUtils.longListToCubeFace(GREEN, GREEN, GREEN, GREEN, GREEN,
                GREEN, RED, RED, RED), colors);
        colors = cube.getColorMapByFace(BOTTOM);
        assertArrayEquals(testUtils.singleColorCubeFace(YELLOW), colors);
    }

    @Test
    public void testRightHalfTurn() throws Exception {
        cube.rotate(RIGHT, HALF_TURN);
        CubeColor[][] colors;
        colors = cube.getColorMapByFace(TOP);
        assertArrayEquals(testUtils.singleRowCubeFace(WHITE, WHITE, YELLOW), colors);

    }

    //    @Test
//    public void testRotations() throws Exception {
//        cube.rotateCube(RIGHT, CLOCKWISE);
//        cube.rotateCube(LEFT, COUNTER_CLOCKWISE);
//        cube.rotateCube(TOP, DOUBLE_CLOCKWISE);
//        cube.rotateCube(BOTTOM, DOUBLE_CLOCKWISE);
//        cube.rotateCube(FRONT, CLOCKWISE);
//        cube.rotateCube(BACK, COUNTER_CLOCKWISE);
//
//        CubeColor[][] cubeFace = cube.displayCubeSide(TOP);
//        assertArrayEquals(testUtils.longListToCubeFace(new CubeColor[]{
//                ORANGE, RED, ORANGE, BLUE, WHITE, BLUE, ORANGE, RED, ORANGE}), cubeFace);
//    }

//    @Test
//    public void testOrientationChangeTopToRight() throws Exception {
//        cube.changeCubeOrientation(TOP, RIGHT);
//
//        assertArrayEquals(testUtils.singleColorCubeFace(RED), cube.displayCubeSide(TOP));
//        assertArrayEquals(testUtils.singleColorCubeFace(WHITE), cube.displayCubeSide(RIGHT));
//        assertArrayEquals(testUtils.singleColorCubeFace(ORANGE), cube.displayCubeSide(BOTTOM));
//        assertArrayEquals(testUtils.singleColorCubeFace(YELLOW), cube.displayCubeSide(LEFT));
//        assertArrayEquals(testUtils.singleColorCubeFace(BLUE), cube.displayCubeSide(FRONT));
//        assertArrayEquals(testUtils.singleColorCubeFace(GREEN), cube.displayCubeSide(BACK));
//    }
//
//    @Test
//    public void testOrientationChangeFrontToBottom() throws Exception {
//        cube.changeCubeOrientation(FRONT, BOTTOM);
//
//        assertArrayEquals(testUtils.singleColorCubeFace(GREEN), cube.displayCubeSide(TOP));
//        assertArrayEquals(testUtils.singleColorCubeFace(ORANGE), cube.displayCubeSide(RIGHT));
//        assertArrayEquals(testUtils.singleColorCubeFace(BLUE), cube.displayCubeSide(BOTTOM));
//        assertArrayEquals(testUtils.singleColorCubeFace(RED), cube.displayCubeSide(LEFT));
//        assertArrayEquals(testUtils.singleColorCubeFace(WHITE), cube.displayCubeSide(FRONT));
//        assertArrayEquals(testUtils.singleColorCubeFace(YELLOW), cube.displayCubeSide(BACK));
//    }
//
//    @Test
//    public void testOrientationChangeLeftToFront() throws Exception {
//        cube.changeCubeOrientation(LEFT, FRONT);
//
//        assertArrayEquals(testUtils.singleColorCubeFace(WHITE), cube.displayCubeSide(TOP));
//        assertArrayEquals(testUtils.singleColorCubeFace(BLUE), cube.displayCubeSide(RIGHT));
//        assertArrayEquals(testUtils.singleColorCubeFace(YELLOW), cube.displayCubeSide(BOTTOM));
//        assertArrayEquals(testUtils.singleColorCubeFace(GREEN), cube.displayCubeSide(LEFT));
//        assertArrayEquals(testUtils.singleColorCubeFace(RED), cube.displayCubeSide(FRONT));
//        assertArrayEquals(testUtils.singleColorCubeFace(ORANGE), cube.displayCubeSide(BACK));
//    }
//
//    @Test
//    public void testRotateRightClockwise() throws Exception {
//        cube.rotateCube(RIGHT, CLOCKWISE);
//
//        assertArrayEquals(testUtils.longListToCubeFace(WHITE, WHITE, BLUE, WHITE, WHITE,
//                BLUE, WHITE, WHITE, BLUE), cube.displayCubeSide(TOP));
//    }
//
//    @Test
//    public void testRotateLeftClockwise() throws Exception {
//        cube.rotateCube(LEFT, CLOCKWISE);
//        assertArrayEquals(testUtils.singleRowCubeFace(GREEN, WHITE, WHITE), cube.displayCubeSide(TOP));
//        assertArrayEquals(testUtils.singleRowCubeFace(WHITE, BLUE, BLUE), cube.displayCubeSide(FRONT));
//        assertArrayEquals(testUtils.singleColorCubeFace(ORANGE), cube.displayCubeSide(RIGHT));
//        assertArrayEquals(testUtils.singleColorCubeFace(RED), cube.displayCubeSide(LEFT));
//        assertArrayEquals(testUtils.singleRowCubeFace(GREEN, GREEN, YELLOW), cube.displayCubeSide(BACK));
////        assertArrayEquals(testUtils.singleRowCubeFace(YELLOW, GREEN, GREEN), cube.displayCubeSide(BACK));
//        assertArrayEquals(testUtils.singleRowCubeFace(BLUE, YELLOW, YELLOW), cube.displayCubeSide(BOTTOM));
//    }

}
