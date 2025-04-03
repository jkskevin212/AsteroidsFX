import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Collision {
    requires Enemy;
    requires Asteroid;
    requires CommonAsteroids;
    requires Player;
    requires CommonBullet;
    requires Common;
    provides IPostEntityProcessingService with dk.sdu.mmmi.cbse.collisionsystem.CollisionDetector;
    uses dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;


}