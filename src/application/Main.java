package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.*;

public class Main extends Application {
    public static Player player = new Player();
    ThreadMain threadMain = new ThreadMain();
    public static double tanTheta = Math.tan(Player.getPerspectiveRadians());
    public static int frames = 60;
    public static String title = "try";
    public static int width = 1920;//1800;
    public static int height = 1000;
    public static double metrePerPixels = (double) 4 / height;
    //    public static double pixelsPerMetre=height/4;
    static Pane pane = new Pane();
    private static final Image background = new Image("file:res/image/Background.png");

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        //player.getStarted();
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
            //TODO: if press E then obj play animation and disappear trigger new obj
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case DOWN, UP, W, S -> player.accZ(0);
                case RIGHT, LEFT, A, D -> player.accX(0);
                case SHIFT -> player.setRunning(false);
            }
        });
        stage.setScene(scene);
        stage.show();
        double treeCount = 100;
        for (double i = 1; i <= treeCount; i++) {
//            GameObject object =new DecorateGameObject();//GameObject();
            GameObject object = new GameObject();
            object.spawnAnywhereFromRealZ(GameObject.getMaxRealZ() * i / treeCount);
//            object.spawnAnywhere();
            threadMain.create(object);
        }
        GameObject questObject = new QuestObject("file:res/image/mystic.jpg");
        questObject.spawnAnywhereFromRealZ(GameObject.getMaxRealZ() / 2);
//            object.spawnAnywhere();
        threadMain.create(questObject);
        threadMain.start();
    }

    protected static void drawIMG(GameObject obj) {
        ImageView im = obj.getImageView();
        Triple pos = obj.getPos();
        removeFromPane(im);
        im.relocate((pos.getX() - pos.getZ() + width) / 2, (pos.getY() - pos.getZ()) / 2);
        im.setFitHeight(pos.getZ());
//        im.setFitWidth(obj.getZ());
        addToPane(im);

    }

    public static void addToPane(ImageView imageview) {
        pane.getChildren().add(imageview);
    }

    public static void removeFromPane(ImageView imageview) {
        pane.getChildren().remove(imageview);
    }

    public void setBackGround(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    public void drawImage(GraphicsContext gc, String image_path) {
        System.out.println(image_path);
        Image javafx_logo = new Image(image_path);
        gc.drawImage(javafx_logo, 40, 250);
    }

    public void drawImageFixSize(GraphicsContext gc, String image_path) {
        System.out.println(image_path);
        Image javafx_logo = new Image(image_path);

        //image, x ,y, width, height
        gc.drawImage(javafx_logo, 40, 40, 600, 200);
    }

    public static Triple getPlayerV() {
        return player.getSpeed();
    }
}
