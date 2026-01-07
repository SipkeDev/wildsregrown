package com.wildsregrown.blocks;

import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.List;

public abstract class VoxelTransform {

    public static final int[] matrix90  = new int[]{0, 1, -1, 0};
    public static final int[] matrix270 = new int[]{0, -1, 1, 0};
    public static final int[] matrix180 = new int[]{-1, 0, 0, -1};

    public static VoxelShape mirrorX(VoxelShape shape){
        List<Box> boxes = shape.getBoundingBoxes();
        VoxelShape[] shapes = new VoxelShape[boxes.size()];

        int i = 0;
        for (Box box: boxes) {
            shapes[i] = VoxelShapes.cuboid(1-box.maxX, box.minY, box.minZ, 1-box.minX, box.maxY, box.maxZ);
            i++;
        }
        return VoxelShapes.union(VoxelShapes.empty(), shapes);
    }
    public static VoxelShape mirrorY(VoxelShape shape){
        List<Box> boxes = shape.getBoundingBoxes();
        VoxelShape[] shapes = new VoxelShape[boxes.size()];

        int i = 0;
        for (Box box: boxes) {
            shapes[i] = VoxelShapes.cuboid(box.minX, 1-box.maxY, box.minZ, box.maxX, 1-box.minY, box.maxZ);
            i++;
        }
        return VoxelShapes.union(VoxelShapes.empty(), shapes);
    }
    public static VoxelShape mirrorZ(VoxelShape shape){
        List<Box> boxes = shape.getBoundingBoxes();
        VoxelShape[] shapes = new VoxelShape[boxes.size()];

        int i = 0;
        for (Box box: boxes) {
            shapes[i] = VoxelShapes.cuboid(box.minX, box.minY, 1-box.maxZ, box.maxX, box.maxY, 1-box.minZ);
            i++;
        }
        return VoxelShapes.union(VoxelShapes.empty(), shapes);
    }

    public static VoxelShape rotate270(VoxelShape shape){
        return rotateShape(shape, matrix270);
    }
    public static VoxelShape rotate180(VoxelShape shape){
        return rotateShape(shape, matrix180);
    }
    public static VoxelShape rotate90 (VoxelShape shape){
        return rotateShape(shape, matrix90);
    }

    private static VoxelShape rotateShape(VoxelShape shape, int[] matrix) {
        List<Box> boxes = shape.getBoundingBoxes();
        VoxelShape[] shapes = new VoxelShape[boxes.size()];

        int i = 0;
        for (Box box: boxes) {
            double[] p1 = rotatePoint2D(new double[]{box.minX, box.minZ}, matrix);
            double[] p2 = rotatePoint2D(new double[]{box.maxX, box.maxZ}, matrix);

            if (p1[0] > p2[0]) {
                double x = p1[0];
                p1[0] = p2[0];
                p2[0] = x;
            }
            if (p1[1] > p2[1]) {
                double x = p1[1];
                p1[1] = p2[1];
                p2[1] = x;
            }

            shapes[i] = VoxelShapes.cuboid(p1[0], box.minY, p1[1], p2[0], box.maxY, p2[1]);
            i++;
        }
        return VoxelShapes.union(VoxelShapes.empty(), shapes);
    }
    public static double[] rotatePoint2D(double[] coordinates, int[] matrix) {
        double temp    = 0.5d + (coordinates[0]-0.5d)*matrix[0] - (coordinates[1]-0.5d)*matrix[1];
        coordinates[1] = 0.5d - (coordinates[0]-0.5d)*matrix[2] + (coordinates[1]-0.5d)*matrix[3];
        coordinates[0] = temp;
        return coordinates;
    }
    public static int[] rotateVec2(int[] coordinates, int[] matrix) {
        int temp       =   coordinates[0]*matrix[0] - coordinates[1]*matrix[1];
        coordinates[1] = - coordinates[0]*matrix[2] + coordinates[1]*matrix[3];
        coordinates[0] = temp;
        return coordinates;
    }
}