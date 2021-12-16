package story;

import application.Main;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import logic.GameObject;
import logic.QuestObject;
import util.GameMediaData;

import static util.Util.delay;
import static util.Util.height;

public class Chapter2 extends BaseChapter {

    @Override
    public void setUp() {
        setUpper(GameMediaData.EVENING);
        QuestObject questObject = new QuestObject();
        questObject.setImage(GameMediaData.SQUID, 4);
        questObject.spawnAnywhereFromRealZ(GameObject.getMaxRealZ() / 2);
        questObject.setConsumer((obj) -> changeChapter(null));
        setFinalQuestObject(questObject);
        StackPane pane = new StackPane();
        Text gameOverText = new Text("Game Over");
        gameOverText.setTextAlignment(TextAlignment.CENTER);
        gameOverText.setFont(new Font(height / 3.0));
        pane.getChildren().add(gameOverText);
        StackPane.setAlignment(gameOverText, Pos.CENTER);
        ObservableList<GameObject> objectList = FXCollections.observableArrayList();
        QuestObject trap = (QuestObject) GameMediaData.getGameObject("trap").clone();//QuestObject.createTrap();
        trap.setConsumer((obj) -> {
            shutdown();
            Main.changeRoot(pane);
            Thread thread = new Thread(() -> {
                delay((long) 1e4);
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
