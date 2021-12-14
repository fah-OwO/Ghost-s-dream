package logic;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.CacheHint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import util.CustomRandom;
import util.Triple;

import java.util.Comparator;
import static util.Util.*;

public class GameObject {
    ImageView imageView;
    int maxSize;
    Triple co;//coordinate
    Triple pos;
    boolean onScreen;
    boolean respawnable;
    boolean destruct;
    GameObject triggeredObj;
    private final static int acceptableBorder = 10;
    private final static double minZ = 20;
    private static final CustomRandom rand = new CustomRandom();
    private static final ObservableList<GameObject> objects = FXCollections.observableArrayList();
    private final static Comparator<GameObject> objComparator = Comparator.comparing(GameObject::getZ);

    public GameObject() {
        imageView = new ImageView("file:res/image/Tree1.png");
        imageView.setPreserveRatio(true);
        imageView.setCache(true);
        imageView.setCacheHint(CacheHint.SPEED);
        maxSize = height;
        co = new Triple();
        pos = new Triple();
        respawnable = true;
        triggeredObj = null;
        destruct = false;
        spawn();
        objects.add(this);
    }

    private void move(Triple v) {
        if (v.equals(new Triple(0, 0, 0))) return;
        co = co.add(v);
        pos = coordinate2screenPos(co);
    }

    public void update() {
        if (respawnable && !onScreen) spawn();
        move(getPlayerV());
        if (pos.getZ() < minZ / 2 ||
                pos.getZ() > maxSize ||
                pos.getX() + pos.getZ() < -width - acceptableBorder ||
                pos.getX() - pos.getZ() > width + acceptableBorder)//edit this line if u spawn image with width>height
            despawn();
    }

    public void spawnAnywhere() {
        onScreen = true;
        co = pos2coordinate(pos);
    }

    public void spawnAnywhereFromRealZ(double z) {
        onScreen = true;
        double w = getMaxRealWidthFromZ(z);
        co.set(rand.randomBetween(-w, w), 0, z);
        pos = coordinate2screenPos(co);
    }

    public void spawnAnywhereFromPosZ(double z) {
        pos.set(0, 0, z);
        z = pos2coordinate(pos).z;
        double w = getMaxRealWidthFromZ(z);
        co.set(rand.randomBetween(-w, w), 0, z);
    }

    public void spawn() {
        onScreen = true;
        pos.set(rand.randomBetween(-width, width), 0, minZ);
        co = pos2coordinate(pos);
    }

    public void despawn() {
        onScreen = false;
        pos.set(0, -height * 2, 1);
        co = pos2coordinate(pos);
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


    protected void onRemove() {
        objects.remove(this);
    }

    public Triple getCoIRL() {
        return co.mul(metrePerPixels);
    }

    public double getHeightIRL() {
        return pos.z * metrePerPixels;
    }

    public static double getMaxRealZ() {
        return k / minZ;
    }

    public static double getMaxRealWidthFromZ(double z) {
        return z * tanTheta;
    }

    public static ObservableList<GameObject> getObjects() {
        objects.sort(objComparator);
        return objects;
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
    public void setImage(String url, double metre) {
        imageView.setImage(new Image(url));
        maxSize = (int) (metre / metrePerPixels);
        imageView.setPreserveRatio(true);
    }

    public boolean isOnScreen() {
        return onScreen;
    }

    public double getZ() {
        return pos.getZ();
    }

    public Triple getPos() {
        return pos;
    }

    public void destruct(){
        destruct=true;
        onRemove();
    }
}
