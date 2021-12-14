package application;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logic.DecorateGameObject;
import logic.GameObject;

import java.util.Comparator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static util.Util.*;

public class ThreadMain {
    private final ObservableList<GameObject> objs = FXCollections.observableArrayList();
    private final ScheduledExecutorService executorService;
    private boolean pause = true;

    public ThreadMain() {
        setPause(true);
        executorService = Executors.newSingleThreadScheduledExecutor(r -> {
            final Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        });
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
        setPause(false);
        accelerate(0, 0.1);
        updateObj();
        accelerate(0, 0);
        executorService.scheduleAtFixedRate(this::updateObj, 0, (long) (1000 / (double) (frames)), TimeUnit.MILLISECONDS);
    }

    public void clear() {
        setPause(true);
        for (GameObject obj : objs) {
            obj.destruct();
            Platform.runLater(() -> Main.removeFromPane(obj.getImageView()));
        }
        objs.clear();
        setPause(false);
    }

    private void updateObj() {
        if (pause) return;
        player.update();
        for (GameObject obj : objs) {
            obj.update();
        }
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
