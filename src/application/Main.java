package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.Triple;
import logic.TryObj;

import java.util.Random;

public class Main extends Application {
    static Triple playerV = new Triple(0, 0, 0);
    ThreadMain threadMain = new ThreadMain();
    private static Button summonPlayer;
    private static Button switchLight;
    private static Button timeUp;
    public int frames = 10;
    public String title = "try";
    public static int width = 1820;
    public static int height = 980;
    public static Random random = new Random();
    Canvas canvas = new Canvas();
    static Pane pane = new Pane();
    //GraphicsContext ctx = canvas.getGraphicsContext2D();
    //private static final Image background = new Image("FieldBackground.png");

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        //canvas.setHeight(height);
        //canvas.setWidth(width);
        VBox root = new VBox();
        root.getChildren().addAll(pane);
        stage.setTitle(title);
        Scene scene = new Scene(root, width, height);
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP -> playerV.setZ(1);
                case DOWN -> playerV.setZ(-1);
            }
            switch (event.getCode()) {
                case RIGHT -> playerV.setX(1);
                case LEFT -> playerV.setX(-1);
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case DOWN:
                    playerV.setZ(0);
                    break;//playerV.set(0,1,0); break;
                case UP:
                    playerV.setZ(0);
                    break;//playerV.set(0,-1,0); break;
                case RIGHT:
                    playerV.setX(0);
                    break;//playerV.set(1,0,0); break;
                case LEFT:
                    playerV.setX(0);
                    break;//playerV.set(-1,0,0); break;
                case SHIFT: //playerV.set(0,0,1); break;
            }
        });
        stage.setScene(scene);
        stage.show();
        threadMain.create();
        threadMain.create();
        threadMain.create();
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

    protected static void drawIMG(TryObj obj) {
        ImageView im = obj.getImageView();
        removeFromPane(im);
        //if(!obj.isOnScreen())return;
//        im.resizeRelocate((double) (obj.getX()), (double) (obj.getY()), (double) (obj.getZ()), (double) (obj.getZ()));
		im.relocate((double)(obj.getX()-obj.getZ())/2, (double)(obj.getY()-obj.getZ())/2);
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
