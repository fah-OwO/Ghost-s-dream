package util;

import java.util.Random;

public class CustomRandom extends Random {
    public double randomBetween(double l, double r) {
        return r == l ? l : Math.random() * (r - l) + l;//nextDouble(r - l) + l;
    }

}
