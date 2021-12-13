package logic;

import java.util.Random;

public class Triple {
    public double x;
    public double y;
    public double z;
    private static final CustomRandom random = new CustomRandom();
    private static final double lowNum = 0.001;

    public Triple() {
        x = y = z = 0;
    }

    public Triple(double i) {
        x = y = z = i;
    }

    public Triple(double x, double y, double z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    public void set(double x, double y, double z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    public Triple add(Triple t) {
        Triple newTriple = new Triple(x, y, z);
        newTriple.x += t.x;
        newTriple.y += t.y;
        newTriple.z += t.z;
        return newTriple;
    }

    public Triple mul(Triple t) {
        Triple newTriple = new Triple(x, y, z);
        newTriple.x *= t.x;
        newTriple.y *= t.y;
        newTriple.z *= t.z;
        return newTriple;
    }

    public Triple mul(double d) {
        Triple newTriple = new Triple(x, y, z);
        newTriple.x *= d;
        newTriple.y *= d;
        newTriple.z *= d;
        return newTriple;
    }

    public void near0() {
        if (x < lowNum && x > -lowNum) x = 0;
        if (y < lowNum && y > -lowNum) y = 0;
        if (z < lowNum && z > -lowNum) z = 0;


    }

    boolean isBetween(Triple buttom_left, Triple top_right) {
        return !(x < buttom_left.x) && !(y < buttom_left.y) && !(z < buttom_left.z) && !(x > top_right.x) && !(y > top_right.y) && !(z > top_right.z);
//        if (x < buttom_left.x || y < buttom_left.y || z < buttom_left.z) return false;
//        if (x > top_right.x || y > top_right.y || z > top_right.z) return false;
//        return true;
    }

    public static Triple randomBetween(Triple l, Triple r) {//this is same as upper but l and r is more readable
        Triple newTriple = new Triple();
        newTriple.x = (double) random.randomBetween(l.x, r.x);
        newTriple.y = (double) random.randomBetween(l.y, r.y);
        newTriple.z = (double) random.randomBetween(l.z, r.z);
        return newTriple;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Triple triple = (Triple) o;

        if (Double.compare(triple.getX(), getX()) != 0) return false;
        if (Double.compare(triple.getY(), getY()) != 0) return false;
        return Double.compare(triple.getZ(), getZ()) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(getX());
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getY());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getZ());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "(" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ')';
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
