package application;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logic.GameObject;

import java.util.Comparator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadMain {
    private final Comparator<GameObject> objComparator = Comparator.comparing(GameObject::getZ);
    private final ObservableList<GameObject> objs = FXCollections.observableArrayList();
    private final ScheduledExecutorService executorService;

    public ThreadMain() {
        executorService = Executors.newSingleThreadScheduledExecutor(r -> {
            final Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        });
        //https://stackoverflow.com/questions/14897194/stop-threads-before-close-my-javafx-program
        //https://stackoverflow.com/questions/50296223/executorservice-with-daemon-threads-explicit-shutdown
    }

    protected void create() {
        create(new GameObject());
    }

    protected void create(GameObject obj) {
        objs.add(obj);
    }

    public void start() {
        Main.accelerate(0.01, 0);
        for (GameObject obj : objs)
            Main.addToPane(obj.getImageView());
        updateObj();
        Main.accelerate(0, 0);
        executorService.scheduleAtFixedRate(this::updateObj, 0, (long) (1000 / (double) (Main.frames)), TimeUnit.MILLISECONDS);
    }

    private void updateObj() {

        Main.player.update();
        for (GameObject obj : objs) {
            obj.update();
        }
        objs.sort(objComparator);
        for (GameObject obj : objs) {
            if (obj.shouldBeDestructed()) {
                Platform.runLater(() -> Main.removeFromPane(obj.getImageView()));
                objs.remove(obj);
                if (obj.getTriggeredObj() != null) objs.add(obj.getTriggeredObj());
            } else
                Platform.runLater(() -> Main.drawIMG(obj));
        }
    }
}
