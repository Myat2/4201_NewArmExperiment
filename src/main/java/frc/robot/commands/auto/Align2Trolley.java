package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.RobotContainer;

// This command is used to align to the trolley
public class Align2Trolley extends SequentialCommandGroup{

  public Align2Trolley(){
    super(
      // Lifts arm
      new DetectionPosition(),
      // sets cvMode to trolley alignment
      new InstantCommand(()-> Globals.cvMode = 5),
      new WaitCommand(4),
      // resets cvMode to idle
      new InstantCommand(()-> Globals.cvMode=-1),
      // Align trolley X
      new TrolleyAlignment(0),
      // Align trolley Y
      new MoveRobotSense(1, 0.26, 0, 0,0.1, ()-> RobotContainer.m_sensor.getFrontIRDistance()<=12), // Changed from 15cm
      new MoveRobot(1, 0.07, 0, 0, 0.1)
    );
  }
}