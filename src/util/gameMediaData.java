package util;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import logic.GameObject;
import logic.QuestObject;

import java.util.*;

import static util.Util.*;

public class gameMediaData {

    private static final Map<String, Image> imageDatabase = new HashMap<>();
    private static final Map<String, GameObject> gameObjectMap = new HashMap<>();
    private static final Map<String, List<GameObject>> decoration = new HashMap<>();//Map<String, Set<String>> dataSet=new HashMap<>();

    public static final AudioClip walkingSound = new AudioClip("file:res/sound/walkingSound.mp3");//https://nofilmschool.com/best-royalty-free-sound-effects//https://gamesounds.xyz/?dir=BBC%20Sound%20Effects%20Library/BBC%2055%20-%20Footsteps%201
    public static final Image BLACK = new Image("file:res/image/Background.png", 1, 1, false, false);
    public static final Image EVENING = new Image("file:res/image/EveningBg.jpg", width, 9999999999D, true, true);
    public static final Image TRAP = new Image("file:res/image/Trap.png");//<a href='https://www.freepik.com/vectors/cartoon'>Cartoon vector created by pch.vector - www.freepik.com</a>
    public static final Image SQUID = getImage("file:res/image/sq43.png");

    public static void initiate() {
        for (int i = 1; i <= 4; i++) {
            GameObject tree = new GameObject();
            tree.setImage("file:res/image/tree/3" + i + ".png", 4);
            gameMediaData.addDecoration("tree", tree);//https://www.cgchan.com/cantree
            gameMediaData.addDecoration("forest", tree);
        }
        GameObject bush = new GameObject();
        bush.setImage("file:res/image/bush.png", 1);
        gameMediaData.addDecoration("bush", bush);//https://www.cgchan.com/cantree
        gameMediaData.addDecoration("forest", bush);

        QuestObject trap = new QuestObject();
        trap.setImage(gameMediaData.TRAP, 0.2);//trap.setObjectHeight(0.2);
        trap.setPassive(true);
        trap.setActiveRange(0.5);
        trap.spawnAnywhere();
        gameObjectMap.put("trap", trap);
    }

    public static Image getImage(String url) {
        if (!imageDatabase.containsKey(url)) {
            Image image = new Image(url);
            Image image1 = new Image(url, width, 0, true, true);
            if (image1.getHeight() < image.getHeight()) image = image1;
            imageDatabase.put(url, image);
        }
        return imageDatabase.get(url);
    }

    public static GameObject getGameObject(String name) {
        return gameObjectMap.get(name);
    }

    public static void addDecoration(String decorationType, GameObject gameObject) {
        if (!decoration.containsKey(decorationType)) decoration.put(decorationType, new ArrayList<>());
        decoration.get(decorationType).add(gameObject);
    }

    public static List<GameObject> getDecoration(String decorationType) {
        return decoration.get(decorationType);
//        GameObject gameObject = decoration.get(rand.nextInt(decoration.size()));
//        return gameObject.clone();
    }

    public static GameObject getRandomDecoration(String decorationType) {
        List<GameObject> decorationList = getDecoration(decorationType);
        return decorationList.get(rand.nextInt(decorationList.size())).clone();

    }

}
