package towerdefense.game.projectiles;

public class Glue extends Projectile{
    private int damage;

    public Glue(int damage){
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}
