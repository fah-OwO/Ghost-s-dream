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
        QuestObject questObject = (QuestObject) GameMediaData.getGameObject("eye");
        questObject.setActiveRange(3);
        questObject.spawnAnywhereFromRealZ(GameObject.getMaxRealZ() / 2);
        questObject.setConsumer((obj) -> changeChapter(new Chapter2()));
        setFinalQuestObject(questObject);
        QuestObject squid = new QuestObject();
        squid.setImage(GameMediaData.SQUID, 10);
        squid.setActiveRange(10);
        squid.setPassive(true);
        squid.setConsumer(obj -> {
            for (double j = 0.0001; obj.getPos().y < height; j *= 1.1) {
                if (obj.isTriggered()) obj.setPosY(metreToCoord(j) / refreshPeriod);
                else break;
                delay(refreshPeriod);
            }
            obj.despawn();
        });
        for (int i = 0; i < 100; i++) {
            QuestObject newSquid = squid.clone();
            newSquid.spawnAnywhere();
            addGameObject(newSquid);
        }
    }
}
