package logic;

import util.Triple;

import static util.Util.frames;
import static util.Util.metreToCoord;

public class Player {
    private static final Triple playerCo = new Triple(0, 0, metreToCoord(0));
    private static final double resistanceMul = 0.8;
    private static final double speedMul = metreToCoord(1) * (1 - resistanceMul) / frames / (resistanceMul);
    private static final double runningMul = 1.5;
    private static final double PerspectiveRadians = Math.toRadians(50 >> 1);
    private static Player instance;
    private final Triple acceleration = new Triple();
    private boolean running;
    private Triple speed = new Triple();

    private Player() {
        running = false;
    }

    public static Player getInstance() {
        if (instance == null) instance = new Player();
        return instance;
    }

    public static double getPerspectiveRadians() {
        return PerspectiveRadians;
    }

    public static double getRunningMul() {
        return runningMul;
    }

    public static Triple getPlayerCo() {
        return playerCo;
    }

    public void update() {
        Triple currentAcceleration = running ? acceleration.mul(runningMul) : acceleration;
        Triple newSpeed = speed.mul(resistanceMul).add(currentAcceleration);
        newSpeed.near0();
        speed = newSpeed;
    }

    public boolean isMoving() {
        return !speed.is0();
    }

    public void accX(double x) {
        acceleration.x = x;
    }

    public void accZ(double z) {
        acceleration.z = z;
    }


    public Triple getSpeed() {
        return speed.mul(speedMul);
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
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
