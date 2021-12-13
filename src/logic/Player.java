package logic;

import application.Main;

public class Player {
    private static final double resistanceMul = 0.7;
    private static final double speedMul = 0.07 / (resistanceMul);
    private static final double runningMul = 1.2;
    private static final double PerspectiveRadians = Math.toRadians(50 >> 1);
    private boolean running;
    private Triple speed = new Triple();
    private Triple acceleration = new Triple();
//    private static final Triple capSpeed = new Triple(1, 0, 1);
//    private static final Triple capSpeedWhileRunning = capSpeed.mul(runningMul);//new Triple();

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
//        Triple cap = running ? capSpeedWhileRunning : capSpeed;
        Triple currentAcceleration = running ? acceleration.mul(runningMul) : acceleration;
        double currentResistanceMul = running ? 1 - (1 - resistanceMul) / runningMul : resistanceMul;
        Triple newSpeed = speed.mul(currentResistanceMul).add(currentAcceleration);
//        System.out.println(newSpeed);
//        if(acceleration.x==0)speed.x*=0.9;
//        if (newSpeed.x > cap.x) newSpeed.x = cap.x;
//        if (newSpeed.y > cap.y) newSpeed.y = cap.y;
//        if (newSpeed.z > cap.z) newSpeed.z = cap.z;
        newSpeed.near0();
        speed = newSpeed;
    }

    public void getStarted() {
        double c = 1e4;
        speed.set(c, c, c);
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
