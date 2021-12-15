package story;

import application.Main;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import logic.GameObject;
import logic.QuestObject;
import util.MediaData;
import util.Triple;

import static util.Util.*;

public class ExampleChapter extends BaseChapter {
    public ExampleChapter() {
        amount=50;
        setUpper(MediaData.EVENING);
        QuestObject questObject = new QuestObject("file:res/image/mystic.jpg");
        questObject.spawnAnywhereFromRealZ(GameObject.getMaxRealZ() / 2);
        questObject.setConsumer((obj) -> changeChapter(new ExampleChapter()));
        setFinalQuestObject(questObject);
        for (int i = 0; i < 100; i++) {
            QuestObject squid = new QuestObject("file:res/image/squid.png");
            squid.setActiveRange(7.5);
            squid.setPassive(true);
            squid.setConsumer((obj)->{
                Thread thread =new Thread(()->{
                    //obj.setCo(new Triple(0 ,0 ,metreToCoord(10)));
                    for (int j = 0; j < height; j+=10) {
                        delay(refreshPeriod);
                        obj.setPosY(j);
                    }
//                    obj.despawn();
                });
                thread.start();
                    });
            squid.setObjectHeight(10);//gameObject.setImage(image, 10);//setImage("file:res/image/62872.jpg",1);
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
            QuestObject trap = QuestObject.createTrap();
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
    }

}
