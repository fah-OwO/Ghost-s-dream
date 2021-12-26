package logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.function.Consumer;

import static util.Util.*;

public class QuestObject extends GameObject implements Cloneable {
    private static final ObservableList<QuestObject> questObjs = FXCollections.observableArrayList();
    //for activate purpose so it must be on screen
    private Consumer<QuestObject> consumer;
    private boolean passive;
    private boolean triggered;
    private double activeRange = metreToCoord(1.5);

    public QuestObject() {
        super();
        passive = false;
        triggered = false;
    }

    public static void run() {
        for (QuestObject obj : questObjs)
            if (obj.isInActiveRangeAndNotTriggered()) {
                obj.individualRun();
                break;
            }
    }

    public void setConsumer(Consumer<QuestObject> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void checkForDespawn() {
        if (passive && isInActiveRangeAndNotTriggered()) individualRun();
        super.checkForDespawn();
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
        questObjs.remove(this);
        triggered = true;
        Thread thread = new Thread(() -> consumer.accept(this));
        thread.setDaemon(true);
        thread.start();
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
        setMinSpawningRange(activeRange);
    }

    public boolean isTriggered() {
        return triggered;
    }

    @Override
    public QuestObject clone() {
        return (QuestObject) super.clone();
    }
}
