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
        questObject.setImage(GameMediaData.SQUID, 4);
        questObject.spawnAnywhereFromRealZ(GameObject.getMaxRealZ() / 2);
        questObject.setConsumer((obj) -> changeChapter(null));
        setFinalQuestObject(questObject);
        ObservableList<GameObject> objectList = FXCollections.observableArrayList();
        for (int i = 0; i < 100; i++) {
            GameObject decorateObject = GameMediaData.getRandomDecoration("tree");
            objectList.add(decorateObject);
            addGameObject(decorateObject);
        }
        GameObject.spreadObject(objectList, 5);
        objectList.clear();
        for (int i = 0; i < 100; i++) {
            GameObject decorateObject = GameMediaData.getRandomDecoration("bush");
            objectList.add(decorateObject);
            addGameObject(decorateObject);
        }
        GameObject.spreadObject(objectList, 1);
    }
}
