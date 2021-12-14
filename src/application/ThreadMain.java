package application;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logic.DecorateGameObject;
import logic.GameObject;

import java.util.concurrent.*;

import static util.Util.*;

public class ThreadMain {
    private final ObservableList<GameObject> objs = FXCollections.observableArrayList();
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

    private void updateSound() {
        double rate = player.isRunning() ? player.getRunningMul() : 1;
        if (!player.isMoving() || rate != walkingSound.getRate()) walkingSound.stop();
        else if (!walkingSound.isPlaying()) walkingSound.play();
        walkingSound.setRate(rate);

    }

    public void create() {
        create(new DecorateGameObject());
    }

    public void create(GameObject obj) {
        objs.add(obj);
    }

    public void start() {
        accelerate(0, 0.1);
        updateObjsPos();
        accelerate(0, 0);
        setPause(false);
    }

    public void clear() {
        setPause(true);
//        delay(refreshPeriod);
        for (GameObject obj : objs) {
            obj.destruct();
            Platform.runLater(() -> Main.removeFromPane(obj.getImageView()));
        }
        objs.clear();
    }

    private void updateObj() {
        if (pause) return;
        player.update();
        updateObjsPos();
        updateScreen();
    }


    private void updateObjsPos() {
        for (GameObject obj : objs) {
            obj.update();
        }
    }

    private void updateScreen() {
        objs.sort(objComparator);
        for (GameObject obj : objs) {
            if (obj.shouldBeDestructed()) {
                Platform.runLater(() -> Main.removeFromPane(obj.getImageView()));
                objs.remove(obj);
                if (obj.getTriggeredObj() != null) objs.add(obj.getTriggeredObj());
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
                "objs=" + objs +
                '}';
    }
}
