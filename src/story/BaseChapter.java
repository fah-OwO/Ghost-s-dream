package story;

import javafx.scene.image.Image;
import logic.DecorateGameObject;
import logic.GameObject;
import logic.QuestObject;
import util.Triple;


import static util.Util.*;

public class BaseChapter {
    QuestObject finalQuestObject;
    Image decorateImage;
    static int amount = 100;

    public BaseChapter() {
    }

    public void call() {
//        threadMain.clear();
        DecorateGameObject.setImage(decorateImage);
        generateDecorate(amount);
        finalQuestObject.deploy();
        threadMain.create(finalQuestObject);
        threadMain.start();
    }

    public void run() {
        call();
    }


    public void generateDecorate(double amount) {
        for (double i = 1; i <= amount; i++) {
            GameObject object = new DecorateGameObject();
//            object.spawnAtCoord(new Triple(0,0,metreToCoord(i)));
//            object.spawnAtCoord(new Triple(metreToCoord(i),0,metreToCoord(i)));
            object.spawnAnywhereFromRealZ(GameObject.getMaxRealZ() * i / amount);
            threadMain.create(object);
            object.deploy();
        }
    }

    public GameObject getFinalQuestObject() {
        return finalQuestObject;
    }

    public void setFinalQuestObject(QuestObject finalQuestObject) {
        this.finalQuestObject = finalQuestObject;
    }

    public Image getDecorateImage() {
        return decorateImage;
    }

    public void setDecorateImage(Image decorateImage) {
        this.decorateImage = decorateImage;
    }
}
