package util;

import javafx.scene.text.Font;
import logic.GameObject;
import logic.Player;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Util {
    public static final double k = 1e6;
    public static final String title = "Ghost's dream";
    public static final int width = 1920;
    public static final int height = 1000;
    public static final double tanTheta = Math.tan(Player.getPerspectiveRadians());
    public static final double cosTheta = Math.cos(Player.getPerspectiveRadians());
    public static final double sinTheta = Math.sin(Player.getPerspectiveRadians());
    public static final double metrePerPixels = (double) 2 / height;
    public static final int frames = 50;
    public static final long refreshPeriod = (1000 / (frames));
    public static final Font font = new Font(72);
    public static final CustomRandom rand = new CustomRandom();
    public final static Comparator<GameObject> objComparator = Comparator.comparing(GameObject::getZ);
    private static final Map<Double, Double> sin = new HashMap<>();
    private static final Map<Double, Double> cos = new HashMap<>();
//    private static final Map<Double, Double> tan = new HashMap<>();

    public static void initiate() {
//        for (double i = -360; i < 360; i += 0.001) {
//            sin.put(i, Math.sin(Math.toRadians(i)));
//            cos.put(i, Math.cos(Math.toRadians(i)));
//            tan.put(i, Math.tan(Math.toRadians(i)));
//        }
    }

    public static double sin(double i) {
        return sin.get(i % 360);
    }

    public static double cos(double i) {
        return cos.get(i % 360);
    }

    public static void clearSinCos(){
        sin.clear();
        cos.clear();
    }

//    public static double tan(double i) {
//        return tan.get(i % 360);
//    }

    public static Triple rotateAaroundB(Triple a, Triple b, double degree) {
        double x = b.x * (b.x * a.x + b.y * a.y + b.z * a.z) * (1d - cos(degree)) + a.x * cos(degree) + (-b.z * a.y + b.y * a.z) * sin(degree);
        double y = b.y * (b.x * a.x + b.y * a.y + b.z * a.z) * (1d - cos(degree)) + a.y * cos(degree) + (b.z * a.x - b.x * a.z) * sin(degree);
        double z = b.z * (b.x * a.x + b.y * a.y + b.z * a.z) * (1d - cos(degree)) + a.z * cos(degree) + (-b.y * a.x + b.x * a.y) * sin(degree);
        return new Triple(x, y, z);
    }

    public static Triple rotateAaroundY(Triple a, double degree) {
        if (!sin.containsKey(degree)) {
            double r = Math.toRadians(degree);
            sin.put(degree, Math.sin(r));
            cos.put(degree, Math.cos(r));
        }
        double x = a.x * cos(degree) + a.z * sin(degree);
        double y = a.y * (1d - cos(degree)) + a.y * cos(degree);
        double z = a.z * cos(degree) + a.x * sin(degree);
        return new Triple(x, y, z);
    }


    public static Triple pos2coordinate(Triple pos) {
        Triple realPos = new Triple();
        realPos.z = Math.sqrt(1 + Math.pow(tanTheta * (pos.x) / width, 2)) * k / pos.z;
        realPos.y = pos.y / pos.z;
        realPos.x = realPos.z * tanTheta * (pos.x) / width;
        return realPos;
    }

    public static Triple coordinate2screenPos(Triple co) {
        Triple screenPos = new Triple();
        screenPos.x = (co.x * (double) width) / (co.z * tanTheta);
        screenPos.z = k / Math.sqrt(co.x * co.x + co.z * co.z);
        screenPos.y = co.y * screenPos.z;
        return screenPos;
    }

    public static double convertZ(double z) {
        return k / z;
    }//convert between pos and co

    public static double coordToMetre(double z) {
        return z * height / k;
    }

    public static double metreToCoord(double z) {
        return z * k / height;
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
