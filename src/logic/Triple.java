package logic;

import java.util.Random;

public class Triple {
    public double x;
    public double y;
    public double z;
    static Random random = new Random();

    public Triple() {
        x = y = z = 0;
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

    boolean isBetween(Triple buttom_left, Triple top_right) {
        return !(x < buttom_left.x) && !(y < buttom_left.y) && !(z < buttom_left.z) && !(x > top_right.x) && !(y > top_right.y) && !(z > top_right.z);
//        if (x < buttom_left.x || y < buttom_left.y || z < buttom_left.z) return false;
//        if (x > top_right.x || y > top_right.y || z > top_right.z) return false;
//        return true;
    }

    Triple randomBetween(Triple l, Triple r) {//this is same as upper but l and r is more readable
        Triple newTriple = new Triple(x, y, z);
        newTriple.x = random.nextDouble(r.x - l.x + 1) + l.x;
        newTriple.y = random.nextDouble(r.y - l.y + 1) + l.y;
        newTriple.z = random.nextDouble(r.z - l.z + 1) + l.z;
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
