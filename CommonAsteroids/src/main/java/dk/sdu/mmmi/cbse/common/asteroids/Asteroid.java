package dk.sdu.mmmi.cbse.common.asteroids;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class Asteroid extends Entity {
    private int size;
    private double dx = 0;
    private double dy = 0;

    public Asteroid(int size) {
        this.size = size;
        setRadius(size * 10);
    }

    public int getSize() {
        return size;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }
}
