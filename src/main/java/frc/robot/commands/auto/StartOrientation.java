package frc.robot.commands.auto;

import frc.robot.Globals;

public class StartOrientation extends MoveRobot{
  public StartOrientation(){
    super(2,0,0,0,Math.PI/2);
  }
  @Override
  public void initialize(){
    // super.s_angle = Math.toDegrees(-Globals.startYaw);
    super.m_dist = -Globals.startYaw;
    super.initialize();
  }
}
