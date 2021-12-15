package util;

import javafx.scene.image.Image;

import java.util.*;

import static util.Util.*;

public class MediaData {

    public static final Image BLACK = new Image("file:res/image/Background.png");
    private static final Map<String, Image> imageDatabase = new HashMap<>();
    private static final List<String> tree = new ArrayList<>();//Map<String, Set<String>> dataSet=new HashMap<>();

    public static Image getImage(String url) {
        if (!imageDatabase.containsKey(url)) imageDatabase.put(url, new Image(url, width, 0, true, true));
        return imageDatabase.get(url);
    }

    public static void addTree(String url){
        if(!tree.contains(url))tree.add(url);
    }
    public static Image getTree() {
        String url = tree.get(rand.nextInt(tree.size()));
        return getImage(url);
    }

}
