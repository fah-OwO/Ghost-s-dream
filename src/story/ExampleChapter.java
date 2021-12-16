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
import util.gameMediaData;

import static util.Util.*;

public class ExampleChapter extends BaseChapter {

    @Override
    public void setUp() {
        setUpper(gameMediaData.EVENING);
        QuestObject questObject = new QuestObject();
        questObject.setImage("file:res/image/mystic.jpg", 0);
        questObject.spawnAnywhereFromRealZ(GameObject.getMaxRealZ() / 2);
        questObject.setConsumer((obj) -> changeChapter(new ExampleChapter()));
        setFinalQuestObject(questObject);
        for (int i = 0; i < 100; i++) {
            QuestObject squid = new QuestObject();
            squid.setImage(gameMediaData.SQUID, 10);
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
                thread.start();//threadMain.setHorizonLineMul(threadMain.getHorizonLineMul()*0.90);
            });
            //squid.setObjectHeight(10);//gameObject.setImage(image, 10);//setImage("file:res/image/62872.jpg",1);
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
            QuestObject trap = (QuestObject) gameMediaData.getGameObject("trap").clone();//QuestObject.createTrap();
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
            GameObject decorateObject = gameMediaData.getRandomDecoration("bush");
            decorateObjectList.add(decorateObject);
            addGameObject(decorateObject);
        }
        GameObject.spreadObject(decorateObjectList);
    }
}
