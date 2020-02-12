package frc.robot.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 *
 */
public class Camera extends SubsystemBase {

    private final boolean m_useCamera;

    private UsbCamera m_camIntake;
    private UsbCamera m_camClimb;
    private VideoSink m_server;

    public Camera() {
        // TODO(pj) - Undo when cameras are plugged in. Disabled now to clean up roboRio logs
        m_useCamera = false;
        // m_useCamera = !RobotBase.isSimulation();
        if (m_useCamera) {
            return; 
        }
        m_camIntake = new UsbCamera("camIntake", Constants.CAMERA_INTAKE);
        m_camIntake.setResolution(320, 240);
        m_camIntake.setFPS(20);
        m_camClimb = new UsbCamera("camClimb", Constants.CAMERA_CLIMB);
        m_camClimb.setResolution(320, 240);
        m_camClimb.setFPS(20);
        m_server = CameraServer.getInstance().addSwitchedCamera("Driver Cameras");
        m_server.setSource(m_camIntake);

        // To see the stream in the Dashboard, add a CameraServer Stream Viewer
    }

    public void switchToCamClimb() {
        if (m_useCamera) {
            return; 
        }
        m_server.setSource(m_camClimb);
        System.out.println("Cam Climb!");
    }

    public void switchToCamIntake() {
        if (m_useCamera) {
            return; 
        }

        m_server.setSource(m_camIntake);
        System.out.println("Cam Intake!");
    }

    public void initDefaultCommand() {
        if (m_useCamera) {
            return; 
        }
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }


}
