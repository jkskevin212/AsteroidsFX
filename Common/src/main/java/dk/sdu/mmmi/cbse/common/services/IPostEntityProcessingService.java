package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * this handles collisions, and used after all entities is updated.
 *
 *
 */
public interface IPostEntityProcessingService {

    void process(GameData gameData, World world);
}
