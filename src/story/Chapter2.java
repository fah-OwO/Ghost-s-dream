package story;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logic.GameObject;
import logic.QuestObject;
import util.GameMediaData;

import static util.Util.delay;
import static util.Util.refreshPeriod;

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
        QuestObject eye = (QuestObject) GameMediaData.getGameObject("eye");
        eye.setPassive(true);
        eye.setConsumer((obj) -> {
            for (int j = 1; j < 5; j++) {
                if (obj.isTriggered()) {
                    int finalJ = j;
                    threadMain.addPreRun(() -> obj.setImage(GameMediaData.getAnimation("eye").get(finalJ)));
                } else break;
                delay(100);
            }
            threadMain.addPreRun(obj::despawn);
            delay(refreshPeriod);
            threadMain.addPreRun(() -> eye.setImage(GameMediaData.getAnimation("eye").get(0)));
        });
        for (int i = 0; i < 100; i++) {
            QuestObject newEye = eye.clone();
            objectList.add(newEye);
            addGameObject(newEye);
        }
        GameObject.spreadObject(objectList, eye.getActiveRange());
        objectList.clear();
        for (int i = 0; i < 100; i++) {
            GameObject decorateObject = GameMediaData.getRandomDecoration("bush");
            objectList.add(decorateObject);
            addGameObject(decorateObject);
        }
        GameObject.spreadObject(objectList, 1);
    }
}
