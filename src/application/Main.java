package application;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import logic.*;
import story.BaseChapter;
import story.ExampleChapter;

import static util.Util.*;

public class Main extends Application {

    private static final VBox root = new VBox();

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        initiate();
        Player player = Player.getInstance();
        stage.setTitle(title);
        BaseChapter exampleChap = new ExampleChapter();
        exampleChap.run();
        root.getChildren().add(exampleChap.getRoot());
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP, W, G -> player.accZ(-1);
                case DOWN, S -> player.accZ(1);
                case RIGHT, D -> player.accX(-1);
                case LEFT, A -> player.accX(1);
                case SHIFT -> player.setRunning(true);
                case E -> QuestObject.run();
                case SPACE ->player.jump();
            }
        });
        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case DOWN, UP, W, S -> player.accZ(0);
                case RIGHT, LEFT, A, D -> player.accX(0);
                case SHIFT -> player.setRunning(false);
                case ESCAPE -> stage.close();
            }
        });
        stage.setScene(scene);
        stage.show();

    }

    public static void changeRoot(Node node) {
        root.getChildren().clear();
        root.getChildren().add(node);
    }

}
