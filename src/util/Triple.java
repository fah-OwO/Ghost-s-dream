package util;

public class Triple implements Cloneable {
    private static final double lowNum = 0.001;
    public double x;
    public double y;
    public double z;

    public Triple() {
        x = y = z = 0;
    }

    public Triple(double i) {
        x = y = z = i;
    }

    public Triple(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    public double getSize() {
        return Math.sqrt(x * x + y * y + z * z);
    }

//    public Triple resizeTo(double size) {
//        return getSize() != 0 ? this.mul(size / getSize()) : new Triple(0);
//    }

    public void near0() {
        if (x < lowNum && x > -lowNum) x = 0;
        if (y < lowNum && y > -lowNum) y = 0;
        if (z < lowNum && z > -lowNum) z = 0;
    }

//    public boolean isBetween(Triple bottom_left, Triple top_right) {
//        return !(x < bottom_left.x) && !(y < bottom_left.y) && !(z < bottom_left.z) && !(x > top_right.x) && !(y > top_right.y) && !(z > top_right.z);
//    }

    public boolean is0() {
        return x == 0 && y == 0 && z == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Triple triple = (Triple) o;

        if (Double.compare(triple.x, x) != 0) return false;
        if (Double.compare(triple.y, y) != 0) return false;
        return Double.compare(triple.z, z) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
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

    @Override
    public Triple clone() {
        try {
            return (Triple) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
