package application;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import logic.GameObject;
import logic.Player;
import util.Triple;

import java.util.concurrent.*;

import static util.MediaData.walkingSound;
import static util.Util.*;

public class ThreadMain {
    private final Player player;
    private double horizonLine = height / 2.0;
    private final ObservableList<GameObject> objects = FXCollections.observableArrayList();
    private boolean pause = true;
    private boolean start;
    private final Pane pane;
    private final ScheduledThreadPoolExecutor threadPoolExecutor;

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
        if (player.isMoving()) updateObjectsPos();
        updateScreen();
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
        Triple speed = player.getSpeed();
        horizonLine -= speed.y;
        speed.y = 0;
    }

    private void updateObjectsPos() {
        player.update();
        for (GameObject obj : objects) {
            obj.move(player.getSpeed());
            obj.update();
        }
    }

    private void updateScreen() {
        objects.sort(objComparator);
        for (GameObject obj : objects) {
            if (obj.shouldBeDestructed()) {
                Platform.runLater(() -> removeFromPane(obj.getImageView()));
                objects.remove(obj);
                if (obj.getTriggeredObj() != null) objects.add(obj.getTriggeredObj());
            } else {
                if (obj.isOnScreen()) Platform.runLater(() -> drawIMG(obj));
                else Platform.runLater(() -> removeFromPane(obj.getImageView()));
            }
        }
    }

    private void drawIMG(GameObject obj) {
        ImageView im = obj.getImageView();
        Triple pos = obj.getPos();
        removeFromPane(im);
        im.relocate((pos.x - pos.z + width) / 2, (height - horizonLine) + pos.z / 2 - obj.getObjectHeight()- pos.y);
        im.setFitHeight(obj.getObjectHeight());
        addToPane(im);
    }


    private void addToPane(ImageView imageview) {
        pane.getChildren().add(imageview);
    }

    private void removeFromPane(ImageView imageview) {
        pane.getChildren().remove(imageview);
    }

    public void setUpperBackground(Image image) {
        WritableImage newImage = new WritableImage(image.getPixelReader(), 0, 0, width, (int) Math.min(image.getHeight(), height - horizonLine));
        ImageView imageView = new ImageView(newImage);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(width);
        addToPane(imageView);
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

    @Override
    public String toString() {
        return "ThreadMain{" + "objs=" + objects + '}';
    }
}
