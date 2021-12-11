package logic;

public class Triple {
    int x,y,z;
    public Triple(){
        x=y=z=0;
    }
    public Triple(int x,int y,int z){
        setX(x);
        setY(y);
        setZ(z);
    }
    public void set(int x,int y,int z){
        setX(x);
        setY(y);
        setZ(z);
    }
    public Triple add(Triple t){
        Triple nt=new Triple(x,y,z);
        nt.x+=t.x;
        nt.y+=t.y;
        nt.z+=t.z;
        return nt;
    }
    public Triple mul(Triple t){
        Triple nt=new Triple(x,y,z);
        nt.x*=t.x;
        nt.y*=t.y;
        nt.z*=t.z;
        return nt;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
