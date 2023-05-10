package frc.robot;
import edu.wpi.first.wpilibj.geometry.Pose2d;

public class Point{
    private String name;
    private Pose2d point;
    public Point(String n, Pose2d pt){
        this.name = n;
        this.point = pt;
    }
    public String getName(){
        return name;
    }
    public Pose2d getPoint(){
        return point;
    }
}