package util;

import application.ThreadMain;
import javafx.scene.image.Image;
import logic.Player;

public class Util {
    public static ThreadMain threadMain = new ThreadMain();
    public static Player player = Player.getInstance();
    public static double k = 1;
    public static String title = "";
    public static int width = 1920;
    public static int height = 1000;
    public static double tanTheta = Math.tan(Player.getPerspectiveRadians());
    public static double metrePerPixels = (double) 1 / height;
    public static final int frames = 50;
    public static Image TREE = new Image("file:res/image/Tree1.png");

    public static Triple pos2coordinate(Triple pos) {
        Triple realPos = new Triple();
        realPos.z = Math.sqrt(1 + Math.pow(tanTheta * (pos.getX()) / width, 2)) * k / pos.getZ();
        realPos.y = pos.getY();
        realPos.x = realPos.z * tanTheta * (pos.getX()) / width;
        return realPos;
    }

    public static Triple coordinate2screenPos(Triple co) {
        double w = width;
        Triple screenPos = new Triple();
        screenPos.x = (co.x * w) / (co.z * tanTheta);
        screenPos.y = co.y;
        screenPos.z = k / Math.sqrt(co.x * co.x + co.z * co.z);
        return screenPos;
    }


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

    public static Triple getPlayerV() {
        return player.getSpeed();
    }

    public static void accelerate(double x, double z) {
        player.accX(x);
        player.accZ(z);
    }
}
