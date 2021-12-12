package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.Triple;
import logic.gameObject;

import java.util.Random;

public class Main extends Application {
    static Triple playerV = new Triple(0, 0, 0);
    ThreadMain threadMain = new ThreadMain();
    private static Button summonPlayer;
    private static Button switchLight;
    private static Button timeUp;
    //public static int playerPerspectiveDegrees =
    public static double playerPerspectiveRadians = Math.toRadians(50 >> 1);
    public static double tanTheta = Math.tan(playerPerspectiveRadians);
    public static int frames = 10;
    public static String title = "try";
    public static int width = 1800;
    public static int height = 1000;
    public static Random random = new Random();
    Canvas canvas = new Canvas();
    static Pane pane = new Pane();
    GraphicsContext ctx = canvas.getGraphicsContext2D();
    private static final Image background = new Image("file:res/image/Background.png");

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        //canvas.setHeight(height);
        //canvas.setWidth(width);
        VBox root = new VBox();
        BackgroundImage backgroundImg = new BackgroundImage(background, null, null, null, null);
        //BackgroundImage backgroundImg=new BackgroundImage(background,null,null,null,new BackgroundSize(width,height,false,false,false,false));
        pane.setBackground(new Background(backgroundImg));
        pane.setPrefSize(width, height);
        root.getChildren().addAll(pane);
        //root.setBackground(new Background(new BackgroundFill(null,null,null)));
        stage.setTitle(title);
        Scene scene = new Scene(root, width, height);
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP, W -> playerV.setZ(-1);
                case DOWN, S -> playerV.setZ(1);
            }
            switch (event.getCode()) {
                case RIGHT, D -> playerV.setX(1);
                case LEFT, A -> playerV.setX(-1);
            }
            //TODO: if press E then obj play animation and disappear trigger new obj
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case DOWN, UP, W, S -> playerV.setZ(0);
                case RIGHT, LEFT, A, D -> playerV.setX(0);
            }
        });
        stage.setScene(scene);
        stage.show();
        //gameObject leftTree= new gameObject();
        for (int i = 0; i < 10; i++) {
            threadMain.create();
        }
//        threadMain.create();
//        threadMain.create();
		/*HBox controlTab = new HBox();
		controlTab.setAlignment(Pos.CENTER);
		summonPlayer = new Button("Summon Player");
		switchLight = new Button("Red Light");
		timeUp = new Button("Time Up");
		controlTab.getChildren().addAll(summonPlayer, switchLight, timeUp);
		summonPlayer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//playerField.addPlayer();
				//threadMain.initalizeNewPlayer(playerField.getPlayerCount()-1);
				threadMain.create();
			}
		});
		switchLight.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				playerV.set(1,0,0);//playerField.killAllRunPlayer();
			}
		});

		timeUp.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				playerV.set(-1,0,0);
			}
		});

		canvas.requestFocus();*/
    }

    protected static void drawIMG(gameObject obj) {
        ImageView im = obj.getImageView();
        removeFromPane(im);
        //if(!obj.isOnScreen())return;
//        im.resizeRelocate((double) (obj.getX()), (double) (obj.getY()), (double) (obj.getZ()), (double) (obj.getZ()));
        im.relocate((obj.getX() - obj.getZ()) / 2, (obj.getY() - obj.getZ()) / 2);
        im.setFitHeight(obj.getZ());
        im.setFitWidth(obj.getZ());
//        System.out.println(im.getFitHeight());
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
        return playerV;
    }
}
