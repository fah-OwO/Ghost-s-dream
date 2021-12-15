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

import static util.Util.*;

public class ExampleChapter extends BaseChapter {
    public ExampleChapter() {
        setUpper(MediaData.EVENING);
        QuestObject questObject = new QuestObject("file:res/image/mystic.jpg");
        questObject.spawnAnywhereFromRealZ(GameObject.getMaxRealZ() / 2);
        questObject.setRunnable(() -> changeChapter(new ExampleChapter()));
        setFinalQuestObject(questObject);

        Image image = MediaData.getImage("file:res/image/62872.jpg");//new Image("file:res/image/62872.jpg", height, width, true, true);
        for (int i = 0; i < 10; i++) {
            GameObject gameObject = new GameObject();
            gameObject.setImage(image, 10);//setImage("file:res/image/62872.jpg",1);
            addGameObject(gameObject);
        }
        StackPane pane = new StackPane();
        Text gameOverText = new Text("Game Over");
        gameOverText.setTextAlignment(TextAlignment.CENTER);
        gameOverText.setFont(new Font(height / 3.0));
        pane.getChildren().add(gameOverText);
        StackPane.setAlignment(gameOverText, Pos.CENTER);
        for (int i = 0; i < 100; i++) {
            QuestObject trap = new QuestObject(null);
            trap.setImage(MediaData.TRAP, 0.2);//trap.setObjectHeight(0.2);
            trap.setPassive(true);
            trap.setActiveRange(1);
            trap.spawnAnywhere();
            trap.setRunnable(() -> {
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
