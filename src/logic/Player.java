package logic;

import application.Main;

public class Player {
    private static final double resistanceMul = 0.7;//maxSpeed(1-res)=acceleration
    private static final double accelerationMul = 0.05;
    private static final double speedMul = 0.07 * 0.01 / Main.frames / (resistanceMul);
    private static final double runningMul = 4;
    private static final double PerspectiveRadians = Math.toRadians(50 >> 1);
    private boolean running;
    private Triple speed = new Triple();
    private final Triple acceleration = new Triple();

    public Player() {
        running = false;
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
        //double currentResistanceMul = resistanceMul;//running ? 1 - (1 - resistanceMul) / runningMul : resistanceMul;
        Triple newSpeed = speed.mul(resistanceMul).add(currentAcceleration);
        newSpeed.near0();
        speed = newSpeed;
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

}
