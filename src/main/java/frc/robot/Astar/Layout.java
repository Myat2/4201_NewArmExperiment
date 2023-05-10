package frc.robot.Astar;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;

public class Layout {
    // Dimension of layout in real unit
    public static final double x_size_m = 2.250;
    public static final double y_size_m = 4.500;
    public static final double tile_size_m = 0.025;

    // List all fixed walls in layout here
    // List all fixed walls in layout here
    public static final double walls_m[][] = {
            // Boundary
            { 0, 0, x_size_m, 0 },
            { x_size_m, 0, x_size_m, y_size_m },
            { x_size_m, y_size_m, 0, y_size_m },
            { 0, y_size_m, 0, 0 },
            // Other walls

    };

    // List all fixed rectangular obstacles in layout here
    public static final double obs_m[][] = {
            // x0 y0 xSize ySize Angle()
            { 1.400, 1.100, 0.300, 0.420, 0 }, // pickup bin
            { 0.85, 3.4, 0.300, 0.420, 0}, // pickup bin2 (Remove for TASK B)
    };
    public static final double obsRound_m[][] = {
            // x, y, diameter
            // {1.33, 2.02, 0.300}, //red
            // {1.84, 2.02 , 0.300}, //green
            // {0.79, 2.02, 0.300}, //blue
            // {1.02,4.34, 0.300}, //T1
            // {1.52, 4.34, 0.300}, //T2
            // {2.02, 4.34, 0.300} //T3

    };

    // Coordinates of PickUp bin
    // 72cm behind pickup bin
    // At 45 deg, it's 0.707*72cm = 50.9cm

    public static final Pose2d TestPickUpBinPos = new Pose2d(0.63, 1.05, new Rotation2d(-Math.PI / 2));
    public static final Pose2d PickUpBinPos = new Pose2d(1.4 - 0.8, 1.1, new Rotation2d(-Math.PI / 2));
    public static final Pose2d PickUpBin2Pos = new Pose2d(0.85 + 0.8, 3.15, new Rotation2d(Math.PI / 2)); // Math.toRad()
    // These are coordinates of the red colored target area (NOTE: indicate the
    // angle of orientation)
    public static final Pose2d RedPos = new Pose2d(2.075, 3.945, new Rotation2d(0)); // Layout

    // These are coordinates of the green colored target area
    public static final Pose2d GreenPos = new Pose2d(2.075, 2.945, new Rotation2d(0));

    // These are coordinates of the blue colored target area
    public static final Pose2d BluePos = new Pose2d(2.075, 1.945, new Rotation2d(0));

    // These are coordinates of the trolleys (NOTE: indicate the angle of
    // orientation)
    public static final Pose2d T1Pos = new Pose2d(0.861, 2.62, new Rotation2d(0));

    public static final Pose2d T2Pos = new Pose2d(0.861, 2.23, new Rotation2d(0));

    public static final Pose2d T3Pos = new Pose2d(0.861, 1.84, new Rotation2d(0));

    // Position for robot to go to for reading work order
    public static final Pose2d workOrderPos = new Pose2d(1.20, 0.250, new Rotation2d(Math.toRadians(-90))); // Changed x
                                                                                                            // from 1.2
                                                                                                            // to
                                                                                                            // 1.15

    // Robot start position.
    // public static final Pose2d startPos = new Pose2d(0.3, 0.3, new Rotation2d(-Math.PI / 4)); // For TaskA and TaskB
    public static final Pose2d startPos = new Pose2d(0.21, 0.21, new Rotation2d(-Math.PI / 2)); // For CPT
    // public static final Pose2d startPos = new Pose2d(0.96, 1.1, new Rotation2d(-Math.PI / 2)); // For open house/testing
    // public static final Pose2d startPos = new Pose2d(1.56, 4.06, new Rotation2d(-0));
    public static Pose2d getCalibrationPos() {
        return PickUpBinPos;
    }

    public Layout() {

    }

    /**
     * Convert a point in cell to m
     *
     * @param pt (int metre)
     * @return pt (in cell size)
     */
    static public Translation2d Convert_cell_m(Translation2d pt) {
        Translation2d pt_m = new Translation2d(pt.getX() * tile_size_m, pt.getY() * tile_size_m);
        return pt_m;
    }

    /**
     * Convert from metre to grid cell index
     *
     * @param m length in metre
     * @return Grid cell index
     */
    static public int Convert_m_cell(double m) {
        return (int) Math.round(m / tile_size_m);
    }

    public double[][] getWalls() {
        return walls_m;
    }

    public double[][] getObs() {
        return obs_m;
    }

    public double[][] getObsRound() {
        return obsRound_m;
    }
}