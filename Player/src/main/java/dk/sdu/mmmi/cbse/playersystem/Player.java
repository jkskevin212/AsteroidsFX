package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.ILifeEntity;
import dk.sdu.mmmi.cbse.common.data.IPlayer;


public class Player extends Entity implements IPlayer {
    private int life = 3;

    public int getLife() {return life;}
    public void hit() {life--;}


}
