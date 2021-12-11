package application;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logic.TryObj;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadMain {
    private final ObservableList<TryObj> objs = FXCollections.observableArrayList();

    public ThreadMain() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::updateObj,0,50, TimeUnit.MILLISECONDS);
    }

    protected void create() {
        TryObj obj = new TryObj();
        objs.add(obj);
    }

    private void updateObj() {
        for (TryObj obj : objs) {
            obj.update();
            Platform.runLater(() -> Main.drawIMG(obj));
        }
    }
}
