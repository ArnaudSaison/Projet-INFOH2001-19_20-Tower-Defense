package towerdefense.game.projectiles;

public class Bullet extends Projectile{

    public Bullet(int damage) {
        super();
        super.damage = damage;
    }

    @Override
    public String toString(){
        return getClass().getName() +super.toString();
    }
}
