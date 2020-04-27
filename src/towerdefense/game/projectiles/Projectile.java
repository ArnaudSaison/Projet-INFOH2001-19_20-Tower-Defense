package towerdefense.game.projectiles;
import towerdefense.game.Drawable;
import towerdefense.game.Movable;

public abstract class Projectile implements Drawable, Movable {
    protected int damage;

    public Projectile(){}

    public int getDamage(){return damage;}

    @Override
    public void move(){}

    @Override
    public void updateDrawing(){}
}
