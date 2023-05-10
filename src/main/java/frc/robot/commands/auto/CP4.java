package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Globals;
// import frc.robot.subsystems.Arm;
import frc.robot.RobotContainer;
import frc.robot.Astar.Layout;
import frc.robot.subsystems.Arm;

public class CP4 extends SequentialCommandGroup {
  private final static Arm m_arm = RobotContainer.m_arm;

  // Move to target area
  public CP4() {
    super(
        new MoveCamera(Globals.NormalCameraAngle),
        new DetectionPosition(), // Line detection position
        new MovetoB(Layout.RedPos) // target posisiton

    );
  }
}