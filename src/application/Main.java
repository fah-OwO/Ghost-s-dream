package application;

import javafx.application.Application;
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
import util.Triple;

import static util.Util.*;

public class Main extends Application {
    private static final Pane pane = new Pane();
    private static final Image background = new Image("file:res/image/Background.png");

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        initiate();
        VBox root = new VBox();
        Scene scene = new Scene(root, width, height);
        stage.setTitle(title);
        root.getChildren().addAll(pane);
        BackgroundImage backgroundImg = new BackgroundImage(background, null, null, null, null);
        pane.setBackground(new Background(backgroundImg));
        pane.setPrefSize(width, height);
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP, W -> player.accZ(-1);
                case DOWN, S -> player.accZ(1);
                case SHIFT -> player.setRunning(true);
            }
            switch (event.getCode()) {
                case RIGHT, D -> player.accX(-1);
                case LEFT, A -> player.accX(1);
            }
            if (event.getCode().equals(KeyCode.E)) QuestObject.run();
        });
        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case DOWN, UP, W, S -> player.accZ(0);
                case RIGHT, LEFT, A, D -> player.accX(0);
                case SHIFT -> player.setRunning(false);
                case ESCAPE -> stage.close();
            }
        });
        BaseChapter exampleChap = new ExampleChapter();
        exampleChap.run();
        stage.setScene(scene);
        stage.show();

    }

    protected static void drawIMG(GameObject obj) {
        ImageView im = obj.getImageView();
        Triple pos = obj.getPos();
        removeFromPane(im);
        im.relocate((pos.getX() - pos.getZ() + width) / 2, (height - pos.getZ()) / 2);
        im.setFitHeight(pos.getZ());
        addToPane(im);
    }

    public static void clearScreen() {
        threadMain.clear();
        pane.getChildren().clear();
    }

    public static void addToPane(ImageView imageview) {
        pane.getChildren().add(imageview);
    }

    public static void removeFromPane(ImageView imageview) {
        pane.getChildren().remove(imageview);
    }

    public static void setUpperBackground(Image image) {
        WritableImage newImage = new WritableImage(image.getPixelReader(), 0, 0, width, height / 2);
        ImageView imageView = new ImageView(newImage);
//        WritableImage anewImage=new WritableImage(imageView.getPixelReader(),0,0,width,height/2);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(width);
        addToPane(imageView);
    }

}
