package story;

import application.Main;
import application.ThreadMain;
import gui.QuickMenu;
import javafx.application.Platform;
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

import static util.Util.height;
import static util.Util.width;

public abstract class BaseChapter {
    protected final VBox root = new VBox();
    protected final ThreadMain threadMain;
    private final ObservableList<GameObject> gameObjectList = FXCollections.observableArrayList();
    protected BaseChapter nextChapter = null;
    private Image upper = null;
    private QuestObject finalQuestObject = null;
    private boolean running = false;

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

    public void run() {
        if (isRunning()) return;
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
        Main.changeRoot(getRoot());
        Main.setThreadMain(threadMain);
    }


    public void changeChapter(BaseChapter chapter) {
        Platform.runLater(() -> {
            if (chapter == null) Main.changeRoot(QuickMenu.initiate(null));
            else chapter.run();
            this.shutdown();
        });
    }

    public void shutdown() {
        if (!isRunning()) return;
        running = false;
        threadMain.shutdown();
        gameObjectList.clear();
        root.getChildren().clear();
    }

    public VBox getRoot() {
        return root;
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

    public BaseChapter getNextChapter() {
        return nextChapter;
    }

    public void setNextChapter(BaseChapter nextChapter) {
        this.nextChapter = nextChapter;
    }
}
