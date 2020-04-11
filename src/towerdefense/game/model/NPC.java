package towerdefense.game.model;

import towerdefense.game.map.Position;

public abstract class NPC implements Hittable{
    protected Position position;
    protected int health;
    private int loot;
    protected boolean detected;
    //private ArrayList<Weapon> weapons; (optionnel)

    public NPC (){
        health =30;
        loot = 1;
    }

    //******Getteurs******

    public int getHealth(){return health;}

    public boolean getDetected(){return detected;}

    public int getLoot(){return loot;}

    //******Autres******

    public void hit(int damage){
        if (health > damage){
            health -= damage;
        }else{health = 0;
        }
    }

    public void kill(NPC npc){npc = null;}

    public void isDetected(Tower tower){
        if (position.getDistance(tower.getTowerPosition()) < tower.getRange()){
            detected = true;
        }
    }
}
