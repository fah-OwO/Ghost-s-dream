package story;

import application.Main;
import javafx.application.Platform;
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
        QuestObject squid = new QuestObject();
        squid.setImage(GameMediaData.SQUID, 10);
        squid.setActiveRange(10);
        squid.setPassive(true);
        squid.setConsumer(obj -> {
            Thread thread = new Thread(() -> {
                for (double j = 0.0001; obj.getPos().y<height; j *=1.1) {
                    delay(refreshPeriod);
                    obj.checkForDespawn();
                    if(obj.isOnScreen())obj.setPosY(metreToCoord(j)/refreshPeriod);
                    else break;
                }
                obj.despawn();
            });
            thread.setDaemon(true);
            thread.start();
        });
        for (int i = 0; i < 100; i++) {
            QuestObject newSquid = squid.clone();
            objectList.add(newSquid);
            addGameObject(newSquid);
        }
        GameObject.spreadObject(objectList, squid.getActiveRange());
        objectList.clear();
    }
}
