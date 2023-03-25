package com.gos.chargedup.subsystems;

import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.Constants;
import com.gos.chargedup.autonomous.AutonomousFactory;
import com.gos.lib.led.LEDPattern;
import com.gos.lib.led.mirrored.MirroredLEDBoolean;
import com.gos.lib.led.mirrored.MirroredLEDFlash;
import com.gos.lib.led.mirrored.MirroredLEDMovingPixel;
import com.gos.lib.led.mirrored.MirroredLEDPatternLookup;
import com.gos.lib.led.mirrored.MirroredLEDPercentScale;
import com.gos.lib.led.mirrored.MirroredLEDRainbow;
import com.gos.lib.led.mirrored.MirroredLEDSolidColor;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.HashMap;
import java.util.Map;

public class LEDManagerSubsystem extends SubsystemBase {

    private static final int MAX_INDEX_LED = 30;

    private static final int AUTO_HEIGHT_START = 0;
    private static final int AUTO_HEIGHT_COUNT = MAX_INDEX_LED / 4;

    private static final int AUTO_MODE_START = AUTO_HEIGHT_COUNT;

    private static final int AUTO_MODE_COUNT = MAX_INDEX_LED / 4;

    private static final int CLAW_HOLD_WAIT_TIME = 1;


    // subsystems
    //private final CommandXboxController m_joystick;
    private final ChassisSubsystem m_chassisSubsystem;
    private final ArmPivotSubsystem m_armSubsystem;

    private final ClawSubsystem m_claw;

    // Led core
    protected final AddressableLEDBuffer m_buffer;
    protected final AddressableLED m_led;

    // Testing LED
    //private final MirroredLEDPercentScale m_drivetrainSpeed;

    // Comp LED
    private final MirroredLEDFlash m_coneGamePieceSignal;
    private final MirroredLEDFlash m_cubeGamePieceSignal;

    private boolean m_optionConeLED;
    private boolean m_optionCubeLED;
    private boolean m_optionDockLED;
    private boolean m_clawIsAligned;

    private final MirroredLEDFlash m_readyToScore;
    private final MirroredLEDBoolean m_armAtAngle;

    private final MirroredLEDPercentScale m_dockAngle;
    private final MirroredLEDFlash m_isEngaged;
    private static final double ALLOWABLE_ERROR_ENGAGE = 2;

    private final MirroredLEDRainbow m_notInCommunityZone;

    private final AutonomousFactory m_autoModeFactory;

    private final MirroredLEDPatternLookup<AutonomousFactory.AutonMode> m_autoModeColor;
    private final MirroredLEDPatternLookup<AutoPivotHeight> m_heightColor;

    private final MirroredLEDFlash m_clawAlignedSignal;

    private final MirroredLEDFlash m_isInLoadingZoneSignal;

    private final MirroredLEDFlash m_isHoldingPieceClaw;

    private final Timer m_clawLEDsTimer = new Timer();

    private boolean m_clawWasTripped;


    public LEDManagerSubsystem(ChassisSubsystem chassisSubsystem, ArmPivotSubsystem armSubsystem, ClawSubsystem claw, AutonomousFactory autonomousFactory) {
        m_autoModeFactory = autonomousFactory;

        m_chassisSubsystem = chassisSubsystem;
        m_armSubsystem = armSubsystem;
        m_claw = claw;

        m_buffer = new AddressableLEDBuffer(MAX_INDEX_LED);
        m_led = new AddressableLED(Constants.LED_PORT);

        // Test LED
        //m_drivetrainSpeed = new MirroredLEDPercentScale(m_buffer, 0, MAX_INDEX_LED, Color.kGreen, 15);

        // Comp LED
        m_coneGamePieceSignal = new MirroredLEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.5, Color.kYellow);
        m_cubeGamePieceSignal = new MirroredLEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.5, Color.kMediumPurple);

        m_readyToScore = new MirroredLEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.5, Color.kGreen);
        //m_turretAngle = new MirroredLEDPercentScale(m_buffer, 20, 10, Color.kRed, 20);
        m_armAtAngle = new MirroredLEDBoolean(m_buffer, 10, 10, Color.kAntiqueWhite, Color.kRed);
        //m_goodDistance = new MirroredLEDBoolean(m_buffer, 0, 10, Color.kAntiqueWhite, Color.kRed);

        //m_goodDistToLoadingPiece = new MirroredLEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.5, Color.kGreen);

        m_isInLoadingZoneSignal = new MirroredLEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.5, Color.kCornflowerBlue);

        m_dockAngle = new MirroredLEDPercentScale(m_buffer, 0, MAX_INDEX_LED, Color.kRed, 30);
        m_optionDockLED = false;
        m_isEngaged = new MirroredLEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.5, Color.kGreen);

        m_notInCommunityZone = new MirroredLEDRainbow(m_buffer, 0, MAX_INDEX_LED);

        // no piece - base color
        // one piece - flash
        // two piece - single light

        Color NODE_0_COLOR = Color.kRed;
        Color NODE_1_COLOR = Color.kOrange;
        Color NODE_2_COLOR = Color.kYellow;
        Color NODE_3_COLOR = Color.kGreen;
        Color NODE_4_COLOR = Color.kBlack;
        Color NODE_5_COLOR = Color.kIndigo;
        Color NODE_6_COLOR = Color.kViolet;
        Color NODE_7_COLOR = Color.kPapayaWhip;
        Color NODE_8_COLOR = Color.kDarkCyan;


        Map<AutonomousFactory.AutonMode, LEDPattern> autonColorMap = new HashMap<>();

        addNoPieceAuto(autonColorMap, AutonomousFactory.AutonMode.DO_NOTHING, Color.kBlack);

        addNoPieceAuto(autonColorMap, AutonomousFactory.AutonMode.ONLY_LEAVE_COMMUNITY_0, NODE_0_COLOR);
        addNoPieceAuto(autonColorMap, AutonomousFactory.AutonMode.ONLY_LEAVE_COMMUNITY_8, NODE_8_COLOR);
        addNoPieceAuto(autonColorMap, AutonomousFactory.AutonMode.ONLY_DOCK_AND_ENGAGE_4, NODE_4_COLOR);

        addOnePieceAndEngageAuto(autonColorMap, AutonomousFactory.AutonMode.ONE_NODE_AND_ENGAGE_3, NODE_3_COLOR);
        addOnePieceAndEngageAuto(autonColorMap, AutonomousFactory.AutonMode.ONE_NODE_AND_ENGAGE_4, NODE_4_COLOR);
        addOnePieceAndEngageAuto(autonColorMap, AutonomousFactory.AutonMode.ONE_NODE_AND_ENGAGE_5, NODE_5_COLOR);

        addOnePieceAuto(autonColorMap, AutonomousFactory.AutonMode.ONE_NODE_AND_LEAVE_COMMUNITY_0, NODE_0_COLOR);
        addOnePieceAuto(autonColorMap, AutonomousFactory.AutonMode.ONE_NODE_AND_LEAVE_COMMUNITY_1, NODE_1_COLOR);
        addOnePieceAuto(autonColorMap, AutonomousFactory.AutonMode.ONE_NODE_AND_LEAVE_COMMUNITY_2, NODE_2_COLOR);
        addOnePieceAuto(autonColorMap, AutonomousFactory.AutonMode.ONE_NODE_AND_LEAVE_COMMUNITY_3, NODE_3_COLOR);
        addOnePieceAuto(autonColorMap, AutonomousFactory.AutonMode.ONE_NODE_AND_LEAVE_COMMUNITY_5, NODE_5_COLOR);
        addOnePieceAuto(autonColorMap, AutonomousFactory.AutonMode.ONE_NODE_AND_LEAVE_COMMUNITY_6, NODE_6_COLOR);
        addOnePieceAuto(autonColorMap, AutonomousFactory.AutonMode.ONE_NODE_AND_LEAVE_COMMUNITY_7, NODE_7_COLOR);
        addOnePieceAuto(autonColorMap, AutonomousFactory.AutonMode.ONE_NODE_AND_LEAVE_COMMUNITY_8, NODE_8_COLOR);

        addTwoPieceAuto(autonColorMap, AutonomousFactory.AutonMode.TWO_PIECE_NODE_0_AND_1, NODE_0_COLOR);
        addTwoPieceAuto(autonColorMap, AutonomousFactory.AutonMode.TWO_PIECE_NODE_7_AND_8, NODE_7_COLOR);

        // no scoring -- solid red
        // low - red, middle - blue, high - purple
        Map<AutoPivotHeight, LEDPattern> autoHeightMap = new HashMap<>();
        addHeightPattern(autoHeightMap, AutoPivotHeight.LOW, Color.kRed);
        addHeightPattern(autoHeightMap, AutoPivotHeight.MEDIUM, Color.kBlue);
        addHeightPattern(autoHeightMap, AutoPivotHeight.HIGH, Color.kPurple);

        m_autoModeColor = new MirroredLEDPatternLookup<>(m_buffer, autonColorMap);
        m_heightColor = new MirroredLEDPatternLookup<>(m_buffer, autoHeightMap);

        m_led.setLength(m_buffer.getLength());

        //for Claw Aligned Check
        m_clawAlignedSignal = new MirroredLEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.5, Color.kOrange);

        //holding piece in claw
        m_isHoldingPieceClaw = new MirroredLEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.05, Color.kRed);
        m_clawWasTripped = false;

        // Set the data
        m_led.setData(m_buffer);
        m_led.start();
    }

    private void addNoPieceAuto(Map<AutonomousFactory.AutonMode, LEDPattern> autonColorMap, AutonomousFactory.AutonMode mode, Color color) {
        autonColorMap.put(mode, new MirroredLEDSolidColor(m_buffer, AUTO_MODE_START, AUTO_MODE_COUNT, color));
    }

    private void addOnePieceAuto(Map<AutonomousFactory.AutonMode, LEDPattern> autonColorMap, AutonomousFactory.AutonMode mode, Color color) {
        autonColorMap.put(mode, new MirroredLEDFlash(m_buffer, AUTO_MODE_START, AUTO_MODE_COUNT, 0.5, color));
    }

    private void addOnePieceAndEngageAuto(Map<AutonomousFactory.AutonMode, LEDPattern> autonColorMap, AutonomousFactory.AutonMode mode, Color color) {
        autonColorMap.put(mode, new MirroredLEDFlash(m_buffer, AUTO_MODE_START, AUTO_MODE_COUNT, 2, color));
    }

    private void addTwoPieceAuto(Map<AutonomousFactory.AutonMode, LEDPattern> autonColorMap, AutonomousFactory.AutonMode mode, Color color) {
        autonColorMap.put(mode, new MirroredLEDMovingPixel(m_buffer, AUTO_MODE_START, AUTO_MODE_COUNT, color));
    }

    private void addHeightPattern(Map<AutoPivotHeight, LEDPattern> autoHeightMap, AutoPivotHeight height, Color color) {
        autoHeightMap.put(height, new MirroredLEDFlash(m_buffer, AUTO_HEIGHT_START, AUTO_HEIGHT_COUNT, 0.5, color));
    }


    private void disabledPatterns() {
        AutoPivotHeight height = m_autoModeFactory.getSelectedHeight();
        m_heightColor.setKey(height);

        if (m_heightColor.hasKey(height)) {
            m_heightColor.writeLeds();
        }

        AutonomousFactory.AutonMode autoMode = m_autoModeFactory.getSelectedAuto();
        m_autoModeColor.setKey(autoMode);

        if (m_autoModeColor.hasKey(autoMode)) {
            m_autoModeColor.writeLeds();
        }
    }

    @SuppressWarnings("PMD.CyclomaticComplexity")
    private void enabledPatterns() {
        if (m_optionConeLED) {
            m_coneGamePieceSignal.writeLeds();
        }
        else if (m_optionCubeLED) {
            m_cubeGamePieceSignal.writeLeds();
        }
        else if (m_optionDockLED || m_chassisSubsystem.tryingToEngage()) {
            dockAndEngagePatterns();
        }
        else if (m_claw.hasGamePiece() && m_clawLEDsTimer.get() < CLAW_HOLD_WAIT_TIME) {
            m_isHoldingPieceClaw.writeLeds();
        }
        else if (m_chassisSubsystem.isInCommunityZone()) {
            communityZonePatterns();
        }
        else if (m_chassisSubsystem.isInLoadingZone()) {
            loadingZonePatterns();
        }

        else {
            m_notInCommunityZone.writeLeds();
        }

        if (m_clawIsAligned) {
            m_clawAlignedSignal.writeLeds();
        }


        /*
        else {
            communityZonePatterns();
        }
        */
    }

    public void shouldTrip() {
        if (!m_clawWasTripped && m_claw.hasGamePiece()) {
            m_clawLEDsTimer.reset();
            m_clawLEDsTimer.start();
        }

        m_clawWasTripped = m_claw.hasGamePiece();
    }

    @Override
    public void periodic() {
        clear();
        if (DriverStation.isDisabled()) {
            disabledPatterns();
        }
        else {
            enabledPatterns();
        }
        shouldTrip();


        // driverPracticePatterns();
        m_led.setData(m_buffer);
    }

    private void clear() {
        for (int i = 0; i < MAX_INDEX_LED; i++) {
            m_buffer.setRGB(i, 0, 0, 0);
        }
    }

    public void setDockOption() {
        m_optionDockLED = true;
    }

    private void dockAndEngagePatterns() {
        if (Math.abs(m_chassisSubsystem.getPitch()) > ALLOWABLE_ERROR_ENGAGE) {
            m_dockAngle.distanceToTarget(m_chassisSubsystem.getPitch());
            m_dockAngle.writeLeds();
        }
        else {
            m_isEngaged.writeLeds();
        }
    }

    public void stopDockAndEngagePatterns() {
        m_optionDockLED = false;
    }

    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void communityZonePatterns() {
        if (m_armSubsystem.isArmAtAngle()) {
            m_readyToScore.writeLeds();
        }
        else if (!m_armSubsystem.isArmAtAngle()) {
            //m_turretAngle.distanceToTarget(m_turretSubsystem.getTurretError());
            m_armAtAngle.setState(m_armSubsystem.isArmAtAngle());
        }
    }

    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void loadingZonePatterns() {
        if (m_chassisSubsystem.isInLoadingZone()) {
            m_isInLoadingZoneSignal.writeLeds();
        }

    }

    public void setClawIsAligned(boolean isAligned) {
        m_clawIsAligned = isAligned;
    }

    //////////////////////
    // Command Factories
    //////////////////////
    public CommandBase commandConeGamePieceSignal() {
        return this.runEnd(() -> m_optionConeLED = true, () -> m_optionConeLED = false);
    }

    public CommandBase commandCubeGamePieceSignal() {
        return this.runEnd(() -> m_optionCubeLED = true, () -> m_optionCubeLED = false);
    }
}

