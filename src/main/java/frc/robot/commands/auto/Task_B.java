package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.RobotContainer;
import frc.robot.Astar.Layout;

public class Task_B extends SequentialCommandGroup{
  public Task_B(){
    super(
      new InstantCommand(() -> RobotContainer.m_omnidrive.UpdatePosition(Layout.startPos)),
      new InstantCommand(()-> Globals.runningTaskB = true),
      new InitialCalibration(),
      // Puts camera in viewing position
      new PerspTfCamPos(),
      new InstantCommand(() -> Globals.cvMode = -1),
      // Move out of starting position
      new MovetoB(new Pose2d(new Translation2d(0.56,0.26), new Rotation2d(-Math.PI/2))), // Change x to 0.46?
      // new MovetoB(new Pose2d(new Translation2d(0.46, 0.26), new Rotation2d())),
      // new Rotate2Orientation(0),
      // Mapping movement sequence
      new LoopMovetoB(),
      new LoopCmd(new LoopMovetoB(), () -> Globals.endConditionTaskBMapping()),
      // If there is obstacle placed in the way 
      new InstantCommand(()->RobotContainer.m_points.setObsfromPointMap()),
      new InstantCommand(()->RobotContainer.m_points.AddObsGrid()),

      //## Calibrate Robot Position ##//
      new InitialCalibration(),
      //## Read WOB ##// 
      new MovetoB(Layout.workOrderPos),
      new ReadWOB(),
      //## Transport Trolleys ##//
      new SortTrolleys(RobotContainer.m_points.pointMap),


      new WaitCommand(2),
      //## Sort Items ##//
      //## pick up bin 1 ##//
      new MovetoB(Layout.PickUpBinPos),
      new Rotate2Orientation(Layout.PickUpBinPos.getRotation().getDegrees()),
      new Align2Line(),
      new ViewItem(),
      new LoopCmd(new SortItems(RobotContainer.m_points.pointMap), ()->Globals.WOBLoopCondition()),
      new MoveArm(new Translation2d(0.33,0.24), 0.5) // Line detection position
      
      
    );
  }
}
