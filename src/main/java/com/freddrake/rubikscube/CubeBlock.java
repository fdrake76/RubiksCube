package com.freddrake.rubikscube;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class CubeBlock {
    private List<CubeColor> colorList;

    public CubeBlock() {
        colorList = Collections.emptyList();
    }

    public CubeBlock(CubeColor... colors) {
        colorList = Arrays.asList(colors);
    }

    public List<CubeColor> getColorList() {
        return Collections.unmodifiableList(colorList);
    }

    public boolean isHiddenBlock() { return colorList.size() == 0; }
    public boolean isSideBlock() { return colorList.size() == 1; }
    public boolean isEdgeBlock() { return colorList.size() == 2; }
    public boolean isCornerBlock() { return colorList.size() == 3; }

    public void shiftColorsLeft() {
        if (isHiddenBlock() || isSideBlock()) return;

        Collections.rotate(colorList, -1);
    }

    public void shiftColorsRight() {
        if (isHiddenBlock() || isSideBlock()) return;
        Collections.rotate(colorList, 1);
    }

    @Override
    public String toString() {
        String type = "UNKNOWN";
        if (isSideBlock()) type = "Side";
        if (isEdgeBlock()) type = "Edge";
        if (isCornerBlock()) type = "Corner";
        if (isHiddenBlock()) type = "Hidden";
        StringBuilder desc = new StringBuilder("CubeBlock ("+type+",");
        if (colorList != null) {
            for(CubeColor color : colorList) {
                desc.append(" ").append(color.name().charAt(0));
            }
        }
        desc.append(")");
        return desc.toString();
    }
}
