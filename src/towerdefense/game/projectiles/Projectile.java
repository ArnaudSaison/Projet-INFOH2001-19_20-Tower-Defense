package towerdefense.game.projectiles;
import towerdefense.game.Drawable;
import towerdefense.game.Movable;

public abstract class Projectile implements Drawable, Movable, Runnable {
    protected int damage;
    protected Thread tProjectile;

    public Projectile(){
        tProjectile = new Thread(this);
        tProjectile.start();
    }

    public int getDamage(){return damage;}

    @Override
    public void move(){}

    @Override
    public void updateDrawing(){}

    @Override
    public void run(){ //TODO: à définir
    }
    @Override
    public String toString(){
        return (" : thread lancé.");
    }
}
