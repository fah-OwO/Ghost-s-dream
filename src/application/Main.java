package application;

import gui.QuickMenu;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;
import logic.Player;
import logic.QuestObject;
import util.GameMediaData;
import util.Util;

import static util.Util.*;

public class Main extends Application {

    private static final VBox root = new VBox();
    private static final Robot robot = new Robot();

    public static void main(String[] args) {
        launch(args);
    }

    public static void changeRoot(Node node) {
        root.getChildren().clear();
        root.getChildren().add(node);
    }

    public void start(Stage stage) {
        Util.initiate();
        GameMediaData.initiate();
        Player player = Player.getInstance();
        stage.setTitle(title);
        Scene scene = new Scene(root);
        root.getChildren().add(QuickMenu.initiate(scene));
        scene.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY)
                QuestObject.run();
        });
        scene.setOnMouseMoved(mouseEvent -> {
            player.rotateY(mouseEvent.getY());
            player.rotateX(mouseEvent.getX());
        });
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP, W, G -> player.accZ(-1);
                case DOWN, S -> player.accZ(1);
                case RIGHT, D -> player.accX(-1);
                case LEFT, A -> player.accX(1);
                case SHIFT, CAPS -> player.setRunning(true);
                case E -> QuestObject.run();
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
        stage.setHeight(height);
        stage.setWidth(width);
        stage.setScene(scene);
        stage.show();

    }

    public static void setMouse(double x, double y) {
        Platform.runLater(() -> robot.mouseMove(x, y));
    }

    public static double setMouseX(double x) {
            double tmp = robot.getMouseX();
            robot.mouseMove(x, robot.getMouseY());
            return tmp-x-0.5;
    }
}
