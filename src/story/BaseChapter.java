package story;

import application.Main;
import application.ThreadMain;
import gui.QuickMenu;
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
import util.GameMediaData;


import static util.Util.*;

public abstract class BaseChapter {
    protected final VBox root = new VBox();
    protected final ThreadMain threadMain;
    private Image upper = null;
    private QuestObject finalQuestObject = null;
    private boolean running = false;
    private final ObservableList<GameObject> gameObjectList = FXCollections.observableArrayList();
    protected BaseChapter nextChapter = null;

    public BaseChapter() {
        Pane pane = new Pane();
        BackgroundImage backgroundImg = new BackgroundImage(GameMediaData.BLACK, null, null, null, null);
        pane.setBackground(new Background(backgroundImg));
        pane.setPrefSize(width, height);
        root.getChildren().addAll(pane);
        threadMain = new ThreadMain(pane, Player.getInstance());
        setUp();
    }

    public abstract void setUp();

    public void call() {
        if (running) return;
        running = true;
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
        threadMain.start();
    }

    public void run() {
        call();
    }

    public void changeChapter(BaseChapter chapter) {
        //if (chapter == null) return;
        if (chapter == null) Main.changeRoot(QuickMenu.initiate(null));
        else {
            chapter.run();
            Main.changeRoot(chapter.getRoot());
        }
        this.shutdown();
    }

    public void shutdown() {
        if (!running) return;
        running = false;
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

    public boolean isRunning() {
        return running;
    }

    public void setNextChapter(BaseChapter nextChapter) {
        this.nextChapter = nextChapter;
    }

    public BaseChapter getNextChapter() {
        return nextChapter;
    }
}
