/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;

import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;


public class PassVariable extends CommandBase {

  private NetworkTable table;
  private NetworkTableEntry myVariableEntry;
  private double myVariable;
  private Timer timer;
  private boolean Terminate;  


  
 /**
     *This command is a flag that checks if the python vision side has run at least once
     */
   
  public PassVariable() {
    
    timer = new Timer();

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
       // Connect to the NetworkTables server running on the VNC Viewer
       NetworkTableInstance inst = NetworkTableInstance.getDefault();
       inst.startClient("10.69.42.2"); ;

       // Get the "MyTable" table from the NetworkTables server
       table = inst.getTable("MyTable");


       //gets myVariable from the shuffleboard during init
       myVariable = table.getEntry("myVariable").getDouble(0.0);


       timer.start();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
       
       final double myVariable2 = table.getEntry("myVariable").getDouble(0.0); //gets myVariable from shuffleboard during execute, some time later 
       //the assumption is that myVariable2 should be different from myVariable1 so the program has at least run one
       

        System.out.println("myVariable #######################################################");
        System.out.println("PreviousValue  = " + myVariable);
        System.out.println("myVariable########################################################");

        System.out.println("myVariable #######################################################");
        System.out.println("NewValue  = " + myVariable2);
        System.out.println("myVariable########################################################");

        if (myVariable != myVariable2)
        {
          Terminate = true;
        }
        else
        {
          Terminate = false;
        }

   
  }
  

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // table.getEntry("myVariable").setDouble(0.0);
  }


  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    
       return Terminate ;
  }
}
