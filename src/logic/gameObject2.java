package logic;

import application.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class gameObject {
    Image image;
    ImageView imageView;
    int maxSize;
    Triple pos;//pos is coordinate on screen
    boolean onScreen;
    boolean respawnable;
    boolean destruct;
    gameObject triggeredObj;
    Triple spawnRangeLeft, spawnRangeRight;
    static double k = 1;
    static Random rand = new Random();
    static double speed = 0.0001/Main.frames;
    //static Triple speed = new Triple(10, 10, 7);//z is in percent
    final static int acceptableBorder = 10;

    public gameObject() {
        image = new Image("file:res/image/Tree1.png");
        imageView = new ImageView(image);
        maxSize = Main.height;
        pos = new Triple();
        respawnable = true;
        triggeredObj = null;
        destruct = false;
        spawnRangeLeft = new Triple(0, (Main.height), 1);
        spawnRangeRight = new Triple(Main.width * 2, (Main.height), 1);
        spawn();
    }

    private void move(Triple v) {
        if (v.equals(new Triple(0, 0, 0))) return;
        Triple realPos = getRealPos();//new Triple(getRealX(), getY(), getRealZ());
        realPos=realPos.mul(1/(1+Math.pow(realPos.x/realPos.z,2)));//realPos = realPos.add(new Triple(-realPos.x*realPos.x/realPos.z/10,0,-realPos.x/10));
        realPos = realPos.add(v);
//        System.out.println(pos);
        pos.set(getXFromReal(realPos.x, realPos.z), realPos.y, getZFromReal(realPos.x, realPos.z));//idk y it's this num
        //pos.set(v.x!=0?getXFromReal(realPos.x,realPos.z):pos.x,pos.y,v.z!=0?getZFromReal(realPos.x,realPos.z):pos.z);
//        System.out.println(pos);

    }

    public void update() {
        if (respawnable && !onScreen) spawn();
        move(Main.getPlayerV().mul(speed));
//        pos = pos.add(Main.getPlayerV().mul(speed));
        if (pos.getZ() <= 0) despawn();//pos.setZ(1);
        if (imageView.getFitHeight() > maxSize || pos.getX() + imageView.getFitWidth() < -acceptableBorder || pos.getX() - imageView.getFitWidth() > Main.width * 2 + acceptableBorder)
            despawn();
    }

    public void spawn() {
        onScreen = true;
//        pos.randomBetween(spawnRangeLeft, spawnRangeRight);
        pos.set(rand.nextInt(Main.width * 2), (Main.height), 100);
        //TODO: if walk left/right spawn smt
    }

    public void despawn() {
        onScreen = false;
        pos.set(-1, -1, 1);
        if (!respawnable) destruct = true;
    }

    public boolean shouldBeDestructed() {
        return destruct;
    }

    public int objIsOnLeftMiddleOrRight() {
        if (pos.getX() < Main.width) return -1;
        if (pos.getX() > Main.width) return 1;
        return 0;//if (pos.getX() == Main.width)
    }

    //private double sq(double x){return Math.pow(x,2)}
    private double getRealZ() {
        return k / pos.getZ() * Math.sqrt(1 + Math.pow(Main.tanTheta / Main.width * (pos.getX() - Main.width), 2));
    }

    private Triple getRealPos() {
        Triple realPos = new Triple();
        realPos.z = Math.sqrt(1 + Math.pow(Main.tanTheta * (pos.getX() - Main.width) / Main.width, 2))*k/pos.getZ();
        realPos.y = pos.getY();
        realPos.x = realPos.z * Main.tanTheta * (pos.getX() - Main.width) / Main.width;
        return realPos;
    }

//    private double getRealX() {
//        return getRealX() / Main.tanTheta * Main.width / (Main.width - pos.getX());
//    }

    private double getXFromReal(double x, double z) {
        double w = Main.width;
        return w+(x * w) / (z * Main.tanTheta);
    }

    private double getZFromReal(double x, double z) {
        return k/Math.sqrt(x * x + z * z) ;
    }

    /*public Triple getSpawnRangeLeft() {
        return spawnRangeLeft;
    }

    public void setSpawnRangeLeft(Triple spawnRangeLeft) {
        this.spawnRangeLeft = spawnRangeLeft;
    }

    public void setSpawnRangeLeft(int x, int y, int z) {
        this.spawnRangeLeft.set(x, y, z);
    }

    public Triple getSpawnRangeRight() {
        return spawnRangeRight;
    }

    public void setSpawnRangeRight(Triple spawnRangeRight) {
        this.spawnRangeRight = spawnRangeRight;
    }

    public void setSpawnRangeRight(int x, int y, int z) {
        this.spawnRangeRight.set(x, y, z);
    }*/

    public boolean isRespawnable() {
        return respawnable;
    }

    public void setRespawnable(boolean respawnable) {
        this.respawnable = respawnable;
    }

    public gameObject getTriggeredObj() {
        return triggeredObj;
    }

    public void setTriggeredObj(gameObject triggeredObj) {
        setRespawnable(false);
        this.triggeredObj = triggeredObj;
    }

    public ImageView getImageView() {
        return imageView;
    }

    //https://pinetools.com/invert-image-colors
    //https://onlinepngtools.com/create-transparent-png
    public void setImage(String url, double maxPercentSize) {
        image = new Image(url);
        imageView = new ImageView(image);
        maxSize = (int) (0.01 * maxPercentSize * Main.width);
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public boolean isOnScreen() {
        return onScreen;
    }

    public double getX() {
        return pos.getX();
    }

    public void setX(int x) {
        pos.setX(x);
    }

    public double getY() {
        return pos.getY();
    }

    public void setY(int y) {
        pos.setY(y);
    }

    public double getZ() {
        return pos.getZ();
    }

    public void setZ(int z) {
        pos.setZ(z);
    }
}
