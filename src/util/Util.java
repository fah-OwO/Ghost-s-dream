package util;

import application.ThreadMain;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import logic.GameObject;
import logic.Player;

import java.io.File;
import java.util.Comparator;
import java.util.Objects;

public class Util {
    public static final double k = 1;
    public static final String title = "";
    public static final int width = 1920;
    public static final int height = 1000;
    public static final double tanTheta = Math.tan(Player.getPerspectiveRadians());
    public static final double metrePerPixels = (double) 2 / height;
    public static final int frames = 50;//100;
    public static final long refreshPeriod = (1000 / (frames));
    public static final CustomRandom rand = new CustomRandom();
    public final static Comparator<GameObject> objComparator = Comparator.comparing(GameObject::getZ);

    public static void initiate() {
        for (int i = 1; i <= 4; i++) {
            MediaData.addTree("file:res/image/tree/3"+i+".png");//https://www.cgchan.com/cantree

        }
//        File folder=new File("res/image/tree");
//        for (final File file : Objects.requireNonNull(folder.listFiles())) {
//            if(!file.isDirectory())MediaData.addTree(file.getAbsolutePath());
//        }
//        MediaData.addTree("file:res/image/Tree1.png");
    }

    public static Triple pos2coordinate(Triple pos) {
        Triple realPos = new Triple();
        realPos.z = Math.sqrt(1 + Math.pow(tanTheta * (pos.x) / width, 2)) * k / pos.z;
        realPos.y = pos.y;
        realPos.x = realPos.z * tanTheta * (pos.x) / width;
        return realPos;
    }

    public static Triple coordinate2screenPos(Triple co) {
        Triple screenPos = new Triple();
        screenPos.x = (co.x * (double) width) / (co.z * tanTheta);
        screenPos.y = co.y;
        screenPos.z = k / Math.sqrt(co.x * co.x + co.z * co.z);
        return screenPos;
    }

    public static double convertZ(double z){return k/z;}//convert between pos and co

    public static double coordToMetre(double z) {
        return z * height;
    }

    public static double metreToCoord(double z) {
        return z / height;
    }

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


    public static void delay(long l) {
        try {
            Thread.sleep(l);
        } catch (InterruptedException ignored) {
        }
    }
}
