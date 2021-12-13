package logic;

import java.util.Random;

public class CustomRandom extends Random {
    public double randomBetween(double l, double r) {
        return r == l ? l : nextDouble(r - l) + l;
    }

    public double mGraphProbabilityRandom(double w) {
        w /= 4;
        return (nextDouble(w) + nextDouble(w)) * (nextBoolean() ? 1 : -1);
    }
}
