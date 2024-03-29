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

import static util.Util.*;

public class ExampleChapter extends BaseChapter {

    @Override
    public void setUp() {
        setUpper(GameMediaData.BACKGROUND);
        QuestObject questObject = new QuestObject();
        questObject.setImage("file:res/image/mystic.jpg", 0);
        questObject.spawnAnywhereFromRealZ(GameObject.getMaxRealZ() / 2);
        questObject.setConsumer((obj) -> changeChapter(new ExampleChapter()));
        setFinalQuestObject(questObject);
        for (int i = 0; i < 100; i++) {
            QuestObject squid = new QuestObject();
            squid.setActiveRange(7.5);
            squid.setPassive(true);
            squid.setConsumer(obj -> {
                Thread thread = new Thread(() -> {
                    for (int j = 0; j < height; j += 10) {
                        delay(refreshPeriod);
                        if (obj.isOnScreen()) obj.setPosY(j);
                        else break;
                    }
                });
                thread.start();
            });
            squid.spawnAnywhere();
            addGameObject(squid);
        }
        StackPane pane = new StackPane();
        Text gameOverText = new Text("Game Over");
        gameOverText.setTextAlignment(TextAlignment.CENTER);
        gameOverText.setFont(new Font(height / 3.0));
        pane.getChildren().add(gameOverText);
        StackPane.setAlignment(gameOverText, Pos.CENTER);
        for (int i = 0; i < 10; i++) {
            QuestObject trap = (QuestObject) GameMediaData.getGameObject("trap").clone();
            trap.setConsumer((obj) -> {
                shutdown();
                Main.changeRoot(pane);
                Thread thread = new Thread(() -> {
                    delay((long) 1e4);
                    Platform.runLater(() -> changeChapter(new ExampleChapter()));
                });
                thread.start();
            });
            addGameObject(trap);
        }
        ObservableList<GameObject> decorateObjectList = FXCollections.observableArrayList();
        for (int i = 0; i < 100; i++) {
            GameObject decorateObject = GameMediaData.getRandomDecoration("bush");
            decorateObject.spawnAnywhere();
            decorateObjectList.add(decorateObject);
            addGameObject(decorateObject);
        }
    }
}
