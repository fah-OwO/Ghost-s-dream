package story;

import javafx.scene.image.Image;
import logic.GameObject;
import logic.QuestObject;

import static util.Util.*;

public class ExampleChapter extends BaseChapter {
    public ExampleChapter() {
        setUpper(EVENING);
        setDecorateImage(TREE);
        QuestObject questObject = new QuestObject("file:res/image/mystic.jpg");
        questObject.spawnAnywhereFromRealZ(GameObject.getMaxRealZ() / 2);
        questObject.setRunnable(() -> threadMain.clear());
        setFinalQuestObject(questObject);
        Image image = new Image("file:res/image/62872.jpg", height, width, true, true);
        for (int i = 0; i < 10; i++) {
            GameObject gameObject = new GameObject();
            gameObject.setImage(image, 1);//setImage("file:res/image/62872.jpg",1);
            addGameObject(gameObject);
        }
    }

    public void dupe() {
        ExampleChapter exampleChapter = new ExampleChapter();
        exampleChapter.run();
    }
}
