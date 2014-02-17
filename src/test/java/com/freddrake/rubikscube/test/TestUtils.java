package com.freddrake.rubikscube.test;

import com.freddrake.rubikscube.Cube;
import com.freddrake.rubikscube.CubeColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.fail;

/**
 *
 */
public class TestUtils {
    private Cube cube;

    public TestUtils(Cube cube) {
        this.cube = cube;
    }

    public CubeColor[][] singleColorCubeFace(CubeColor color) {
        CubeColor[][] cubeFace = new CubeColor[cube.getCubeComplexity()][];
        for(int i=0; i<cubeFace.length; i++) {
            cubeFace[i] = new CubeColor[cube.getCubeComplexity()];
            Arrays.fill(cubeFace[i], color);
        }

        return cubeFace;
    }

    public CubeColor[][] singleRowCubeFace(CubeColor... colorList) {
        int listLength = (int)Math.pow(colorList.length, 2);
        List<CubeColor> longList = new ArrayList<>(listLength);
        for(int i=0; i<colorList.length; i++) {
            longList.addAll(Arrays.asList(colorList));
        }
        return longListToCubeFace(longList.toArray(new CubeColor[listLength]));
    }

    public CubeColor[][] longListToCubeFace(CubeColor... colorList) {
        double expectedLength = Math.pow(cube.getCubeComplexity(), 2);
        if (colorList.length != expectedLength) {
            fail("List is illegal length ("+colorList.length+
                    ") and we expected "+expectedLength);
        }

        CubeColor[][] cubeFace = new CubeColor[cube.getCubeComplexity()][];
        int listCounter = 0;
        for(int i=0;i<cube.getCubeComplexity();i++) {
            cubeFace[i] = new CubeColor[cube.getCubeComplexity()];
            for(int j=0;j<cube.getCubeComplexity();j++) {
                cubeFace[i][j] = colorList[listCounter++];
            }
        }

        return cubeFace;
    }

}
