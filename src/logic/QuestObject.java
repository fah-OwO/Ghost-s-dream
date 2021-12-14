package logic;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.Triple;

public class QuestObject extends GameObject {
    private Runnable runnable;
    private static final ObservableList<QuestObject> questObjs = FXCollections.observableArrayList();
    private static Triple activeRange = new Triple(1, 0, 1);

    public QuestObject(String url) {
        super();
        super.setImage(url, 50);
        questObjs.add(this);
    }


    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public static void setActiveRange(double range) {
        activeRange = new Triple(1, 0, 1);
        activeRange = activeRange.mul(range);
    }

    public Triple getActiveRange() {
        return activeRange;
    }

    public static void run() {
        for (QuestObject obj : questObjs) {
            System.out.println(obj.co);
            if (obj.co.isBetween(activeRange.mul(-1), activeRange)) {
                Platform.runLater(obj.runnable);
                questObjs.remove(obj);
                break;
            }
        }
    }

    @Override
    protected void onRemove() {
        super.onRemove();
        questObjs.remove(this);
    }

}
