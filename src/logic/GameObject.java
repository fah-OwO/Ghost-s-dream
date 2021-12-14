package logic;

import javafx.collections.ObservableList;
import javafx.scene.CacheHint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import util.Triple;


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
    }

    private void move(Triple v) {
        co = co.add(v);
        pos = coordinate2screenPos(co);
    }

    public void update() {
        if (respawnable && !onScreen) {
            spawn();
            deploy();
        }
        if(player.isMoving())move(player.getSpeed());
        if (pos.getZ() < minZ / 2 ||
                pos.getZ() > maxSize ||
                pos.getX() + pos.getZ() < -width - acceptableBorder ||
                pos.getX() - pos.getZ() > width + acceptableBorder)//edit this line if u spawn image with width>height
            despawn();
    }

    public void spawnAtCoord(Triple coord) {
        co=coord;
        pos = coordinate2screenPos(co);
    }
    public void spawnAnywhere(){
        spawnAnywhereFromRealZ(rand.randomBetween(1,getMaxRealZ()));
    }
    public void spawnAnywhereFromRealZ(double z) {
        double w = getMaxRealWidthFromRealZ(z);
        co.set(rand.randomBetween(-w, w), 0, z);
        pos = coordinate2screenPos(co);
    }


    public void spawn() {
        pos.set(rand.randomBetween(-width, width), 0, minZ);
        co = pos2coordinate(pos);
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

    public boolean shouldBeDestructed() {
        return destruct;
    }


    protected void onAdd() {
        //if (!objects.contains(this)) objects.add(this);
    }

    protected void onRemove() {
        //objects.remove(this);
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
    public void setImage(Image image, double metre) {
        imageView.setImage(image);
        maxSize = (int) (metre / metrePerPixels);
        imageView.setPreserveRatio(true);
    }

    public void setOnScreen(boolean onScreen) {
        if (onScreen) onAdd();
        else onRemove();
        this.onScreen = onScreen;
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

    public void destruct() {
        destruct = true;
        onRemove();
    }

    @Override
    public String toString() {
        return "GameObject{" +
                "co=" + co +
                ", pos=" + pos +
                ", onScreen=" + onScreen +
                '}';
    }
}
