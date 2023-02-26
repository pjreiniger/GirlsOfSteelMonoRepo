package com.gos.chargedup.subsystems;

import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.Constants;
import com.gos.chargedup.autonomous.AutonomousFactory;
import com.gos.lib.led.LEDFlash;
import com.gos.lib.led.LEDMovingPixel;
import com.gos.lib.led.LEDPattern;
import com.gos.lib.led.mirrored.MirroredLEDBoolean;
import com.gos.lib.led.mirrored.MirroredLEDFlash;
import com.gos.lib.led.mirrored.MirroredLEDPercentScale;
import com.gos.lib.led.mirrored.MirroredLEDRainbow;
import com.gos.lib.led.mirrored.MirroredLEDSolidColor;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import java.util.HashMap;
import java.util.Map;

public class LEDManagerSubsystem extends SubsystemBase {
    // subsystems
    private final CommandXboxController m_joystick;
    private final ChassisSubsystem m_chassisSubsystem;
    private final ArmSubsystem m_armSubsystem;
    private final TurretSubsystem m_turretSubsystem;

    // Led core
    protected final AddressableLEDBuffer m_buffer;
    protected final AddressableLED m_led;

    // Testing LED
    private final MirroredLEDPercentScale m_drivetrainSpeed;

    // Comp LED
    private final MirroredLEDFlash m_coneGamePieceSignal;
    private final MirroredLEDFlash m_cubeGamePieceSignal;

    private boolean m_optionConeLED;
    private boolean m_optionCubeLED;
    private boolean m_optionDockLED;

    private final MirroredLEDFlash m_readyToScore;
    private final MirroredLEDPercentScale m_turretAngle;
    private final MirroredLEDBoolean m_armAtAngle;
    // private final MirroredLEDBoolean m_goodDistance;

    private final MirroredLEDFlash m_goodDistToLoadingPiece;

    private final MirroredLEDPercentScale m_dockAngle;

    private final MirroredLEDRainbow m_notInCommunityZone;

    private static final int MAX_INDEX_LED = 30;
    private static final int HALF_MAX_INDEX_LED = (int)(MAX_INDEX_LED / 2);

    public LEDManagerSubsystem(CommandXboxController joystick, ChassisSubsystem chassisSubsystem, ArmSubsystem armSubsystem, TurretSubsystem turretSubsystem) {
        m_joystick = joystick;
        m_chassisSubsystem = chassisSubsystem;
        m_armSubsystem = armSubsystem;
        m_turretSubsystem = turretSubsystem;

        m_buffer = new AddressableLEDBuffer(MAX_INDEX_LED);
        m_led = new AddressableLED(Constants.LED_PORT);

        // Test LED
        m_drivetrainSpeed = new MirroredLEDPercentScale(m_buffer, 0, MAX_INDEX_LED, Color.kGreen, 15);

        // Comp LED
        m_coneGamePieceSignal = new MirroredLEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.5, Color.kYellow);
        m_cubeGamePieceSignal = new MirroredLEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.5, Color.kMediumPurple);

        m_readyToScore = new MirroredLEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.5, Color.kGreen);
        m_turretAngle = new MirroredLEDPercentScale(m_buffer, 20, 10, Color.kRed, 20);
        m_armAtAngle = new MirroredLEDBoolean(m_buffer, 10, 10, Color.kAntiqueWhite, Color.kRed);
        // m_goodDistance = new MirroredLEDBoolean(m_buffer, 0, 10, Color.kAntiqueWhite, Color.kRed);

        m_goodDistToLoadingPiece = new MirroredLEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.5, Color.kGreen);

        m_dockAngle = new MirroredLEDPercentScale(m_buffer, 0, MAX_INDEX_LED, Color.kRed, 30);

        m_notInCommunityZone = new MirroredLEDRainbow(m_buffer, 0, MAX_INDEX_LED);

        // no piece - base color
        // one piece - flash
        // two piece - single light

        // starting position -- color (1 - pink, 2 - red, 3 - orange, 4 - yellow, 5 - green, 6 - blue, 7 - purple)
        Map<AutonomousFactory.AutonMode, LEDPattern> autonColorMap = new HashMap<>();
        autonColorMap.put(AutonomousFactory.AutonMode.ONLY_LEAVE_COMMUNITY_END, new MirroredLEDSolidColor(m_buffer, 0, HALF_MAX_INDEX_LED, Color.kPink));
        autonColorMap.put(AutonomousFactory.AutonMode.ONLY_LEAVE_COMMUNITY_PLAYER_STATION, new MirroredLEDSolidColor(m_buffer, 0, HALF_MAX_INDEX_LED, Color.kPurple));
        autonColorMap.put(AutonomousFactory.AutonMode.ONLY_DOCK_AND_ENGAGE, new MirroredLEDSolidColor(m_buffer, 0, HALF_MAX_INDEX_LED, Color.kPapayaWhip));

        autonColorMap.put(AutonomousFactory.AutonMode.SCORE_AT_CURRENT_POS, new MirroredLEDFlash(m_buffer, 0, HALF_MAX_INDEX_LED, 0.5, Color.kPapayaWhip));
        autonColorMap.put(AutonomousFactory.AutonMode.ONE_NODE_AND_ENGAGE_3, new MirroredLEDFlash(m_buffer, 0, HALF_MAX_INDEX_LED, 0.5, Color.kDarkOrange));
        autonColorMap.put(AutonomousFactory.AutonMode.ONE_NODE_AND_ENGAGE_4, new MirroredLEDFlash(m_buffer, 0, HALF_MAX_INDEX_LED, 0.5, Color.kYellow));
        autonColorMap.put(AutonomousFactory.AutonMode.ONE_NODE_AND_ENGAGE_5, new MirroredLEDFlash(m_buffer, 0, HALF_MAX_INDEX_LED, 0.5, Color.kGreen));

        autonColorMap.put(AutonomousFactory.AutonMode.TWO_PIECE_NODE_0_AND_1, new LEDMovingPixel(m_buffer, 0, HALF_MAX_INDEX_LED, Color.kPink));
        autonColorMap.put(AutonomousFactory.AutonMode.TWO_PIECE_NODE_7_AND_8, new LEDMovingPixel(m_buffer, 0, HALF_MAX_INDEX_LED, Color.kPurple));

        // no scoring -- solid red
        // low - red, middle - blue, high - purple
        Map<AutoPivotHeight, LEDPattern> autoHightMap = new HashMap<>();
        autoHightMap.put(AutoPivotHeight.LOW, new LEDFlash(m_buffer, HALF_MAX_INDEX_LED, HALF_MAX_INDEX_LED, 0.5, Color.kRed));
        autoHightMap.put(AutoPivotHeight.MEDIUM, new LEDFlash(m_buffer, HALF_MAX_INDEX_LED, HALF_MAX_INDEX_LED,0.5, Color.kBlue));
        autoHightMap.put(AutoPivotHeight.HIGH, new LEDFlash(m_buffer, HALF_MAX_INDEX_LED, HALF_MAX_INDEX_LED, 0.5, Color.kPurple));


        m_led.setLength(m_buffer.getLength());

        // Set the data
        m_led.setData(m_buffer);
        m_led.start();
    }

    public boolean atGoodPositionToLoadPiece() {
        // get y distance
        // get the angle of the turret (field-oriented)
        // good distance to loading piece?
        return false;
    }

    @Override
    public void periodic() {
        clear();
        if (m_optionConeLED) {
            m_coneGamePieceSignal.writeLeds();
        }

        else if (m_optionCubeLED) {
            m_cubeGamePieceSignal.writeLeds();
        }

        // else if (chassis.incommunityzone): community zone patterns

        // else if chassis.inloadingzone: loading zone patterns
        else if (m_optionDockLED) {
            dockAndEngagePatterns();
        }

        else {
            communityZonePatterns();
        }

        // driverPracticePatterns();
        m_led.setData(m_buffer);
    }

    public void clear() {
        for (int i = 0; i < MAX_INDEX_LED; i++) {
            m_buffer.setRGB(i, 0, 0, 0);
        }
    }

    public void dockAndEngagePatterns() {
        m_optionDockLED = true;
        m_dockAngle.distanceToTarget(m_chassisSubsystem.getPitch());
    }

    public void communityZonePatterns() {
        if (m_turretSubsystem.atTurretAngle() && m_armSubsystem.atArmAngle()) {
            m_readyToScore.writeLeds();
        }
        else if (!m_turretSubsystem.atTurretAngle() || !m_armSubsystem.atArmAngle()) {
            m_turretAngle.distanceToTarget(m_turretSubsystem.getTurretError());
            m_armAtAngle.setState(m_armSubsystem.atArmAngle());
        }
    }

    public void loadingZonePatterns() {
        // if good dist
        m_goodDistToLoadingPiece.writeLeds();
        // else {
            m_notInCommunityZone.writeLeds();
        // }

    }

    public void generalTeleopPatterns() {
        m_optionCubeLED = false;
        m_optionConeLED = false;
        m_notInCommunityZone.writeLeds();
    }

    private void driverPracticePatterns() {
        m_drivetrainSpeed.distanceToTarget(Math.abs(m_joystick.getLeftY()));
        m_drivetrainSpeed.writeLeds();
    }

    public void setConeGamePieceSignal() {
        m_optionConeLED = true;

    }

    public void setCubeGamePieceSignal() {
        m_optionCubeLED = true;
    }

    public CommandBase commandConeGamePieceSignal() {
        return this.runEnd(this::setConeGamePieceSignal, this::generalTeleopPatterns);
    }

    public CommandBase commandCubeGamePieceSignal() {
        return this.runEnd(this::setCubeGamePieceSignal, this::generalTeleopPatterns);
    }
}

