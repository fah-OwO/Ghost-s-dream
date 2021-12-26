package logic;

import javafx.scene.CacheHint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import util.GameMediaData;
import util.Triple;

import static util.Util.*;

public class GameObject implements Cloneable {
    private final static double acceptableBorder = 1.01;
    private final static double minZ = 20;
    private final static int maxZ = height;
    protected ImageView imageView;
    protected Triple co;//coordinate
    protected Triple pos;
    protected double objectHeight;
    protected boolean onScreen;
    protected boolean respawnable;
    protected boolean destruct;
    private double minSpawningRange = metreToCoord(1);

    public GameObject() {
        imageView = new ImageView(GameMediaData.BLACK);
        imageView.setPreserveRatio(true);
        imageView.setCache(true);
        imageView.setCacheHint(CacheHint.SPEED);
        co = new Triple();
        pos = new Triple();
        objectHeight = height;
        respawnable = true;
        destruct = false;
        spawn();
    }

    public static double getMaxRealZ() {
        return convertZ(minZ) / cosTheta;
    }


    public void checkForSpawn(double x) {
        if (respawnable && !onScreen) {
            spawn();
            pos.x -= rand.randomBetween(0, x);
            co = pos2coordinate(pos);
            deploy();
        }
    }

    public void move(Triple v) {
        co = co.add(v);
        pos = coordinate2screenPos(co);
    }

    public void checkForDespawn() {
        if (co.z > getMaxRealZ() ||
                pos.z > maxZ * acceptableBorder ||
                co.z < 0 ||
                Math.abs(pos.x) * 2 - getObjectWidth() > width * acceptableBorder)
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
            case 0 -> spawnFront();
            case 1 -> spawnLeft();
            case 2 -> spawnRight();
        }
    }

    private void spawnLeft() {
        Triple tmp = new Triple(0, 0, rand.randomBetween(minSpawningRange, getMaxRealZ()));
        pos = coordinate2screenPos(tmp);
        pos.x = -(width + getObjectWidth()) / 2;
        co = pos2coordinate(pos);
    }

    private void spawnRight() {
        Triple tmp = new Triple(0, 0, rand.randomBetween(minSpawningRange, getMaxRealZ()));
        pos = coordinate2screenPos(tmp);
        pos.x = (width + getObjectWidth()) / 2;
        co = pos2coordinate(pos);
    }

    private void spawnFront() {
        double tmp = getMaxRealWidthFromRealZ(getMaxRealZ());
        co.set(rand.randomBetween(-tmp, tmp), 0, getMaxRealZ());
        pos = coordinate2screenPos(co);
    }

    public void spawnAnywhere() {
        spawnAnywhereFromRealZ(rand.randomBetween(convertZ(maxZ) + minSpawningRange, getMaxRealZ()));
    }

    public void spawnAnywhereFromRealZ(double z) {
        double w = getMaxRealWidthFromRealZ(z) / 2;
        co.set(rand.randomBetween(-w, w), 0, z);
        pos = coordinate2screenPos(co);
    }

    public void spawnAnywhereFromPosZ(double z) {
        pos.set(rand.randomBetween(-width / 2.0, width / 2.0), 0, z);
        co = pos2coordinate(pos);
    }

    public void spawnAtCoord(Triple coord) {
        co = coord;
        pos = coordinate2screenPos(co);
    }

    public boolean shouldBeDestructed() {
        return destruct;
    }

    public void setImage(String url, double metre) {
        setImage(GameMediaData.getImage(url), metre);
    }

    public void setImage(Image image, double metre) {
        setImage(image);
        setObjectHeight(metre);
    }

    public void setImage(Image image) {
        if (image != null)
            imageView.setImage(image);
        imageView.setPreserveRatio(true);
    }


    public void setImage(GameObject gameObject) {
        if (gameObject == null) return;
        setObjectHeight(coordToMetre(gameObject.objectHeight));
        imageView.setImage(gameObject.getImageView().getImage());
        imageView.setPreserveRatio(true);
    }

    public void destruct() {
        destruct = true;
        onRemove();
    }

    public ImageView getImageView() {
        return imageView;
    }

    public boolean isOnScreen() {
        return onScreen;
    }

    public void setOnScreen(boolean onScreen) {
        if (this.onScreen == onScreen) return;
        if (onScreen) onAdd();
        else onRemove();
        this.onScreen = onScreen;
    }

    public double getZ() {
        return pos.z;
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

    private double getObjectHeight() {
        return objectHeight * pos.z / height;
    }

    public void setObjectHeight(double objectHeight) {
        if (objectHeight == 0) this.objectHeight = height;
        else this.objectHeight = toPixel(objectHeight);
    }

    private double getObjectWidth() {
        return getObjectHeight() * getImageRatioWpH();
    }


    public void setCo(Triple triple) {
        co = triple;
        pos = coordinate2screenPos(co);
    }

    public void setPosY(double y) {
        co.y = y;
    }

    @Override
    public GameObject clone() {
        try {
            GameObject clone = (GameObject) super.clone();
            clone.imageView = new ImageView(imageView.getImage());
            clone.co = co.clone();
            clone.pos = pos.clone();
            clone.objectHeight = objectHeight;
            clone.onScreen = onScreen;
            clone.destruct = destruct;
            clone.respawnable = respawnable;
            clone.imageView.setPreserveRatio(true);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public void draw(double horizonLineMul) {
        imageView.relocate(pos.x + (-getObjectWidth() + width) / 2, height * (horizonLineMul) + pos.z / 2 - getObjectHeight() - pos.y);
        imageView.setFitHeight(getObjectHeight());
    }

    public void setMinSpawningRange(double minSpawningRangeInMetre) {
        this.minSpawningRange = metreToCoord(minSpawningRangeInMetre);
    }

    public Image getImage() {
        return getImageView().getImage();
    }

    private double getImageRatioWpH() {
        return getImage().getWidth() / getImage().getHeight();
    }

    public Triple getPos() {
        return pos;
    }

    public void rotate(double x) {
        pos.x += x;
        co = pos2coordinate(pos);
    }
}
