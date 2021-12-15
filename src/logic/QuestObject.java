package logic;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.MediaData;
import util.Triple;

import java.util.function.Consumer;

import static util.Util.*;

public class QuestObject extends GameObject {
    private Consumer<GameObject> consumer;
    private boolean passive;
    private static final ObservableList<QuestObject> questObjs = FXCollections.observableArrayList();
    //for activate purpose so it must be on screen
    private Triple activeRange = (new Triple(1, 0, 1)).mul(metreToCoord(1.5));

    public QuestObject(String url) {
        super();
        passive = false;
        if(url!=null)super.setImage(url,DEFAULT_HEIGHT);
    }

    public void setConsumer(Consumer<GameObject> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void update() {
        super.update();
        if (passive && isInActiveRange()) individualRun();
    }


    public static void run() {
        for (QuestObject obj : questObjs) {
            if (obj.isInActiveRange()) {
                obj.individualRun();
                break;
            }
        }
    }

    public boolean isInActiveRange() {
        return co.isBetween(activeRange.mul(-1), activeRange);
    }

    public void individualRun() {
        Platform.runLater(()->consumer.accept(this));
        questObjs.remove(this);
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

    public void setActiveRange(double metre) {
        activeRange = new Triple(1, 0, 1);
        activeRange = activeRange.mul(metreToCoord(metre));
    }

    public static QuestObject createTrap(){
        QuestObject trap=new QuestObject(null);
        trap.setImage(MediaData.TRAP, 0.2);//trap.setObjectHeight(0.2);
        trap.setPassive(true);
        trap.setActiveRange(0.5);
        trap.spawnAnywhere();
        return trap;
    }
}
