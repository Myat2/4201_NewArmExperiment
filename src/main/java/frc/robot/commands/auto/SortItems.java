package frc.robot.commands.auto;

import java.util.Map;
import frc.robot.Globals;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Astar.Layout;

// The Routine for picking and transporting items to trolley/target area
public class SortItems extends SequentialCommandGroup {
    private enum CommandSelector {
        ONE, TWO, THREE
    }

    static public CommandSelector selectTarget() {
        if (Globals.curTarget == 0)
            return CommandSelector.ONE;
        else if (Globals.curTarget == 1)
            return CommandSelector.TWO;
        else if (Globals.curTarget == 2)
            return CommandSelector.THREE;
        else
            return null;

    }

    static public CommandSelector selectBin() {

        if (Globals.curBin == 0)
            return CommandSelector.ONE;
        else
            return CommandSelector.TWO;

    }

    static public CommandSelector selectRotation() {

        if (Globals.curBin == 0)
            return CommandSelector.ONE;
        else
            return CommandSelector.TWO;

    }
    static public CommandSelector calibration() {

        if (Globals.curBin == 1)
            return CommandSelector.ONE;
        else
            return CommandSelector.TWO;

    }

    public SortItems() {
        super(
                /*
                 * Pick First
                 */
                // new PickItemfromBin(),
                // new MoveCamera(286),
                // new SelectCommand(
                //         Map.ofEntries(
                //                 Map.entry(CommandSelector.ONE, new GotoTrolley(Layout.T1Pos)), // Go to Red
                //                 Map.entry(CommandSelector.TWO, new GotoTrolley(Layout.T2Pos)),
                //                 Map.entry(CommandSelector.THREE, new GotoTrolley(Layout.T3Pos))),
                //         SortItems::selectTarget),
                // // Lifts arm
                // new DetectionPosition(),
                // // sets cvMode to trolley alignment
                // new InstantCommand(() -> Globals.cvMode = 5),
                // new WaitCommand(3),
                // // resets cvMode to idle
                // new InstantCommand(() -> Globals.cvMode = -1),
                // // Align trolley X
                // new TrolleyAlignment(0),

                // new PlaceDown(),
                // // new MoveRobot(1, -0.05, 0, 0, 0.1),
                // new SelectCommand(
                //         Map.ofEntries(
                //                 Map.entry(CommandSelector.ONE, new MovetoB(Layout.PickUpBinPos)),
                //                 Map.entry(CommandSelector.TWO, new MovetoB(Layout.PickUpBin2Pos))),
                //         SortItems::selectBin),
                // // new SelectCommand(
                // //         Map.ofEntries(
                // //                 Map.entry(CommandSelector.ONE,
                // //                         new Rotate2Orientation(Layout.PickUpBinPos.getRotation().getDegrees())),
                // //                 Map.entry(CommandSelector.TWO,
                // //                         new Rotate2Orientation(Layout.PickUpBin2Pos.getRotation().getDegrees()))),
                // //         SortItems::selectRotation),

                // new Align2Line(),
                // new ViewItem());

                // Trolley first
                new PickItemfromBin(),
                new MoveCamera(286),
                new SelectCommand(
                        Map.ofEntries(
                                Map.entry(CommandSelector.ONE, new GotoTrolley(Layout.RedPos)), // Go to Red
                                Map.entry(CommandSelector.TWO, new GotoTrolley(Layout.GreenPos)),
                                Map.entry(CommandSelector.THREE, new GotoTrolley(Layout.BluePos))),
                        SortItems::selectTarget),
                // Lifts arm
                new DetectionPosition(),
                // sets cvMode to trolley alignment
                new InstantCommand(() -> Globals.cvMode = 5),
                new WaitCommand(3),
                // resets cvMode to idle
                new InstantCommand(() -> Globals.cvMode = -1),
                // Align trolley X
                new TrolleyAlignment(0),

                new PlaceDown(),
                // new MoveRobot(1, -0.05, 0, 0, 0.1),
                new SelectCommand(
                        Map.ofEntries(
                                Map.entry(CommandSelector.ONE, new MovetoB(Layout.PickUpBinPos)),
                                Map.entry(CommandSelector.TWO, new MovetoB(Layout.PickUpBin2Pos))),
                        SortItems::selectBin),
                // new SelectCommand(
                //         Map.ofEntries(
                //                 Map.entry(CommandSelector.ONE,
                //                         new Rotate2Orientation(Layout.PickUpBinPos.getRotation().getDegrees())),
                //                 Map.entry(CommandSelector.TWO,
                //                         new Rotate2Orientation(Layout.PickUpBin2Pos.getRotation().getDegrees()))),
                //         SortItems::selectRotation),

                new Align2Line(),
                new ViewItem());
    }

    public SortItems(Map<String, Pose2d> pointMap) {
        super(
                new PickItemfromBin(),
                new MoveCamera(286),
                new SelectCommand(
                        Map.ofEntries(
                                Map.entry(CommandSelector.ONE, new SequentialCommandGroup(new MovetoB(()->RobotContainer.m_Grid.findGotoPos(RobotContainer.m_points.getPoint("T1").getTranslation(), 0.6)), new MoveRobot(1, 0.05, 0, 0, 0.4))),
                                Map.entry(CommandSelector.TWO, new SequentialCommandGroup(new MovetoB(()->RobotContainer.m_Grid.findGotoPos(RobotContainer.m_points.getPoint("T2").getTranslation(), 0.6)), new MoveRobot(1, 0.05, 0, 0, 0.4))),
                                Map.entry(CommandSelector.THREE, new SequentialCommandGroup(new MovetoB(()->RobotContainer.m_Grid.findGotoPos(RobotContainer.m_points.getPoint("T3").getTranslation(), 0.6)), new MoveRobot(1, 0.05, 0, 0, 0.4)))),
                        SortItems::selectTarget),
                // Lifts arm
                new DetectionPosition(),
                // sets cvMode to trolley alignment
                new InstantCommand(() -> Globals.cvMode = 5),
                new WaitCommand(3),
                // resets cvMode to idle
                new InstantCommand(() -> Globals.cvMode = -1),
                // Align trolley X
                new TrolleyAlignment(0),

                new PlaceDown(),
                new SelectCommand(
                        Map.ofEntries(
                                Map.entry(
                                        CommandSelector.ONE, new SequentialCommandGroup(
                                                new MovetoB(Layout.PickUpBinPos),
                                                new Align2Line()
                                                )
                                        ),
                                Map.entry(CommandSelector.TWO, new InstantCommand()) // blank
                                ),
                        SortItems::calibration),
                // new MoveRobot(1, -0.05, 0, 0, 0.1),
                new SelectCommand(
                        Map.ofEntries(
                                Map.entry(CommandSelector.ONE, new MovetoB(Layout.PickUpBinPos)),
                                Map.entry(CommandSelector.TWO, new MovetoPoint("Bin2", 0.75))),
                        SortItems::selectBin),
                // new SelectCommand(
                //         Map.ofEntries(
                //                 Map.entry(CommandSelector.ONE,
                //                         new Rotate2Orientation(Layout.PickUpBinPos.getRotation().getDegrees())),
                //                 Map.entry(CommandSelector.TWO,
                //                         new SequentialCommandGroup())),
                //         SortItems::selectRotation),

                new Align2Line(),
                new ViewItem()
                );
    }

}