package util;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import logic.Cloud;
import logic.GameObject;
import logic.QuestObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static util.Util.*;

public class GameMediaData {

    public static final AudioClip walkingSound = new AudioClip(getRes("file:res/sound/walkingSound.mp3"));// https://nofilmschool.com/best-royalty-free-sound-effects//https://gamesounds.xyz/?dir=BBC%20Sound%20Effects%20Library/BBC%2055%20-%20Footsteps%201
    public static final Image BLACK = new Image(getRes("file:res/image/Black.png"), 1, 1, false, false);
    private static final Map<String, Image> imageDatabase = new HashMap<>();
    public static final Image BACKGROUND = getImage("file:res/image/Background.png");
    private static final Map<String, List<Image>> animationDatabase = new HashMap<>();
    private static final Map<String, GameObject> gameObjectMap = new HashMap<>();
    private static final Map<String, List<GameObject>> decoration = new HashMap<>();

    private static String getRes(String s) {
        return s;//return Objects.requireNonNull(GameMediaData.class.getClassLoader().getResource(s.replace("file:", ""))).toString();
    }

    public static void initiate() {
        QuestObject squid = new QuestObject();
        squid.setImage("file:res/image/sq43.png", 10);
        squid.setActiveRange(10);
        squid.setPassive(true);
        addGameObject("squid", squid);
        GameObject bush = new GameObject();
        bush.setImage("file:res/image/bush.png", 0.6);
        addGameObject("bush", bush);// https://www.cgchan.com/cantree
        for (int i = 0; i < 2; i++) {
            Cloud cloud = new Cloud();
            cloud.setImage("file:res/image/cloud/cloud" + i + ".png", 4);
            cloud.setMinSpawningRange(7);
            addDecoration("cloud", cloud);// https://www.cgchan.com/cantree
        }
        for (int i = 0; i < 5; i++)
            addAnimation("eye", GameMediaData.getImage("file:res/image/eye/eye" + i + ".png"));
        QuestObject eye = new QuestObject();
        eye.setImage(getAnimation("eye").get(0), 3);
        eye.setActiveRange(10);
        addGameObject("eye", eye);
        QuestObject table=new QuestObject();
        table.setImage(GameMediaData.getImage("file:res/image/table.png"),0.8);
        table.setActiveRange(3);
        table.spawnAnywhereFromRealZ(GameObject.getMaxRealZ() / 2);
        addGameObject("table",table);
    }

    private static void addGameObject(String s, GameObject gameObject) {
        gameObjectMap.put(s, gameObject);
        addDecoration(s, gameObject);
        addImage2Database(s, gameObject.getImage());
    }

    public static Image getImage(String url) {
        url = getRes(url);
        if (!imageDatabase.containsKey(url)) {
            Image image = new Image(url);
            Image image1 = new Image(url, width, 0, true, true);
            if (image1.getHeight() < image.getHeight())
                image = image1;
            imageDatabase.put(url, image);
        }
        return imageDatabase.get(url);
    }

    public static void addImage2Database(String s, Image image) {
        imageDatabase.put(s, image);
    }

    public static GameObject getGameObject(String name) {
        return gameObjectMap.get(name).clone();
    }

    public static void addDecoration(String decorationType, GameObject gameObject) {
        if (!decoration.containsKey(decorationType))
            decoration.put(decorationType, new ArrayList<>());
        decoration.get(decorationType).add(gameObject);
    }

    public static List<GameObject> getDecoration(String decorationType) {
        return decoration.get(decorationType);
    }

    public static Image getRandomImage(String decorationType) {
        return getRandomDecoration(decorationType).getImageView().getImage();
    }

    public static GameObject getRandomDecoration(String decorationType) {
        List<GameObject> decorationList = getDecoration(decorationType);
        return decorationList.get(rand.nextInt(decorationList.size())).clone();
    }

    public static StackPane setUpPaneFromString(String s) {
        StackPane pane = new StackPane();
        Text text = new Text(s);
        pane.getChildren().add(text);
        StackPane.setAlignment(text, Pos.CENTER);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(new Font(height / 8.0));
        return pane;
    }

    public static void addAnimation(String s, Image image) {
        if (!animationDatabase.containsKey(s))
            animationDatabase.put(s, new ArrayList<>());
        animationDatabase.get(s).add(image);
    }

    public static List<Image> getAnimation(String s) {
        return animationDatabase.get(s);
    }
}
