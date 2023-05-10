package frc.robot.commands.auto;

import frc.robot.Globals;
import frc.robot.RobotContainer;
// import the commands
import frc.robot.commands.auto.MoveRobot;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Arm;

public class CP5 extends SequentialCommandGroup {

  private final static Arm m_arm = RobotContainer.m_arm;

  public CP5(String target) {
    super(

        // Puts camera in viewing position
        new PerspTfCamPos(),
        // Puts camera in viewing position
        new InstantCommand(() -> Globals.cvMode = -1),
        // Move out of starting position
        new MoveRobot(0, -0.05, 0, 0, 5),
        new MoveRobot(1, 0.35, 0, 0, 5),

        new LoopMovetoB(target),
        new LoopCmd(new LoopMovetoB(target), () -> Globals.endConditionCP5(target)),
        /* Go to Point using findGotoPos */
        new CheckAndMoveTarget(target, 0.5),
        /* Go forward */
        new MoveRobot(1, 0.5, 0, 0, 5)

    );
  }
}