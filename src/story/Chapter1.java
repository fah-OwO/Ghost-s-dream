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
        for (int i = 0; i < 100; i++) {
            QuestObject newSquid = (QuestObject) GameMediaData.getGameObject("squid").clone();
            newSquid.spawnAnywhere();
            addGameObject(newSquid);
        }
    }
}
