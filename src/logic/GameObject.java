package logic;

import application.ThreadMain;
import javafx.scene.CacheHint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import util.MediaData;
import util.Triple;


import static util.Util.*;

public class GameObject {
    protected ImageView imageView;
    protected Triple co;//coordinate
    protected Triple pos;
    protected double objectHeight;
    protected boolean onScreen;
    protected boolean respawnable;
    protected boolean destruct;
    protected GameObject triggeredObj;
    protected final static double DEFAULT_HEIGHT = toMetre(height);
    private final static double acceptableBorder = 0.8;
    private final static double minZ = 20;
    private final static int maxZ = height;

    public GameObject() {
        imageView = new ImageView(MediaData.getTree());
        imageView.setPreserveRatio(true);
        imageView.setCache(true);
        imageView.setCacheHint(CacheHint.SPEED);
        co = new Triple();
        pos = new Triple();
        objectHeight = height;
        respawnable = true;
        triggeredObj = null;
        destruct = false;
        spawn();
    }

    public void move(Triple v) {
        co = co.add(v);
        pos = coordinate2screenPos(co);
    }

    public void update() {
        if (respawnable && !onScreen) {
            spawn();
            deploy();
        }
        if (pos.z < minZ * acceptableBorder ||
                co.z < 0 ||
                pos.z > maxZ ||
                Math.abs((pos.x+getObjectWidth()/2)* acceptableBorder) > width + acceptableBorder)
            despawn();
    }

    public void deploy() {
        setOnScreen(true);
    }

    public void despawn() {
        setOnScreen(false);
        pos.set(0, -height * 2, 1);
        co = pos2coordinate(pos);
        if (!respawnable) destruct();
    }

    public void spawn() {
        switch (rand.nextInt(3)) {
            case 0 -> spawnLeft();
            case 1 -> spawnRight();
            case 2 -> spawnFront();
        }
    }

    private void spawnLeft() {
//        spawnRight();
//        co.x *= -1;
//        pos.x *= -1;
    }

    private void spawnRight() {
////        co.x = 0;
////        co.z = Math.random() * getMaxRealZ();
////        System.out.println(co);
////        pos = coordinate2screenPos(co);
//        pos.z = rand.randomBetween(minZ, maxZ);
//        pos.x = width;
//        co = pos2coordinate(pos);
//        System.out.println(pos);
    }

    private void spawnFront() {
        pos.set(rand.randomBetween(-width, width), 0, minZ);
        co = pos2coordinate(pos);
    }

    public void spawnAnywhere() {
        spawnAnywhereFromRealZ(rand.randomBetween(convertZ(maxZ)+metreToCoord(1), getMaxRealZ()));
    }

    public void spawnAnywhereFromRealZ(double z) {
        double w = getMaxRealWidthFromRealZ(z);
        co.set(rand.randomBetween(-w, w), 0, z);
        pos = coordinate2screenPos(co);
    }

    public void spawnAtCoord(Triple coord) {
        co = coord;
        pos = coordinate2screenPos(co);
    }

    public boolean shouldBeDestructed() {
        return destruct;
    }

    //https://pinetools.com/invert-image-colors
    //https://onlinepngtools.com/create-transparent-png
    public void setTriggeredObj(GameObject triggeredObj) {
        setRespawnable(false);
        this.triggeredObj = triggeredObj;
    }

    public void setImage(String url, double metre) {
        setImage(MediaData.getImage(url), metre);
    }

    public void setImage(Image image, double metre) {
        imageView.setImage(image);
        setObjectHeight(metre);
        imageView.setPreserveRatio(true);
    }

    public void setOnScreen(boolean onScreen) {
        if (onScreen) onAdd();
        else onRemove();
        this.onScreen = onScreen;
    }

    public void destruct() {
        destruct = true;
        onRemove();
    }

    public static double getMaxRealZ() {
        return k / minZ;
    }

    public static double getMaxRealWidthFromRealZ(double z) {
        return z * tanTheta;
    }

    public void setRespawnable(boolean respawnable) {
        this.respawnable = respawnable;
    }

    public GameObject getTriggeredObj() {
        return triggeredObj;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public boolean isOnScreen() {
        return onScreen;
    }

    public double getZ() {
        return pos.z;
    }

    public Triple getPos() {
        return pos;
    }

    @Override
    public String toString() {
        return "GameObject{" +
                "co=" + co +
                ", pos=" + pos +
                ", onScreen=" + onScreen +
                '}';
    }

    protected void onAdd() {
    }

    protected void onRemove() {
    }

    public double getObjectHeight() {
        return objectHeight * pos.z / height;
    }

    public double getObjectWidth() {
        return getObjectHeight() / 2;
    }

    public void setObjectHeight(double objectHeight) {
        if (objectHeight == 0) this.objectHeight = height;
        else this.objectHeight = toPixel(objectHeight);
    }
}
