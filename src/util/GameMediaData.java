package util;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import logic.GameObject;
import logic.QuestObject;

import java.util.*;

import static util.Util.*;

public class GameMediaData {

    public static final AudioClip walkingSound = new AudioClip(getRes("file:res/sound/walkingSound.mp3"));// https://nofilmschool.com/best-royalty-free-sound-effects//https://gamesounds.xyz/?dir=BBC%20Sound%20Effects%20Library/BBC%2055%20-%20Footsteps%201
    public static final Image BLACK = new Image(getRes("file:res/image/Background.png"), 1, 1, false, false);
    public static final Image EVENING = new Image(getRes("file:res/image/EveningBg.jpg"), width, 9999999999D, true, true);
    private static final Map<String, Image> imageDatabase = new HashMap<>();
    public static final Image TRAP = getImage("file:res/image/Trap.png");
    public static final Image SQUID = getImage("file:res/image/sq43.png");
    private static final Map<String, GameObject> gameObjectMap = new HashMap<>();
    private static final Map<String, List<GameObject>> decoration = new HashMap<>();

    private static String getRes(String s) {
        return Objects.requireNonNull(GameMediaData.class.getClassLoader().getResource(s.replace("file:", ""))).toString();
    }

    public static void initiate() {
        for (int i = 1; i <= 4; i++) {
            GameObject tree = new GameObject();
            tree.setImage("file:res/image/tree/3" + i + ".png", 4);
            GameMediaData.addDecoration("tree", tree);// https://www.cgchan.com/cantree
            GameMediaData.addDecoration("forest", tree);
        }
        GameObject bush = new GameObject();
        bush.setImage("file:res/image/bush.png", 1);
        GameMediaData.addDecoration("bush", bush);// https://www.cgchan.com/cantree
        GameMediaData.addDecoration("forest", bush);

        QuestObject trap = new QuestObject();
        trap.setImage(GameMediaData.TRAP, 0.5);// trap.setObjectHeight(0.2);
        trap.setPassive(true);
        trap.setActiveRange(1);
        trap.spawnAnywhere();
        GameMediaData.addDecoration("trap", trap);// https://www.cgchan.com/cantree
        gameObjectMap.put("trap", trap);
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

    public static GameObject getGameObject(String name) {
        return gameObjectMap.get(name);
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
        text.setFont(new Font(height / 3.0));
        return pane;
    }

}
