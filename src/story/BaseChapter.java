package story;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import logic.DecorateGameObject;
import logic.GameObject;
import logic.QuestObject;


import java.util.ArrayList;

import static util.Util.*;

public class BaseChapter {
    private Image upper;
    private QuestObject finalQuestObject;
    private Image decorateImage;
    private final ObservableList<GameObject> gameObjectList;
    protected static int amount = 100;

    public BaseChapter() {
        upper = null;
        finalQuestObject = null;
        decorateImage = TREE;
        gameObjectList = FXCollections.observableArrayList();
    }

    public void call() {
        Main.clearScreen();
        if (upper != null) Main.setUpperBackground(upper);
        if (finalQuestObject != null) {
            threadMain.create(finalQuestObject);
            finalQuestObject.deploy();
        }
        for (GameObject object : gameObjectList) {
            threadMain.create(object);
            object.deploy();
        }
        if (upper != null)
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

    public void addGameObject(GameObject gameObject) {
        gameObjectList.add(gameObject);
    }
}
