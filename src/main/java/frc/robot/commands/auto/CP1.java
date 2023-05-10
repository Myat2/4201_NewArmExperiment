package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Globals;
import frc.robot.RobotContainer;


public class CP1 extends SequentialCommandGroup {

  // Pick item from bin
  public CP1() {
    super(
        // set arm to vertical position
        new DetectionPosition(), // Line detection position
        new MoveRobotSense(1, 0.3, 0, 0, 0.25, () -> RobotContainer.m_sensor.getFrontIRDistance() <= 15),
        new ViewItem().alongWith(new Gripper(0)),
        new InstantCommand(()-> Globals.CP1()),
        new PickItemfromBin()

    );
  }

}
