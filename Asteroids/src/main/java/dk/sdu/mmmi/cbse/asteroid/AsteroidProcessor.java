package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class AsteroidProcessor implements IEntityProcessingService {
    private IAsteroidSplitter asteroidSplitter = new AsteroidSplitterImpl();

    @Override
    public void process(GameData gameData, World world) {
        for (Entity e : world.getEntities(Asteroid.class)) {
            Asteroid asteroid = (Asteroid) e;


            double dx = asteroid.getDx();
            double dy = asteroid.getDy();

            if (dx == 0 && dy == 0) {
                dx = Math.cos(Math.toRadians(asteroid.getRotation())) * 0.5;
                dy = Math.sin(Math.toRadians(asteroid.getRotation())) * 0.5;
            }

            asteroid.setX(asteroid.getX() + dx);
            asteroid.setY(asteroid.getY() + dy);


            if (asteroid.getX() < 0) asteroid.setX(asteroid.getX() + gameData.getDisplayWidth());
            if (asteroid.getX() > gameData.getDisplayWidth()) asteroid.setX(asteroid.getX() % gameData.getDisplayWidth());
            if (asteroid.getY() < 0) asteroid.setY(asteroid.getY() + gameData.getDisplayHeight());
            if (asteroid.getY() > gameData.getDisplayHeight()) asteroid.setY(asteroid.getY() % gameData.getDisplayHeight());
        }
    }

    /**
     * Dependency Injection using OSGi Declarative Services
     */
    public void setAsteroidSplitter(IAsteroidSplitter asteroidSplitter) {
        this.asteroidSplitter = asteroidSplitter;
    }

    public void removeAsteroidSplitter(IAsteroidSplitter asteroidSplitter) {
        this.asteroidSplitter = null;
    }
}
