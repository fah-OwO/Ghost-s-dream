package application;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logic.DecorateGameObject;
import logic.GameObject;
import logic.Player;

import java.util.concurrent.*;

import static util.Util.*;

public class ThreadMain {
    private final ObservableList<GameObject> objects = FXCollections.observableArrayList();
    private boolean pause = true;

    public ThreadMain() {
        setPause(true);
        ScheduledThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(4, r -> {
            Thread thread = Executors.defaultThreadFactory().newThread(r);
            thread.setDaemon(true);
            return thread;
        });
        long refreshPeriod = (1000 / (frames));
        threadPoolExecutor.scheduleAtFixedRate(this::updateObj, 0, refreshPeriod, TimeUnit.MILLISECONDS);
        threadPoolExecutor.scheduleAtFixedRate(this::updateSound, 0, refreshPeriod, TimeUnit.MILLISECONDS);
        //https://stackoverflow.com/questions/13883293/turning-an-executorservice-to-daemon-in-java
    }

    public void create(GameObject obj) {
        objects.add(obj);
    }

    public void start() {
        accelerate(0, 0.1);
        updateObjectsPos();
        accelerate(0, 0);
        setPause(false);
    }

    public void clear() {
        setPause(true);
        for (GameObject obj : objects) {
            obj.destruct();
            Platform.runLater(() -> Main.removeFromPane(obj.getImageView()));
        }
        objects.clear();
    }

    private void updateObj() {
        if (pause) return;
        player.update();
        updateObjectsPos();
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

    private void updateObjectsPos() {
        for (GameObject obj : objects) obj.update();
    }

    private void updateScreen() {
        objects.sort(objComparator);
        for (GameObject obj : objects) {
            if (obj.shouldBeDestructed()) {
                Platform.runLater(() -> Main.removeFromPane(obj.getImageView()));
                objects.remove(obj);
                if (obj.getTriggeredObj() != null) objects.add(obj.getTriggeredObj());
            } else {
                if (obj.isOnScreen()) Platform.runLater(() -> Main.drawIMG(obj));
                else Platform.runLater(() -> Main.removeFromPane(obj.getImageView()));
            }
        }
    }

    public void setPause(boolean p) {
        pause = p;
    }

    @Override
    public String toString() {
        return "ThreadMain{" +
                "objs=" + objects +
                '}';
    }
}
