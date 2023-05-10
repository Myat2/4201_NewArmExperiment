package frc.robot.subsystems;

import java.util.Map;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SelectCommand;
//WPI imports
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Globals;
import frc.robot.commands.auto.*;
import frc.robot.commands.tele.*;

public class Menu extends SubsystemBase {

    private final OI m_oi;

    // Shuffleboard
    private final ShuffleboardTab tab = Shuffleboard.getTab("Menu");
    private final NetworkTableEntry D_button = tab.add("button", -1).getEntry();
    private final NetworkTableEntry D_menu = tab.add("menu", "?").getEntry();
    private NetworkTableEntry D_debug[] = new NetworkTableEntry[Globals.DNUM];
    // tab.add("Motion", TestMotion);

    int menuNum = 0;
    private final String[] menuName;

    public Menu(final OI oi) {

        for (int i = 0; i < Globals.DNUM; i++) {
            D_debug[i] = tab.add(Globals.debugNames[i], -1).getEntry();
        }
        m_oi = oi;
        m_oi.buttonStart.whenPressed(
                new SelectCommand(
                        Map.ofEntries(
                                Map.entry(menuNum++, new CP1()),
                                Map.entry(menuNum++, new CP2()),
                                Map.entry(menuNum++, new CP3()),
                                Map.entry(menuNum++, new CP4()),
                                Map.entry(menuNum++, new CP5Red()),
                                Map.entry(menuNum++, new CP5("GreenTarget")),
                                Map.entry(menuNum++, new CP5("BlueTarget")),
                                Map.entry(menuNum++, new CP6()),
                                Map.entry(menuNum++, new CP7())),
                        () -> Globals.menuItem));
        menuName = new String[] {
                "core1",
                "core2",
                "core3",
                "core4",
                "core5RedTarget",
                "core5GreenTarget",
                "core5BlueTarget",
                "core6",
                "core7"
        };

        tab.add("TestMotionX", new TestMotionX());
        tab.add("TestMotionY", new TestMotionY());
        tab.add("TestMotionRot", new TestMotionRot());
        tab.add("TestPicking", new TestPicking());
        tab.add("TestLine", new TestLine());
        tab.add("TestColor", new TestColor());
        tab.add("ReleaseTrolley", new TrolleyHolder(0));
        tab.add("HoldTrolley", new TrolleyHolder(1));
        tab.add("ColorPosition", new ColorPosition());
        tab.add("CloseGripper", new Gripper(0,80));
        tab.add("OpenGripper", new Gripper(1,80));
        tab.add("Align2Line", new Align2Line());
        // tab.add("CP6", new CP6());

        // A-up button, Y-down button
        m_oi.buttonA.whenPressed(() -> {
            Globals.menuItem--;
            Globals.menuItem = (Globals.menuItem + menuNum) % menuNum;
        });
        m_oi.buttonY.whenPressed(() -> {
            Globals.menuItem++;
            Globals.menuItem %= menuNum;
        });
    }

    @Override
    public void periodic() {

        D_menu.setString(menuName[Globals.menuItem]);
        D_button.setNumber(m_oi.getDriveButtons());
        for (int i = 0; i < Globals.DNUM; i++) {
            D_debug[i].setNumber(Globals.debug[i]);
        }

    }
}