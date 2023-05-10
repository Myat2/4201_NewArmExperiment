package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Globals;
import frc.robot.RobotContainer;
import frc.robot.Astar.Layout;
import frc.robot.subsystems.Arm;

public class CP2 extends SequentialCommandGroup {

  // move and push trolley
  public CP2() {
    super(
        new MoveCamera(Globals.NormalCameraAngle),
        new MovetoB(Layout.T1Pos) // Trolley location

    );
  }

}