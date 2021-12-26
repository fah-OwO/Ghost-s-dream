package logic;

import util.Triple;

import static util.Util.*;

public class Cloud extends GameObject implements Cloneable {
    @Override
    protected void spawnFront() {
        if (rand.nextBoolean()) spawnLeft();
        spawnRight();
    }

    @Override
    public void move(Triple v) {
    }

    @Override
    public void deploy() {
        super.deploy();
        pos.y = rand.randomBetween(pos.z / 2, height);
        co = pos2coordinate(pos);
    }

    @Override
    public double getZ() {
        return -super.getZ();
    }

    @Override
    public Cloud clone() {
        return (Cloud) super.clone();
    }
}
