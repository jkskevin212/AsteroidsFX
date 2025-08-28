package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.IEnemy;
import dk.sdu.mmmi.cbse.common.data.ILifeEntity;

public class Enemy extends Entity implements IEnemy {
    private int life = 3;

    public int getLife() {return life;}
    public void hit() {life--;}
}
