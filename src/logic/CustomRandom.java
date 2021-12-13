package logic;

import java.util.Random;

public class CustomRandom extends Random {
    public double randomBetween(double l, double r) {
//        double l= (double) leftNum;
//        double r= (double) rightNum;
//        System.out.println(r == l ? nextDouble(r - l) + l : l);
        return r == l ? l : nextDouble(r - l) + l;
    }

    public double mGraphProbabilityRandom(double w) {
//        double w= (double) width;
        w /= 4;
        return (nextDouble(w) + nextDouble(w)) * (nextBoolean() ? 1 : -1);
    }
}
