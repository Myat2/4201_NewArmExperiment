package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Globals;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;

public class CP7 extends SequentialCommandGroup {
    private final static Arm m_arm = RobotContainer.m_arm;

    public CP7() {

        super(
                // Puts camera in viewing position
                // Puts camera in viewing position
                new PerspTfCamPos(),
                // Puts camera in viewing position
                new InstantCommand(() -> Globals.cvMode = -1),
                // Move out of starting position
                new MoveRobot(0, -0.05, 0, 0, 5),
                new MoveRobot(1, 0.35, 0, 0, 5),
                new LoopMovetoB(),
                new LoopCmd(new LoopMovetoB(), () -> Globals.endConditionCP7()),
                // Loop MoveRobot Commands until target area is found
                new CheckAndMoveTarget("T1", 0.5),
                new Align2Trolley(),
                new TrolleyHolder(1));
    }
}