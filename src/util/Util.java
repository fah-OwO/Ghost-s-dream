package util;

import logic.GameObject;
import logic.Player;

import java.util.Comparator;

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
            GameObject tree = new GameObject();
            tree.setImage("file:res/image/tree/3" + i + ".png", 4);
            MediaData.addDecoration(tree);//https://www.cgchan.com/cantree

        }
        GameObject bush = new GameObject();
        bush.setImage("file:res/image/bush.png", 1);
        MediaData.addDecoration(bush);//https://www.cgchan.com/cantree
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

    public static double convertZ(double z) {
        return k / z;
    }//convert between pos and co

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

    public static double getCoDistance(Triple co1, Triple co2) {
        return co1.add(co2.mul(-1)).getSize();
    }
}
