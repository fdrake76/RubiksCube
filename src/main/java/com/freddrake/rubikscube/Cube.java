package com.freddrake.rubikscube;

import static com.freddrake.rubikscube.CubeColor.*;
import static com.freddrake.rubikscube.Face.*;

import java.util.*;

/**
 *
 */
public class Cube {
    private CubeBlock[][][] blockMap;


    // Bread-crumb map for handling orientation changes
    private Face[][] rotationList;

    // Groups faces for testing orientation changes
    private Map<Face, Integer> groupLevels;

    public Cube() {
        rotationList = new Face[][]{
                new Face[]{TOP, RIGHT, BOTTOM, LEFT}, // front-back static
                new Face[]{TOP, BACK, BOTTOM, FRONT}, // left-right static
                new Face[]{FRONT, LEFT, BACK, RIGHT} // top-bottom static
        };

        groupLevels = new HashMap<>();
        groupLevels.put(TOP, 1);
        groupLevels.put(BOTTOM, 1);
        groupLevels.put(LEFT, 2);
        groupLevels.put(RIGHT, 2);
        groupLevels.put(FRONT, 3);
        groupLevels.put(BACK, 3);

        blockMap = new CubeBlock[][][]{
                new CubeBlock[][]{
                        new CubeBlock[]{
                                new CubeBlock(WHITE, RED, BLUE),
                                new CubeBlock(WHITE, BLUE),
                                new CubeBlock(WHITE, BLUE, ORANGE)
                        },
                        new CubeBlock[]{
                                new CubeBlock(RED, BLUE),
                                new CubeBlock(BLUE),
                                new CubeBlock(BLUE, ORANGE)
                        },
                        new CubeBlock[]{
                                new CubeBlock(YELLOW, BLUE, RED),
                                new CubeBlock(YELLOW, BLUE),
                                new CubeBlock(YELLOW, ORANGE, BLUE)
                        }
                },
                new CubeBlock[][] {
                        new CubeBlock[]{
                                new CubeBlock(WHITE, RED),
                                new CubeBlock(WHITE),
                                new CubeBlock(WHITE, ORANGE)
                        },
                        new CubeBlock[]{
                                new CubeBlock(RED),
                                new CubeBlock(),
                                new CubeBlock(ORANGE)
                        },
                        new CubeBlock[]{
                                new CubeBlock(YELLOW, RED),
                                new CubeBlock(YELLOW),
                                new CubeBlock(YELLOW, ORANGE)
                        }
                },
                new CubeBlock[][] {
                        new CubeBlock[]{
                                new CubeBlock(WHITE, GREEN, RED),
                                new CubeBlock(WHITE, GREEN),
                                new CubeBlock(WHITE, ORANGE, GREEN)
                        },
                        new CubeBlock[]{
                                new CubeBlock(GREEN, RED),
                                new CubeBlock(GREEN),
                                new CubeBlock(ORANGE, GREEN)
                        },
                        new CubeBlock[]{
                                new CubeBlock(YELLOW, RED, GREEN),
                                new CubeBlock(YELLOW, GREEN),
                                new CubeBlock(YELLOW, GREEN, ORANGE)
                        }
                }
        };
    }

    public int getCubeComplexity() {
        return blockMap.length;
    }

    public void rotate(Face face, Direction direction) {
        if (direction == Direction.HALF_TURN) {
            rotate(face, Direction.CLOCKWISE);
            rotate(face, Direction.CLOCKWISE);
            return;
        }

        // Find the face that is NOT in the rotation list.
        int rotationListIndex = -1;
        for(int i=0; i<rotationList.length; i++) {
            if (!Arrays.asList(rotationList[i]).contains(face)) {
                 rotationListIndex = i;
                break;
            }
        }

        // rotationListIndex should never be -1, but bail out just in case
        if (rotationListIndex == -1) {
            throw new CubeException("Couldn't get rotation list");
        }

        // Based on the rotationListIndex and direction, we can determine
        // the previous and next faces that it should be.  For instance,
        // a right face clockwise rotation would produce a previous face
        // of TOP and a next face of BACK.  A left face clockwise rotation
        // would be the opposite direction (previous TOP, next FRONT).
        boolean moveRight = direction == Direction.CLOCKWISE;
        if (face == LEFT || face == BACK || face == BOTTOM) {
            moveRight = !moveRight;
        }
        Face previousFace = rotationList[rotationListIndex][0];
        Face nextFace = moveRight ?
                rotationList[rotationListIndex][1] :
                rotationList[rotationListIndex][rotationList[rotationListIndex].length-1];

        // Build a 3D boolean grid that determines which blocks will be moving.
        Boolean[][][] moveMap = new Boolean[getCubeComplexity()][][];
        for(int i=0; i<getCubeComplexity(); i++) {
            moveMap[i] = new Boolean[getCubeComplexity()][];
            for(int j=0; j<getCubeComplexity(); j++) {
                moveMap[i][j] = new Boolean[getCubeComplexity()];
                for(int k=0; k<getCubeComplexity(); k++) {
                    moveMap[i][j][k] = (face == TOP && j == 0) ||
                            (face == BOTTOM && j == getCubeComplexity() - 1) ||
                            (face == LEFT && k == 0) ||
                            (face == RIGHT && k == getCubeComplexity() - 1) ||
                            (face == FRONT && i == 0) ||
                            (face == BACK && i == getCubeComplexity() - 1);
                }
            }
        }

        // We know which blocks are changing, we know the rotation list, and we
        // know the direction of that list.  Create a new block map and shift
        // blocks to the appropriate locations.
        CubeBlock[][][] updatedBlockMap = new CubeBlock[getCubeComplexity()][][];
        for(int i=0; i<getCubeComplexity(); i++) {
            updatedBlockMap[i] = new CubeBlock[getCubeComplexity()][];
            for (int j=0; j<getCubeComplexity(); j++) {
                updatedBlockMap[i][j] = new CubeBlock[getCubeComplexity()];
            }
        }
        for(int i=0; i<getCubeComplexity(); i++) {
            for(int j=0; j<getCubeComplexity(); j++) {
                for (int k=0; k<getCubeComplexity(); k++) {
                    if (!moveMap[i][j][k]) {
                        // This block doesn't move.
                        updatedBlockMap[i][j][k] = blockMap[i][j][k];
                    } else {
                        // This block does.  Find out where it moves to, what the
                        // new colors are for the cube after rotation, then set it
                        // in the updatedBlockMap.
                        int[] coords = getCubeLocationByCoordinate(previousFace,
                                nextFace, i, j, k);
                        adjustCubeBlockRotation(previousFace, nextFace, i, j, k, coords[1]);
                        updatedBlockMap[coords[0]][coords[1]][coords[2]] =
                                blockMap[i][j][k];
                    }
                }
            }
        }

        blockMap = updatedBlockMap;
    }

    public CubeColor[][] getColorMapByFace(Face face) {
        CubeColor[][] colorMap = new CubeColor[getCubeComplexity()][];
        for (int i=0; i<getCubeComplexity(); i++) {
            colorMap[i] = new CubeColor[getCubeComplexity()];
            for (int j=0; j<getCubeComplexity(); j++) {
                CubeColor color;
                if (face == TOP) {
                    CubeBlock block = blockMap[getCubeComplexity() - 1 - i][0][j];
                    color = block.getColorList().get(0);
                } else if (face == BOTTOM) {
                    CubeBlock block = blockMap[getCubeComplexity() - 1 - i]
                            [getCubeComplexity() - 1][getCubeComplexity() - 1 - j];
                    color = block.getColorList().get(0);
                } else if (face == FRONT) {
                    CubeBlock block = blockMap[0][i][j];
                    color = getFrontRightBackFaceColor(i, j, block);
                } else if (face == RIGHT) {
                    CubeBlock block = blockMap[j][i][getCubeComplexity() - 1];
                    color = getFrontRightBackFaceColor(i, j, block);
                } else if (face == LEFT) {
                    CubeBlock block = blockMap[getCubeComplexity() - 1 - j]
                            [i][0];
                    color = getBackFaceColor(i, j, block);
                } else if (face == BACK) {
                    CubeBlock block = blockMap[getCubeComplexity() - 1]
                            [i][getCubeComplexity() - 1 - j];
                    color = getFrontRightBackFaceColor(i, j, block);
                } else {
                    throw new CubeException("Unknown face: "+face);
                }
                colorMap[i][j] = color;

            }
        }

        return colorMap;
    }

    private CubeColor getFrontRightBackFaceColor(int x, int y, CubeBlock block) {
        CubeColor color;
        if (block.isCornerBlock() && ((x == 0 && y == 0) ||
                (x == getCubeComplexity() - 1 && y == getCubeComplexity() - 1))) {
            // Corner blocks that use the third color for this face
            color = block.getColorList().get(2);
        } else if (block.isCornerBlock()) {
            // Corner blocks that use the second color for this face
            color = block.getColorList().get(1);
        } else if (block.isEdgeBlock() && (x == 0 || y == 0 ||
                x == getCubeComplexity() - 1)) {
            // Edge blocks that use the second color for this face
            color = block.getColorList().get(1);
        } else {
            // All else use the first color.
            color = block.getColorList().get(0);
        }
        return color;
    }

    private CubeColor getBackFaceColor(int x, int y, CubeBlock block) {
        CubeColor color;
        if (block.isCornerBlock() && ((x == 0 && y == 0) ||
                (x == getCubeComplexity() - 1 && y == getCubeComplexity() - 1))) {
            // Corner blocks that use the third color for this face
            color = block.getColorList().get(2);
        } else if (block.isCornerBlock()) {
            // Corner blocks that use the second color for this face
            color = block.getColorList().get(1);
        } else if (block.isEdgeBlock() && (x == 0 || y == 0 ||
                x == getCubeComplexity() - 1)) {
            // Edge blocks that use the second color for this face
            color = block.getColorList().get(1);
        } else {
            // All else use the first color.
            color = block.getColorList().get(0);
        }
        return color;
    }

    // Gets a particular cube's new coordinates, based on previous face, next face,
    // and current xyz coordinates.  Only supports TOP and FRONT as previous faces.
    private int[] getCubeLocationByCoordinate(Face previousFace, Face nextFace,
            int x, int y, int z) {
        int[] coordinates = new int[3];

        // Six turn possibilities for the cube
        if (previousFace == TOP && nextFace == FRONT) {
            coordinates[0] = y;
            coordinates[1] = getCubeComplexity() - 1 - x;
            coordinates[2] = z;
        } else if (previousFace == TOP && nextFace == BACK) {
            coordinates[0] = getCubeComplexity() - 1 - y;
            coordinates[1] = x;
            coordinates[2] = z;
        } else if (previousFace == TOP && nextFace == LEFT) {
            coordinates[0] = x;
            coordinates[1] = getCubeComplexity() - 1 - z;
            coordinates[2] = y;
        } else if (previousFace == TOP && nextFace == RIGHT) {
            coordinates[0] = x;
            coordinates[1] = z;
            coordinates[2] = getCubeComplexity() - 1 - y;
        } else if (previousFace == FRONT && nextFace == LEFT) {
            coordinates[0] = getCubeComplexity() - 1 - z;
            coordinates[1] = y;
            coordinates[2] = x;
        } else if (previousFace == FRONT && nextFace == RIGHT) {
            coordinates[0] = z;
            coordinates[1] = y;
            coordinates[2] = getCubeComplexity() - 1 - x;
        } else {
            throw new CubeException("Unknown face combination "+previousFace+", "+nextFace);
        }

        return coordinates;
    }

    private void adjustCubeBlockRotation(Face previousFace, Face nextFace,
                                              int x, int y, int z, int newY) {
        CubeBlock block = blockMap[x][y][z];
        if (block.isHiddenBlock() || block.isSideBlock()) {
            // No colors to rotate.
            return;
        }

        if (previousFace == TOP) {
            if (block.isEdgeBlock() && isEdgeShiftFrontBack(nextFace, x, y, z, newY)) {
                block.shiftColorsLeft();
            }

            if (block.isCornerBlock()) {
                if (isCornerBlockLeftShift(nextFace, x, y, z)) {
                    block.shiftColorsLeft();
                } else {
                    block.shiftColorsRight();
                }
            }
        }
    }

    // For calculating if an edge block's colors should be shifted.  Only
    // applicable if the previous face is TOP.
    private boolean isEdgeShiftFrontBack(Face nextFace, int x, int y, int z, int newY) {
        boolean shiftColors;
        if (nextFace == FRONT || nextFace == BACK) {
            shiftColors = !(y == getCubeComplexity() - 1 && z == 0) &&
                    !(x == getCubeComplexity() - 1 && z == 0) &&
                    !(x == 0 && z == getCubeComplexity() - 1) &&
                    !(y == 0 && z == getCubeComplexity() - 1);
            if (nextFace == BACK && x > 0 && x < getCubeComplexity() - 1) {
                return !shiftColors;
            }
            return shiftColors;
        } else if (nextFace == LEFT || nextFace == RIGHT) {
            shiftColors = !(x == 0 && y == 0) && !(x == 0 && z == 0) &&
                    !(x == getCubeComplexity() - 1 && z == getCubeComplexity() - 1) &&
                    !(x == getCubeComplexity() - 1 && y == getCubeComplexity() - 1);
            if (nextFace == RIGHT && newY > 0 && newY < getCubeComplexity() - 1) {
                return !shiftColors;
            }
            return shiftColors;
        }

        // Should never get here.
        throw new CubeException("Shouldn't be in this part of code.");
    }

    // We assume the block is a corner block and previous face is TOP.
    // true = corner colors should shift left, false = shift right
    private boolean isCornerBlockLeftShift(Face nextFace, int x, int y, int z) {
        boolean check = (x == 0 && y == 0 && z == getCubeComplexity() - 1) ||
                (x == 0 && y == getCubeComplexity() - 1 && z == 0) ||
                (x == getCubeComplexity() - 1 && y == 0 && z == 0) ||
                (x == getCubeComplexity() - 1 && y == getCubeComplexity() - 1 &&
                z == getCubeComplexity() - 1);
        if (nextFace == LEFT || nextFace == RIGHT) {
            check = !check;
        }
        return check;
    }
}
