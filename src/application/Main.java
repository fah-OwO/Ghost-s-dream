package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import logic.*;

public class Main extends Application {
    ThreadMain threadMain = new ThreadMain();
    public static Player player = Player.getInstance();
    public static String title = "";
    public static final int frames = 50;
    public static int width = 1920;
    public static int height = 1000;
    public static double tanTheta = Math.tan(Player.getPerspectiveRadians());
    public static double metrePerPixels = (double) 1 / height;
    private static final Pane pane = new Pane();
    private static final Image background = new Image("file:res/image/Background.png");

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        VBox root = new VBox();
        BackgroundImage backgroundImg = new BackgroundImage(background, null, null, null, null);
        pane.setBackground(new Background(backgroundImg));
        pane.setPrefSize(width, height);
        root.getChildren().addAll(pane);
        stage.setTitle(title);
        Scene scene = new Scene(root, width, height);
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
        stage.setScene(scene);
        stage.show();
        double treeCount = 100;
        for (double i = 1; i <= treeCount; i++) {
            GameObject object = new DecorateGameObject();
//            object.spawnAnywhereFromRealZ(metreToCoord(2));
            object.spawnAnywhereFromRealZ(GameObject.getMaxRealZ() * i / treeCount);
            threadMain.create(object);
        }
        QuestObject questObject = new QuestObject("file:res/image/mystic.jpg");
        questObject.spawnAnywhereFromRealZ(GameObject.getMaxRealZ() / 2);
        questObject.setRunnable(() -> System.out.println("nice"));
        QuestObject.setActiveRange(metreToCoord(1.5));//1 metre
        threadMain.create(questObject);
        threadMain.start();

    }

    protected static void drawIMG(GameObject obj) {
        ImageView im = obj.getImageView();
        Triple pos = obj.getPos();
        removeFromPane(im);
        im.relocate((pos.getX() - pos.getZ() + width) / 2, (height - pos.getZ()) / 2);
        im.setFitHeight(pos.getZ());
        addToPane(im);
    }

    public static void addToPane(ImageView imageview) {
        pane.getChildren().add(imageview);
    }

    public static void removeFromPane(ImageView imageview) {
        pane.getChildren().remove(imageview);
    }

    public static Triple getPlayerV() {
        return player.getSpeed();
    }

    public static double coordToMetre(double z) {
        return z *height;/// metrePerPixels;//1 / toMetre(1 / z);
//        z=GameObject.coordinate2screenPos(new Triple(0, 0, z)).z;
//        z = toMetre(z);
//        return GameObject.pos2coordinate(new Triple(0, 0, z)).z;
    }

    public static double metreToCoord(double z) {
        return z/height;//metrePerPixels * z;//1 / toPixel(1 / z);
    }

//    public static double getDistanceInMetre(GameObject gameObject) {
//        Triple pos = gameObject.getPos();
//        double z = toMetre(pos.z);
//        return GameObject.pos2coordinate(new Triple(0, 0, z)).z;
//    }

    public static Triple toMetre(Triple pos) {
        return pos.mul(metrePerPixels);
    }

    public static double toMetre(double i) {
        return i * (metrePerPixels);
    }

    public static Triple toPixel(Triple pos) {
        return pos.mul(1 / metrePerPixels);
    }

    public static double toPixel(double i) {
        return i / (metrePerPixels);
    }

    public static void accelerate(double x, double z) {
        player.accX(x);
        player.accZ(z);
    }
}
