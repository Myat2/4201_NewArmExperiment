package frc.robot;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.Astar.Layout;

public class Points {
  private final ShuffleboardTab tab = Shuffleboard.getTab("Points");
  private NetworkTableInstance inst = NetworkTableInstance.getDefault();

  private NetworkTable pointsTable = inst.getTable("Shuffleboard/Points");

  public Map<String, Pose2d> pointMap = new HashMap<>();
  public Map<String, Pose2d> obstacleMap = new HashMap<>();
  private Layout layout = RobotContainer.m_layout;
  // Target Areas
  public Pose2d redTarget = new Pose2d();
  public Pose2d greenTarget = new Pose2d();
  public Pose2d blueTarget = new Pose2d();
  public Pose2d trolley = new Pose2d();
  private double[] defaultValue = new double[13];
  public Pose2d camOffset = new Pose2d(0.015, 0.59, new Rotation2d(0)); // Update this
  private int trolleyCount = 1;
  private int binCount = 1;

  public Points() {
    trolleyCount = 1;
    binCount = 1;
    pointMap.put("Bin1", new Pose2d(new Translation2d((double) (Layout.obs_m[0][0]), (double) (Layout.obs_m[0][1])),
        new Rotation2d(Layout.obs_m[0][4])));
    obstacleMap.put("Bin1", new Pose2d(new Translation2d((double) (Layout.obs_m[0][0]), (double) (Layout.obs_m[0][1])),
        new Rotation2d(Layout.obs_m[0][4])));
    pointMap.put("T1", trolley);
    pointMap.put("T2", trolley);
    pointMap.put("T3", trolley);
    // pointMap.put("T3", Layout.T3Pos);
    // obstacleMap.put("T3", Layout.T3Pos);
    pointMap.put("RedTarget", redTarget);
    pointMap.put("BlueTarget", blueTarget);
    pointMap.put("GreenTarget", greenTarget);
  }

  public void resetMap() {
    trolleyCount = 1;
    binCount = 1;
    pointMap.clear();
    pointMap.put("Bin1", new Pose2d(new Translation2d((double) (Layout.obs_m[0][0]), (double) (Layout.obs_m[0][1])),
        new Rotation2d(Layout.obs_m[0][4])));
    // pointMap.put("T3", Layout.T3Pos);

  }

  public void resetObsMap() {
    obstacleMap.clear();
    // obstacleMap.put("T3", Layout.T3Pos);
  }

  public void addObsPoint(String pointname, Pose2d newpose) {
    obstacleMap.put(pointname, newpose);

  }

  public void addPoint(String pointname, Pose2d newpose) {
    pointMap.put(pointname, newpose);

  }

  public Pose2d getPoint(String pointname) {
    if (pointMap.containsKey(pointname))
      return pointMap.get(pointname);
    else
      return Globals.curPose;
  }

  public Pose2d getObstacle(String pointname) {
    if (obstacleMap.containsKey(pointname))
      return obstacleMap.get(pointname);
    else
      return Globals.curPose;

  }

  public double[] getDistanceTarget(String targetName) {
    double[] distance = (pointsTable.getEntry(targetName).getDoubleArray(defaultValue));
    return distance;
  }



  // Tested and failed
  public Pose2d getObsPointAftPlace(){
    int w = (int) Globals.curPose.getRotation().getDegrees();
    double radian = Globals.curPose.getRotation().getRadians();
    double radius = 0.44;
    Translation2d xy = new Translation2d();
    // Between 0 to -90
    if (w<0 && w>-90) {
      xy = new Translation2d(
        Globals.curPose.getTranslation().getX() + Math.abs(radius * Math.sin(radian)), 
        Globals.curPose.getTranslation().getY() + Math.abs(radius * Math.cos(radian))
        );
    }

    // Between 0 to 90
    else if (w>0 && w<90) {
      xy = new Translation2d(
        Globals.curPose.getTranslation().getX() - Math.abs(radius * Math.cos(radian)), 
        Globals.curPose.getTranslation().getY() + Math.abs(radius * Math.sin(radian))
        );
    }

    // Between 90 to 180
    else if (w>90 && w<180) {
      xy = new Translation2d(
        Globals.curPose.getTranslation().getX() - Math.abs(radius * Math.cos(radian)), 
        Globals.curPose.getTranslation().getY() - Math.abs(radius * Math.sin(radian))
        );
    }
    // Between -90 to -180
    else if (w<-90 && w>-180) {
      xy = new Translation2d(
        Globals.curPose.getTranslation().getX() + Math.abs(radius * Math.sin(radian)), 
        Globals.curPose.getTranslation().getY() + Math.abs(radius * Math.cos(radian))
        );
    }
    else if (Math.abs(w) == 0) {
      if(Double.doubleToRawLongBits(w) == 0x8000000000000000L) // If Negative
      {
        xy = new Translation2d(
        Globals.curPose.getTranslation().getX() , 
        Globals.curPose.getTranslation().getY() + radius
        );
      }
      else{
        xy = new Translation2d(
        Globals.curPose.getTranslation().getX() , 
        Globals.curPose.getTranslation().getY() - radius
        );
      }
      
    }
    // w = 90
    else if(w == -90){
      xy = new Translation2d(
        Globals.curPose.getTranslation().getX() + radius, 
        Globals.curPose.getTranslation().getY()
        );
    }
    else if(w == 90){
      xy = new Translation2d(
        Globals.curPose.getTranslation().getX() - radius, 
        Globals.curPose.getTranslation().getY()
        );
    }

    return new Pose2d(xy, new Rotation2d());
  }

  public void updatePoint(String targetName) {
    // If trolley, check size of array
    double x, y;
    int w = (int) Globals.curPose.getRotation().getDegrees();
    int totalTrolleys = 1, totalBins = 1;
    boolean alreadyExist = false;
    double tolerance = 0.3;
    Pose2d upperbound = new Pose2d();
    Pose2d lowerbound = new Pose2d();

    if (targetName == "Trolley") {
      double[] distance = getDistanceTarget(targetName);
      totalTrolleys += distance[2] != 0 ? 1 : 0 + distance[4] != 0 ? 1 : 0;
      // System.out.println("Total Trolley Count" + totalTrolleys);
      for (int i = 0; i < totalTrolleys; i++) {
        // System.out.println("Current Trolley Count" + trolleyCount);
        alreadyExist = false;
        if ((w > -45)) { // If robot is in the same axis as the arena

          y = RobotContainer.m_omnidrive.getPose().getTranslation().getY() + distance[i * 2 + 1]
              + camOffset.getTranslation().getY();
          x = RobotContainer.m_omnidrive.getPose().getTranslation().getX() + distance[i * 2 + 0]
              + camOffset.getTranslation().getX();
          // System.out.printf("!!!!!!!!!!!!!!0 xy=%f,%f", x, y);
        } else {
          x = RobotContainer.m_omnidrive.getPose().getTranslation().getX() + distance[i * 2 + 1]
              + camOffset.getTranslation().getY();
          y = RobotContainer.m_omnidrive.getPose().getTranslation().getY() - distance[i * 2 + 0]
              + camOffset.getTranslation().getX();
          // System.out.printf("!!!!!!!!!!!!!!-90 xy=%f,%f", x, y);
        }

        upperbound = new Pose2d(new Translation2d(x + tolerance, y + tolerance), new Rotation2d());
        lowerbound = new Pose2d(new Translation2d(x - tolerance, y - tolerance), new Rotation2d());

        for (Pose2d location : pointMap.values()) {
          if ((location.getTranslation().getX() >= lowerbound.getTranslation().getX()
              && location.getTranslation().getX() <= upperbound.getTranslation().getX())
              && (location.getTranslation().getY() >= lowerbound.getTranslation().getY()
                  && location.getTranslation().getY() <= upperbound.getTranslation().getY())) {
            alreadyExist = true;
            break;
          }
        }

        if (!alreadyExist) {
          String name = new String();
          name = "T" + trolleyCount++;
          pointMap.put(name, new Pose2d(new Translation2d(x, y), Globals.curPose.getRotation()));
          obstacleMap.put(name, new Pose2d(new Translation2d(x, y), Globals.curPose.getRotation()));
        }
      }
    }
    else if(targetName == "Bin"){
      double[] distance = getDistanceTarget(targetName);
      totalBins += distance[3] > 0? 1 : 0 ;
      for(int i = 0; i< totalBins; i++){
        alreadyExist = false;
        if ((w > -45)){ // If robot is in the same axis as the arena
        y = RobotContainer.m_omnidrive.getPose().getTranslation().getY() +
        distance[i * 3 + 1] + camOffset.getTranslation().getY();
        x = RobotContainer.m_omnidrive.getPose().getTranslation().getX() +
        distance[i * 3 + 0] + camOffset.getTranslation().getX();
        }
        else{
        x = RobotContainer.m_omnidrive.getPose().getTranslation().getX() +
        distance[i * 3 + 1] + camOffset.getTranslation().getY();
        y = RobotContainer.m_omnidrive.getPose().getTranslation().getY() -
        distance[i * 3 + 0] + camOffset.getTranslation().getX();
      }

      upperbound = new Pose2d(new Translation2d(x + tolerance, y + tolerance), new
      Rotation2d());
      lowerbound = new Pose2d(new Translation2d(x - tolerance, y - tolerance), new
      Rotation2d());

      for(Pose2d location : pointMap.values()){
        if((location.getTranslation().getX() >= lowerbound.getTranslation().getX() &&
        location.getTranslation().getX() <=upperbound.getTranslation().getX()) &&
        (location.getTranslation().getY() >= lowerbound.getTranslation().getY() &&
        location.getTranslation().getY() <=upperbound.getTranslation().getY())){
        alreadyExist = true;
        break;
        }
    }

      if(!alreadyExist){
          String name = new String();
          name = "Bin" + ++binCount;
          pointMap.put(name, new Pose2d(new Translation2d(x, y),
          Globals.curPose.getRotation()));
          obstacleMap.put(name, new Pose2d(new Translation2d(x, y),
          Globals.curPose.getRotation()));
        }
      }
    }
    else {
      double[] distance = getDistanceTarget(targetName);
      if ((w > -45)) {
        y = RobotContainer.m_omnidrive.getPose().getTranslation().getY() + distance[1]
            + camOffset.getTranslation().getY();
        x = RobotContainer.m_omnidrive.getPose().getTranslation().getX() + distance[0]
            + camOffset.getTranslation().getX();
      } else {
        x = RobotContainer.m_omnidrive.getPose().getTranslation().getX() + distance[1]
            + camOffset.getTranslation().getY();
        y = RobotContainer.m_omnidrive.getPose().getTranslation().getY() - distance[0]
            + camOffset.getTranslation().getX();
      }

      // double upperbound = distanceFromOrigin + tolerance;
      // double lowerbound = distanceFromOrigin - tolerance;
      upperbound = new Pose2d(new Translation2d(x + tolerance, y + tolerance), new Rotation2d());
      lowerbound = new Pose2d(new Translation2d(x - tolerance, y - tolerance), new Rotation2d());

      // Some values given by python script is lesser than startpos, why? idk

      for (Pose2d location : pointMap.values()) {
        if ((location.getTranslation().getX() >= lowerbound.getTranslation().getX()
            && location.getTranslation().getX() <= upperbound.getTranslation().getX())
            && (location.getTranslation().getY() >= lowerbound.getTranslation().getY()
                && location.getTranslation().getY() <= upperbound.getTranslation().getY())) {
          alreadyExist = true;
          break;
        }
      }

      if (!alreadyExist) {
        pointMap.put(targetName, new Pose2d(new Translation2d(x, y), Globals.curPose.getRotation()));
      }
    }

  }

  public void updateAllPoints() {
    String[] targetAreas = { "RedTarget", "GreenTarget", "BlueTarget", "Trolley", "Bin" };
    for (String targetName : targetAreas) {
      double[] distance = getDistanceTarget(targetName);
      if (distance[0] != 0 && distance[1] != 0) {
        updatePoint(targetName);
      }
    }
  }

  public void AddSingleRoundObs(Translation2d xy) {
    int x0 = Layout.Convert_m_cell(xy.getX());
    int y0 = Layout.Convert_m_cell(xy.getY());
    int dia = Layout.Convert_m_cell(0.3);
    RobotContainer.m_Grid.AddObsRound(x0, y0, dia);
  }

  // From hashmap
  public void AddObsGrid() {
    for (Map.Entry<String, Pose2d> obstacleEntry : obstacleMap.entrySet()) {
      if (obstacleEntry.getKey().contains("T")) {

        int x0 = Layout.Convert_m_cell(obstacleEntry.getValue().getTranslation().getX());
        int y0 = Layout.Convert_m_cell(obstacleEntry.getValue().getTranslation().getY());
        int dia = Layout.Convert_m_cell(0.3);
        RobotContainer.m_Grid.AddObsRound(x0, y0, dia);
      } else if (obstacleEntry.getKey().contains("Bin")) {
        int x0 = Layout.Convert_m_cell(obstacleEntry.getValue().getTranslation().getX());
        int y0 = Layout.Convert_m_cell(obstacleEntry.getValue().getTranslation().getY());
        int dx = Layout.Convert_m_cell(0.3);
        int dy = Layout.Convert_m_cell(0.42);
        RobotContainer.m_Grid.AddObstacle(x0, y0, dx, dy, obstacleEntry.getValue().getRotation().getRadians());
      }

    }

    RobotContainer.m_Grid.ExpandObstacles(Globals.robotRadius_m);
  }

  public void removeObs(String key) {
    RobotContainer.m_Grid.ClearObs();
    RobotContainer.m_Grid.AddFixedObstacles(RobotContainer.m_layout);
    RobotContainer.m_Grid.ExpandObstacles(Globals.robotRadius_m);
    obstacleMap.remove(key);
    AddObsGrid();

  }

  public void SetTrolleysAsObstacles() {
    obstacleMap.put("T1", Layout.T1Pos);
    obstacleMap.put("T2", Layout.T2Pos);
    obstacleMap.put("T3", Layout.T3Pos);
    AddObsGrid();
  }

  public void setObsfromPointMap(){
    for (Map.Entry<String, Pose2d> point : pointMap.entrySet()) {
      if(point.getKey().codePointAt(0) == 84){
        obstacleMap.put(point.getKey(), point.getValue());
      }
    }
  }

}