package frc.robot.commands.auto;
import java.util.function.Supplier;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class CheckAndMoveTarget extends SequentialCommandGroup{
    public CheckAndMoveTarget(String targetName, double dist){
        super(
            new MovetoPoint(targetName, dist)
            
        );
    }
    public CheckAndMoveTarget(Supplier<String> targetName, double dist){
        super(
            new MovetoPoint(targetName, dist)
        );
    }
}