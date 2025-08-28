package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.*;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class CollisionDetector implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        List<Entity> es = new ArrayList<>(world.getEntities());
        int n = es.size();
        for (int i = 0; i < n; i++) {
            Entity a = es.get(i);
            for (int j = i + 1; j < n; j++) {
                Entity b = es.get(j);

                if (a instanceof Asteroid && b instanceof Asteroid) continue;
                if ((a instanceof IEnemy && b instanceof Asteroid) || (a instanceof Asteroid && b instanceof IEnemy)) continue;

                if (!collides(a, b)) continue;

                if (a instanceof Asteroid && b instanceof Bullet) {
                    Bullet blt = (Bullet) b;
                    if ("player".equals(blt.getOwner())) {
                        splitAsteroid(a, world);
                        world.removeEntity(b);
                    }
                    continue;
                }
                if (b instanceof Asteroid && a instanceof Bullet) {
                    Bullet blt = (Bullet) a;
                    if ("player".equals(blt.getOwner())) {
                        splitAsteroid(b, world);
                        world.removeEntity(a);
                    }
                    continue;
                }

                if (a instanceof IEnemy && b instanceof Bullet) {
                    Bullet blt = (Bullet) b;
                    if ("player".equals(blt.getOwner())) {
                        reduceLife(a, world);
                        world.removeEntity(b);
                    }
                    continue;
                }
                if (b instanceof IEnemy && a instanceof Bullet) {
                    Bullet blt = (Bullet) a;
                    if (!"player".equals(blt.getOwner())) {
                        reduceLife(b, world);
                        world.removeEntity(a);
                    }
                    continue;
                }

                if ((a instanceof IEnemy && b instanceof IPlayer) || (b instanceof IEnemy && a instanceof IPlayer)) {
                    reduceLife(a, world);
                    reduceLife(b, world);
                    continue;
                }

                if (a instanceof IPlayer && b instanceof Asteroid) {
                    // hvis du vil instant-død: world.removeEntity(a); else:
                    reduceLife(a, world);
                    continue;
                }
                if (b instanceof IPlayer && a instanceof Asteroid) {
                    // hvis du vil instant-død: world.removeEntity(b); else:
                    reduceLife(b, world);
                }
            }
        }
    }

    private void splitAsteroid(Entity asteroid, World world) {
        for (IAsteroidSplitter s : ServiceLoader.load(IAsteroidSplitter.class)) {
            s.createSplitAsteroid(asteroid, world);
        }
    }

    private void reduceLife(Entity e, World w) {
        if (e instanceof ILifeEntity le) {
            le.hit();
            if (le.getLife() <= 0) w.removeEntity(e);
        }
    }

    private boolean collides(Entity e1, Entity e2) {
        float dx = (float)(e1.getX() - e2.getX());
        float dy = (float)(e1.getY() - e2.getY());
        float dist = (float)Math.sqrt(dx*dx + dy*dy);
        return dist < (e1.getRadius() + e2.getRadius());
    }
}