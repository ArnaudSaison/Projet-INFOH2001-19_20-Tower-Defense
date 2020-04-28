package towerdefense.game.projectiles;

public class Glue extends Projectile{

    public Glue(int damage) {
        super();
        super.damage = damage;
    }
    @Override
    public String toString(){
        return getClass().getName() +super.toString();
    }
}
