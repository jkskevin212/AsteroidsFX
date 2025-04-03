package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

import java.util.Random;

public class AsteroidSplitterImpl implements IAsteroidSplitter {
    private static final Random random = new Random();

    @Override
    public void createSplitAsteroid(Entity e, World world) {
        if (!(e instanceof Asteroid)) return;
        Asteroid parent = (Asteroid) e;
        float parentRadius = parent.getRadius();
        int parentSize = parent.getSize();
        if (parentSize <= 1) {
            world.removeEntity(parent);
            return;
        }
        double px = parent.getX();
        double py = parent.getY();
        world.removeEntity(parent);
        int childSize = parentSize - 1;
        for (int i = 0; i < 2; i++) {
            Asteroid child = new Asteroid(childSize);
            float newRadius = Math.max(8f, parentRadius * 0.6f);
            child.setRadius(newRadius);
            child.setPolygonCoordinates(buildAsteroid(newRadius));
            double angle = Math.toRadians(random.nextInt(360));
            double offset = Math.max(6.0, parentRadius * 0.6);
            child.setX(px + Math.cos(angle) * offset);
            child.setY(py + Math.sin(angle) * offset);
            child.setRotation(random.nextInt(360));
            world.addEntity(child);
        }
    }

    private double[] buildAsteroid(double radius) {
        int points = 8;
        double[] poly = new double[points * 2];
        for (int i = 0; i < points; i++) {
            double angle = (Math.PI * 2 * i) / points;
            double r = radius * (0.8 + random.nextDouble() * 0.4);
            poly[i * 2] = Math.cos(angle) * r;
            poly[i * 2 + 1] = Math.sin(angle) * r;
        }
        return poly;
    }
}
