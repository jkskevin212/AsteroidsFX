package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
public class EnemyPlugin implements IGamePluginService {

    private Entity enemy;

    public EnemyPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {


        enemy = spawnEnemy(gameData);
        world.addEntity(enemy);
    }

    private Entity spawnEnemy(GameData gameData) {

        Entity enemy = new Enemy();
        enemy.setPolygonCoordinates(-10,0,0,5,10,15,10,5,5,0,10,-5,10,-15,0,-5);
        enemy.setX(gameData.getDisplayWidth());
        enemy.setY(Math.random()*gameData.getDisplayHeight());
        enemy.setRadius(8);
        return enemy;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        for (Entity e : world.getEntities()) {
            if (e.getClass() == Enemy.class) {
                world.removeEntity(e);
            }
        }
    }

}