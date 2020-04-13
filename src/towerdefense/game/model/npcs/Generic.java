package towerdefense.game.model.npcs;

import towerdefense.game.model.position.Path;

public class Generic extends NPC {

    public Generic(){
        Path path = new Path();
        super.health = 40;
    }
}
