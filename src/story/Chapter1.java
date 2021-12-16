package story;

import application.Main;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logic.GameObject;
import logic.QuestObject;
import util.GameMediaData;

import static util.Util.delay;

public class Chapter1 extends BaseChapter {

    @Override
    public void setUp() {
        setUpper(GameMediaData.EVENING);
        QuestObject questObject = new QuestObject();
        questObject.setImage(GameMediaData.SQUID, 4);
        questObject.spawnAnywhereFromRealZ(GameObject.getMaxRealZ() / 2);
        questObject.setConsumer((obj) -> changeChapter(new Chapter2()));
        setFinalQuestObject(questObject);
        ObservableList<GameObject> objectList = FXCollections.observableArrayList();
        QuestObject trap = (QuestObject) GameMediaData.getGameObject("trap").clone();//QuestObject.createTrap();
        trap.setConsumer((obj) -> {
            shutdown();
            Main.changeRoot(GameMediaData.setUpPaneFromString("Game Over"));
            Thread thread = new Thread(() -> {
                delay((long) 3e3);
                Platform.runLater(() -> changeChapter(null));
            });
            thread.start();
        });
        for (int i = 0; i < 100; i++) {
            QuestObject newTrap = trap.clone();
            objectList.add(newTrap);
            addGameObject(newTrap);
        }
        GameObject.spreadObject(objectList, 4);
        objectList.clear();
        for (int i = 0; i < 10; i++) {
            GameObject decorateObject = GameMediaData.getRandomDecoration("tree");
            objectList.add(decorateObject);
            addGameObject(decorateObject);
        }
        GameObject.spreadObject(objectList, 5);
    }
}
