package logic;

import application.Main;
import util.Triple;

import static util.Util.*;

public class Player {
    private static final double resistanceMul = 0.8;//maxSpeed(1-res)=acceleration
    private static double accelerationMul = 1;
    private static final double speedMul = metreToCoord(1) * (1 - resistanceMul) / frames / (resistanceMul);
    private static final double runningMul = 2;
    private static final double PerspectiveRadians = Math.toRadians(50 >> 1);
    private boolean running;
    private Triple speed = new Triple();
    private final Triple acceleration = new Triple();
    private static Player instance;

    private Player() {
        running = false;
    }

    public static Player getInstance() {
        if (instance == null) instance = new Player();
        return instance;
    }

    public void accX(double x) {
        acceleration.x = x;
    }

    public void accZ(double z) {
        acceleration.z = z;
    }

    public void update() {
        Triple currentAcceleration = running ? acceleration.mul(runningMul) : acceleration;
        currentAcceleration = currentAcceleration.mul(accelerationMul);
        Triple newSpeed = speed.mul(resistanceMul).add(currentAcceleration);
        newSpeed.near0();
        speed = newSpeed;
    }

    public void setWalkSpeed(double maxSpeed) {
        accelerationMul = maxSpeed * (1 - resistanceMul);
    }

    public Triple getSpeed() {
        return speed.mul(speedMul);
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public static double getPerspectiveRadians() {
        return PerspectiveRadians;
    }

    @Override
    public String toString() {
        return "Player{" +
                "running=" + running +
                ", speed=" + speed +
                ", acceleration=" + acceleration +
                '}';
    }
}
