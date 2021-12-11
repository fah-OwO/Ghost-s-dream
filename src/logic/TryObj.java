package logic;

import application.Main;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.Random;

public class TryObj {
    Image image = new Image("file:res/image/mystic.jpg");
    ImageView imageView;
    Triple pos;//=new Triple();
    boolean onScreen;
    Random rand = new Random();
    Triple speed = new Triple(10, 10, 7);//z is in percent
    final static int nextX = 10;
    final static int acceptableBorder = 10;

    public TryObj() {
        imageView = new ImageView(image);
//        imageView.relocate((double) (Main.width) / 2, (double) (Main.height) / 2);//imageView.setLayoutY(100);
        pos = new Triple(0, 0, 1);
        spawn();
        onScreen = true;
    }

    public void update() {
        if (!onScreen) spawn();
        pos = pos.add(speed.mul(Main.getPlayerV()));
        if (pos.getZ() <= 0) pos.setZ(1);
        if (pos.getZ() > Main.height || pos.getX() + pos.getZ() < -acceptableBorder || pos.getX() - pos.getZ() > Main.width + acceptableBorder)
            despawn();
    }

    public void spawn() {
        onScreen = true;
        pos.set(rand.nextInt(Main.width), (Main.height) / 2, 1);
    }

    public void despawn() {
        onScreen = false;
        pos.set(-1, -1, 1);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public boolean isOnScreen() {
        return onScreen;
    }

    public int getX() {
        return pos.getX();
    }

    public void setX(int x) {
        pos.setX(x);
    }

    public int getY() {
        return pos.getY();
    }

    public void setY(int y) {
        pos.setY(y);
    }

    public int getZ() {
        return pos.getZ();
    }

    public void setZ(int z) {
        pos.setZ(z);
    }
}
