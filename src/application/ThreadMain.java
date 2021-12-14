package application;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logic.DecorateGameObject;
import logic.GameObject;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static util.Util.*;

public class ThreadMain {
    private final ObservableList<GameObject> objs = FXCollections.observableArrayList();
    private boolean pause = true;

    public ThreadMain() {
        setPause(true);
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(r -> {
            final Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        });
        long refreshPeriod = (1000 / (frames));
        executorService.scheduleAtFixedRate(this::updateObj, 0, refreshPeriod, TimeUnit.MILLISECONDS);
        //https://stackoverflow.com/questions/14897194/stop-threads-before-close-my-javafx-program
        //https://stackoverflow.com/questions/50296223/executorservice-with-daemon-threads-explicit-shutdown
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
        updateObjsPos();
        updateScreen();
    }

    private void updateObjsPos() {
        player.update();
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
