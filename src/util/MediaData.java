package util;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

import java.util.*;

import static util.Util.*;

public class MediaData {

    public static final AudioClip walkingSound = new AudioClip("file:res/sound/walkingSound.mp3");//https://nofilmschool.com/best-royalty-free-sound-effects//https://gamesounds.xyz/?dir=BBC%20Sound%20Effects%20Library/BBC%2055%20-%20Footsteps%201
    public static final Image BLACK = new Image("file:res/image/Background.png");
    public static final Image EVENING = new Image("file:res/image/EveningBg.jpg", width, 9999999999D, true, true);
    public static final Image TRAP = new Image("file:res/image/Trap.png");//<a href='https://www.freepik.com/vectors/cartoon'>Cartoon vector created by pch.vector - www.freepik.com</a>
    private static final Map<String, Image> imageDatabase = new HashMap<>();
    private static final List<String> tree = new ArrayList<>();//Map<String, Set<String>> dataSet=new HashMap<>();

    public static Image getImage(String url) {
        if (!imageDatabase.containsKey(url)) {
            Image image = new Image(url);
            Image image1 = new Image(url, width, 0, true, true);
            if (image1.getHeight() < image.getHeight()) image = image1;
            imageDatabase.put(url, image);
        }
        return imageDatabase.get(url);
    }

    public static void addTree(String url) {
        if (!tree.contains(url)) tree.add(url);
    }

    public static Image getTree() {
        String url = tree.get(rand.nextInt(tree.size()));
        return getImage(url);
    }

}
