package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IEntityProcessingService {

    /**
     * this runs all frames for updating entities, like shooting and movement.
     *
     *
     */
    void process(GameData gameData, World world);
}
