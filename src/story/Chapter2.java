package story;

import application.Main;
import javafx.application.Platform;
import logic.Cloud;
import logic.GameObject;
import logic.QuestObject;
import util.GameMediaData;

import static util.Util.delay;

public class Chapter2 extends BaseChapter {


    @Override
    public void setUp() {
        setUpper(GameMediaData.BACKGROUND);
        QuestObject table = (QuestObject) GameMediaData.getGameObject("table");
        table.setConsumer(obj -> {
            Platform.runLater(() -> Main.changeRoot(GameMediaData.setUpPaneFromString("\n\nThx for playing\n\nto be continued \n(as if I will)")));
            Main.togglePause();
            delay(5000);
            changeChapter(null);
        });
        setFinalQuestObject(table);
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
            threadMain.addPreRun(() -> {
                obj.despawn();
                obj.setImage(GameMediaData.getAnimation("eye").get(0));
            });
        });
        for (int i = 0; i < 300; i++) {
            QuestObject newEye = eye.clone();
            newEye.spawnAnywhere();
            addGameObject(newEye);
        }
        for (int i = 0; i < 100; i++) {
            GameObject decorateObject = GameMediaData.getRandomDecoration("bush");
            decorateObject.spawnAnywhere();
            addGameObject(decorateObject);
        }

        for (int i = 0; i < 20; i++) {
            Cloud cloud = (Cloud) GameMediaData.getRandomDecoration("cloud");
            cloud.spawnAnywhere();
            addGameObject(cloud);
        }
    }
}
