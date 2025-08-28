package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class enemySpawnTest {

    @Test
    void spawnEnemy() throws Exception {
        GameData gd = new GameData();
        EnemyPlugin ecs = new EnemyPlugin();

        Method m = EnemyPlugin.class.getDeclaredMethod("spawnEnemy", GameData.class);
        m.setAccessible(true);
        Entity e = (Entity) m.invoke(ecs, gd);

        assertEquals(gd.getDisplayWidth(), e.getX(), 1e-3);
        assertTrue(e.getY() >= 0 && e.getY() <= gd.getDisplayHeight());
        assertEquals(8f, e.getRadius(), 1e-3);
        assertEquals(16, e.getPolygonCoordinates().length);
    }
}

