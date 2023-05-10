package frc.robot.commands.auto;


import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;

import frc.robot.RobotContainer;
import frc.robot.subsystems.Sensor;

// This command is used to align to the black line and move towards the bin
public class Align2Line extends SequentialCommandGroup{
  private final static Sensor m_sensor = RobotContainer.m_sensor;

  public static boolean runningTaskB() {
    return Globals.runningTaskB;
  }
  // aligns to line
  public Align2Line(){
    super(
      // Lifts arm up and open gripper
      new DetectionPosition().alongWith(new Gripper(1,80)),
      // sets cv mode to line detection
      new InstantCommand(()-> RobotContainer.m_vision.setColor("Black")),
      new InstantCommand(()-> Globals.cvMode = 0),
      new WaitCommand(1), // Org 0.5
      // aligns to line
      new AlignRobot(211,158,true), // Robot 6942

      // resets cv mode to idle mode
      new InstantCommand(()-> Globals.cvMode=-1),
      // wait 2 secs
      new WaitCommand(2),
      // resets robot's position
      new ConditionalCommand(
        new ResetPosition("TaskB"), 
        new ResetPosition(), 
        Align2Line::runningTaskB),
      new Gripper(0,80),
      // moves forward until robot is 15 cm away and close gripper
      new MoveRobotSense(1, 0.3, 0, 0,0.25, ()-> m_sensor.getFrontIRDistance()<=15)
    );
  }
  
}