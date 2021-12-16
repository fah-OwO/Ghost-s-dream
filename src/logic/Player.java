package logic;

import util.Triple;

import static util.Util.*;

public class Player {
    private static final Triple playerCo = new Triple(0, 0, metreToCoord(0));
    private static final double resistanceMul = 0.8;//maxSpeed(1-res)=acceleration
    private static double accelerationMul = 1;
    private static final double speedMul = metreToCoord(1) * (1 - resistanceMul) / frames / (resistanceMul);
    private static final double runningMul = 1.5;
    private static final double PerspectiveRadians = Math.toRadians(50 >> 1);
    private boolean running;
    //    private boolean onGround;
    private Triple speed = new Triple();
    private final Triple rotate = new Triple();
    private final Triple acceleration = new Triple();
    private static Player instance;

    private Player() {
        running = false;
    }

    public static Player getInstance() {
        if (instance == null) instance = new Player();
        return instance;
    }

    public void update() {
        Triple currentAcceleration = running ? acceleration.mul(runningMul) : acceleration;
        currentAcceleration = currentAcceleration.mul(accelerationMul);
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

    public void rotateX(double x) {
        rotate.x = x;
    }

    public void rotateY(double y) {
        rotate.y = y;
    }

    public Triple getRotate() {
        return rotate;
    }

    public Triple getSpeed() {
        return speed.mul(speedMul);
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isOnGround() {
        return speed.y == 0;//onGround;
    }

    @Override
    public String toString() {
        return "Player{" +
                "running=" + running +
                ", speed=" + speed +
                ", acceleration=" + acceleration +
                '}';
    }

    public static void setWalkSpeed(double maxSpeed) {
        accelerationMul = maxSpeed * (1 - resistanceMul);
    }

    public static double getPerspectiveRadians() {
        return PerspectiveRadians;
    }

    public static double getRunningMul() {
        return runningMul;
    }

    public void jump() {
//        if (isOnGround()) acceleration.y = -1;
    }

    public static Triple getPlayerCo() {
        return playerCo;
    }

    public boolean isWalkLeft() {
        return speed.x > 0.0;
    }

    public boolean isWalkRight() {
        return speed.x < 0.0;
    }

    public boolean isWalkFoward() {
        return speed.z < 0.0;
    }

    public boolean isWalkBack() {
        return speed.z > 0.0;
    }
//
//    public void walkLeft() {
//        accX(1);
//    }
//
//    public void walkRight() {
//        accX(-1);
//    }
//
//    public void walkForward() {
//        accZ(-1);
//    }
//
//    public void walkBack() {
//        accZ(1);
//    }

}
