package logic;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.CacheHint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Comparator;

public class GameObject {
    //    Image image;
    ImageView imageView;
    int maxSize;
    Triple co;//coordinate
    Triple pos;
    boolean onScreen;
    boolean respawnable;
    boolean destruct;
    GameObject triggeredObj;
    Triple spawnRangeLeft, spawnRangeRight;
    static double k = 1;
    static CustomRandom rand = new CustomRandom();
    static double speed = 0.001 / Main.frames;
    final static int acceptableBorder = 10;
    final static double minZ = 20;
    private static final ObservableList<GameObject> objects = FXCollections.observableArrayList();
    private final static Comparator<GameObject> objComparator = Comparator.comparing(GameObject::getZ);
//    static final double fieldSize=100;
//    final static Triple leftCoord=new Triple(-fieldSize,(Main.height),-fieldSize);
//    final static Triple rightCoord=new Triple(fieldSize,(Main.height),fieldSize);

    public GameObject() {
//        image = new Image("file:res/image/Tree1.png");
//        imageView = new ImageView(image);
        imageView = new ImageView("file:res/image/Tree1.png");
        imageView.setPreserveRatio(true);
        imageView.setCache(true);
        imageView.setCacheHint(CacheHint.SPEED);
        maxSize = Main.height;
        co = new Triple();
        pos = new Triple();
        respawnable = true;
        triggeredObj = null;
        destruct = false;
        spawnRangeLeft = new Triple(0, (Main.height), 1);
        spawnRangeRight = new Triple(Main.width * 2, (Main.height), 1);
        spawn();
        objects.add(this);
    }

    private void move(Triple v) {
        if (v.equals(new Triple(0, 0, 0))) return;
        co = co.add(v);
        pos = coordinate2screenPos();//pos2coordinate();
        System.out.println(pos.x + " " + pos.z);
    }

    public void update() {
        if (respawnable && !onScreen) spawn();
        move(Main.getPlayerV().mul(speed));
        if (pos.getZ() < minZ / 2) despawn();
        else if (pos.getZ() > maxSize || pos.getX() + pos.getZ() < -Main.width - acceptableBorder || pos.getX() - pos.getZ() > Main.width + acceptableBorder)//edit this line if u spawn image with width>height
            despawn();
    }

    public void spawnAnywhere() {
        onScreen = true;
//        pos.set(rand.nextInt(Main.width * 2), (Main.height), rand.nextInt(Main.width / 10) + minZ);
        co = pos2coordinate();
//        co=Triple.randomBetween(leftCoord,rightCoord);//co.set(rand.nextDouble(Main.width * 2), (Main.height), rand.nextInt(maxSize)+1);
//        pos=coordinate2screenPos();
    }

    public void spawnAnywhereFromRealZ(double z) {
        onScreen = true;
        double w = getMaxRealWidthFromZ(z);
        co.set(rand.randomBetween(-w, w), (Main.height), z);
        pos = coordinate2screenPos();
//        co=Triple.randomBetween(leftCoord,rightCoord);//co.set(rand.nextDouble(Main.width * 2), (Main.height), rand.nextInt(maxSize)+1);
//        pos=coordinate2screenPos();
    }

    public void spawn() {
        onScreen = true;
//        spawnAnywhere();
        pos.set(rand.randomBetween(-Main.width, Main.width), (Main.height), minZ);
        co = pos2coordinate();
    }
//    public void spawnFront(){
//        pos.set(rand.nextInt(Main.width * 2), (Main.height), 40);
//    }
//    public void spawnLeft(){
//        double size=rand.nextDouble(maxSize)+1;
//        pos.set(rand.nextInt(Main.width * 2), (Main.height), 40);
//    }

    public void despawn() {
        System.out.println(co + "despawn" + pos);
        onScreen = false;
        pos.set(0, -Main.height * 2, 1);
        co = pos2coordinate();
        if (!respawnable) {
            destruct = true;
            objects.remove(this);
        }
    }

    public boolean shouldBeDestructed() {
        return destruct;
    }

    public int objIsOnLeftMiddleOrRight() {
        if (pos.getX() < 0) return -1;
        if (pos.getX() > 0) return 1;
        return 0;
    }

    private Triple pos2coordinate() {
        Triple realPos = new Triple();
        realPos.z = Math.sqrt(1 + Math.pow(Main.tanTheta * (pos.getX()) / Main.width, 2)) * k / pos.getZ();
        realPos.y = pos.getY();
        realPos.x = realPos.z * Main.tanTheta * (pos.getX()) / Main.width;
        return realPos;
    }

    private Triple coordinate2screenPos() {
        double w = Main.width;
        Triple screenPos = new Triple();
        screenPos.x = (co.x * w) / (co.z * Main.tanTheta);
        screenPos.y = co.y;
        screenPos.z = k / Math.sqrt(co.x * co.x + co.z * co.z);
        return screenPos;
    }

    public static double getMaxRealZ() {
        return k / minZ;
    }

    public static double getMaxRealWidthFromZ(double z) {
        return z * Main.tanTheta;
    }

    public static ObservableList<GameObject> getObjects() {
        objects.sort(objComparator);
        return objects;
    }

    public boolean isRespawnable() {
        return respawnable;
    }

    public void setRespawnable(boolean respawnable) {
        this.respawnable = respawnable;
    }

    public GameObject getTriggeredObj() {
        return triggeredObj;
    }

    public void setTriggeredObj(GameObject triggeredObj) {
        setRespawnable(false);
        this.triggeredObj = triggeredObj;
    }

    public ImageView getImageView() {
        return imageView;
    }

    //https://pinetools.com/invert-image-colors
    //https://onlinepngtools.com/create-transparent-png
    public void setImage(String url, double maxPercentSize) {
//        image = new Image(url);
//        imageView = new ImageView(image);
        imageView.setImage(new Image(url));
        imageView.setPreserveRatio(true);
        //maxSize = (int) (0.01 * maxPercentSize * Main.width);
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

    public Triple getPos() {
        return pos;
    }
}
