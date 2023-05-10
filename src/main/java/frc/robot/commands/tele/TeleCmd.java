package frc.robot.commands.tele;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Globals;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.OmniDrive;
import frc.robot.subsystems.Sensor;

//This command will be run during teleop mode
public class TeleCmd extends CommandBase
{
    /**
     * Bring in Subsystem and Gamepad code
     */
    private final OmniDrive m_omnidrive;
    private final Sensor m_sensor;
    private final Arm m_arm;
    private final OI m_oi;
    private final int[] cvModes = {-2,-1,0,1,2,3,4,5};
    private int cvSelector = 0;
    /**
     * Constructor
     */
    public TeleCmd(OmniDrive omnidrive, OI oi, Arm arm)
    {
        m_omnidrive = RobotContainer.m_omnidrive;
        m_sensor = RobotContainer.m_sensor;
        m_oi = RobotContainer.m_oi;
        m_arm = RobotContainer.m_arm;
        addRequirements(m_omnidrive); //add the drive subsystem as a requirement 
		//addRequirements(m_menu); 
    }

    /**
     * Code here will run once when the command is called for the first time
     */
    @Override
    public void initialize()
    {
        cvSelector = 0;
    }

    /**
     * Code here will run continously every robot loop until the command is stopped
     */
    @Override
    public void execute()
    {
        /**
         * Get Joystick data
         */
        //Right stick for X-Y control
        //Left stick for W (rotational) control
        double x = m_oi.getRightDriveX();
        double y = -m_oi.getRightDriveY();//Down is positive. Need to negate
        double w = -m_oi.getLeftDriveX(); //X-positive is CW. Need to negate
        boolean open = m_oi.getDriveAButton();
        boolean cvModeSW = m_oi.getDriveBButton();
        boolean lower = m_oi.getDriveRightBumper();
        if(cvModeSW){
            cvSelector++;
        }
        if (lower){
            m_arm.setArmPos(new Translation2d(0.33,-0.07));
        }
        else{
            m_arm.setArmPos(new Translation2d(0.33,0.24));
        }
        Globals.cvMode = cvModes[cvSelector%8];
        
        if(open){
            m_arm.setGripper(300);
        }
        else{
            m_arm.setGripper(0);
        }
        m_arm.setGripper(0);
        // m_arm.setCameraAngle(90);
        //Get other buttons?

        //Add code here to control servo motor etc.

        //m_omnidrive.setMotorOut012(s0,s1,s2);
        // m_arm.setShoulderAngle( (w*150) + 150);
        // m_arm.setElbowAngle( (w*150) + 150);
        m_omnidrive.setRobotSpeedXYW(x*0.4, y*0.4, w*Math.PI/2);
        Translation2d pos = new Translation2d(m_arm.getSliderX(), m_arm.getSliderY());
        m_arm.setArmPos(pos);
        m_arm.setCameraAngle(m_arm.getSliderCamera());
        m_arm.setGripper(m_arm.getSliderGripper());

    }

    /**
     * When the comamnd is stopped or interrupted this code is run
     * <p>
     * Good place to stop motors in case of an error
     */
    @Override
    public void end(boolean interrupted)
    {
        m_omnidrive.setMotorOut012(0, 0, 0);
    }

    /**
     * Check to see if command is finished
     */
    @Override
    public boolean isFinished()
    {
        return false;
    }
}