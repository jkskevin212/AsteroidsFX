package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.Random;

public class AsteroidPlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
        for (int i = 0; i < 10; i++) {
            Entity asteroid = createAsteroid(gameData);
            world.addEntity(asteroid);
        }
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            world.removeEntity(asteroid);
        }
    }

    private Entity createAsteroid(GameData gameData) {
        Random rnd = new Random();
        int size = rnd.nextInt(10) + 20;
        Asteroid asteroid = new Asteroid(2);
        asteroid.setPolygonCoordinates(buildAsteroid(size));
        int corner = rnd.nextInt(4);
        switch (corner) {
            case 0 -> { asteroid.setX(0); asteroid.setY(0); }
            case 1 -> { asteroid.setX(gameData.getDisplayWidth()); asteroid.setY(0); }
            case 2 -> { asteroid.setX(0); asteroid.setY(gameData.getDisplayHeight()); }
            case 3 -> { asteroid.setX(gameData.getDisplayWidth()); asteroid.setY(gameData.getDisplayHeight()); }
        }
        asteroid.setRadius(size);
        asteroid.setRotation(rnd.nextInt(360));
        return asteroid;
    }

    private double[] buildAsteroid(double radius) {
        Random rnd = new Random();
        int points = 8;
        double[] poly = new double[points * 2];
        for (int i = 0; i < points; i++) {
            double angle = (Math.PI * 2 * i) / points;
            double r = radius * (0.8 + rnd.nextDouble() * 0.4);
            poly[i * 2] = Math.cos(angle) * r;
            poly[i * 2 + 1] = Math.sin(angle) * r;
        }
        return poly;
    }
}
