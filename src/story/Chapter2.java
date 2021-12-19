package story;

import application.Main;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logic.GameObject;
import logic.QuestObject;
import util.GameMediaData;

import static util.Util.*;

public class Chapter2 extends BaseChapter {

    @Override
    public void setUp() {
        setUpper(GameMediaData.EVENING);
        QuestObject questObject = new QuestObject();
        questObject.setImage(GameMediaData.getRandomDecoration("tree"));
        questObject.spawnAnywhereFromRealZ(GameObject.getMaxRealZ() / 2);
        questObject.setConsumer((obj) -> {
            shutdown();
            Main.changeRoot(GameMediaData.setUpPaneFromString("You win!"));
            Thread thread = new Thread(() -> {
                delay((long) 3e3);
                Platform.runLater(() -> changeChapter(null));
            });
            thread.start();
        });
        setFinalQuestObject(questObject);
        ObservableList<GameObject> objectList = FXCollections.observableArrayList();
        int squidActiveRange = 10;
        QuestObject squid = new QuestObject();
        squid.setImage(GameMediaData.SQUID, 10);
        squid.setActiveRange(squidActiveRange);
        squid.setPassive(true);
        squid.setConsumer(obj -> {
            Thread thread = new Thread(() -> {
                for (double j = 0; obj.getPos().y<height; j += 0.001) {
                    delay(refreshPeriod);
                    if (obj.isOnScreen()) obj.setPosY(metreToCoord(j)/refreshPeriod);
                    else break;
                }
                obj.despawn();
            });
            thread.start();
        });
        for (int i = 0; i < 100; i++) {
            QuestObject newSquid = squid.clone();
            objectList.add(newSquid);
            addGameObject(newSquid);
        }
        GameObject.spreadObject(objectList, squid.getActiveRange());
        objectList.clear();
        for (int i = 0; i < 100; i++) {
            GameObject decorateObject = GameMediaData.getRandomDecoration("bush");
            objectList.add(decorateObject);
            addGameObject(decorateObject);
        }
        GameObject.spreadObject(objectList, 1);
    }
}
