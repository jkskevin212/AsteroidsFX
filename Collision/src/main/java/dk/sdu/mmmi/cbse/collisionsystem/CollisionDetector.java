package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.ILifeEntity;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.enemysystem.Enemy;
import dk.sdu.mmmi.cbse.playersystem.Player;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

import java.util.ServiceLoader;

public class CollisionDetector implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity1 : world.getEntities()) {
            for (Entity entity2 : world.getEntities()) {
                if (entity1.getID().equals(entity2.getID())) continue;


                if (entity1 instanceof Asteroid && entity2 instanceof Asteroid) continue;


                if ((entity1 instanceof Enemy && entity2 instanceof Asteroid) ||
                        (entity1 instanceof Asteroid && entity2 instanceof Enemy)) continue;

                if (collides(entity1, entity2)) {


                    if (entity1 instanceof Asteroid && entity2 instanceof Bullet) {
                        Bullet b = (Bullet) entity2;
                        if ("player".equals(b.getOwner())) {
                            splitAsteroid(entity1, world);
                            world.removeEntity(entity2);
                            continue;
                        }
                    }

                    if (entity2 instanceof Asteroid && entity1 instanceof Bullet) {
                        Bullet b = (Bullet) entity1;
                        if ("player".equals(b.getOwner())) {
                            splitAsteroid(entity2, world);
                            world.removeEntity(entity1);
                            continue;
                        }
                    }


                    if (entity1 instanceof Enemy && entity2 instanceof Bullet) {
                        Bullet b = (Bullet) entity2;
                        if ("player".equals(b.getOwner())) {
                            reduceLife(entity1, world);
                            world.removeEntity(entity2);
                            continue;
                        }
                    }

                    if (entity2 instanceof Enemy && entity1 instanceof Bullet) {
                        Bullet b = (Bullet) entity1;
                        if (!"player".equals(b.getOwner())) {
                            reduceLife(entity2, world);
                            world.removeEntity(entity1);
                            continue;
                        }
                    }


                    if ((entity1 instanceof Enemy && entity2 instanceof Player) ||
                            (entity2 instanceof Enemy && entity1 instanceof Player)) {
                        reduceLife(entity1, world);
                        reduceLife(entity2, world);
                        continue;
                    }


                    if (entity1 instanceof Player && entity2 instanceof Asteroid) {
                        reduceLife(entity1, world);
                        continue;
                    }

                    if (entity2 instanceof Player && entity1 instanceof Asteroid) {
                        reduceLife(entity2, world);
                    }
                }
            }
        }
    }

    private void splitAsteroid(Entity asteroid, World world) {
        for (IAsteroidSplitter splitter : ServiceLoader.load(IAsteroidSplitter.class)) {
            splitter.createSplitAsteroid(asteroid, world);
        }
    }

    private void reduceLife(Entity entity, World world) {
        if (entity instanceof ILifeEntity) {
            ILifeEntity lifeEntity = (ILifeEntity) entity;
            lifeEntity.hit();
            if (lifeEntity.getLife() <= 0) {
                world.removeEntity(entity);
            }
        }
    }

    private boolean collides(Entity entity1, Entity entity2) {
        float dx = (float) entity1.getX() - (float) entity2.getX();
        float dy = (float) entity1.getY() - (float) entity2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }
}
