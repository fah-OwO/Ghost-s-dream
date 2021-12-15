package story;

import application.Main;
import javafx.scene.image.Image;
import logic.GameObject;
import logic.QuestObject;
import util.MediaData;

import static util.Util.*;

public class ExampleChapter extends BaseChapter {
    public ExampleChapter() {
        setUpper(EVENING);
        setDecorateImage(MediaData.getTree());
        QuestObject questObject = new QuestObject("file:res/image/mystic.jpg");
        questObject.spawnAnywhereFromRealZ(GameObject.getMaxRealZ() / 2);
        questObject.setRunnable(this::dupe);
        setFinalQuestObject(questObject);
        Image image = new Image("file:res/image/62872.jpg", height, width, true, true);
        for (int i = 0; i < 10; i++) {
            GameObject gameObject = new GameObject();
            gameObject.setImage(image, 10);//setImage("file:res/image/62872.jpg",1);
            addGameObject(gameObject);
        }
    }

    public void dupe() {
        this.shutdown();
        ExampleChapter exampleChapter = new ExampleChapter();
        exampleChapter.run();
        Main.changeRoot(exampleChapter.getRoot());
    }
}
