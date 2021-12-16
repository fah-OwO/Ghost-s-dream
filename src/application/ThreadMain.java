package application;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import logic.GameObject;
import logic.Player;

import java.util.concurrent.*;

import static util.GameMediaData.BLACK;
import static util.GameMediaData.walkingSound;
import static util.Util.*;

public class ThreadMain {
    private final Player player;
    private double horizonLineMul = 1 / 2.0;
    private final ObservableList<GameObject> objects = FXCollections.observableArrayList();
    private boolean pause = true;
    private boolean start;
    private final Pane pane;
    private final ScheduledThreadPoolExecutor threadPoolExecutor;
    private final ImageView ground = new ImageView();

    public ThreadMain(Pane pane, Player player) {
        setPause(true);
        start = false;
        this.pane = pane;
        this.player = player;
        threadPoolExecutor = new ScheduledThreadPoolExecutor(4, r -> {
            Thread thread = Executors.defaultThreadFactory().newThread(r);
            thread.setDaemon(true);
            return thread;
        });
        setGroundFromImage(BLACK);//this.ground=new ImageView(BLACK);
        //https://stackoverflow.com/questions/13883293/turning-an-executorservice-to-daemon-in-java
    }

    public void create(GameObject obj) {
        objects.add(obj);
    }

    public void start() {
        if (!start) {
            threadPoolExecutor.scheduleAtFixedRate(this::updateObj, 0, refreshPeriod, TimeUnit.MILLISECONDS);
            threadPoolExecutor.scheduleAtFixedRate(this::updateSound, 0, refreshPeriod, TimeUnit.MILLISECONDS);
            start = true;
        }
        updateObjectsPos();
        setPause(false);
    }

    public void clear() {
        setPause(true);
        for (GameObject obj : objects) {
            obj.destruct();
            Platform.runLater(() -> removeFromPane(obj.getImageView()));
        }
        objects.clear();
        pane.getChildren().clear();
    }

    private void updateObj() {
        if (pause) return;
        updatePlayer();
        horizonLineMul = 1 - (player.getRotate().y / height);
        //if (player.isMoving())
        updateObjectsPos();
        Platform.runLater(this::updateScreen);//updateScreen();
    }

    private void updateSound() {
        if (pause) {
            walkingSound.stop();
            return;
        }
        double rate = player.isRunning() ? Player.getRunningMul() : 1;
        if (!player.isMoving() || rate != walkingSound.getRate()) walkingSound.stop();
        else if (!walkingSound.isPlaying()) walkingSound.play();
        walkingSound.setRate(rate);
    }

    private void updatePlayer() {
        player.update();
    }

    private void updateObjectsPos() {
        player.update();
        for (GameObject obj : objects) {
            obj.move(player.getSpeed());
            obj.update();
        }
    }

    private void updateScreen() {
        drawGround();
        objects.sort(objComparator);
        for (GameObject obj : objects) {
            if (obj.shouldBeDestructed()) {
                removeFromPane(obj.getImageView());
                objects.remove(obj);
            } else if (obj.isOnScreen()) drawIMG(obj);
            else removeFromPane(obj.getImageView());
        }
    }

    private void drawGround() {
        removeFromPane(ground);
        ground.relocate(0, height * horizonLineMul);
        addToPane(ground);
    }

    private void drawIMG(GameObject obj) {
        obj.draw(pane, horizonLineMul);
    }


    private void addToPane(ImageView imageview) {
        pane.getChildren().add(imageview);
    }

    private void removeFromPane(ImageView imageview) {
        pane.getChildren().remove(imageview);
    }

    public void setUpperBackground(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(width);
        addToPane(imageView);
    }

    public void setGroundFromImage(Image image) {
        ground.setImage(image);
        ground.setPreserveRatio(true);
        ground.setFitWidth(width);
//        removeFromPane(this.ground);
    }

    public void shutdown() {
        setPause(true);
        threadPoolExecutor.shutdown();
        updateObj();
        updateSound();
        clear();
    }

    public void setPause(boolean p) {
        pause = p;
    }

    public double getHorizonLineMul() {
        return horizonLineMul;
    }

    public void setHorizonLineMul(double horizonLineMul) {
        this.horizonLineMul = horizonLineMul;
    }

    @Override
    public String toString() {
        return "ThreadMain{" + "objs=" + objects + '}';
    }
}
