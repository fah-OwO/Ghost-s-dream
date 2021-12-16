package logic;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.function.Consumer;

import static util.Util.*;

public class QuestObject extends GameObject implements Cloneable {
    private static final ObservableList<QuestObject> questObjs = FXCollections.observableArrayList();
    private Consumer<GameObject> consumer;
    private boolean passive;
    //for activate purpose so it must be on screen
    private boolean triggered;
    private double activeRange = metreToCoord(1.5);//private Triple activeRange = (new Triple(1, 0, 1)).mul(metreToCoord(1.5));

    public QuestObject() {
        super();
        passive = false;
        triggered = false;
        //if (url != null) super.setImage(url, DEFAULT_HEIGHT);
    }

    public static void run() {
        for (QuestObject obj : questObjs) {
            if (obj.isInActiveRangeAndNotTriggered()) {
                obj.individualRun();
                break;
            }
        }
    }

    public void setConsumer(Consumer<GameObject> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void update() {
        if (passive && isInActiveRangeAndNotTriggered()) individualRun();
        super.update();
    }

    @Override
    public void deploy() {
        super.deploy();
        triggered = false;
    }

    public boolean isInActiveRangeAndNotTriggered() {
        return getCoDistance(Player.getPlayerCo(), co) < activeRange && !triggered;
    }

    public void individualRun() {
        Platform.runLater(() -> consumer.accept(this));
        questObjs.remove(this);
        triggered = true;
    }

    @Override
    protected void onAdd() {
        super.onAdd();
        if (!questObjs.contains(this)) questObjs.add(this);
    }

    @Override
    protected void onRemove() {
        super.onRemove();
        questObjs.remove(this);
    }

    public void setPassive(boolean passive) {
        this.passive = passive;
    }

    public double getActiveRange() {
        return coordToMetre(activeRange);
    }

    public void setActiveRange(double metre) {
        activeRange = metreToCoord(metre);
    }

    @Override
    public QuestObject clone() {
        return (QuestObject) super.clone();
    }
}
