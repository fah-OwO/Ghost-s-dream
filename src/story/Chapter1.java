package story;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logic.GameObject;
import logic.QuestObject;
import util.GameMediaData;

import static util.Util.*;

public class Chapter1 extends BaseChapter {

    @Override
    public void setUp() {
        setUpper(GameMediaData.EVENING);
        QuestObject questObject = new QuestObject();
        questObject.setImage(GameMediaData.getRandomDecoration("tree"));
        questObject.spawnAnywhereFromRealZ(GameObject.getMaxRealZ() / 2);
        questObject.setConsumer((obj) -> changeChapter(new Chapter2()));
        setFinalQuestObject(questObject);
        ObservableList<GameObject> objectList = FXCollections.observableArrayList();
        int squidActiveRange = 10;
        QuestObject squid = new QuestObject();
        squid.setImage(GameMediaData.SQUID, 10);
        squid.setActiveRange(squidActiveRange);
        squid.setPassive(true);
        squid.setConsumer(obj -> {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < height * 2; j += 10) {
                    delay(refreshPeriod);
                    if (obj.isOnScreen()) obj.setPosY(j);
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
