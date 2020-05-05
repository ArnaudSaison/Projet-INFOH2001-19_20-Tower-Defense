package towerdefense.game.projectiles;

import towerdefense.game.Drawable;
import towerdefense.game.Movable;
import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;

public abstract class Projectile implements Runnable, Drawable, Movable {
    private int damage;

    private Position position;
    private Map map;
    private GameModel gameModel;
    private Thread tProjectile;


    public Projectile(Map map, GameModel gameModel, int damage){
        this.damage = damage;
        position = new Position(map);

        this.map = map;
        this.gameModel = gameModel;
        tProjectile = new Thread();
        tProjectile.start();
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void move(){}

    @Override
    public void run(){}
}
