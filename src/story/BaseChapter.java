package story;

import application.Main;
import application.ThreadMain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import logic.GameObject;
import logic.Player;
import logic.QuestObject;
import util.MediaData;


import static util.Util.*;

public class BaseChapter {
    private final VBox root = new VBox();
    private final ThreadMain threadMain;
    private Image upper;
    private QuestObject finalQuestObject;
    private final ObservableList<GameObject> gameObjectList;
    protected static int amount = 100;

    public BaseChapter() {
        Pane pane = new Pane();
        BackgroundImage backgroundImg = new BackgroundImage(MediaData.BLACK, null, null, null, null);
        pane.setBackground(new Background(backgroundImg));
        pane.setPrefSize(width, height);
        root.getChildren().addAll(pane);
        threadMain = new ThreadMain(pane, Player.getInstance());
        upper = null;
        finalQuestObject = null;
        gameObjectList = FXCollections.observableArrayList();
    }

    public void call() {
        threadMain.clear();
        if (upper != null) threadMain.setUpperBackground(upper);
        if (finalQuestObject != null) {
            threadMain.create(finalQuestObject);
            finalQuestObject.deploy();
        }
        for (GameObject object : gameObjectList) {
            threadMain.create(object);
            object.deploy();
        }
        generateDecorate(amount);
        threadMain.start();
    }

    public void changeChapter(BaseChapter chapter) {
        this.shutdown();
        chapter.run();
        Main.changeRoot(chapter.getRoot());
    }

    public void run() {
        call();
    }

    public void spreadObject(ObservableList<GameObject> gameObjectList) {
        int i = 0, amount = gameObjectList.size();
        for (GameObject object : gameObjectList) {
            object.spawnAnywhereFromRealZ(GameObject.getMaxRealZ() * ++i / amount);
            threadMain.create(object);
            object.deploy();
        }
    }

    public void generateDecorate(double amount) {
        ObservableList<GameObject> gameObjectList = FXCollections.observableArrayList();
        for (int i = 0; i < amount; i++) {
            GameObject decorateGameObject = MediaData.getDecoration();
            gameObjectList.add(decorateGameObject);
        }
        spreadObject(gameObjectList);
    }

    public void shutdown() {
        threadMain.shutdown();
        gameObjectList.clear();
        root.getChildren().clear();
    }

    public VBox getRoot() {
        return root;
    }

    public GameObject getFinalQuestObject() {
        return finalQuestObject;
    }

    public void setFinalQuestObject(QuestObject finalQuestObject) {
        this.finalQuestObject = finalQuestObject;
    }

    public void setUpper(Image upper) {
        this.upper = upper;
    }

    public void addGameObject(GameObject gameObject) {
        gameObjectList.add(gameObject);
    }
}
