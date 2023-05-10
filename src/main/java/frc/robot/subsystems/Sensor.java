package frc.robot.subsystems;

//Java imports

//Vendor imports

import com.studica.frc.Cobra;
import com.studica.frc.Servo;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
//import edu.wpi.first.wpilibj.Servo;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
//WPI imports
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Globals;

public class Sensor extends SubsystemBase {
    // Creates all necessary hardware interface here for sensors

    // Sensors
    private final DigitalInput input10;
    private AnalogInput frontIR;
    private AnalogInput gripperIR;
    private Cobra cobra;
    double cobraValue[];
    private int i;

    // Good for debugging
    // Shuffleboard
    private final ShuffleboardTab tab = Shuffleboard.getTab("Sensors");
    private final NetworkTableEntry D_inputDisp = tab.add("inputDisp", false).getEntry();
    private final NetworkTableEntry D_cntDisp = tab.add("cntDisp", 0).getEntry();
    private final NetworkTableEntry D_FrontIRSensor = tab.add("Front IR Value (cm)", 0).getEntry();
    private final NetworkTableEntry D_GripperIRSensor = tab.add("Gripper IR Value (cm)", 0).getEntry();

    
    // Subsystem for sensors
    // This is just an example.
    public Sensor() {
        cobraValue = new double[4];

        // Constuct a new instance
        cobra = new Cobra();
        frontIR = new AnalogInput(0);
        gripperIR = new AnalogInput(1);
        input10 = new DigitalInput(Constants.INPUT0);

    }

    /**
     * Sets the servo angle
     * <p>
     * 
     * @param degrees degree to set the servo to, range 0° - 300°
     */

    public Boolean getSwitch() {
        return input10.get();
    }
   

    /**
     * Call for the raw ADC value
     * <p>
     * 
     * @param channel range 0 - 3 (matches what is on the adc)
     * @return value between 0 and 2047 (11-bit)
     */
    public int getCobraRawValue(final int channel) {
        return cobra.getRawValue(channel);
    }

    /**
     * Call for the distance measured by the Sharp IR Sensor located at the front of the Robot
     * <p>
     * 
     * @return value between 0 - 100 (valid data range is 10cm - 80cm)
     */
    public double getFrontIRDistance() {
        return (Math.pow(frontIR.getAverageVoltage(), -1.2045)) * 27.726;
    }
    /**
     * Call for the distance measured by the Sharp IR Sensor located at the gripper
     * <p>
     * 
     * @return value between 0 - 100 (valid data range is 10cm - 80cm)
     */
    public double getGripperIRDistance() {
        return (Math.pow(gripperIR.getAverageVoltage(), -1.2045)) * 27.726;
    }

    public double getCobraTotal() {
        // return
        // (cobra.getRawValue(0)+cobra.getRawValue(1)+cobra.getRawValue(2)+cobra.getRawValue(3));
        return (cobra.getRawValue(1) + cobra.getRawValue(2));
    }

    /**
     * Code that runs once every robot loop
     */
    @Override
    // Runs every 20ms
    public void periodic() {
        
        // Display on shuffleboard
        // These display is good for debugging but may slow system down.
        // Good to remove unnecessary display during competition
       
        D_FrontIRSensor.setNumber(getFrontIRDistance());
        D_GripperIRSensor.setNumber(getGripperIRDistance());
        // D_Cobra_3.setNumber(getCobraRawValue(3));
        // D_CobraTotal.setNumber(getCobraTotal());
    }
}