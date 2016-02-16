package org.usfirst.frc.team115.robot.subsystems;

import org.usfirst.frc.team115.robot.Constants;
import org.usfirst.frc.team115.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 * 
 * @author Heather Baker
 */


public class FlyWheels extends Subsystem {

  private CANTalon[] flyWheels = new CANTalon[1];
  private Encoder[] flyEncoders = new Encoder[1];
  private PIDController[] controllers = new PIDController[1];
  
  private final double TICKS_PER_DEGREE = 1.00; // TODO
  private final double MAX_SPEED = 0.75;
  private final double INTAKE_SPEED = -0.7;
  private final int P = 0, I = 0, D = 0, F = 0; // TODO
  
  public FlyWheels() {
    flyWheels[Constants.kLeft] = new CANTalon(RobotMap.FLYWHEEL_MOTOR);
    
    flyEncoders[Constants.kLeft] = new Encoder(RobotMap.FLYWHEEL_ENCODER_A, RobotMap.FLYWHEEL_ENCODER_B, false, Encoder.EncodingType.k4X);
    
    
    controllers[Constants.kLeft] = new PIDController(P, I, D, F, flyEncoders[Constants.kLeft], flyWheels[Constants.kLeft]);
    
    
    for (PIDController controller : controllers) {
      controller.setInputRange(-MAX_SPEED, MAX_SPEED);
      controller.setOutputRange(-MAX_SPEED, MAX_SPEED);
    }
    
    resetEncoders();
  }
  
  public void drive(double speed) {
    for (CANTalon flyWheel : flyWheels) {
      flyWheel.set(speed);
    }
  }
  
  public void intake() {
    drive(INTAKE_SPEED);
  }
  
  public void stop() {
    drive(0);
  }
  
  public void resetEncoders() {
    for (Encoder flyEncoder : flyEncoders) {
      flyEncoder.reset();
    }
  }
  
  public double getAngle(int side) {
    return (flyEncoders[side].get() / (TICKS_PER_DEGREE));
  }
  
  public double getAngle() {
    return (getAngle(Constants.kLeft));
  }
  
  public void startPID(double speed) {
    for (PIDController controller : controllers) {
      controller.setSetpoint(speed);
      controller.enable();
    }
  }
  
  public void resetPID() {
    for (PIDController controller : controllers) {
      controller.reset();
    }
  }

  @Override
  protected void initDefaultCommand() {
    
  }

}