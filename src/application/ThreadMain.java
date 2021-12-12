package application;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logic.gameObject;

import java.util.Comparator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadMain {
    private final Comparator<gameObject> objComparator = Comparator.comparing(gameObject::getZ);
    private final ObservableList<gameObject> objs = FXCollections.observableArrayList();

    public ThreadMain() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::updateObj, 0, 50, TimeUnit.MILLISECONDS);
    }

    protected void create() {
        create(new gameObject());
    }

    protected void create(gameObject obj) {
        objs.add(obj);
        objs.sort(objComparator);
    }

    private void updateObj() {

        //objs.sort(objComparator);
        //SortedList<TryObj> SortedObjs = new SortedList<>(objs,objComparator);
        for (gameObject obj : objs) {
            obj.update();
            Platform.runLater(() -> Main.drawIMG(obj));
            if (obj.shouldBeDestructed()) {
                Main.removeFromPane(obj.getImageView());
                objs.remove(obj);
                if (obj.getTriggeredObj() != null) objs.add(obj.getTriggeredObj());
            }
        }
    }
}
