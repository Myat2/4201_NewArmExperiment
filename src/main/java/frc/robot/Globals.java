package frc.robot;

import java.util.ArrayList;
import java.util.Collections;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import frc.robot.Astar.Layout;

//Put all global variables here
public class Globals {
  static public int menuItem;

  static public final int DNUM = 4;
  static public int debug[] = new int[DNUM];
  static public String[] debugNames = new String[] { "debug0", "debug1", "debug2", "debug3" };
  // Vision //
  // Calibration
  /** (length in m)/(800 pixels) */
  public static double convertPxToM = 0.0006225;// 0.0006225 Resolution 0.00060625
  public static double camera_offset = 0.105;  // 0.1  actual is 10.5cm 0.015
  public static double camera_mount_offset_x = 0.015;//  actual is 1.5cm
  public static double arm_offset_y = 0.125; // 0.125
  public static double arm_offset_z = 0.25;
  public static double gripper_offset = 0.19; // actual is 0.19
  /** (height of cam - (height of coke - 3cm))/(height of cam) */
  public static double CokeRatio = 0.8; // actual is 0.805 O.G 0.79 (Tarun Code)
  public static double AdjustFactor = 1;
  public static int imH = 600;
  public static int imW = 800;

  // Camera angles Robot 6942
  public static int NormalCameraAngle = 292;
  public static int ColorDetectionAngle = 265;
  public static int PickingCameraAngle = 271;
  public static int WOBAngle = 153;
  public static int PerspTfCamAngle = 279;

  // Camera Angles Robot 6942
  // public static int NormalCameraAngle = 295;
  // public static int ColorDetectionAngle = 265;
  // public static int PickingCameraAngle = 275;
  // public static int WOBAngle = 158;
  // public static int PerspTfCamAngle = 287;

  //Align2Line
  public static boolean runningTaskB = false;
  /**
   * CokeU = 0
   * Coke = 1
   * Dettol = 2
   * Jagabee = 3
   */
  public static int curItemType = 0;
  public static double curItemX;
  public static double curItemY;
  /**
   * Red = 0
   * Green = 1
   * Blue = 2
   */
  public static int curTarget = 0;
  /**
   * Bin1 = 0
   * Bin2 = 1
   */
  public static int curBin = 0;
  /**
   * Idle = -1 (does nothing, use to turn off the other modes)
   * <p>
   * Line detection = 0
   * <p>
   * Object Detection = 1
   * <p>
   * Work Order Board = 2
   */
  public static int cvMode = -1;
  /**
   * C|D|J
   * <p>
   * R|x|x|x|
   * <p>
   * G|x|x|x|
   * <p>
   * B|x|x|x|
   * <P>
   * 
   * This array stores the number of items in each target area
   * NOTE: even though coke upright and coke are different objects, they are
   * stored under the same column
   */
  public static int[][] Targets = new int[][] {};
  // Omnidrive //
  public static double curDir = 0;
  public static double curAngle = 0;
  public static double startYaw;
  public static int curTrolley = 0;
  // Extra //
  public static Pose2d g_posB = new Pose2d();
  public static int LoopCnt = 0; // use as counter for loops
  public static int loopCount = 0;
  public static Pose2d curPose;
  public static double[][] moveCommands = {
      { 2, Math.PI / 2, 0, 0, Math.PI / 2 },
      { 2, -Math.PI / 2, 0, 0, Math.PI / 2 },
      { 0, -0.43, 0, 0, 0.4 },
      { 0, -0.43, 0, 0, 0.4 }
  };
  public static Pose2d[] pose2dMoveCommands = {
      new Pose2d(new Translation2d(0, 0), new Rotation2d(0)),
      new Pose2d(new Translation2d(0, 0), new Rotation2d(-Math.PI / 2)),
      new Pose2d(new Translation2d(0, 0.43), new Rotation2d(-Math.PI / 2)),
      new Pose2d(new Translation2d(0, 0.43), new Rotation2d(-Math.PI / 2)),
  };
  public static Pose2d[] placeholderTrolleyPos = {
      new Pose2d(new Translation2d(0.3 + 0.27, 0.6), new Rotation2d(Math.PI / 2)), 
      new Pose2d(new Translation2d(0.3 + 0.27, 1.8), new Rotation2d(Math.PI / 2)),
      new Pose2d(new Translation2d(0.3 + 0.27, 2.6), new Rotation2d(Math.PI / 2)),
  };

  public static void CP1() {
    for (int id = 0; id < 4; id++) {
      if (RobotContainer.m_vision.getObjects()[id * 3] > 0) {
        Globals.curItemType = id;
        Globals.curItemY = RobotContainer.m_vision.getObjects()[Globals.curItemType * 3 + 2];
        Globals.curItemX = RobotContainer.m_vision.getObjects()[Globals.curItemType * 3 + 1];
      }
    }
  }
  

  // public static Pose2d[] placeholderTrolleyPos = {
  // new Pose2d(new Translation2d(0.15,0.15), new
  // Rotation2d(Math.toRadians(135))),
  // new Pose2d(new Translation2d(0.15,0.45), new
  // Rotation2d((Math.toRadians(135)))),
  // new Pose2d(new Translation2d(0.15,0.75), new
  // Rotation2d((Math.toRadians(135)))),
  // };
  public static int placeholderCount = 0; // Changed from 0 to -1
  // End Conditions //

  public static double robotRadius_m = 0.24;

  // End condition for pick and place
  // NOTE: 2d array has 3 columns but there are 4 objects
  public static boolean WOBLoopCondition() {
    // loops targets
    for (Globals.curTarget = 0; Globals.curTarget < 3; Globals.curTarget++) {
      // loops items
      for (Globals.curItemType = 0; Globals.curItemType < 4; Globals.curItemType++) {
        // IF curItem is a coke
        if (Globals.curItemType == 0 || Globals.curItemType == 1) {
          // while array is not empty
          while (Globals.Targets[Globals.curTarget][0] > 0) {
            // check if box contains item
            if (RobotContainer.m_vision.getObjects()[Globals.curItemType * 3] > 0) {
              // if last item
              if ((RobotContainer.m_vision.getObjects()[0 * 3] + RobotContainer.m_vision.getObjects()[1 * 3]
                  + RobotContainer.m_vision.getObjects()[2 * 3] + RobotContainer.m_vision.getObjects()[3 * 3]) == 1)
                Globals.curBin++;

              Globals.curItemY = RobotContainer.m_vision.getObjects()[Globals.curItemType * 3 + 2];
              Globals.curItemX = RobotContainer.m_vision.getObjects()[Globals.curItemType * 3 + 1];
              Globals.Targets[Globals.curTarget][0]--; // decrements ONLY the column[0] with coke
              return false;
            } else if ((RobotContainer.m_vision.getObjects()[0 * 3] + RobotContainer.m_vision.getObjects()[1 * 3]
                + RobotContainer.m_vision.getObjects()[2 * 3] + RobotContainer.m_vision.getObjects()[3 * 3]) == 0) {
              if (Globals.curBin == 0) {
                Globals.curBin++;
                return false;
              } else
                return true;
            } else // if box does not contain current item carry on
              break;
          }
        }
        // If the item is not coke
        else {
          // while array is not empty
          while (Globals.Targets[Globals.curTarget][Globals.curItemType - 1] > 0) {
            // check if box contains item
            if (RobotContainer.m_vision.getObjects()[Globals.curItemType * 3] > 0) {
              // If last item
              if ((RobotContainer.m_vision.getObjects()[0 * 3] + RobotContainer.m_vision.getObjects()[1 * 3]
                  + RobotContainer.m_vision.getObjects()[2 * 3] + RobotContainer.m_vision.getObjects()[3 * 3]) == 1)
                Globals.curBin++;

              Globals.curItemY = RobotContainer.m_vision.getObjects()[Globals.curItemType * 3 + 2];
              Globals.curItemX = RobotContainer.m_vision.getObjects()[Globals.curItemType * 3 + 1];
              Globals.Targets[Globals.curTarget][Globals.curItemType - 1]--;
              return false;
            } else if ((RobotContainer.m_vision.getObjects()[0 * 3] + RobotContainer.m_vision.getObjects()[1 * 3]
                + RobotContainer.m_vision.getObjects()[2 * 3] + RobotContainer.m_vision.getObjects()[3 * 3]) == 0) {
              if (Globals.curBin == 0) {
                Globals.curBin++;
                return false;
              } else
                return true;

            } else // if box does not contain current item carry on
              break;
          }
        }
      }
    }
    Globals.curItemType = 0;
    Globals.curTarget = 0;
    return true;
  }

  public static boolean endConditionCP5(String targetArea) {
    loopCount++;
    if (curPose.getTranslation().getY() < 3.7 && !RobotContainer.m_points.pointMap.containsKey(targetArea)) {
      return false;
    } else {
      loopCount = 0;
      return true;
    }
  }

  public static boolean endConditionCP7() {
    loopCount++;
    if (curPose.getTranslation().getY() < 3.7 && !RobotContainer.m_points.pointMap.containsKey("T1")) {
      return false;
    } else {
      loopCount = 0;
      return true;
    }
  }

  public static Pose2d waypoint = new Pose2d();

  public static boolean endConditionTaskBMapping() {
    loopCount++;
    if (curPose.getTranslation().getY() < 3.7) {
      return false;
    } else {
      loopCount = 0;
      return true;
    }
  }

  public void addListTaskA() {
    targetAreas.add(0, Layout.RedPos);
    targetAreas.add(1, Layout.GreenPos);
    targetAreas.add(2, Layout.BluePos);

    trolleys.add(0, Layout.T1Pos);
    trolleys.add(1, Layout.T2Pos);
    trolleys.add(2, Layout.T3Pos);
  }

  public static ArrayList<Point[]> pairedTrolleyTarget = new ArrayList<Point[]>();
  public static ArrayList<Pose2d> targetAreas = new ArrayList<Pose2d>(); // Add when putting a point
  public static ArrayList<Pose2d> trolleys = new ArrayList<Pose2d>();

  public static void pairTargetNTrolley() {
    ArrayList<Double> distances = new ArrayList<Double>();

    int[][] combinations = new int[][] {
        { 0, 1, 2 },
        { 1, 2, 0 },
        { 2, 0, 1 },
        { 1, 0, 2 },
        { 2, 1, 0 },
        { 0, 2, 1 }
    };
    String[] targetNames = new String[] { "RedTarget", "GreenTarget", "BlueTarget" };
    for (int i = 0; i < combinations.length; i++) {
      double redTDist = targetAreas.get(0).getTranslation()
          .getDistance(trolleys.get(combinations[i][0]).getTranslation());
      double greenTDist = targetAreas.get(1).getTranslation()
          .getDistance(trolleys.get(combinations[i][1]).getTranslation());
      double blueTDist = targetAreas.get(2).getTranslation()
          .getDistance(trolleys.get(combinations[i][2]).getTranslation());
      distances.add(Math.pow(redTDist, 2) + Math.pow(greenTDist, 2) + Math.pow(blueTDist, 2));
    }
    int minIndex = distances.indexOf(Collections.min(distances));
    Point[] pair = new Point[2];
    for (int j = 0; j < combinations[minIndex].length; j++) {
      pair[0] = new Point(targetNames[j], targetAreas.get(j));
      pair[1] = new Point("T" + (combinations[minIndex][j] + 1), trolleys.get(combinations[minIndex][j]));
      pairedTrolleyTarget.add(pair);
    }

  }
}