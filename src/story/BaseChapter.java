package story;

import application.Main;
import javafx.scene.image.Image;
import logic.DecorateGameObject;
import logic.GameObject;
import logic.QuestObject;


import static util.Util.*;

public class BaseChapter {
    Image upper;
    QuestObject finalQuestObject;
    Image decorateImage;
    static int amount = 100;

    public BaseChapter() {
        upper=null;
        finalQuestObject=null;
        decorateImage=TREE;
    }

    public void call() {
        threadMain.clear();
        if(upper!=null) Main.setUpperBackground(upper);
        if(finalQuestObject!=null) {
            finalQuestObject.deploy();
            threadMain.create(finalQuestObject);
        }
        if(upper!=null)
        DecorateGameObject.setImage(decorateImage);
        generateDecorate(amount);
        threadMain.start();
    }

    public void run() {
        call();
    }


    public void generateDecorate(double amount) {
        for (double i = 1; i <= amount; i++) {
            GameObject object = new DecorateGameObject();
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

    public void setUpper(Image upper) {
        this.upper = upper;
    }
}
